package com.splan.xbet.admin.back.dto;

import com.splan.xbet.admin.back.utils.MD5Util;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayBackDto implements Serializable {
    private String amount;

    private String payment_reference;

    private String provider_reference;

    private String reference;

    private String status;

    private String sign;

    private String product_id;

    private String key;

    public boolean checkMD5(){
        String param = "amount=" + (null == amount ? "" : amount)
                + "&payment_reference=" + (null == payment_reference ? "" : payment_reference)
                + "&product_id=" + (null == product_id ? "" : product_id)
                + "&provider_reference=" + (null == provider_reference ? "" : provider_reference)
                + "&reference=" + (null == reference ? "" : reference)
                + "&status=" + (null == status ? "" : status)
                + "&key=" + (null == key ? "" : key);
        System.out.println(param);
        String sign = MD5Util.crypt(param);
        System.out.println(sign);
        if (sign.equalsIgnoreCase(this.sign)){
            return true;
        }
        return false;
    }
}
