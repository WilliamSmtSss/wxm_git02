package com.splan.bplan.service;

import com.splan.bplan.http.*;

public interface ISeriesGameService {

    String saveSeries(SeriesGameNotify seriesGameNotify, BaseGameNotify baseGameNotify,String method);

    String saveTopic(BetGameNotify betGameNotify, BaseGameNotify baseGameNotify, String method);

    String saveNotification(NotificationNotify notificationNotify, BaseGameNotify baseGameNotify, String method);

    String cancelOrder(CancelOrderNotify cancelOrderNotify, BaseGameNotify baseGameNotify, String method);
}
