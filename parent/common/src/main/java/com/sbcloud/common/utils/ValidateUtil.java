package com.sbcloud.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class ValidateUtil {
    private static final Pattern URL_PATTERN = Pattern.compile("[a-zA-z]+://[^\\s]*");
    private static final Pattern TELE_PHONE_PATTERN = Pattern.compile("\\d{3}-\\d{8}|\\d{4}-\\d{7}|d{8}");
    private static final Pattern POST_NUMBER_PATTERN = Pattern.compile("\\d{6}");
    private static final Pattern NUMBER_OR_ABC_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");
    private static final Pattern MONTH_PATTERN = Pattern.compile("^\\d{4}(-|/)(0?[1-9]|1[0-2])$");
    private static final Pattern ID_15_PATTERN = Pattern.compile("^\\d{15}$");
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(-)?(\\d*)(\\.(\\d*))?$");
    private static final Pattern DATE_PATTERN = Pattern.compile(
            "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))");
    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    private static final Pattern mobilePatt = Pattern.compile("^(?:\\+86|86)?1(\\d{10})$");

    private static final Pattern IP_PATTERN = Pattern.compile(
            "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");

    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    private final static String phoneReg = "\\bNokia|SAMSUNG|MIDP-2|CLDC1.1|SymbianOS|MAUI|UNTRUSTED/1.0"
            + "|Windows CE|iPhone|iPad|Android|BlackBerry|UCWEB|ucweb|BREW|J2ME|YULONG|YuLong|COOLPAD|TIANYU|TY-"
            + "|K-Touch|Haier|DOPOD|Lenovo|LENOVO|HUAQIN|AIGO-|CTC/1.0"
            + "|CTC/2.0|CMCC|DAXIAN|MOT-|SonyEricsson|GIONEE|HTC|ZTE|HUAWEI|webOS|GoBrowser|IEMobile|WAP2.0\\b";

    private final static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    // 移动设备正则匹配：手机端、平板
    private final static Pattern PHONE_TYPE_PATTERN = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);

    private final static Pattern TABLET_TYPE_PATTERN = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^[-\\+]?[.\\d]*$");

    /**
     * 判断是否是ajax请求
     * 
     * @param request
     * @return true是ajax请求 false不是
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 金钱验证
     *
     * @return
     */
    public static boolean isAmount(String amount) {
        if(amount.trim().length()==0)return false;
        return AMOUNT_PATTERN.matcher(amount).matches();
    }

    /**
     * 判断是否是一个中文汉字
     * 
     * @param c
     *            字符
     * @return true表示是中文汉字，false表示是英文字母
     * @throws UnsupportedEncodingException
     *             使用了JAVA不支持的编码格式
     */
    public static boolean isChineseChar(char c) throws UnsupportedEncodingException {
        // 如果字节数大于1，是汉字
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
        return String.valueOf(c).getBytes("GBK").length > 1;
    }

    /**
     * 验证日期格式, 匹配YYYY-MM-DD YYYY/MM/DD YYYY_MM_DD YYYY.MM.DD的形式 , 支持闰年
     *
     * @param date
     * @return
     */
    public static boolean isDate(String core) {
        return DATE_PATTERN.matcher(core).matches();
    }

    /** 判断是否为小数(包括整数,包括负数) 支持直接 .1 */
    public static boolean isDecimal(String arg) {
        return DECIMAL_PATTERN.matcher(arg).matches();
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * email
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String core) {
        return EMAIL_PATTERN.matcher(core).matches();
    }

    public static boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }

    public static boolean isEmpty(String core) {
        return core == null || core.trim().length() <= 0 ? false : true;
    }

    /**
     * 是否参数一所在月份的第一天 只支持yyyyMMdd的日期格式
     *
     * @param date1
     * @return
     * @throws ParseException
     */
    public static boolean isFirstDay(String core, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            calendar.setTime(sdf.parse(core));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return core.equals(sdf.format(calendar.getTime()));
    }

    /**
     * 验证是否为15位身份证
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCardWith15len(String core) {
        return ID_15_PATTERN.matcher(core).matches();
    }

    // 判断是否是一个IP
    public static boolean isIp(String ip) {
        return IP_PATTERN.matcher(ip).matches();
    }

    @SuppressWarnings("deprecation")
    public static boolean isLastDay(String core, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            calendar.setTime(sdf.parse(core));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        } // 画面上传过来的日期
        Date lastDate = calendar.getTime(); // 获得传过来的日期
        lastDate.setDate(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.setTime(lastDate);// /设置到最后一天
        return core.equals(sdf.format(calendar.getTime()));
    }

    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 检测是否是移动设备访问
     * 
     * @Title: check
     * @Date : 2014-7-7 下午01:29:07
     * @param userAgent
     *            浏览器标识
     * @return true:移动设备接入，false:pc端接入
     */
    public static boolean isMobileAccress(String userAgent) {
        if (null == userAgent) {
            return false;
        }
        Matcher matcherPhone = PHONE_TYPE_PATTERN.matcher(userAgent);
        Matcher matcherTable = TABLET_TYPE_PATTERN.matcher(userAgent);
        return matcherPhone.find() || matcherTable.find();
    }

    /**
     * 验证是否为年月格式, 匹配YYYY-MM YYYY/MM形式，例：2012-1，2012-01，2012/1，2012/01
     *
     * @param date
     * @return
     */
    public static boolean isMonth(String core) {
        return MONTH_PATTERN.matcher(core).matches();
    }

    /**
     * str空判断
     * 
     * @param str
     * @return
     * @author
     */
    public static boolean isNull(String str) {
        return (null == str || str.equalsIgnoreCase("null") || str.equals(""));
    }

    /**
     * 数字验证
     *
     * @return
     */
    public static boolean isNumber(String core) {
        return NUMBER_PATTERN.matcher(core).matches();
    }

    /**
     * 英数字-验证
     *
     * @param number
     * @return
     */
    public static boolean isNumberOrAbc(String core) {
        return NUMBER_OR_ABC_PATTERN.matcher(core).matches();
    }

    public static boolean isNumeric(String str) {
        return INTEGER_PATTERN.matcher(str).matches();
    }

    /**
     * 邮编验证
     *
     * @param number
     * @return
     */
    public static boolean isPostNumber(String core) {
        return POST_NUMBER_PATTERN.matcher(core).matches();
    }

    /**
     * 国内电话
     *
     * @param number
     * @return
     */
    public static boolean isTelePhone(String telephone) {
        return TELE_PHONE_PATTERN.matcher(telephone).matches();
    }

    /**
     * url
     *
     * @param core
     * @return
     */
    public static boolean isUrl(String core) {
        return URL_PATTERN.matcher(core).matches();
    }

    public static void main(String[] args) {
        String a = "1";
        String b = "1.1";
        String c = "+1";
        String d = "-1";
        String e = "1+1";
        String f = "+1.1";
        String g = "-.1";

        System.out.println(isDecimal(g));

    }

    public static String normalize(String mobile) {
        Matcher m = mobilePatt.matcher(mobile);
        if (m.find()) {
            return "1" + m.group(1);
        }
        return null;
    }

}
