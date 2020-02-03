package com.splan.data.datacenter.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class DateDto implements Serializable {

    private String startDate;

    private String endDate;
}
