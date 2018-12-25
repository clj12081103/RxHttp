//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cai.rxhttplib.utils;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    private static final String AES_ALG = "AES";
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final String[] HEX_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public EncryptUtil() {
    }

    public static String encryptContent(String content, String encryptType, String encryptKey) throws Exception {
        if("AES".equals(encryptType)) {
            try {
                String encryptContent = aesEncrypt(content, encryptKey);
                return encryptContent;
            } catch (Exception var5) {
                throw new Exception(var5.getLocalizedMessage());
            }
        } else {
            throw new Exception("当前不支持该算法类型：encrypeType=" + encryptType);
        }
    }

    public static String decryptContent(String content, String encryptType, String encryptKey) throws Exception {
        if (!TextUtils.isEmpty(content)) {
            if("AES".equals(encryptType)) {
                try {
                    String decryptContent = aesDecrypt(content, encryptKey);
                    return decryptContent;
                } catch (Exception var5) {
                    throw new Exception(var5.getLocalizedMessage());
                }
            } else {
                throw new Exception("当前不支持该算法类型：encrypeType=" + encryptType);
            }
        }
        return "";
    }

    public static String hmacSha1(String data, String key) throws Exception {
        try {
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return byteArrayToHexString(mac.doFinal(data.getBytes()));
        } catch (InvalidKeyException | NoSuchAlgorithmException var5) {
            throw new Exception(var5.getLocalizedMessage());
        }
    }

    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        SecretKeySpec skeySpec = getKey(encryptKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(1, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return android.util.Base64.encodeToString(encrypted, android.util.Base64.NO_WRAP);
    }

    public static String aesDecrypt(String content, String decryptKey) throws Exception {
        SecretKeySpec skeySpec = getKey(decryptKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(2, skeySpec, iv);
        byte[] original = cipher.doFinal(Base64.decode(content));
        String originalString = new String(original);
        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16];

        for(int i = 0; i < arrBTmp.length && i < arrB.length; ++i) {
            arrB[i] = arrBTmp[i];
        }

        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        byte[] var2 = b;
        int var3 = b.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte aB = var2[var4];
            sb.append(byteToHexString(aB));
        }

        return sb.toString();
    }

    private static String byteToHexString(byte b) {
        return HEX_DIGITS[(b & 240) >> 4] + HEX_DIGITS[b & 15];
    }
}
