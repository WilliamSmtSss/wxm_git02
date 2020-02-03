package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.GiftListBean;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

public interface GiftListBeanMapper extends SuperMapper<GiftListBean> {
}