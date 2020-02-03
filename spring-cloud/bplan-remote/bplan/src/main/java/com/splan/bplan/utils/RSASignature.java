package com.splan.bplan.utils;

import sun.misc.BASE64Encoder;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RSASignature {

    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA256withRSA";


    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String sign(String content, PrivateKey privateKey, String encode) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return byte2Hex(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String sign(String content, PrivateKey privateKey) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return byte2Hex(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sign(String content, String privateKey1) {
        try {
            PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKey1);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update(content.getBytes("utf-8"));
            byte[] signed = signature.sign();
            return new BASE64Encoder().encode(signed);
            //return byte2Hex(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将byte[] 转换成字符串
     */
    public static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean doCheck(String content, String sign, PublicKey publicKey, String encode) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(publicKey);
            signature.update(content.getBytes(encode));

            boolean bverify = signature.verify(hex2Bytes(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean doCheck(String content, String sign, PublicKey publicKey) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(content.getBytes("utf-8"));
            boolean bverify = signature.verify(hex2Bytes(sign));
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 将16进制字符串转为转换成字符串
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    public static void main(String[] args) throws Exception {
        String content = "amount=10&bet_option_id=345&odd=1.16&tenant_customer_no=10000&tenant_order_no=STORM20190226161118759579";
        String md5 = MD5Util.crypt(content);
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIT0J1wclxjG9BPElgQvSAskiYQTE/Zi5CcKyTcg1Zlr6KWjhf80gHmj+dIMXf/wFXIpBDclw+lPYGcaViQ5VpnwMp0L0loKtO/O3xhumCdqOFVVtbmXu5L23D+JxtJK4q8TFXNMqU/aIvpqD/jJLP/7WHp+bu/o8zQHrp+ZopNDAgMBAAECgYAuHCMebgf1HuL9jBtfHirFOom61YcB13A7HiGEzg1mZSdNcvYA+WtpRHZa49Eh91jou9LrcVyNqOrNNzQS4hPnUoaqgYuoshvmtTEekzb5b5odGeN77yqrF/jw2wAFLLol+xWPRMH6tICeHoj87p3N95vGj62rr0U2mY4+sUwJyQJBAOVKqXQW7uCZZoS7c/w/JQBnWPxoXvGAbZ9NHBGKl65k44T+rmeR3RMgep0t2qWhoxPigg9su01CrUWabWsIOz0CQQCUcMDZkDXhk9QnmNxL/ZFhlotsrfADihYYhNiSB34xaLa89caAf2bmjd+l4LdBJZrnMYLaQO/nVX/HDxdNVPB/AkEAxYqTfy6a0umWR1hwTqvgJv7izWLmGU46I3p9aidrEpZc5iG9BAHI40eiG3R74SLmqqUExGE65Q9C1Kp9Bi9rSQJANu7HH3xzxCgN4h16oRCUhZXNwQlZgiXO6YH4F4p4U9aZkbIupI0BSp8EPgG8L3q4KYi0EQro/SYx5DucdvzRBwJBALkOBYXNROfrWm4SjLG1ExckLXmQsGgJ/G1Vf4NzIb64W49n+o7vKGdoeklvqAZF2H2c4l3UTHAr+nOdQAwHy0w=";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCE9CdcHJcYxvQTxJYEL0gLJImEExP2YuQnCsk3INWZa+ilo4X/NIB5o/nSDF3/8BVyKQQ3JcPpT2BnGlYkOVaZ8DKdC9JaCrTvzt8YbpgnajhVVbW5l7uS9tw/icbSSuKvExVzTKlP2iL6ag/4ySz/+1h6fm7v6PM0B66fmaKTQwIDAQAB";
        String signstr = RSASignature.sign(md5, privateKey);
        System.out.println("签名原串：" + md5);
        System.out.println("签名串：" + signstr);
        System.out.println();

        System.out.println("---------------公钥校验签名------------------");
        System.out.println("签名原串：" + md5);
        System.out.println("签名串：" + signstr);

        System.out.println("验签结果：" + RSASignature.doCheck(md5, signstr, RSAUtil.string2PublicKey(publicKey)));
        System.out.println();
    }
}
