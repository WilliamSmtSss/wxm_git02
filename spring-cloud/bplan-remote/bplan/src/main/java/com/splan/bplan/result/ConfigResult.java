package com.splan.bplan.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigResult implements Serializable {

    private String logo;

    private Integer hasHeader = 1;
}
