package com.splan.data.datacenter.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DateDtoUpdate implements Serializable {

    public String startDateUpdate;

    public String endDateUpdate;
}
