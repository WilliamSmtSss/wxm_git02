package com.splan.data.datacenter.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class DateDtoOrder implements Serializable {

    private String startDateOrder;

    private String endDateOrder;
}
