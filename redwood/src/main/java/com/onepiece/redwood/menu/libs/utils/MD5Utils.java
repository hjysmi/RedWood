package com.onepiece.redwood.menu.libs.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2015/8/13.
 */
public class MD5Utils {
    /**
     * 加密
     *
     * @param key
     * @return
     */
    public static String decode(String key) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = messageDigest.getInstance("MD5");
            messageDigest.reset();
            //UTF-8编码
            try {
                messageDigest.update(key.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                buffer.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                buffer.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return buffer.toString();
    }
}
