package com.splan.xbet.back.backforbusiness.contantes;

public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 24*7;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";

    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USER_INFO = "userInfo";
    public static final String SESSION_USER_PERMISSION = "userPermission";
    public static final String SESSION_USER_ROLE = "userRole";
    public static final String SESSION_USER_MENU = "userMenu";

    public static final String SUCCESS_CODE = "100";
    public static final String SUCCESS_MSG = "请求成功";

    public static final String SHIRO_SESSION = "shiro_session_management";

    public static final String EXCEL_TEMP = "excel_temp";

}
