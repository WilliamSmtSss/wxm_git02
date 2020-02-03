package com.splan.bplan.service.impl;

import com.splan.bplan.BplanApplication;
import com.splan.base.bean.UserBalanceBean;
import com.splan.bplan.http.CancelOrderNotify;
import com.splan.bplan.http.NotificationNotify;
import com.splan.bplan.mappers.UserBalanceBeanMapper;
import com.splan.bplan.service.INotifyService;
import com.splan.bplan.service.ISeriesGameService;
import com.splan.bplan.utils.JsonUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BplanApplication.class)
public class NotifyServiceImplTest {

    @Autowired
    private ISeriesGameService seriesGameService;

    @Test
    public void test() {
        String data = " {\n" +
                "    \"notify_type\": \"topic\",\n" +
                "    \"created_at\": \"2019-04-07 19:00:00\",\n" +
                "    \"body_from\": {\n" +
                "      \"series_id\": 6326,\n" +
                "      \"number\": 0,\n" +
                "      \"operation\": 101,\n" +
                "      \"reason\": 1001,\n" +
                "      \"flag\": \"series_1_0\",\n" +
                "      \"start_time\": \"2019-04-07 12:00:05\",\n" +
                "      \"end_time\": \"2019-04-07 19:00:00\"\n" +
                "    }\n" +

                "}";
        NotificationNotify cancelOrderNotify = JsonUtil.stringToObject(data, NotificationNotify.class);
        seriesGameService.saveNotification(cancelOrderNotify,null,null);
    }
}