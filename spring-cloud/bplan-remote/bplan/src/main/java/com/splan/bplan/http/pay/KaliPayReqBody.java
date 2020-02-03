package com.splan.bplan.http.pay;

import com.splan.base.enums.KaliPayType;
import com.splan.bplan.utils.MD5Util;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class KaliPayReqBody {
    private BigDecimal amount;

    private String callbackUrl;

    private String channel;

    private int club = 1;

    private String currency = "CNY";

    private String ip;

    private int league = 1;

    private String loginName;

    private String payer;

    private String paymentReference;

    private String productId;

    private String key;

    public Map<String, String> getData() {
        String param = "amount=" + (null == amount ? "" : amount)
                + "&callback_url=" + (null == callbackUrl ? "" : callbackUrl)
                + "&channel=" + (null == channel ? "" : channel)
                + "&club=" + club
                + "&currency=" + (null == currency ? "" : currency)
                + "&ip=" + (null == ip ? "" : ip)
                + "&league=" + league
                + "&login_name=" + (null == loginName ? "" : loginName)
                + "&payer=" + (null == payer ? "" : payer)
                + "&payment_reference=" + (null == paymentReference ? "" : paymentReference)
                + "&product_id=" + (null == productId ? "" : productId)
                + "&key=" + (null == key ? "" : key);

        String sign = MD5Util.crypt(param);
        Map<String, String> data = new HashMap<>();
        data.put("amount", amount.toString());
        data.put("club", club + "");
        data.put("currency", currency);
        data.put("ip", ip);
        data.put("league", league + "");
        data.put("login_name", loginName);
        data.put("channel", channel);
        data.put("callback_url", callbackUrl);
        data.put("payer", payer);
        data.put("payment_reference", paymentReference);
        data.put("product_id", productId);
        data.put("sign",sign);
        return data;
    }

    //取款
    private String accountNumber;

    private String accountName;

    private String branch;

    private String bankName;

    public Map<String, String> getWithDrawData() {
        String param = "account_name=" + (null == accountName ? "" : accountName)
                + "&account_number=" + (null == accountNumber ? "" : accountNumber)
                + "&amount=" + (null == amount ? "" : amount)
                + "&bank_name=" + (null == bankName ? "" : bankName)
                + "&branch=" + (null == branch ? "" : branch)
                + "&callback_url=" + (null == callbackUrl ? "" : callbackUrl)
                + "&channel=" + (null == channel ? "" : channel)
                + "&club=" + club
                + "&currency=" + (null == currency ? "" : currency)
                + "&league=" + league
                + "&login_name=" + loginName
                + "&payment_reference=" + (null == paymentReference ? "" : paymentReference)
                + "&product_id=" + (null == productId ? "" : productId)
                + "&key=" + (null == key ? "" : key);

        String sign = MD5Util.crypt(param);
        Map<String, String> data = new HashMap<>();
        data.put("amount", amount.toString());
        data.put("club", club + "");
        data.put("currency", currency);
        data.put("league", league + "");
        data.put("login_name", loginName);
        data.put("channel", channel);
        data.put("callback_url", callbackUrl);
        data.put("payment_reference", paymentReference);
        data.put("product_id", productId);
        data.put("account_name", accountName);
        data.put("account_number", accountNumber);
        data.put("branch", branch);
        data.put("bank_name", bankName);
        data.put("sign",sign);
        return data;
    }
}
