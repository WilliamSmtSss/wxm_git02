package com.splan.data.datacenter.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class OrderParam implements Serializable {

    public long current=1;

    public long size=100;

    public String startDate;

    public String endDate;

    public String orderStatus;

    public String searchId;

    public String orderId;

    public String orderType;

    public String startDateOrder;

    public String endDateOrder;

    public String startDateUpdate;

    public String endDateUpdate;

}
