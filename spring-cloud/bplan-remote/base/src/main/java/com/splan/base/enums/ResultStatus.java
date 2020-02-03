package com.splan.base.enums;

public enum ResultStatus {

    SUCCESS("0000", "成功","successful","Thành công","成功"),
    CODE_SUCCESS("0000", "验证码发送成功","The verification code has been sent","Mã xác nhận đã được gửi thành công","驗證碼發送成功"),
    SC_UNAUTHORIZED("401", "未登录","Unlogin","Chưa đăng nhập","未登錄"),
    LOGIN_SUCCESS("0001", "登录成功","login successful","Đăng nhập thành công","登錄成功"),
    PARAM_EMPTY("1001", "必选参数为空","The required parameter is empty","Tham số bắt buộc để trống","必選參數為空"),
    PARAM_ERROR("1002", "参数格式错误","Incorrect parameter format","Định dạng tham số không chính xác","參數格式錯誤"),
    SIGN_ERROR("1003", "签名错误","Sign error","Sign error","签名错误"),
    //NOT_AGENT("1004", "您还不是代理，申请成为代理"),
    REGISTER_SUCCESS("0000", "注册成功","Registration successful","Đăng ký thành công","註冊成功"),
    METHOD_NOT_FOUND("1003", "请求不存在","Request does not exist","Yêu cầu không tồn tại","請求不存在"),

    USERNAME_OR_PASSWORD_ERROR("2001", "用户名或密码错误","username or password incorrect","Tên đăng nhập hoặc mật khẩu sai","用戶名或密碼錯誤"),
    USER_NOT_FOUND("2002", "用户不存在","Username does not exist","Tài khoản không tồn tại","用戶不存在"),
    USER_NOT_LOGIN("2003", "用户未登录","User is not logged in","Chưa đăng nhập tài khoản","用戶未登錄"),
    //VERIFICATIONCODEERROR("2004","验证码错误"),
    USER_IS_EXISTS("2005","用户已存在","Username has been used","Tài khoản đã tồn tại","用戶已存在"),
    //PASSWORD_NOT_EQUAL("2006", "两次密码不一致","Insufficient permissions"),
    PASSWORD_NOT_BEFORE("2007", "原密码不匹配","Original password does not match","Mã số gốc không khớp","原密碼不匹配"),
    USER_DISABLE("2008", "账户被禁用，请联系管理员","user disabled","user disabled","許可權不足"),
    USER_LOCK("2009", "账户被锁定，请在1小时后重新登陆","user lock","user lock",""),
    NOTPERMISSION("2010","权限不足","not permission","not permission",""),
    //NOTTOUP("2011","未达到晋级流水"),

    NOT_ENOUGH_MONEY("3001","余额不足","Insufficient balance","Số dư không đủ","餘額不足"),
    ODD_IS_CHANGE("3002","赔率已变化","Odds changes","Tỷ lệ đã thay đổi","賠率已變化"),
    BET_FINISH("3003","盘口已结束","Market Close","Kèo chấp đã kết thúc","盤口已結束"),
    BET_NOT_EXIST("3004","盘口不存在","Market does not exist","Cược chấp không tồn tại","盤口不存在"),
    BET_CANCEL("3005","盘口已取消或者已结束","Market cancel or finish","Kèo chấp đã bị hủy bỏ hoặc đã kết thúc","盤口已取消或者已結束"),
    BET_NOT_ALLOW_CAMEO("3006","该盘口不接受串关","The Market does not accept the combination bet","Kèo này không chấp nhận cược xiên","該盤口不接受串關"),
    BET_OUT_LIMIT("3007","下注金额超出限额","Exceeds the limit","Tiền cược vượt quá giới hạn cược","下注金額超出限額"),
    PAY_ORDER_NOT_EQUAL("3008", "订单金额不匹配","Order amount does not match","Tiền cược không khớp","訂單金額不匹配"),
    PAY_ORDER_IS_SETTLED("3009", "请勿重复提交订单","Do not submit orders repeatedly","Vui lòng không gửi lại đơn cược","請勿重複提交訂單"),
    PAY_ORDER_NOT_FOUND("3010", "订单不存在","Betslip does not exist","Vé cược không tồn tại","訂單不存在"),
    BET_LOCK("3011","盘口已锁定","Market is locked","Kèo chấp đã bị khóa","盤口已鎖定"),

    //USER_CARD_IS_EXISTS("4001", "银行卡已存在"),
    //WITH_DRAW_CLOSE("4002", "取款通道关闭"),

    LIMIT("5001","超出调用次数","Exceed the number of calls","Vượt quá số lần điều động","超出調用次數"),
    TOO_MANY_RUN("5002","调用太频繁,请稍后再试","Please try again later","Điều động quá thường xuyên, vui lòng thử lại sau","調用太頻繁,請稍後再試"),

    //NO_TO_LIMIT("6001","未达到领取标准或已领取"),

    TIME_OUT("8001", "超时","time out","Hết giờ","超時"),

    UNKNOWN_ERROR("9999", "系统繁忙，请稍后再试....","The system is busy, please try again later","Hệ thống đang bận, vui lòng thử lại sau …","系統繁忙，請稍後再試…"),
    UNKNOWN_FUNCTION("9998", "接口不存在","Interface does not exist","Giao diện không tồn tại","介面不存在"),
    RESUBMIT("9997","请勿重复提交","Do not submit again","Vui lòng không gửi lại","請勿重複提交"),
    EXPORTERROR("9998","导出失败","Failed","Trích xuất thất bại","導出失敗"),

    OPERATIONFAIL("9999","操作失败","Failed","Thao tác thất bại","操作失敗"),

    BUSSINESS_NO_ROLE("10000","请先创建角色...","","",""),

    LOGIN_EXPIRED("10001","您已登出或者长时间未操作，当前登陆已失效，请重新登陆！","","",""),

    //Xbet 11000

    SYS_EXISTS("11000","该账号已存在","","",""),

    INOUT_DATE("11000","","","",""),

    OUT_ONE_MONTH("11000","","","",""),

    CLICK_FAST("11000","点击过于频繁，请稍后再试！","","",""),

    NO_JURISDICTION("11005","此账号没有权限","","",""),

    EXPORT_SUCCESS("11006","文件导出中,请稍后到导出列表中下载文件,文件最长保留7天!","","",""),

//    front 12000

    LOGIN_FAIL("12001","登录账号或密码不正确","","",""),

    PHONE_DEFIND("12002","手机号不存在","","",""),

    CODE_ERROR("12003","验证码不正确","","",""),

    PASSWORD_INCONSISTENT("12004","两次密码输入不一致","","",""),

    TYY_AGAIN("12005","请稍后再试！","","","");


    /**
     * 返回码
     */
    private String code;

    /**
     * 返回结果描述
     */
    private String message;

    private String message_en;

    private String message_vi;

    private String message_tc;

    ResultStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultStatus(String code, String message,String message_en,String message_vi,String message_tc) {
        this.code = code;
        this.message = message;
        this.message_en = message_en;
        this.message_vi = message_vi;
        this.message_tc = message_tc;
    }

    public String getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    public String getMessage_en() {
        return message_en;
    }

    public String getMessage_vi() {
        return message_vi;
    }

    public String getMessage_tc() {
        return message_tc;
    }

    @Override
    public String toString() {
        return "ResultStatus{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", message_en='" + message_en + '\'' +
                '}';
    }

    public static String showAllEnums(){
        StringBuffer stringBuffer = new StringBuffer();
        for (ResultStatus e : ResultStatus.values()) {
            stringBuffer.append("code:" + e.getCode());
            stringBuffer.append("  ,  ");
            stringBuffer.append("message:" +e.getMessage());
            stringBuffer.append("  ,  ");
            stringBuffer.append("message_en:" +e.getMessage_en());
            stringBuffer.append("\n");
            //System.out.println(e.toString());
        }
        return stringBuffer.toString();
    }
}
