package com.splan.xbet.admin.back.result;


import com.splan.base.bean.NoticeBean;
import lombok.Data;

@Data
public class NoticeResult {

    private Integer messageCount;

    private Integer gameNoticeCount;

    private NoticeBean message;

    private NoticeBean gameNotice;
}
