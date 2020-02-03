package com.splan.bplan.http.pay;

import com.splan.bplan.utils.MD5Util;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;

import java.math.BigDecimal;

@Data
public class JafuPayReqBody {
    private String parter;

    private int type;

    private BigDecimal value;

    private String orderId;

    private String callbackUrl;

    private String hrefbackUrl;

    private String payerIp;

    private String attach;

    private String key;

    public String getUrl() {
        String md5Key = "parter=" + parter
                + "&type=" + type
                + "&value=" + value
                + "&orderid=" + orderId
                + "&callbackurl=" + callbackUrl
                + key;
        String sign = MD5Util.crypt(md5Key);
        StringBuffer sb = new StringBuffer();
        sb.append("parter=").append(parter).append("&type=").append(type).append("&value=").append(value)
                .append("&orderid=").append(orderId).append("&callbackurl=").append(callbackUrl)
                .append("&hrefbackUrl=").append(hrefbackUrl).append("&payerId=").append(payerIp)
                .append("&attach=").append(attach).append("&sign=").append(StringUtils.lowerCase(sign));
        return sb.toString();
    }
}
