package com.splan.base.result;

import lombok.Data;

@Data
public class CommonOperatorResult {

    /**
     * 新增
     */
    private  boolean add=true;
    /**
     * 编辑
     */
    private  boolean edit=true;

    /**
     * 删除
     */
    private  boolean del=true;
}
