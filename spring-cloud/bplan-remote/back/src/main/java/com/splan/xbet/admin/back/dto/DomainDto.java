package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DomainDto implements Serializable {

    private String domain;

    private String channel;

    private String registerChannel;
}
