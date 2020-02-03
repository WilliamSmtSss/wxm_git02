package com.splan.xbet.admin.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.MoneyRecordBean;
import com.splan.base.bean.UserBean;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.mappers.BusinessCurrencyConfigBeanMapper;
import com.splan.xbet.admin.back.mappers.UserMapper;
import com.splan.xbet.admin.back.mappers.XbetBackMapper;
import com.splan.xbet.admin.back.service.CachePublicService;
import com.splan.xbet.admin.back.service.XBetPlayerUserService;
import com.splan.xbet.admin.back.utils.ResultUtil;
import com.splan.xbet.admin.back.utils.export.ReportExcel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class XBetPlayerUserServiceImpl implements XBetPlayerUserService {

    @Autowired
    private XbetBackMapper xbetBackMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private ReportExcel reportExcel;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BusinessCurrencyConfigBeanMapper businessCurrencyConfigBeanMapper;

    @Autowired
    private CachePublicService cachePublicService;

    @Override
    public CommonResult<IPage<UserBean>> list(Page page, String bigBusiness, String smallBusiness, String searchId, String export) {
        if(StringUtils.isBlank(bigBusiness))bigBusiness=null;
        if(StringUtils.isBlank(smallBusiness))smallBusiness=null;
        if(StringUtils.isBlank(searchId))searchId=null;
        if(StringUtils.isNotBlank(export))page=null;
        List<UserBean> list=listPublic(page,bigBusiness,smallBusiness,searchId,export);
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    private List<UserBean> listPublic(Page page, String bigBusiness, String smallBusiness, String searchId,String export){
//        List<UserBean> list=xbetBackMapper.getUserPageList(page,bigBusiness,smallBusiness,searchId);
//        List<Integer> userIds=new ArrayList<>();
//        list.forEach(userBean -> {
//            userIds.clear();
//            try {
//                userIds.add(Integer.parseInt(userBean.getUserBalanceBean().getUserId() + ""));
//            }catch (Exception e){
//                userIds.add(-1);
//            }
//            JSONObject jsonObject=xbetBackMapper.getOrderStatistics(userIds,null,null);
//            userBean.setXBetOrderTotal(jsonObject.getBigDecimal("orderAmount"));
//            userBean.setXBetOrderCount(jsonObject.getBigDecimal("orderCount"));
//            userBean.setXBetOrderProfit(jsonObject.getBigDecimal("orderAmount").subtract(jsonObject.getBigDecimal("returnAmount")));
//            if(null!=userBean.getBusinessConfigBean())userBean.setBusinessConfigBeanName(userBean.getBusinessConfigBean().getApiId());
//            if(null!=userBean.getBusinessConfigBeanUp())userBean.setBusinessConfigBeanUpName(userBean.getBusinessConfigBeanUp().getApiId());
//            userBean.setUserBalanceCoin(null!=userBean.getUserBalanceBean()?userBean.getUserBalanceBean().getCoin():new BigDecimal("0"));
//            userBean.setWalletTypeName(null!=userBean.getBusinessConfigBean()?WalletType.getCnameByeName(userBean.getBusinessConfigBean().getWalletType()):"");
//           try {
//               userBean.setBusinessConfigBeanAdminName(userMapper.selectById(userBean.getBusinessConfigBean().getSysUserId()).getUsername());
//           }catch (Exception e){
//               userBean.setBusinessConfigBeanAdminName("");
//           }
//        });
        return cachePublicService.listPublic(page, bigBusiness, smallBusiness, searchId,export);
    }

    @Override
    @Async
    public CommonResult<IPage<UserBean>> listExport(Page page, String bigBusiness, String smallBusiness, String searchId, String export) {
            List<UserBean> list=listPublic(page,bigBusiness,smallBusiness,searchId,export);
////            Thread thread = new Thread(new Runnable() {
////                @Override
////                public void run() {
////                    //无返回值的业务代码
////                    ReportExcel reportExcel = new ReportExcel();
////                    reportExcel.excelExport(list,"会员列表", UserBean.class,1,httpServletResponse,httpServletRequest);
////                }
////            });
            reportExcel.excelExport2(list,"会员列表", UserBean.class,1);
            return null;
    }

    @Override
    public CommonResult<IPage<MoneyRecordBean>> moneyRecord(Page page,String bigBusiness, String smallBusiness, String searchId, String orderId,String export) {
        if(StringUtils.isBlank(bigBusiness))bigBusiness=null;
        if(StringUtils.isBlank(smallBusiness))smallBusiness=null;
        if(StringUtils.isBlank(searchId))searchId=null;
        if(StringUtils.isBlank(orderId))orderId=null;
        List<MoneyRecordBean> list=moneyRecordPublic(page,bigBusiness,smallBusiness,searchId,orderId,export);
//        if(StringUtils.isNotBlank(export)){
//            ReportExcel reportExcel = new ReportExcel();
//            reportExcel.excelExport(list,"会员流水", MoneyRecordBean.class,1,httpServletResponse,httpServletRequest);
//            return null;
//        }
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    @Async
    public CommonResult<IPage<MoneyRecordBean>> moneyRecordExport(Page page, String bigBusiness, String smallBusiness, String searchId, String orderId, String export) {
        System.err.println("=============异步==========moneyRecordExport"+Thread.currentThread().getName());
        List<MoneyRecordBean> list=moneyRecordPublic(null,bigBusiness,smallBusiness,searchId,orderId,export);
        reportExcel.excelExport2(list,"会员流水", MoneyRecordBean.class,1);
        return null;
    }

    private List<MoneyRecordBean> moneyRecordPublic(Page page, String bigBusiness, String smallBusiness, String searchId, String orderId,String export){
//        List<MoneyRecordBean> list=xbetBackMapper.getMoneyRecordPageList(page,bigBusiness,smallBusiness,searchId,orderId);
//        list.forEach(moneyRecordBean -> {
//            if(null!=moneyRecordBean.getBusinessConfigBean())moneyRecordBean.setBusinessConfigBeanName(moneyRecordBean.getBusinessConfigBean().getApiId());
//            if(null!=moneyRecordBean.getBusinessConfigBeanUp())moneyRecordBean.setBusinessConfigBeanUpName(moneyRecordBean.getBusinessConfigBeanUp().getApiId());
//            try {
//                moneyRecordBean.setBusinessConfigBeanAdminName(userMapper.selectById(moneyRecordBean.getBusinessConfigBean().getSysUserId()).getUsername());
//            }catch (Exception e){
//                moneyRecordBean.setBusinessConfigBeanAdminName("");
//            }
//
//            moneyRecordBean.setMoneyableTypeName(MoneyAbleType.getCnameByeName(moneyRecordBean.getMoneyableType().toString()));
//            try {
//                moneyRecordBean.setCurrencyName(businessCurrencyConfigBeanMapper.selectById(moneyRecordBean.getBusinessConfigBean().getCurrency()).getCode());
//            }catch (Exception e){
//                System.out.println(e.getClass()+"\n"+e.getMessage()+"\n"+e.getStackTrace());
//                moneyRecordBean.setCurrencyName("");
//            }
//
//            try {
//                moneyRecordBean.setWalletTypeName(WalletType.getCnameByeName(moneyRecordBean.getBusinessConfigBean().getWalletType()));
//            }catch (Exception e){
//                moneyRecordBean.setWalletTypeName("");
//            }
//
//        });
        return cachePublicService.moneyRecordPublic(page, bigBusiness, smallBusiness, searchId, orderId,export);
    }

}
