package com.bigerdranch.android.test.dao;

/**
 * Created by Administrator on 2017/12/4.
 */

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//AES算法加密
public class AesTest {
    public static byte[] encrypt(byte[] datasource, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        Key key = toKey(password.getBytes());
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, random);
        return cipher.doFinal(datasource);
    }
    //AES解密
    public static byte[] decrypt(byte[] src, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        Key key = toKey(password.getBytes());
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key, random);
        return cipher.doFinal(src);
    }
    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
    //二进制转换为字符串
    public static String byte2String(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString();
    }
    //字符串转换为二进制
    public static byte[] str2Byte(String strIn) throws NumberFormatException {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
//    public static void main(String[] args) throws Exception {
//        String str = "abddddc123tesaabbc";
//        String password = "1234567890ABCDEF";
//        System.out.println("加密前：" + str);
//        byte[] result = AesTest.encrypt(str.getBytes(),password);
//        String res = byte2String(result);
//        System.out.println("加密后："+res);
//        byte[] decryResult = AesTest.decrypt(str2Byte(res), password);
//        System.out.println("解密后："+new String(decryResult));
//
//    }

}
