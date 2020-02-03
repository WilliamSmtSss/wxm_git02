package com.splan.xbet.frontback.frontback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackNotice;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.back.NoticeStatus;
import com.splan.base.enums.back.NoticeType;
import com.splan.base.enums.back.ProductType;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.mappers.BackNoticeMapper;
import com.splan.xbet.frontback.frontback.service.NoticeService;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private BackNoticeMapper backNoticeMapper;

    @Override
    public CommonResult add(Map<String, Object> requestParam) {
        BackNotice insert=chgParamtoBean(requestParam,new BackNotice());
        return ResultUtil.returnSuccess(backNoticeMapper.insert(insert));
    }

    @Override
    public CommonResult edit(Map<String, Object> requestParam) {
        BackNotice insert=chgParamtoBean(requestParam,new BackNotice());
        if(null == insert.getId()) return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        return ResultUtil.returnSuccess(backNoticeMapper.updateById(insert));
    }

    @Override
    public CommonResult del(Map<String, Object> requestParam) {
        String id=requestParam.get("id").toString();
        BackNotice u=new BackNotice();
        u.setId(Integer.parseInt(id));
        u.setStatus(NoticeStatus.del.toString());
        return ResultUtil.returnSuccess(backNoticeMapper.updateById(u));
    }

    @Override
    public CommonResult<Page<BackNotice>> sel(Map<String, Object> requestParam) {
        BackNotice query=chgParamtoBean(requestParam,new BackNotice());
        Page page=new Page();
        page.setCurrent(Integer.parseInt(requestParam.get("current").toString()));
        page.setSize(Integer.parseInt(requestParam.get("size").toString()));
        List<BackNotice> list=backNoticeMapper.pageList(page,query);
        for(BackNotice b:list){
            b.setStatusName(NoticeStatus.getCName(b.getStatus()));
            b.setTypeName(NoticeType.getCName(b.getType()));
            b.setProductName(ProductType.getCName(b.getProduct()));
        }
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }


    private static <T> T chgParamtoBean(Map<String, Object> requestParam, T obj){
        Class clazz;
        List<Field> field2=new ArrayList<>();
        clazz = obj.getClass();
        while (clazz != null){
            field2.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        for(Field f:field2){
            if(requestParam.containsKey(f.getName())){
                f.setAccessible(true);
//                if(f.getType().getName().contains("String")){
                    try {
                        f.set(obj,requestParam.get(f.getName()));
                    }catch (Exception e){

                    }
//                }
            }
        }
        return obj;
    }

}
