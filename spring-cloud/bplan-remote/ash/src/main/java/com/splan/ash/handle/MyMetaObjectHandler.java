package com.splan.ash.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = metaObject.getValue("createTime");
        Object updateTime = metaObject.getValue("updateTime");
        if (createTime==null){
            metaObject.setValue("createTime",new Date());
        }
        if (updateTime==null){
            metaObject.setValue("updateTime",new Date());
        }


    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",new Date());
    }


}
