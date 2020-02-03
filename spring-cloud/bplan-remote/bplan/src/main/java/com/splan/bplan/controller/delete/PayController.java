//package com.splan.bplan.controller.api;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.splan.bplan.annotation.Authorization;
//import com.splan.bplan.annotation.CurrentUser;
//import com.splan.base.bean.CommonPayParamBean;
//import com.splan.base.bean.PayOrderBean;
//import com.splan.base.bean.SysParamBean;
//import com.splan.base.bean.UserBean;
//import com.splan.bplan.constants.SysParamConstants;
//import com.splan.bplan.dto.PayBackDto;
//import com.splan.bplan.dto.PayDepositDto;
//import com.splan.bplan.dto.PayWithDrawDto;
//import com.splan.bplan.dto.UserDto;
//import com.splan.base.enums.AccessType;
//import com.splan.base.enums.ResultStatus;
//import com.splan.bplan.http.CommonResult;
//import com.splan.bplan.mappers.SysParamBeanMapper;
//import com.splan.bplan.service.IPayOrderService;
//import com.splan.bplan.service.IPayParamService;
//import com.splan.bplan.service.IPayService;
//import com.splan.bplan.utils.PayServiceFactory;
//import com.splan.bplan.utils.ResultUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@RequestMapping("/api/pay")
//@Api(value = "支付接口",tags = {"支付接口"})
//public class PayController extends BaseController{
//
//    @Autowired
//    private PayServiceFactory payServiceFactory;
//
//    @Autowired
//    private IPayParamService payParamService;
//
//    @Autowired
//    private IPayOrderService payOrderService;
//
//    @Autowired
//    private SysParamBeanMapper sysParamBeanMapper;
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @PostMapping("/result/kalipay")
//    @ApiOperation(value = "接受返回结果", notes = "")
//    public CommonResult<String> result(PayBackDto payBackDto) {
//        if (StringUtils.equals(payBackDto.getStatus(), "ok")) {
//            logger.info("kalipay订单成功: {}", payBackDto);
//        } else if (StringUtils.equals(payBackDto.getStatus(), "error")) {
//            logger.info("kalipay订单失败: {}", payBackDto.getPayment_reference());
//        } else {
//            logger.info("kalipay订单执行中: {}", payBackDto.getPayment_reference());
//            return ResultUtil.returnSuccess("");
//        }
//        return payOrderService.updateOrder(payBackDto.getPayment_reference(), payBackDto.getStatus(), payBackDto.getAmount());
//    }
//
//    @PostMapping("/deposit")
//    @ApiOperation(value = "请求支付", notes = "")
//    @Authorization
//    public CommonResult<String> deposit(@CurrentUser @ApiIgnore UserBean userBean, PayDepositDto payDeposit, HttpServletRequest request) {
//        CommonPayParamBean payParamBean = payParamService.getEnablePayParam();
//        IPayService service = payServiceFactory.getPayService(payParamBean.getType());
//        return ResultUtil.returnSuccess(service.deposit(userBean, payParamBean, payDeposit, request));
//    }
//
//    @PostMapping("/withdraw")
//    @ApiOperation(value = "请求取款", notes = "")
//    @Authorization
//    public CommonResult<String> withdraw(PayWithDrawDto payWithDrawDto) {
//        SysParamBean sysParamBean = sysParamBeanMapper.selectByParamName(SysParamConstants.PAY_REVIEW_FLAG);
//        if (StringUtils.equals(sysParamBean.getParamValue(), "CLOSE")) {
//            return ResultUtil.returnError(ResultStatus.WITH_DRAW_CLOSE);
//        }
//        CommonPayParamBean payParamBean = payParamService.getEnablePayParam();
//        IPayService service = payServiceFactory.getPayService(payParamBean.getType());
//        return service.withdraw(payParamBean, payWithDrawDto);
//    }
//
//    @GetMapping("/history")
//    @ApiOperation(value = "充值历史", notes = "")
//    @Authorization
//    public CommonResult<IPage<PayOrderBean>> history(@CurrentUser @ApiIgnore UserBean user, Page page, AccessType accessType) {
//        QueryWrapper<PayOrderBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("tenant_user_no", user.getId());
//        queryWrapper.eq("access_type", accessType);
//
//        return ResultUtil.returnSuccess(payOrderService.page(page, queryWrapper));
//    }
//
//}
