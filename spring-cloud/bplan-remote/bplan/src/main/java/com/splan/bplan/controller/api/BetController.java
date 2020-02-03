package com.splan.bplan.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.bplan.annotation.Authorization;
import com.splan.bplan.annotation.AvoidRepeatableCommit;
import com.splan.bplan.annotation.CurrentUser;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.OrderBetDto;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.BetOrderResult;
import com.splan.bplan.service.IBetOrderService;
import com.splan.bplan.service.IBetOutService;
import com.splan.bplan.service.IBetService;
import com.splan.bplan.service.IBusinessConfigService;
import com.splan.bplan.utils.IpUtil;
import com.splan.bplan.utils.JsonUtil;
import com.splan.bplan.utils.ResultUtil;
import com.splan.bplan.utils.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bets")
@Api(value="下注接口",tags={"下注"})
@Slf4j
public class BetController extends BaseController{

    @Autowired
    private IBetService betService;

    @Autowired
    private IBetOutService betOutService;

    @Autowired
    private IBetOrderService betOrderService;

    @Autowired
    private IBusinessConfigService businessConfigService;


    @PostMapping("/order")
    @ApiOperation(value="下注")
    @Authorization
    @AvoidRepeatableCommit
    public CommonResult<List<BetOrderBean>> order(HttpServletRequest request, String data, @CurrentUser @ApiIgnore UserBean userBean){
        if (data == null){
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                data = IOUtils.toString(reader);
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
        List<OrderBetDto> orderBetDtoList = JsonUtil.stringToListObject(data, OrderBetDto.class);
        List<BetOrderBean> list = new ArrayList<>();
        CommonResult validateCommonResult = ValidatorUtil.validateCommonResult(orderBetDtoList);
        String ip = IpUtil.getIpAddr(request);
        if (!validateCommonResult.isSuccess()){
            return validateCommonResult;
        }
        //log.info(data);

        if (StringUtils.isNotBlank(userBean.getApiId())){
            BusinessConfigBean businessConfigBean = businessConfigService.selectByApiId(userBean.getApiId());
            if (businessConfigBean.getWalletType().intValue()==2){
                for (int i = 0; i < orderBetDtoList.size(); i++) {
                    orderBetDtoList.get(i).setIp(ip);
                    CommonResult<List<BetOrderBean>> result;
                    if (orderBetDtoList.get(i).isCameoOrder()){
                        result = betOutService.cameoOrder(userBean,orderBetDtoList.get(i));

                    }else {
                        result = betOutService.order(userBean,orderBetDtoList.get(i));
                    }
                    if (result.isSuccess()){
                        list.addAll(result.getData());
                    }else {
                        return result;
                    }

                }
            }else {
                for (int i = 0; i < orderBetDtoList.size(); i++) {
                    orderBetDtoList.get(i).setIp(ip);
                    CommonResult<List<BetOrderBean>> result;
                    if (orderBetDtoList.get(i).isCameoOrder()){
                        result = betService.cameoOrder(userBean,orderBetDtoList.get(i));

                    }else {
                        result = betService.order(userBean,orderBetDtoList.get(i));
                    }
                    if (result.isSuccess()){
                        list.addAll(result.getData());
                    }else {
                        return result;
                    }

                }
            }

        }else {
            for (int i = 0; i < orderBetDtoList.size(); i++) {
                orderBetDtoList.get(i).setIp(ip);
                CommonResult<List<BetOrderBean>> result;
                if (orderBetDtoList.get(i).isCameoOrder()){
                    result = betService.cameoOrder(userBean,orderBetDtoList.get(i));

                }else {
                    result = betService.order(userBean,orderBetDtoList.get(i));
                }
                if (result.isSuccess()){
                    list.addAll(result.getData());
                }else {
                    return result;
                }

            }
        }

        return ResultUtil.returnSuccess(list);

    }

    @GetMapping("/orderlist")
    @ApiOperation(value="历史竞猜",notes="")
    @Authorization
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "0.未结算 1.已结算",dataType = "Integer"),
            @ApiImplicitParam(name = "orderDate",value = "比赛时间",dataType = "String")}
    )
    public CommonResult<IPage<BetOrderBean>> orderList(@RequestParam(value="type", defaultValue="0")Integer type, String orderDate, @RequestParam(value="page", defaultValue="1") int currentPage,
                                                       @RequestParam(defaultValue="10") int per, @CurrentUser @ApiIgnore UserBean userBean){
        Page page = new Page<>(currentPage,per);
        if (type==null){
            type = 0;
        }

        return betOrderService.selectByType(userBean,type,orderDate,page);
    }

    @GetMapping("/rollorderlist")
    @ApiOperation(value="滚动竞猜显示",notes="")
    public CommonResult<List<BetOrderResult>> rollorderList(){
        return betOrderService.rollOrderList();
    }

    @GetMapping("/test")
    public Integer test(){
        return betOrderService.checkHistoryResult();
    }

    @GetMapping("/test2")
    public Integer test2(){
        return betOrderService.syncToOrder();
    }
}
