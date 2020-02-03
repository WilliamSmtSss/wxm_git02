package com.splan.xbet.admin.back.controller.xbetback;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessCurrencyConfigBean;
import com.splan.base.bean.GameTypeBean;
import com.splan.base.bean.SysUser;
import com.splan.base.bean.XExportBean;
import com.splan.base.enums.Language;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.annotation.CurrentSysUser;
import com.splan.xbet.admin.back.enums.*;
import com.splan.xbet.admin.back.service.XBetCommonService;
import com.splan.xbet.admin.back.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/back/xBet/common")
@Api(tags={"xBet后台:公共接口"})
public class CommonController {

    @Autowired
    private XBetCommonService xbetCommonService;

    @GetMapping("/sysUserStatus")
    @ApiOperation(value = "权限管理状态", notes = "")
    public CommonResult getSysUserStatus(){
        JSONArray jsonArray=new JSONArray();
        for(CommonStatus commonStatus:CommonStatus.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",commonStatus.geteName());
            jsonObject.put("cName",commonStatus.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/betStatus")
    @ApiOperation(value = "盘口状态", notes = "")
    public CommonResult getBetStatus(){
        JSONArray jsonArray=new JSONArray();
        for(BetStatus betStatus:BetStatus.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",betStatus.geteName());
            jsonObject.put("cName",betStatus.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/orderStatus")
    @ApiOperation(value = "订单状态", notes = "")
    public CommonResult getOrderStatus(){
        JSONArray jsonArray=new JSONArray();
        for(OrderStatus orderStatus:OrderStatus.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",orderStatus.geteName());
            jsonObject.put("cName",orderStatus.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public CommonResult<String> upload(MultipartFile file, String type){
        return xbetCommonService.upload(file,type);
    }

    @GetMapping("/currencyList")
    @ApiOperation(value = "商户币种", notes = "")
    public CommonResult<List<BusinessCurrencyConfigBean>> currencyList(){
        return xbetCommonService.currencyList();
    }

    @GetMapping("/productType")
    @ApiOperation(value = "产品形态", notes = "")
    public CommonResult productType(){
        JSONArray jsonArray=new JSONArray();
        for(ProductType productType:ProductType.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",productType.geteName());
            jsonObject.put("cName",productType.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/productSkin")
    @ApiOperation(value = "产品皮肤", notes = "")
    public CommonResult productSkin(){
        JSONArray jsonArray=new JSONArray();
        for(ProductSkin productSkin:ProductSkin.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",productSkin.geteName());
            jsonObject.put("cName",productSkin.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/coefficient")
    @ApiOperation(value = "赔率影响", notes = "")
    public CommonResult coefficient(){
        JSONArray jsonArray=new JSONArray();
        for(Coefficient coefficient:Coefficient.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",coefficient.geteName());
            jsonObject.put("cName",coefficient.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/assignAdmin")
    @ApiOperation(value = "关联账号列表", notes = "")
    public CommonResult<List<SysUser>> assignAdmin(String apiId){
        return xbetCommonService.assignAdmin(apiId);
    }

    @GetMapping("/businessList")
    @ApiOperation(value = "获取商户/下级商户列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType", value = "查询类型 0:商户列表 1：商户下线列表"),
            @ApiImplicitParam(name = "bigBusiness", value = "商户名称"),
    })
    public CommonResult<List<String>> businessList(@CurrentSysUser @ApiIgnore SysUser sysUser,String queryType,String bigBusiness){
        return xbetCommonService.businessList(sysUser,queryType,bigBusiness);
    }

    @GetMapping("/language")
    @ApiOperation(value = "语言", notes = "")
    public CommonResult language(){
        JSONArray jsonArray=new JSONArray();
        for(Language language:Language.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",language.geteName());
            jsonObject.put("cName",language.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/gameType")
    @ApiOperation(value = "游戏列表", notes = "")
    public CommonResult<List<GameTypeBean>> gameType(){
        return xbetCommonService.gameType();
    }

    @GetMapping("/walletType")
    @ApiOperation(value = "钱包类型", notes = "")
    public CommonResult walletType(){
        JSONArray jsonArray=new JSONArray();
        for(WalletType walletType: WalletType.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",walletType.geteName());
            jsonObject.put("cName",walletType.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }


    @GetMapping("/businessStatus")
    @ApiOperation(value = "商户状态", notes = "")
    public CommonResult businessStatus(){
        JSONArray jsonArray=new JSONArray();
        for(BusinessStatus businessStatus:BusinessStatus.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",businessStatus.geteName());
            jsonObject.put("cName",businessStatus.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/getExcelTemp")
    @ApiOperation(value = "获取临时下载文件地址", notes = "")
    public CommonResult getExcelTemp(@CurrentSysUser @ApiIgnore SysUser sysUser){
        return xbetCommonService.getExcelTemp(sysUser);
    }

    @GetMapping("/getExportTempFiles")
    @ApiOperation(value = "获取导出文件列表", notes = "")
    public CommonResult<IPage<XExportBean>> getExportTempFiles(@CurrentSysUser @ApiIgnore SysUser sysUser,Page page){
        return xbetCommonService.getExportTempFiles(sysUser, page);
    }

}
