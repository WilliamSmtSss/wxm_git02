package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.splan.base.bean.*;
import com.splan.base.enums.NoticeAction;
import com.splan.base.enums.Status;
import com.splan.bplan.http.*;
import com.splan.bplan.mappers.GameDataBeanMapper;
import com.splan.bplan.mapping.AreaMap;
import com.splan.bplan.mapping.GameScoreMap;
import com.splan.bplan.mapping.MessageMap;
import com.splan.bplan.mapping.TopicMap;
import com.splan.bplan.result.GameResult;
import com.splan.bplan.service.*;
import com.splan.bplan.thread.BetFinishCallable;
import com.splan.bplan.thread.BetReFinishCallable;
import com.splan.bplan.utils.BeanUtil;
import com.splan.bplan.utils.DateUtil;
import com.splan.bplan.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class SeriesGameServiceImpl implements ISeriesGameService {


    @Autowired
    private IGameDataService gameDataSerivce;

    @Autowired
    private IGameLeagueService gameLeagueService;

    @Autowired
    private IGameTeamService gameTeamService;

    @Autowired
    private IGameCampaignService gameCampaignService;

    @Autowired
    private IGameScoreService gameScoreService;

    @Autowired
    private IBetTopicService betTopicService;

    @Autowired
    private IBetOptionService betOptionService;

    @Autowired
    private IGameDataTeamService gameDataTeamService;

    @Autowired
    private INettyService nettyService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private IBetOrderService betOrderService;

    @Autowired
    private IGameService gameService;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IAnnouncementService announcementService;

    @Autowired
    private GameDataBeanMapper gameDataBeanMapper;

    /*@Autowired
    private IProxyConfigService proxyConfigService;*/

    @Autowired
    private IBetOrderDetailService betOrderDetailService;

    @Autowired
    private IBetExampleService betExampleService;


    private ExecutorService service = Executors.newFixedThreadPool(100);

    @Override
    //@Transactional(rollbackFor=RuntimeException.class)
    public String saveSeries(SeriesGameNotify seriesGameNotify, BaseGameNotify baseGameNotify,String method) {

        try{
            /***
             * data插入
             */
            GameDataBean gameDataBean =  new GameDataBean();
            BeanUtils.copyProperties(seriesGameNotify,gameDataBean);
            gameDataBean.setId(baseGameNotify.getId());
            gameDataBean.setLeagueId(seriesGameNotify.getLeague().getId());
            gameDataBean.setCreateTime(DateUtil.stringToDate(baseGameNotify.getTimestamp()));
            if (seriesGameNotify.getSeriesDetail()!=null){
                if (StringUtils.isNotBlank(seriesGameNotify.getSeriesDetail().getLiveUrl())){
                    String liveUrl = seriesGameNotify.getSeriesDetail().getLiveUrl();
                    if (liveUrl.contains("http://")){
                        liveUrl = liveUrl.replace("http","https");
                    }
                    gameDataBean.setLiveUrl(liveUrl);
                }
                gameDataBean.setShowBo(seriesGameNotify.getSeriesDetail().getBo());

            }
            gameDataSerivce.saveOrUpdate(gameDataBean);
            /**
             * 联赛插入
             */
            GameLeagueBean gameLeagueBean = new GameLeagueBean();
            BeanUtils.copyProperties(seriesGameNotify.getLeague(),gameLeagueBean);
            gameLeagueService.saveOrUpdate(gameLeagueBean);

            /**
             * 队伍插入
             */
            List<GameTeamBean> gameTeamBeanList = BeanUtil.copyList(seriesGameNotify.getTeams(),GameTeamBean.class);
            for (int i = 0; i < gameTeamBeanList.size(); i++) {
                gameTeamBeanList.get(i).setStatus(Status.ENABLE);
                if (gameTeamBeanList.get(i).getLogo().contains("http://")){
                    gameTeamBeanList.get(i).setLogo(gameTeamBeanList.get(i).getLogo().replace("http","https"));
                }
                gameTeamService.saveOrUpdate(gameTeamBeanList.get(i));
                /**
                 * 队伍关联
                 */

                GameDataTeamBean gameDataTeamBean = new GameDataTeamBean();
                gameDataTeamBean.setDataId(gameDataBean.getId());
                gameDataTeamBean.setTeamId(gameTeamBeanList.get(i).getId());


                QueryWrapper<GameDataTeamBean> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("data_id",gameDataBean.getId());
                queryWrapper.eq("team_id",gameTeamBeanList.get(i).getId());

                int x = gameDataTeamService.count(queryWrapper);
                if (x==0){
                    gameDataTeamService.saveOrUpdate(gameDataTeamBean);
                }

            }

            /**
             * 比赛场次插入
             */
            List<GameCampaignBean> gameCampaignBeanList = BeanUtil.copyList(seriesGameNotify.getCampaigns(),GameCampaignBean.class);
            for (int i = 0; i < gameCampaignBeanList.size(); i++) {
                gameCampaignBeanList.get(i).setDataId(baseGameNotify.getId());
                gameCampaignService.saveOrUpdate(gameCampaignBeanList.get(i));
            }

            /**
             * 队伍得分
             */
            List<GameScoreBean> gameScoreBeanList = BeanUtil.copyList(seriesGameNotify.getScores(),GameScoreBean.class);
            for (int i = 0; i < gameScoreBeanList.size(); i++) {
                gameScoreBeanList.get(i).setDataId(baseGameNotify.getId());
                QueryWrapper<GameScoreBean> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("data_id",gameScoreBeanList.get(i).getDataId());
                queryWrapper.eq("team_id",gameScoreBeanList.get(i).getTeamId());
                queryWrapper.eq("sequence",gameScoreBeanList.get(i).getSequence());
                //queryWrapper.eq("score",gameScoreBeanList.get(i).getScore());
                GameScoreBean gameScoreBean = gameScoreService.getOne(queryWrapper);
                if (gameScoreBean == null){
                    gameScoreService.save(gameScoreBeanList.get(i));
                }else {
                    if (gameScoreBean.getScore().intValue()!=gameScoreBeanList.get(i).getScore().intValue()){
                        gameScoreBean.setScore(gameScoreBeanList.get(i).getScore());
                        gameScoreService.updateById(gameScoreBean);
                    }
                }

            }

//            if (method.equals(HttpMethod.POST)){
                //新增通知
            //nettyService.broadCastMessageByGroup("default","data",JSON.toJSONString(seriesGameNotify));
            if (method.equals("POST")){
                nettyService.broadCastMessage("gameCreate-"+JSON.toJSONString(gameService.getGameDetailWebSocket(gameDataBean.getId())));
            }else if (method.equals("PUT") && (gameDataBean.getStatus().equals("ongoing") || gameDataBean.getStatus().equals("finished"))){
                nettyService.broadCastMessage("gameUpdate-"+JSON.toJSONString(gameService.getGameDetailWebSocket(gameDataBean.getId())));
            }

        }catch (RuntimeException e){
            log.error(e.toString());
            throw new RuntimeException();
        }
        /**
         * 通知页面
         */
        return "";
    }

    @Override
    public String saveTopic(BetGameNotify betGameNotify, BaseGameNotify baseGameNotify, String method) {
        try{
            BetTopicsBean betTopicsBean = new BetTopicsBean();
            BeanUtils.copyProperties(betGameNotify,betTopicsBean);
            betTopicsBean.setId(baseGameNotify.getId());

            GameCampaignBean gameCampaignBean = null;
            if (betTopicsBean.getTopicableType().equals("Campaign")){
                gameCampaignBean = gameCampaignService.getById(betTopicsBean.getTopicableId());
                if (gameCampaignBean!=null){
                    betTopicsBean.setDataId(gameCampaignBean.getDataId());
                }

            }else if (betTopicsBean.getTopicableType().equals("Series")){
                betTopicsBean.setDataId(betTopicsBean.getTopicableId());
            }


            betTopicsBean.setUpdateTime(new Date());
            betTopicsBean.setUserBetLimit(betGameNotify.getBetLimit().getUserBetLimit());
            betTopicsBean.setUserSingleBetProfitLimit(betGameNotify.getBetLimit().getUserSingleBetProfitLimit());
            betTopicsBean.setTopicHandicapProfitLimit(betGameNotify.getBetLimit().getHandicapProfitLimit());
            BetTopicsBean betTopicsBeanOld = betTopicService.getById(baseGameNotify.getId());
            GameResult gameResult = gameDataSerivce.selectGameResultWithTeamById(betTopicsBean.getDataId());
            Integer gameId = 0;
            if (gameResult.getGameId()<5){
                gameId = gameResult.getGameId();
            }
            BetExampleBean betExampleBean = betExampleService.selectBySupport(betTopicsBean.getTopicableType(),betTopicsBean.getCategory(),betTopicsBean.getSupport(),gameId);
            if (betExampleBean==null){
                log.info(betTopicsBean.getTopicableType()+"_"+betTopicsBean.getCategory()+"_"+betTopicsBean.getSupport()+"盘口暂未配置");
                return "";
            }
            String groupName = betExampleBean.getExample();
            /*if (betTopicsBean.getTopicableType().equals("Series") && betTopicsBean.getCategory().equals(5) && betTopicsBean.getSupport().equals(5)){
                if (betTopicsBean.getMarkValue()!=null && betTopicsBean.getMarkValue()==1){
                    groupName = "预选队伍";
                }else {
                    groupName = "直绕队伍";
                }

            }else if (betTopicsBean.getTopicableType().equals("Series") && betTopicsBean.getCategory().equals(5) && betTopicsBean.getSupport().equals(6)){
                if (betTopicsBean.getMarkValue()!=null){
                    groupName = TopicMap.get(betTopicsBean.getMarkValue().intValue());
                }else {
                    groupName = TopicMap.get(1);
                }

            }else */
            /*if (betTopicsBean.getTopicableType().equals("Series") && betTopicsBean.getCategory().equals(5) && betTopicsBean.getSupport().equals(3)){
                groupName = AreaMap.get(betTopicsBean.getMarkValue().intValue());

            }else {*/
                if (groupName.contains("{x}")){
                    if (gameCampaignBean!=null){
                        groupName = groupName.replace("{x}", NumberUtil.numToCH(gameCampaignBean.getNumber()));
                    }

                }
                if (groupName.contains("{left}")){
                    if (gameCampaignBean!=null){
                        groupName = groupName.replace("{left}", gameResult.getTeams().get(0).getAbbr());
                    }
                }
                if (groupName.contains("{right}")){
                    if (gameCampaignBean!=null){
                        groupName = groupName.replace("{right}", gameResult.getTeams().get(1).getAbbr());
                    }
                }
            //}

            betTopicsBean.setGroupName(groupName);

            if (betTopicsBeanOld!=null){
                /**
                 * 盘口结算错误，重新计算
                 */
                if ((betTopicsBeanOld.getStatus().equals("checked") || betTopicsBeanOld.getStatus().equals("canceled")) && betTopicsBean.getStatus().equals("default") ){
                    BetReFinishCallable betFinishCallable = new BetReFinishCallable(betTopicsBean,betOrderService,noticeService);
                    service.submit(betFinishCallable);
                }
                betTopicService.updateById(betTopicsBean);
            }else {
                betTopicService.save(betTopicsBean);
            }
            //betTopicService.saveOrUpdate(betTopicsBean);

            List<BetOptionBean> optionBeanList = BeanUtil.copyList(betGameNotify.getBetOptions(),BetOptionBean.class);
            boolean flag = false;

            for (int i = 0; i < optionBeanList.size(); i++) {

                if(betTopicsBean.getStatus().equals("default")){
                    optionBeanList.get(i).setBetResult(0);//默认未开奖
                }else if(betTopicsBean.getStatus().equals("canceled")){
                    optionBeanList.get(i).setBetResult(3);//取消
                }else {
                    optionBeanList.get(i).setBetResult(2);//失败
                }



                for (int j = 0; j < betGameNotify.getBetResults().size(); j++) {

                    if (optionBeanList.get(i).getId()==betGameNotify.getBetResults().get(j).getId()){
                        //获胜结果
                        optionBeanList.get(i).setBetResult(1);
                        break;
                    }
                }

                //进行结算
                if (optionBeanList.get(i).getBetResult()!=null && optionBeanList.get(i).getBetResult()!=0){
                    flag = true;

                }
                optionBeanList.get(i).setBetDataId(betTopicsBean.getId());
                optionBeanList.get(i).setUpdateTime(new Date());
                String optionName = "";
                if (betTopicsBean.getTopicableType().equals("Series") && betTopicsBean.getCategory().equals(5)){
                    //冠军盘处理
                    if (betTopicsBean.getSupport().equals(1) || (betTopicsBean.getSupport()>40 && betTopicsBean.getSupport()<68)){
                        GameTeamBean gameTeamBean = gameTeamService.getById(optionBeanList.get(i).getSequence());
                        optionName = gameTeamBean.getAbbr();
                    }else if (betTopicsBean.getSupport().equals(3)){
                        optionName = AreaMap.get(optionBeanList.get(i).getSequence());
                    }
                } else if (betTopicsBean.getTopicableType().equals("Series") && betTopicsBean.getCategory().equals(2) && betTopicsBean.getSupport().equals(0)){
                    optionName = GameScoreMap.get(gameResult.getBo(),optionBeanList.get(i).getSequence());
                } else {
                    if (i == 0) {
                        optionName = betExampleBean.getSequence1();
                        if (optionName.contains("{team}")) {
                            optionName = optionName.replace("{team}", gameResult.getTeams().get(0).getAbbr());
                        }
                        if (optionName.contains("{%mark_value}") || optionName.contains("{@mark_value}")) {
                            if (betTopicsBean.getMarkValue() > 0) {
                                optionName = optionName.replace("{%mark_value}", "+" + betTopicsBean.getMarkValue());
                                optionName = optionName.replace("{@mark_value}", ">" + betTopicsBean.getMarkValue());
                            } else {
                                optionName = optionName.replace("{%mark_value}", "" + betTopicsBean.getMarkValue());
                                optionName = optionName.replace("{@mark_value}", "<" + betTopicsBean.getMarkValue() * -1);
                            }

                        }
                    }
                    if (i == 1) {
                        optionName = betExampleBean.getSequence2();
                        if (optionName.contains("{team}")) {
                            optionName = optionName.replace("{team}", gameResult.getTeams().get(1).getAbbr());
                        }
                        if (optionName.contains("{%mark_value}") || optionName.contains("{@mark_value}")) {
                            if (betTopicsBean.getMarkValue() < 0) {
                                optionName = optionName.replace("{%mark_value}", "+" + betTopicsBean.getMarkValue() * -1);
                                optionName = optionName.replace("{@mark_value}", ">" + betTopicsBean.getMarkValue() * -1);
                            } else {
                                optionName = optionName.replace("{%mark_value}", "-" + betTopicsBean.getMarkValue());
                                optionName = optionName.replace("{@mark_value}", "<" + betTopicsBean.getMarkValue());
                            }

                        }
                    }
                    if (optionBeanList.get(i).getSequence() == 3) {
                        optionName = "平";
                    }
                }

                optionBeanList.get(i).setName(optionName);

            }
            if (flag){
                BetFinishCallable betFinishCallable = new BetFinishCallable(optionBeanList,betOrderService,noticeService);
                service.submit(betFinishCallable);
            }


            //netty推送到h5
            //nettyService.broadCastMessageByGroup("dataId-"+betTopicsBean.getDataId(),"bet",JSON.toJSONString(optionBeanList));
            betTopicsBean.setBetOptions(optionBeanList);
            nettyService.broadCastMessage("bet-"+JSON.toJSONString(betTopicsBean));
            /*if (betTopicsBean.getTopicableType().equals("Series")){
                nettyService.broadCastMessageByGroup("default","bet",JSON.toJSONString(optionBeanList));
            }*/
            betOptionService.saveOrUpdateBatch(optionBeanList);
        }catch (RuntimeException e){
            log.error(e.toString());
            throw new RuntimeException();
        }

        return "";
    }

    @Override
    public String saveNotification(NotificationNotify notificationNotify, BaseGameNotify baseGameNotify, String method) {
        StringBuffer message = new StringBuffer();
        if (notificationNotify.getNotifyType().equals("series")){
            GameResult gameResult = gameDataBeanMapper.selectGameResultWithTeamById(notificationNotify.getBodyFrom().getSeriesId());
            message.append(gameResult.getGameTypeBean().getNameEn()+"-");
            message.append(gameResult.getLeague().getName()+"-");
            message.append(gameResult.getTeams().get(0).getAbbr()+" vs "+ gameResult.getTeams().get(1).getAbbr()+" ");
            message.append("因"+ MessageMap.get("reason",notificationNotify.getBodyFrom().getReason()));
            if (notificationNotify.getBodyFrom().getNumber().intValue()==0){
                message.append(MessageMap.get("operation",notificationNotify.getBodyFrom().getOperation())+"全部盘口");
            }else {
                message.append(MessageMap.get("operation",notificationNotify.getBodyFrom().getOperation())+"第"+notificationNotify.getBodyFrom().getNumber()+"局盘口");
            }
            if (notificationNotify.getBodyFrom().getOperation().intValue()==102){
                message.append(notificationNotify.getBodyFrom().getStartTime());
                message.append("到");
                message.append(notificationNotify.getBodyFrom().getEndTime());
                message.append("内投注的注单");
            }

        }else if (notificationNotify.getNotifyType().equals("topic")){
            GameResult gameResult = gameDataBeanMapper.selectGameResultWithTeamById(notificationNotify.getBodyFrom().getSeriesId());
            message.append(gameResult.getGameTypeBean().getNameEn()+"-");
            message.append(gameResult.getLeague().getName()+"-");
            message.append(gameResult.getTeams().get(0).getAbbr()+" vs "+ gameResult.getTeams().get(1).getAbbr()+" ");
            if (notificationNotify.getBodyFrom().getNumber().intValue()==0){
                message.append("全局");
            }else {
                message.append("第"+notificationNotify.getBodyFrom().getNumber()+"局");
            }
            /*String flag = notificationNotify.getBodyFrom().getFlag();
            String[] flags = flag.split("_");*/
            String pan = redis.opsForValue().get(upperCase(notificationNotify.getBodyFrom().getFlag()));
            message.append(pan);
            //TODO
            message.append("，因"+ MessageMap.get("reason",notificationNotify.getBodyFrom().getReason()));
            message.append(" "+MessageMap.get("operation",notificationNotify.getBodyFrom().getOperation()));

            if (notificationNotify.getBodyFrom().getOperation().intValue()==102){
                message.append(notificationNotify.getBodyFrom().getStartTime());
                message.append("到");
                message.append(notificationNotify.getBodyFrom().getEndTime());
                message.append("内投注的注单");
            }
        }
        log.info(message.toString());
        /*List<String> clientIds = proxyConfigService.getclientids();
        for (int i = 0; i < clientIds.size(); i++) {
            NoticeBean noticeBean = new NoticeBean();
            noticeBean.setApiId(clientIds.get(i));
            noticeBean.setContent("\"" + message.toString() + "\"");
            noticeBean.setAction(NoticeAction.notice);
            if (notificationNotify.getNotifyType().equals("series")) {
                noticeBean.setTitle("比赛取消通知");
            }else {
                noticeBean.setTitle("盘口取消通知");
            }

            noticeBean.setStatus(Status.ENABLE);
            noticeBean.setOrderFlag(10);
            noticeService.save(noticeBean);
        }*/
        AnnouncementBean noticeBean = new AnnouncementBean();
        noticeBean.setContent(message.toString());
        noticeBean.setAction(NoticeAction.bet_notice);
        if (notificationNotify.getNotifyType().equals("series")) {
            noticeBean.setTitle("比赛取消通知");
        }else {
            noticeBean.setTitle("盘口取消通知");
        }
        if(notificationNotify.getBodyFrom().getOperation().equals("103")){
            noticeBean.setTitle("重新结算通知");
        }

        noticeBean.setStatus(Status.ENABLE);
        noticeBean.setOrderFlag(10);
        announcementService.save(noticeBean);

        List<BetTopicsBean> betTopicsBeanList = betTopicService.selectAllByDataIdAndNumber(notificationNotify.getBodyFrom().getSeriesId(),notificationNotify.getBodyFrom().getNumber());
        List<BetTopicsBean> betTopicsBeanLists = new ArrayList<>();
        List<BetOrderDetailBean> betOrderDetailBeanList = betOrderDetailService.selectByDataId(notificationNotify.getBodyFrom().getSeriesId());
        if (StringUtils.isNotBlank(notificationNotify.getBodyFrom().getFlag())){
            String[] flags = notificationNotify.getBodyFrom().getFlag().split("_");

            for (int i = 0; i < betTopicsBeanList.size(); i++) {

                if (flags[0].equalsIgnoreCase(betTopicsBeanList.get(i).getTopicableType()) && flags[1].equals(betTopicsBeanList.get(i).getCategory().toString()) && flags[2].equals(betTopicsBeanList.get(i).getSupport().toString())){
                    betTopicsBeanLists.add(betTopicsBeanList.get(i));
                }
            }

        }else {
            betTopicsBeanLists = betTopicsBeanList;
        }
        //更新盘口取消
        for (int i = 0; i < betOrderDetailBeanList.size(); i++) {
            xx:
            for (int j = 0; j < betTopicsBeanLists.size(); j++) {
                for (int k = 0; k < betTopicsBeanLists.get(j).getBetOptions().size(); k++) {
                    BetOptionBean betOptionBean = betTopicsBeanLists.get(j).getBetOptions().get(k);
                    if (betOrderDetailBeanList.get(i).getBetOptionId().equals(betOptionBean.getId())){
                        betOrderDetailBeanList.get(i).setCancelReason(message.toString());
                        betOrderDetailService.updateById(betOrderDetailBeanList.get(i));
                        continue xx;
                    }
                }
            }
        }

        return message.toString();
    }

    @Override
    public String cancelOrder(CancelOrderNotify cancelOrderNotify, BaseGameNotify baseGameNotify, String method) {
        String[] orders = cancelOrderNotify.getOrdersNumber();
        if (orders.length>0){

            betOrderService.cancelOrders(orders);

        }

        return null;
    }

    public String upperCase(String str) {
        if ((str == null) || (str.length() == 0)) return str;
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

}
