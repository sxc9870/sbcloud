package com.sbcloud.common.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.sbcloud.common.SysConfig;


public class DateUtils {

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };

    /**
     * 线程安全的yyyyMMddHHmmss
     * 
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parseFull(String dateStr) throws ParseException {
        return threadLocal.get().parse(dateStr);
    }

    /**
     * 线程安全的yyyyMMddHHmmss
     * 
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static String formatFull(Date date) {
        return threadLocal.get().format(date);
    }

    // 增加或减少天数 某个日期
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DATE, num);
        return startDT.getTime();
    }

    // 增加或减少月数 当天
    public static Date addMonth(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.MONTH, num);
        return startDT.getTime();
    }

    // 增加或减少年数 当天
    public static Date addYear(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.YEAR, num);
        return startDT.getTime();
    }

    /**
     * @param stype
     *            返回值类型 0为多少天，1为多少个月，2为多少年 date1开始日期date2结束日期
     * @return
     * @throws ParseException 
     */
    public static int compareDate(String date1, String date2, int stype) throws ParseException {
        String formatStyle = SysConfig.DATE_FORMAT_YYYYMMMDD;
        DateFormat df = new SimpleDateFormat(formatStyle);
        return compareDate (df.parse(date1),df.parse(date2),stype);
    }

    public static int compareDate(Date date1, Date date2, int stype) {
        int n = 0;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(date1);
            c2.setTime(date2);
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        while (!c1.after(c2)) {
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1);// 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            }
        }

        n = n - 1;

        if (stype == 2) {
            int yushu = n % 365;
            n = yushu == 0 ? (n / 365) : ((n / 365) - 1);
        }

        // System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);
        return n;
    }

    /**
     * 比较两个时间
     * 
     * @param d1,d2
     * @return d1>=d2 返回true 否则返回false
     */
    public static boolean compareTime(Date d1, Date d2) {
        boolean flag = true;
        try {
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            int result = c1.compareTo(c2);
            if (result < 0) {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 比较2个时间是否是同一天
     *
     * @return true代表同一天false代表不是同一天
     */
    public static boolean compareTwoDate(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            return true;
        }
        SimpleDateFormat format = new SimpleDateFormat(SysConfig.DATE_FORMAT_YYYYMMMDD);
        String temp1 = format.format(date1);
        String temp2 = format.format(date2);
        if (temp1.equals(temp2)) {
            return true;
        }
        return false;
    }

    /**
     * 年月日加减
     *
     * @param number
     *            数字
     * @param date
     *            日期
     * @param format
     *            格式
     * @param flg
     *            M/Y/D
     * @return
     * @throws ParseException
     */
    public static String dateAddByFlg(int number, String date, String format, String flg) throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        c.setTime(sdf.parse(date));
        int flgType = Calendar.DATE;
        if (flg.equalsIgnoreCase("M"))
            flgType = Calendar.MONTH;
        if (flg.equalsIgnoreCase("Y"))
            flgType = Calendar.YEAR;
        c.add(flgType, number);
        return sdf.format(c.getTime());
    }

    /**
     * 年月日加减
     *
     * @param number
     *            数字
     * @param date
     *            日期
     * @param format
     *            格式
     * @param flg
     *            M/Y/D
     * @return
     * @throws ParseException
     */
    public static String dateAddByFlg(int number, Date date, String format, String flg) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        c.setTime(date);
        int flgType = Calendar.DATE;
        if (flg.equalsIgnoreCase("M"))
            flgType = Calendar.MONTH;
        if (flg.equalsIgnoreCase("Y"))
            flgType = Calendar.YEAR;
        c.add(flgType, number);
        return sdf.format(c.getTime());
    }

    public static String dateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(SysConfig.DATE_FORMAT_YYYYMMMDD);
        return format.format(date);
    }

    /**
     * 将时间转为字符串。
     *
     * @param date
     *            需要格式化的时间。
     * @return String 传入时间的格式化字符串。
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfig.DATE_FORMAT_YYYYMMMDD);
        return sdf.format(date);
    }

    public static String formatDateChin(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);
    }

    public static String formatDateD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(date);
    }

    public static String formatDateDou(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");
        return sdf.format(date);
    }

    public static String formatDateH(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(date);
    }

    public static String formatDateHMS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(date);
    }

    public static String formatDateHMSMsec(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
        return sdf.format(date);
    }

    public static String formatDateHMSMsecMao(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss SSS");
        return sdf.format(date);
    }

    public static String formatDateM(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(date);
    }

    public static String formatDateMd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(date);
    }

    public static String formatDateMsec(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("SSS");
        return sdf.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String formatDateYMDHMS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     *
     * @param date
     * @param from
     * @param to
     * @return
     * @throws ParseException
     */
    public static String formatDateTime(String date, String from, String to) throws ParseException {
        if (date == null || date.trim().length() == 0)
            return null;
        return new SimpleDateFormat(to).format(new SimpleDateFormat(from).parse(date));
    }

    public static String formatDateY(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(date);
    }

    public static String formatDateYM(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(date);
    }

    public static String formatDateYMd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/");
        return sdf.format(date);
    }

    public static String formatDateYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String formatDateYMdHms(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    /**
     * 将字符串转化为JAVA时间类型(精确到秒)。
     *
     * @param dateStr
     *            字符串。
     * @return date JAVA时间类型。
     */
    public static Date formatFullString(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatMSDates(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return sdf.format(date);
    }

    public static Date formatNoSString(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串转化为JAVA时间类型。
     *
     * @param dateStr。字符串。
     * @return date。JAVA时间类型。
     */
    public static Date formatYYYYMMDD(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfig.DATE_FORMAT_YYYYMMMDD);
        try {
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到传入日期n天后的日期,如果传入日期为null，则表示当前日期n天后的日期
     * 
     * @param dt
     * @param days
     *            可以为任何整数，负数表示前days天，正数表示后days天
     * @return Date
     */
    public static Date getAddDayDateNext(Date dt, int days) {
        if (dt == null)
            dt = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        // cal.set(Calendar.MILLISECOND, 999); // mysql5.6后毫秒进1
        return cal.getTime();
    }

    /**
     * 得到传入日期n天后的日期,如果传入日期为null，则表示当前日期n天后的日期
     *
     * @param dt
     * @param days
     *            可以为任何整数，负数表示前days天，正数表示后days天
     * @return Date
     */
    public static Date getAddDayDatePrv(Date dt, int days, boolean resetTime) {
        if (dt == null)
            dt = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days);
        if (resetTime) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        return cal.getTime();
    }

    /**
     * 得到传入日期n小时后的日期,如果传入日期为null，则表示当前日期n小时后的日期
     * 
     * @param date
     *            传入日期
     * @param hours
     *            可以为任何整数，负数表示前hours小时，正数表示后hours小时
     * @return Date
     */
    public static Date getAddHoursTime(Date date, int hours) {
        if (date == null)
            date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    /**
     * 得到传入日期n分钟后的日期,如果传入日期为null，则表示当前日期n小时后的日期
     * 
     * @param date
     *            传入日期
     * @param minutes
     *            可以为任何整数，负数表示前minutes小时，正数表示后minutes小时
     * @return Date
     */
    public static Date getAddMinutesTime(Date date, int minutes) {
        if (date == null)
            date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    /**
     * 获得当前日期 前day 天的日期
     *
     * @param day
     * @param format
     * @return
     */
    public static String getCurrentDateBeforeOrAfterDay(int day, String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, day);// 日期+-
        return new SimpleDateFormat(format).format(c.getTime());
    }

    /**
     *
     * @param format
     * @return
     */
    public static String getCurrentDateTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 从1970年至今的秒数得到日期
     *
     * @param seconds
     *            秒
     * @return
     */
    public static Date getDateByMillis(Integer seconds) {
        BigDecimal b = new BigDecimal(seconds).multiply(new BigDecimal(1000));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(b.longValue());
        return calendar.getTime();
    }

    /**
     * 获得指定日期所在的月之后某月的最后一天
     * 
     * @param date
     *            日期
     * @param Months
     *            月数 1为本月
     * @return 获得指定日期所在的月之后某月的最后一天
     */
    @SuppressWarnings("deprecation")
    public static Date getDateByMonth(Date date, int Months) {
        int tempMonth = date.getMonth() + 1 + Months;
        int years = tempMonth / 12;
        int month = tempMonth % 12;
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, date.getYear() + 1900 + years);
        time.set(Calendar.MONTH, month - 1);// Calendar对象默认一月为0
        time.set(Calendar.DATE, time.get(Calendar.DATE) - 1);
        time.set(Calendar.HOUR, 11);
        time.set(Calendar.MINUTE, 59);
        time.set(Calendar.SECOND, 59);
        return time.getTime();
    }

    /**
     * 设置时间为最后1秒
     * 
     * @return Date
     */
    public static Date getDateEndTime(Date date) {
        if (date == null)
            date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 将CST的时间字符串转换成需要的日期格式字符串<br>
     *
     * @param cststr
     *            The source to be dealed with. <br>
     *            (exp:Fri Jan 02 00:00:00 CST 2009)
     * @param fmt
     *            The format string
     * @return string or <code>null</code> if the cststr is unpasrseable or is
     *         null return null,else return the string.
     * @author HitSnail
     */
    public static String getDateFmtStrFromCST(String cststr, String fmt) {
        if ((null == cststr) || (null == fmt)) {
            return null;
        }
        String str = null;
        SimpleDateFormat sdfy = new SimpleDateFormat(fmt.trim());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
        try {
            str = sdfy.format(sdf.parse(cststr.trim()));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return str;
    }

    /**
     * 通过格式化字符串得到时间
     *
     * @param parrern
     *            格式化字符串 例如：yyyy-MM-dd
     * @param str
     *            时间字符串 例如：2007-08-01
     * @return 出错返回null
     */
    public static Date getDateFromPattern(String parrern, String str) {
        if (str == null || ("").equals(str))
            // if (StringUtils.isEmpty(str))
            return null;
        SimpleDateFormat fmt = new SimpleDateFormat(parrern);
        try {
            return fmt.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日期的秒数
     */
    public static Integer getDateMillis(Date date) {
        if (date == null) {
            date = new Date();
        }
        return new BigDecimal(date.getTime()).divide(new BigDecimal(1000)).intValue();
    }

    /**
     * 获取日期号
     */
    public static String getDateNum(Date date, Integer num) {
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfig.DATE_FORMAT_YYYYMMMDD);
        String dateStr = sdf.format(DateUtils.getAddDayDatePrv(date, num, false));
        return dateStr.substring((dateStr.length() - 2), dateStr.length());
    }

    /**
     * 获取早中晚
     *
     * @return
     */
    public static String getDateSx() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 8) {
            return "早上好";
        } else if (hour >= 8 && hour < 11) {
            return "上午好";
        } else if (hour >= 11 && hour < 13) {
            return "中午好";
        } else if (hour >= 13 && hour < 18) {
            return "下午好";
        } else {
            return "晚上好";
        }
    }

    /**
     * @param d2
     * @return day
     * @see根据传入的两个时间求时间间隔 (返回天数)
     */
    public static int getDayBetweenDay(java.util.Date d1, java.util.Date d2) {
        Date[] d = new Date[2];
        d[0] = d1;
        d[1] = d2;
        Calendar[] cal = new Calendar[2];
        for (int i = 0; i < cal.length; i++) {
            cal[i] = Calendar.getInstance();
            cal[i].setTime(d[i]);
        }
        long m = cal[0].getTime().getTime();
        long n = cal[1].getTime().getTime();
        // 取间隔天数
        int daytime = (int) ((m - n) / (1000 * 60 * 60 * 24));
        if (daytime > 0) {
            return daytime;
        }
        return 0;
    }

    /**
     * @param d2
     * @return second
     * @see根据传入的两个时间求时间间隔 (返回秒)
     */
    public static int getDayBetweenSecond(java.util.Date d1, java.util.Date d2) {
        // return (int)(d1.getTime()-d2.getTime())/(1000*60*60*24);
        Date[] d = new Date[2];
        d[0] = d1;
        d[1] = d2;
        Calendar[] cal = new Calendar[2];
        for (int i = 0; i < cal.length; i++) {
            cal[i] = Calendar.getInstance();
            cal[i].setTime(d[i]);
            cal[i].set(Calendar.HOUR_OF_DAY, 0);
            cal[i].set(Calendar.MINUTE, 0);
            cal[i].set(Calendar.SECOND, 0);
        }
        long m = cal[0].getTime().getTime();
        long n = cal[1].getTime().getTime();
        return (int) ((m - n) / 1000);
    }

    /**
     * 获得当前月之后某月有多少天
     * 
     * @param date
     *            月份所在的时间
     * @return 当前月之后某月多少天
     */
    @SuppressWarnings("deprecation")
    public static int getDayByMonth(Date date, int months) {
        int tempMonth = date.getMonth() + 1 + months;
        int years = tempMonth / 12;
        int month = tempMonth % 12;
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, date.getYear() + years);
        time.set(Calendar.MONTH, month - 1);// Calendar对象默认一月为0
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
        return day;
    }

    /**
     * 设置日期的时分秒毫秒
     * 
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @param millsecond
     * @return
     */
    public static Date getDayHMS(Date date, int hour, int minute, int second, int millsecond) {
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minute);
        time.set(Calendar.SECOND, second);
        time.set(Calendar.MILLISECOND, millsecond);
        return time.getTime();
    }

    /**
     * @see两个日期的差距(天数)
     */
    public static long getDistDates(Date startDate, Date endDate) {
        long totalDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
        return totalDate;
    }

    /**
     * @see两个日期的差距(毫秒)
     */
    public static Long getDistDatesInMillis(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) {
            return null;
        }
        return startDate.getTime() - endDate.getTime();
    }

    /**
     * 返回参数日期所在天的最后一秒（如2011-11-30 23:59:59） <b>时分秒为23:59:59 999</b>
     *
     * @param String
     *            时间
     * @return String 时间
     */
    public static String getEndTimeOfDate(String date) {
        if (ValidateUtil.isEmpty(date)) {
            return null;
        }
        date = date.substring(0, 10);
        return date + "T23:59:59+08:00";
    }

    /**
     * 获得当年的第一天。
     *
     * @return Date数组。第一位是当年的第一天和第二位是当年的最后一天。
     */
    public static Date[] getFirstAndLastDays(String yyyy) {
        Date date = getDateFromPattern(SysConfig.DATE_FORMAT_YYYYMMMDD, yyyy + "-01-01");
        String dateStr = formatDate(date);
        String year = dateStr.substring(0, 4);

        // 当年第一天的字符串形式。
        String firstDayStr = dateStr.replaceFirst(year + "-\\d{2}-\\d{2}", year + "-01-01");

        // 当年最后一天的字符串形式。
        String lastDayStr = dateStr.replaceFirst(year + "-\\d{2}-\\d{2}", year + "-12-31");

        Date firstDay = formatYYYYMMDD(firstDayStr);
        Date lastDay = formatYYYYMMDD(lastDayStr);
        return new Date[] { firstDay, lastDay };
    }

    /**
     * 获得本月份的第一天
     * 
     * @param date
     *            月份所在的时间
     * @return 月份的最后一天
     */
    public static Date getFirstDateByMonth(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }

    /*
     * 获取传入时间的当前月的第一天
     */
    @SuppressWarnings("deprecation")
    public static Date getFristDayOfMonth(Date sDate1) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(sDate1);
        final int fristDay = cDay1.getActualMinimum(Calendar.DAY_OF_MONTH);
        Date fristDate = cDay1.getTime();
        fristDate.setDate(fristDay);
        return fristDate;
    }

    // 返回java.sql.date
    public static java.sql.Date getFullSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获得本月的最后一天
     * 
     * @param date
     * @return 月份的第一天
     */
    public static Date getLastDateByMonth(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
        now.set(Calendar.DATE, 1);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
        now.set(Calendar.HOUR, 11);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        return now.getTime();
    }

    /**
     * 获得本周的最后一天
     * 
     * @param date
     * @return
     */
    @SuppressWarnings("static-access")
    public static Date getSundayOfWeek(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(now.DAY_OF_WEEK, now.SUNDAY);
        return now.getTime();
    }

    public static Date getMondayOfWeek(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(now.DAY_OF_WEEK, now.MONDAY);
        return now.getTime();
    }

    @SuppressWarnings("deprecation")
    public static Date getLastDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime((date));
        date.setDate(c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return date;
    }

    /**
     * 获得某月的剩余天数
     * 
     * @param date
     * @param Months
     * @return
     */
    public static int getLastDayByMonth(Date date, int Months) {
        return getDayBetweenDay(getDateByMonth(new Date(), Months), date) / 86400;
    }

    /**
     * @param months
     *            月份数
     * @return
     */
    public static Date getLastDayByMonth(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + months - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /*
     * 获得传入时间的当月最后一天
     */
    @SuppressWarnings("deprecation")
    public static Date getLastDayOfMonth(Date sDate1) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(sDate1);
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }

    /**
     * 根据传入的两个时间求时间间隔
     * 
     * @param d1,d2
     * @return 返回时间间隔*毫秒
     */
    public static int getMillSecondsBetween(java.util.Date d1, java.util.Date d2) {
        Date[] d = new Date[2];
        d[0] = d1;
        d[1] = d2;
        Calendar[] cal = new Calendar[2];
        for (int i = 0; i < cal.length; i++) {
            cal[i] = Calendar.getInstance();
            cal[i].setTime(d[i]);
        }
        long m = cal[0].getTime().getTime();
        long n = cal[1].getTime().getTime();
        // 取间隔毫秒数
        int secondtime = (int) (m - n);
        return (Math.abs(secondtime) >= 0) ? Math.abs(secondtime) : 0;
    }

    /**
     * dateU 往前推X小时X分钟 或者往后推
     * 
     * @param dateU
     *            为当前时间
     * @param minTime
     *            为想减去的时间
     * @return
     * @throws Exception
     */
    public static Date getMinDate(Date dateU, String minTime, Long flag) {
        Date wantDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // 转换为02:30
        // 2小时30分钟
        try {
            wantDate = sdf.parse(minTime);
        } catch (ParseException e) {
            wantDate = new Date(0);
            e.printStackTrace();
        }
        String strDate = sdf.format(wantDate);
        int ss = 0;// 转换后的毫秒数
        if (null != strDate) {
            int Hour = Integer.parseInt(strDate.split(":")[0].toString());
            int minute = Integer.parseInt(strDate.split(":")[1].toString());
            ss = Hour * 60 * 60 * 1000 + minute * 60 * 1000;
        }
        Long chaSec = 0L;
        if (flag == 1) {// 往前推
            chaSec = dateU.getTime() - ss;
        } else if (flag == 2) {
            chaSec = dateU.getTime() + ss;
        }
        Date d = new Date(chaSec);
        return d;

    }

    // 百分比
    public static float getPercent() {
        Calendar cc = Calendar.getInstance();
        cc.setTime(new Date());
        float currmum = cc.get(Calendar.DAY_OF_MONTH);
        float maxmum = cc.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(currmum / maxmum);
        return currmum / maxmum;
    }

    // 天数差
    public static int getQuot() {
        Calendar cc = Calendar.getInstance();
        cc.setTime(new Date());
        int currmum = cc.get(Calendar.DAY_OF_MONTH); // 当月的第几天
        int maxmum = cc.getActualMaximum(Calendar.DAY_OF_MONTH); // 当月最大天数
        return (maxmum - currmum);
    }

    /**
     * 根据年月算出剩余天数(包含当天)
     *
     * @param year
     * @param month
     *            真实月份 1-12
     * @return
     */
    public static int getRemainDaysByYearMonth(int year, int month) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:OO"));
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        if (cYear == year && cMonth + 1 == month) {
            int today = c.get(Calendar.DAY_OF_MONTH);
            int last = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            return last - today + 1;
        } else {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month - 1);
            return c.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }

    /**
     * 获得当日零点
     * 
     * @param date
     * @return
     */
    public static Date getStartTimeByDay(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }

    /**
     * 根据传入的两个时间求时间间隔
     * 
     * @param d1,d2
     * @return 返回时间间隔，如*秒钟，*分钟，*小时，*天
     */
    public static String getTimeBetween(java.util.Date d1, java.util.Date d2) {
        Date[] d = new Date[2];
        d[0] = d1;
        d[1] = d2;
        Calendar[] cal = new Calendar[2];
        for (int i = 0; i < cal.length; i++) {
            cal[i] = Calendar.getInstance();
            cal[i].setTime(d[i]);
        }
        long m = cal[0].getTime().getTime();
        long n = cal[1].getTime().getTime();
        long submn = m - n;
        StringBuffer sb = new StringBuffer();
        // 取间隔天数
        int daytime = (int) (submn / (1000 * 60 * 60 * 24));
        if (Math.abs(daytime) > 0) {
            sb.append(Math.abs(daytime) + " 天 ");
        }
        // 取间隔小时数
        int hourtime = (int) (submn % (1000 * 60 * 60 * 24) / (1000 * 60 * 60));
        if (Math.abs(hourtime) >= 0) {
            sb.append(Math.abs(hourtime) + " 小时 ");
        }
        // 取间隔分钟数
        int minuteime = (int) (submn % (1000 * 60 * 60 * 24) % (1000 * 60 * 60) / (1000 * 60));
        if (Math.abs(minuteime) >= 0) {
            sb.append(Math.abs(minuteime) + " 分 ");
        }
        // 取间隔秒钟数
        int secondtime = (int) (submn % (1000 * 60 * 60 * 24) % (1000 * 60 * 60) % (1000 * 60) / 1000);
        if (Math.abs(secondtime) >= 0) {
            sb.append(Math.abs(secondtime) + " 秒");
        }
        return sb.toString();
    }

    /**
     * 返回两个日期相差的时间 [d天H时m分]
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static String getTimeDHMBetween(java.util.Date d1, java.util.Date d2) {
        String timeBetween = getTimeBetween(d1, d2);
        int lastIndexOf = timeBetween.lastIndexOf("分");
        if (lastIndexOf > -1) {
            timeBetween = timeBetween.substring(0, lastIndexOf + 1);
        }
        timeBetween = timeBetween.replace("小", "").replace(" ", "");
        return timeBetween;
    }

    /**
     * 获取星期几
     */
    public static String getWeekNum(Date date, Integer num) {
        String weekStr = DateUtils.getWeekOfDateStr(DateUtils.getAddDayDatePrv(date, num, false));
        return weekStr.substring((weekStr.length() - 1), weekStr.length());
    }

    /**
     * 获取日期是星期几<br>
     * 想返回数字:1为周一2为周二，去掉数组weekDays,直接返回w 想返回汉字周几见下
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0 || w == 0) {
            w = 7;
        }
        return w;
    }

    /**
     * 获取日期是星期几<br>
     *
     * 想返回汉字周几见下
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDateStr(Date dt) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        // if (w < 0||w==0){
        // w = 7;
        // }
        return weekDays[w];
    }

    /**
     * 比较2个日期是否是同一天
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (formatDate(date1).equals(formatDate(date2))) {
            return true;
        }
        return false;
    }

    public static Date objToDate(Object objDate) throws Exception {
        String dateString = objDate.toString();
        SimpleDateFormat format = new SimpleDateFormat(SysConfig.DATE_FORMAT_YYYYMMMDD);
        return format.parse(dateString);
    }

    public static Date objToFullDate(Object objDate) throws Exception {
        if (objDate == null)
            return null;
        String dateString = objDate.toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(dateString);
    }

    public static Date strToDate(String strDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(SysConfig.DATE_FORMAT_YYYYMMMDD);
        return format.parse(strDate);
    }

}
