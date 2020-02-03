package com.splan.xbet.frontback.frontback.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.splan.base.bean.BusinessCurrencyConfigBean;
import com.splan.base.bean.GameTypeBean;
import com.splan.base.enums.Language;
import com.splan.base.enums.WalletType;
import com.splan.base.enums.back.NoticeStatus;
import com.splan.base.enums.back.NoticeType;
import com.splan.base.enums.back.OddType;
import com.splan.base.enums.back.ProductType;
import com.splan.base.http.CommonResult;
import com.splan.base.service.v2.V2BackService;
import com.splan.base.utils.ResultUtil;

import com.splan.xbet.frontback.frontback.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontBack/common")
@Api(tags={"公共接口"})
public class CommonController {

    @Autowired
    private V2BackService v2BackService;

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public CommonResult<String> upload(MultipartFile file){
        return commonService.upload(file);
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

    @GetMapping("/oddType")
    @ApiOperation(value = "赔率类型", notes = "")
    public CommonResult oddType(){
        JSONArray jsonArray=new JSONArray();
        for(OddType oddType: OddType.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",oddType.geteName());
            jsonObject.put("cName",oddType.getcName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/currencyList")
    @ApiOperation(value = "币种列表", notes = "")
    public CommonResult currencyList(){
        Map<String,Object> map=new HashMap<>();
        String result=v2BackService.CurrencySel(map);
        List<BusinessCurrencyConfigBean> list= JSON.parseObject(result,new TypeReference<List<BusinessCurrencyConfigBean>>(){});
        return ResultUtil.returnSuccess(list);
    }

    @GetMapping("/gameList")
    @ApiOperation(value = "游戏列表", notes = "")
    public CommonResult gameList(){
        Map<String,Object> map=new HashMap<>();
        String result=v2BackService.gameList(map);
        List<GameTypeBean> list= JSON.parseObject(result,new TypeReference<List<GameTypeBean>>(){});
        return ResultUtil.returnSuccess(list);
    }

    @GetMapping("/NoticeStatus")
    @ApiOperation(value = "公告状态", notes = "")
    public CommonResult NoticeStatus(){
        JSONArray jsonArray=new JSONArray();
        for(NoticeStatus b: NoticeStatus.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",b.toString());
            jsonObject.put("cName",b.getName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/NoticeType")
    @ApiOperation(value = "公告类型", notes = "")
    public CommonResult NoticeType(){
        JSONArray jsonArray=new JSONArray();
        for(NoticeType b: NoticeType.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",b.toString());
            jsonObject.put("cName",b.getName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

    @GetMapping("/ProductType")
    @ApiOperation(value = "产品类型", notes = "")
    public CommonResult ProductType(){
        JSONArray jsonArray=new JSONArray();
        for(ProductType b: ProductType.values()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("eName",b.toString());
            jsonObject.put("cName",b.getName());
            jsonArray.add(jsonObject);
        }
        return ResultUtil.returnSuccess(jsonArray);
    }

}
