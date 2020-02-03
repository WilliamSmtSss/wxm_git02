package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.enums.ContactType;
import lombok.Data;

/**
 * agent_submit
 */
@Data
@TableName(value = "agent_submit")
public class AgentSubmit extends BaseBean {
    /**
     * 
     */
    @TableField(value = "qq")
    private String qq;

    /**
     * 
     */
    @TableField(value = "contact")
    private ContactType contact;

    /**
     * 
     */
    @TableField(value = "contact_detail")
    private String contactDetail;

    /**
     * 联系电话
     */
    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "user_id")
    private Long userId;


}