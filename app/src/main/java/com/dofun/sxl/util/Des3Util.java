package com.dofun.sxl.util;

import com.blankj.utilcode.util.EncodeUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 运算模式CBC,ECB。 在CBC模式下使用key,向量iv;在ECB模式下仅使用key。
 *
 * @ClassName: Des3Util
 * @author: caimf
 */
public class Des3Util {

    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

    private static String defaultEncryptKey = "rhtt0615";

    public static String encodeDES(String encryptString, String encryptKey)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return EncodeUtils.base64Encode2String(encryptedData);
    }


    public static String encodeDES(String encryptString) {
        try {
            return encodeDES(encryptString, defaultEncryptKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}