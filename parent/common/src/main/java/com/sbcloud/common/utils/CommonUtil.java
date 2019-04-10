package com.sbcloud.common.utils;

import java.util.Random;

import org.springframework.util.StringUtils;

 
/**
 * 通用util
 *
 * @author
 *
 */
public class CommonUtil {
    /**
     * 文字前补零
     * 
     * @param str
     * @param strLength
     * @return
     */
    public static String addZeroForStr(String str, int strLength) {
        if (str.length() < strLength) {
            int youNumber = Integer.valueOf(str);
            str = String.format("%0" + strLength + "d", youNumber);
        }
        return str;
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
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toLowerCase(firstChar) + tail;
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toUpperCase(firstChar) + tail;
        return str;
    }

   

    /** 获取字符串str 左边len位数 */
    public static String getLeft(String str, int len) {
        if (len <= 0)
            return "";
        if (str.length() <= len)
            return str;
        else
            return str.substring(0, len);
    }

    

    public static int getRangeRandom(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /** 获取字符串str 右边len位数 */
    public static String getRight(String str, int len) {
        if (len <= 0)
            return "";
        if (str.length() <= len)
            return str;
        else
            return str.substring(str.length() - len, str.length());
    }

    /**
     * 拆分身份证信息
     */
    public static String getUserIdNoInfo(String userIdNo) {
        if (StringUtils.isEmpty(userIdNo)) {
            return "";
        }
        String sexStr = "", yearStr = "";
        if (userIdNo.length() == 18) {
            Integer sex = Integer.valueOf(userIdNo.substring(16, 17));
            if (sex % 2 == 0) {
                sexStr = "女";
            } else {
                sexStr = "男";
            }
            yearStr = userIdNo.substring(6, 10);
            return yearStr + "年 " + sexStr;
        }
        return "";
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

    public static String NullToZero(String str) {
        if (str == null || str.isEmpty()) {
            str = "0";
        }
        return str;
    }

    /**
     * 移除所有空格
     * 
     * @param str
     * @return
     */
    public static String removeSpace(String str) {
        return str.replaceAll("\\s*", "");
    }

    /**
     * 左边第I个
     *
     * @param i
     * @return
     */
    public static String stringLeft(int i, String core) {
        return core.substring(0, i);
    }

    /**
     *
     * @param i
     * @return
     */
    public static String stringMid(int start, int end, String core) {
        return core.substring(start, end);

    }

    /**
     * 右边第I个
     *
     * @param i
     * @return
     */
    public static String stringRight(int i, String core) {
        return core.substring(core.length() - i, core.length());
    }

    

    /** 小数 转 百分数 */
    public static String toPercent(Double str) {
        StringBuffer sb = new StringBuffer(Double.toString(str * 100.0000d));
        return sb.append("%").toString();
    }

    /** 百分数 转 小数 */
    public static Double toPercent2(String str) {
        if (str.charAt(str.length() - 1) == '%')
            return Double.parseDouble(str.substring(0, str.length() - 1)) / 100.0000d;
        return 0d;
    }
}
