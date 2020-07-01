package com.cdxt.app.util;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Description: 日期工具类
 * @Author: tangxiaohui
 * @CreateDate: 2020/5/27 17:31
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
public class DateUtil {
    /**
     * 默认的日期格式化格式
     */
    public static final String DEFAUL_TIME_FORMATER = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat sdf = new SimpleDateFormat(DEFAUL_TIME_FORMATER);

    /**
     * 日期加减操作
     *
     * @param date     进行加减的日期
     * @param dateType 加减的类型  比如天、小时、分钟、秒等  详见Calendar类
     * @param adder    加上的天数、小时数、分钟数等  减去时为负数
     */
    public static Date getAddSubDate(Date date, int dateType, int adder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateType, adder);
        return calendar.getTime();
    }

    /**
     * 使用给定的格式对日期进行格式化
     */
    public static String formatDate(Date date, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 使用默认的格式进行日期格式化
     */
    public static String formatDate(Date date) {
        return DateUtil.formatDate(date, DEFAUL_TIME_FORMATER);
    }

    /**
     * 使用默认的格式进行日期格式化
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null) return null;
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 根据指定的格式解析日期
     */
    public static Date parseDate(String dateStr, String formater) {
        if (dateStr == null) return null;
        if (formater == null) formater = DEFAUL_TIME_FORMATER;
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
//        sdf.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * UTC格式转换
     */
    public static Date parseUTCDate(String dateStr, String formater) {
        if (dateStr == null) return null;
        dateStr = dateStr.replace("Z", "+0800");
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 将日期转成UTC格式字符串
     */
    public static String getDateUTCtring(String dateString, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        if (dateString.contains(".")) {
            try {
                date = df.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            date = DateUtil.getDateFromUSDate(dateString);

        }
        if (date == null) return "";
        String dateStr = sdf.format(date);
        return dateStr.replace("+0800", "Z");
    }

    /**
     * 将某种格式的日期转成指定格式字符串
     */
    public static String getAnotherDateString(String dateStr, String dateStrFormater, String targetDateStrFormater) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateStrFormater);
        SimpleDateFormat df = new SimpleDateFormat(targetDateStrFormater);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) return "";
        return df.format(date);
    }

    /**
     * 通过身份证号 获取出生日期
     */
    public static String getBirthDayByIdCard(String idcard) {
        if (idcard == null) {
            return "";
        }
        if (idcard.equals("") || idcard.length() < 18) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(idcard.substring(6, 10));
        sb.append("-");
        sb.append(idcard.substring(10, 12));
        sb.append("-");
        sb.append(idcard.substring(12, 14));
        return sb.toString();
    }


    /**
     * 获取现在时间
     */
    public static String getNowDate() {
        return DateUtil.formatDate(new Date());
    }


    /**
     * 通过指定格式的字符串获取日期对象
     */
    private static Date getDateFromUSDate(String dateString) {
        Date date = null;
        if (StringUtils.isEmpty(dateString)) return null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd hh:mm:ss z yyyy", Locale.US);
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 通过指定格式的字符串获取日期字符串
     */
    public static String getDateFromUSString(String dateString, String fomatter) {
        SimpleDateFormat formatter = new SimpleDateFormat(fomatter);
        Date date = DateUtil.getDateFromUSDate(dateString);
        if (date == null) return "";
        return formatter.format(date);
    }


    /**
     * @Description: 是否两个日期为同一天
     */
    public static boolean isSamDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    /**
     * @return: com.cdxt.inter.util.BirthAge
     * @description: 根据生日获取年龄
     * @Param birthDay:
     * @date: 2020/5/30 0030 11:04
     */
    public static BirthAge getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        if (age == 0) {
            if (monthNow < monthBirth) {
                age = monthNow + (11 - monthBirth);
            } else if (monthNow > monthBirth) {
                age = monthNow - monthBirth;
            } else if (monthNow == monthBirth) {
                age = dayOfMonthNow - dayOfMonthBirth;
                return new BirthAge(age, "3");
            }
            return new BirthAge(age, "2");
        }
        return new BirthAge(age, "1");
    }
}
