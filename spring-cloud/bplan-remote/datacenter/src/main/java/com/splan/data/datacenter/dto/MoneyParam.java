package com.splan.data.datacenter.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
public class MoneyParam implements Serializable {

    public long current=1;

    public long size=100;

    public String searchId;

    public String orderId;

    public String startDate;

    public String endDate;

}
