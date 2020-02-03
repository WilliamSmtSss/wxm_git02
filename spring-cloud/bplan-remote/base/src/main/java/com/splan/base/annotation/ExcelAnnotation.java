package com.splan.base.annotation;

import java.lang.annotation.*;


/**
 * <p>
 *  功能：excel模板设置
 * </p>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAnnotation {
    //Excel列ID(Excel列排序序号)
    int id();
    //Excel列名
    String[] name();
    //Excel列宽
    int width() default 5000;
}