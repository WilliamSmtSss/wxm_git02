package com.splan.xbet.admin.back.result;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class InviteRewardResultOut implements Serializable {
    @ApiModelProperty(value = "累计收益")
    private Integer alreadyBenefits;
    @ApiModelProperty(value = "预计总收益")
    private Integer totalBenefits;
    @ApiModelProperty(value = "待领取收益")
    private Integer waitBenefits;

    private List<InviteRewardResult> inviteRewardResults;

    private Page page;
}
