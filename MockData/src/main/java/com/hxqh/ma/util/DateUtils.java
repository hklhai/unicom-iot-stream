package com.hxqh.ma.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Lin
 * 日期时间工具类
 */
public class DateUtils {

    private static final String TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";

    /**
     * 判断一个时间是否在另一个时间之前
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean before(String time1, String time2) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT_STRING);
            Date dateTime1 = timeFormat.parse(time1);
            Date dateTime2 = timeFormat.parse(time2);

            if (dateTime1.before(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean after(String time1, String time2) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT_STRING);
            Date dateTime1 = timeFormat.parse(time1);
            Date dateTime2 = timeFormat.parse(time2);

            if (dateTime1.after(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算时间差值（单位为秒）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int minus(String time1, String time2) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT_STRING);

            Date datetime1 = timeFormat.parse(time1);
            Date datetime2 = timeFormat.parse(time2);

            long millisecond = datetime1.getTime() - datetime2.getTime();

            return Integer.valueOf(String.valueOf(millisecond / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取年月日和小时
     *
     * @param datetime 时间（yyyy-MM-dd HH:mm:ss）
     * @return 结果
     */
    public static String getDateHour(String datetime) {
        String date = datetime.split(" ")[0];
        String hourMinuteSecond = datetime.split(" ")[1];
        String hour = hourMinuteSecond.split(":")[0];
        return date + "_" + hour;
    }


    /**
     * 获取当天日期（yyyy-MM-dd）
     *
     * @return 当天日期
     */
    public static String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
        return dateFormat.format(new Date());
    }


    /**
     * 获取明天日期（yyyy-MM-dd）
     *
     * @return 当天日期
     */
    public static String getTomorrowDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, +1);

        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
        return dateFormat.format(date);
    }

    /**
     * 获取昨天的日期（yyyy-MM-dd）
     *
     * @return 昨天的日期
     */
    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -1);

        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
        return dateFormat.format(date);
    }

    /**
     * 格式化日期（yyyy-MM-dd）
     *
     * @param date Date对象
     * @return 格式化后的日期
     */
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
        return dateFormat.format(date);
    }

    /**
     * 格式化时间（yyyy-MM-dd HH:mm:ss）
     *
     * @param date Date对象
     * @return 格式化后的时间
     */
    public static String formatTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT_STRING);
        return timeFormat.format(date);
    }


    /**
     * 解析时间字符串
     *
     * @param time 时间字符串
     * @return Date
     */
    public static Date parseTime(String time) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT_STRING);
            return timeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
