package com.splan.bplan.http.pay;

import com.alibaba.fastjson.JSONObject;
import com.splan.bplan.utils.MD5Util;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class HyPayReqBody {
    private final String method = "applypay";

    private final String version = "1.0";

    private String appId;

    private final String mchUid = "3018049";

    private final String charset = "UTF-8";

    private Date timestamp;

    private BizContent bizContent;

    private final String sign_type = "md5";

    private String sign;

    private String key;

    @Data
    public class BizContent {
        private String outTradeNo;

        private String subject;

        private String attach;

        private String totalFee;

        private String channelType;

        private String clientIp;

        private String payOption;

        private String nofityUrl;

        private String returnUrl;

        public void setTotalFee(BigDecimal totalFee) {
            this.totalFee = totalFee.multiply(BigDecimal.valueOf(100)).toString();
        }
    }

    public Map<String, String> getData() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(this.timestamp);
        String md5key = "app_id=" + appId
                + "&biz_content=" + JSONObject.toJSONString(bizContent)
                + "&charset=" + charset
                + "&mch_uid=" + mchUid
                + "&method=" + method
                + "&sign_type=" + sign_type
                + "&timestamp=" + timestamp
                + "&version=" + version
                + "&key=" + key;
        String sign = MD5Util.crypt(md5key);
        Map<String, String> data = new HashMap<>();
        data.put("app_id", appId);
        data.put("biz_content", JSONObject.toJSONString(bizContent));
        data.put("charset", charset);
        data.put("mch_uid", mchUid);
        data.put("method", method);
        data.put("sign_type", sign_type);
        data.put("timestamp", timestamp);
        data.put("version", version);
        data.put("sign", StringUtils.upperCase(sign));
        return data;
    }

    public BizContent getBizContent() {
        return new BizContent();
    }

    public void setBizContent(BizContent bizContent) {
        this.bizContent = bizContent;
    }
}
