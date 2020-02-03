package com.splan.bplan.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by lyn on 2019/1/18.
 */
@Data
public class UserCardDto {
    private Long userId;

    private String bankCode;

    private String bankName;

    private Integer bankId;

    @NotEmpty(message = "银行卡号不能为空")
    private String creditCard;
}
