package com.splan.bplan.service.impl;

import com.splan.bplan.BplanApplication;
import com.splan.bplan.service.INoticeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BplanApplication.class)
public class NoticeServiceImplTest {

    @Autowired
    private INoticeService noticeService;

    @Test
    public void sendNotice() {

        //noticeService.sendNotice("refund","test2019000",new BigDecimal(100),"系统拒绝",1000l,10000l);
    }
}