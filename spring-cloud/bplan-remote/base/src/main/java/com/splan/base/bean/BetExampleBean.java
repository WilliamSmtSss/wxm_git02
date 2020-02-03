package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * bet_example
 */
@Data
@TableName(value = "bet_example_co")
public class BetExampleBean extends BaseBean {
    /**
     * 
     */
    @TableField(value = "topicable_type")
    private String topicableType;

    /**
     * 
     */
    @TableField(value = "category")
    private Integer category;

    /**
     * 解释
     */
    @TableField(value = "explain_text")
    private String explainText;

    /**
     * 
     */
    @TableField(value = "support")
    private Integer support;

    /**
     * 
     */
    @TableField(value = "explain_text2")
    private String explainText2;

    /**
     * 
     */
    @TableField(value = "example")
    private String example;

    @TableField(value = "example_tc")
    private String exampleTc;

    @TableField(value = "example_en")
    private String exampleEn;

    @TableField(value = "example_vi")
    private String exampleVi;

    @TableField(value = "game_type")
    private Integer gameType;

    @TableField(value = "sequence1")
    private String sequence1;

    @TableField(value = "sequence2")
    private String sequence2;

    @TableField(value = "multiple")
    private Integer multiple;



}