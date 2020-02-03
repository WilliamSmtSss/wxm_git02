package com.splan.bplan.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }

    public static String privateDecrypt(String content,String privateKeyStr) throws Exception{
        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
        byte[] base642Byte = RSAUtil.base642Byte(content);
        byte[] outString = RSAUtil.privateDecrypt(base642Byte,privateKey);
        return new String(outString);
    }

//    public static void main(String[] args) {
//        KeyPair keyPair = null;
//        try {
//            keyPair = RSAUtil.getKeyPair();
//
//        String publicKeyStr = RSAUtil.getPublicKey(keyPair);
//        String privateKeyStr = RSAUtil.getPrivateKey(keyPair);
//        System.out.println("RSA公钥Base64编码:" + publicKeyStr);
//        System.out.println("RSA私钥Base64编码:" + privateKeyStr);
//
//        //=================客户端=================
//        //hello, i am infi, good night!加密
//        String message = "hello, i am infi, good night!";
//        //将Base64编码后的公钥转换成PublicKey对象
//        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
//        //用公钥加密
//        byte[] publicEncrypt = RSAUtil.publicEncrypt(message.getBytes(), publicKey);
//        //加密后的内容Base64编码
//        String byte2Base64 = RSAUtil.byte2Base64(publicEncrypt);
//        System.out.println("公钥加密并Base64编码的结果：" + byte2Base64);
//
//
//        //##############	网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容     #################
//
//
//
//        //===================服务端================
//        //将Base64编码后的私钥转换成PrivateKey对象
//        PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
//        //加密后的内容Base64解码
//        byte[] base642Byte = RSAUtil.base642Byte(byte2Base64);
//        //用私钥解密
//        byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
//        System.out.println(privateDecrypt);
//        //解密后的明文
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public static byte[] encryptPrivateKey(byte[] binaryData, String privateKey) throws Exception {
        byte[] keyBytes = decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key priKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        return cipher.doFinal(binaryData);
    }

    public static String encodeBase64(byte[] binaryData) {
        return Base64.encode(binaryData);
    }

    public static byte[] decodeBase64(String encoded){
        return Base64.decode(encoded);
    }



    public static void main(String[] args) throws Exception {

//        String content = "y2WFh9/mDHSPXKVT+YtMYjdzogrtllKQu6z/AVXssVFtsLju10uLNmH2hY/CVJVgnVzDh9lWRcEZOiw8st7oue/6QUU6xj0LnMxUXrooA7yI/H/GudfuUsuEUJOOGdGS9UIzTSU6c/Ag8BUdq83Fh717T0R34Q16l8AG9tyLRy4=";
//        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANmSnjrvjLvYmm4Mafs+ilm/V0NsXgbqnIaZiRr6Soypi7am/f+H8pwjoW6qHsAVCSwrYDouEd4RIDQNZQtgVUUFgdeHrFYee8hJfWMzdXvirYyZfiyWWltnPjkRcb2onbzgsX71fdygFbe2BO1gr1CaYteznEYP6dffABFaIIjFAgMBAAECgYBpkjMk9ceb/mYwr+Vc++O12JpHJPYa9QwuY6GbbYdu6XXZkv38V8Lo8IEMaY+yNILW49U1ZdVVo4m0uCsncf7tMa10NsF5WZnVRtxrSLQz5TRs1E28dyi2Eo3NIN1MGJle2Yl0ZPHAaMBYV53rlMz6NOIIiJb4jkaYTKaJpacioQJBAPQJEB+We9jf1DXI2WBYrpp9dOUSdQafnWxNlsqKKYUmH9NsGvFmr/pLN8dtC3ZrWaaDTB8Tmyhwm2a4ig6JmJsCQQDkPWqyjaP0saqGEgiXi1gO3faIF1fy3h8SrF6qQhazCnSl5w7Ciy9Emf7vH1wQlJrHFE/LJrO/33RYWFnsEwofAkBklSe0HU5GgVryICqYb3Tn8DWyBP1/EWLNNN2l9XRPkPslJTWlsfFudHo1FFjZIj39R7ikRmx5KpCisgUVINlpAkBiWVSwu/f6aNRiEUNCifjX7y1cVzK+pJgfI8dm9jNx0v+QvY/OpMspdHI19/1j56UKQ+ZeU78A3vjzWJ7m6w1hAkEAh0CubpkVUh2WPgRQLemyTBhqoAJrZjafIBJcRg4NzyQvOXDZ9tK8IjLHbSYXx3r7wlwZA4irFgGJHKm9CECi1A==";
        //String content = "Joiqv527yKEKBok+5W0i+UzNKjDB5xHdtRkitXO5AMoCXa4sYbnBPHTMU9L13k49PRlAYzlPiWPnGPXzp4TC2chN85GVtMLbqtF3CFfyfokWlGfKZ/0N2AdEReQ6yazeVcGgL1AHX9PxjLmfoHFcFKUb5xwUbFLy68AbRIUYoe0=";
        String content = "Bv+7uwGB71l65E+dFTB7zYkvnBUKVx5Ci1iTx2qtLzMmAn4sXXk48+LSJDgHZ4MEfrTWUXS8bQhWHOK3eD+pHwVd166ciaLnHdNqG18Xu8mLRVLOgDBt0n5Eea/guH2utWNAcGRAO9Kchx+lPAxHXi/Nf/DLFZ2i9fd5nb6Pd/c=";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIT0J1wclxjG9BPElgQvSAskiYQTE/Zi5CcKyTcg1Zlr6KWjhf80gHmj+dIMXf/wFXIpBDclw+lPYGcaViQ5VpnwMp0L0loKtO/O3xhumCdqOFVVtbmXu5L23D+JxtJK4q8TFXNMqU/aIvpqD/jJLP/7WHp+bu/o8zQHrp+ZopNDAgMBAAECgYAuHCMebgf1HuL9jBtfHirFOom61YcB13A7HiGEzg1mZSdNcvYA+WtpRHZa49Eh91jou9LrcVyNqOrNNzQS4hPnUoaqgYuoshvmtTEekzb5b5odGeN77yqrF/jw2wAFLLol+xWPRMH6tICeHoj87p3N95vGj62rr0U2mY4+sUwJyQJBAOVKqXQW7uCZZoS7c/w/JQBnWPxoXvGAbZ9NHBGKl65k44T+rmeR3RMgep0t2qWhoxPigg9su01CrUWabWsIOz0CQQCUcMDZkDXhk9QnmNxL/ZFhlotsrfADihYYhNiSB34xaLa89caAf2bmjd+l4LdBJZrnMYLaQO/nVX/HDxdNVPB/AkEAxYqTfy6a0umWR1hwTqvgJv7izWLmGU46I3p9aidrEpZc5iG9BAHI40eiG3R74SLmqqUExGE65Q9C1Kp9Bi9rSQJANu7HH3xzxCgN4h16oRCUhZXNwQlZgiXO6YH4F4p4U9aZkbIupI0BSp8EPgG8L3q4KYi0EQro/SYx5DucdvzRBwJBALkOBYXNROfrWm4SjLG1ExckLXmQsGgJ/G1Vf4NzIb64W49n+o7vKGdoeklvqAZF2H2c4l3UTHAr+nOdQAwHy0w=";
        System.out.println(privateDecrypt(content,privateKey));
    }

    public static String getMd5Sign(String content , String  privateKey) throws Exception {
        PrivateKey privateKey1 = string2PrivateKey(privateKey);
        byte[] contentBytes = content.getBytes("utf-8");
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey1);
        signature.update(contentBytes);
        byte[] signs = signature.sign();
        return Base64.encode(signs);
    }



}
