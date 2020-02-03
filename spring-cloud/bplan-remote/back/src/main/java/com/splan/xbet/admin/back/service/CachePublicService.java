package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.BetTopicsBean;
import com.splan.base.bean.MoneyRecordBean;
import com.splan.base.bean.UserBean;
import com.splan.xbet.admin.back.dto.DateDto;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Cacheable(value = "backCache",condition = "#export ne '' and #export ne null",unless = "#result==null")
public interface CachePublicService {

//    @Cacheable(value = "backCache",key = "'bet_data_'+#bigBusiness+#smallBusiness+#gameId+(#dateDto.startDate!=null?#dateDto.startDate.toString():'')+(#dateDto.endDate!=null?#dateDto.endDate.toString():'')+#dataId+#betId+#betStatus",condition = "#export ne '' and #export ne null",unless = "#result==null")
//    @Cacheable(value = "backCache",condition = "#export ne '' and #export ne null",unless = "#result==null")
    List<BetTopicsBean> betDataPublic(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus, String export);

//    @Cacheable(value = "backCache",key = "'order_data_'+#bigBusiness+#smallBusiness+#gameId+(#dateDto.startDate!=null?#dateDto.startDate.toString():'')+(#dateDto.endDate!=null?#dateDto.endDate.toString():'')+#orderStatus+#searchId+#orderId+#betId+#orderType+#dataId",condition = "#export ne '' and #export ne null",unless = "#result==null")
//    @Cacheable(value = "backCache",condition = "#export ne '' and #export ne null",unless = "#result==null")
    List<BetOrderBean> orderDataPublic(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String orderStatus, String searchId, String orderId, String betId, String orderType, String export, String dataId);

//    @Cacheable(value = "backCache",condition = "#export ne '' and #export ne null",unless = "#result==null")
    List<UserBean> listPublic(Page page, String bigBusiness, String smallBusiness, String searchId,String export);

//    @Cacheable(value = "backCache",condition = "#export ne '' and #export ne null",unless = "#result==null")
    List<MoneyRecordBean> moneyRecordPublic(Page page, String bigBusiness, String smallBusiness, String searchId, String orderId,String export);

}
