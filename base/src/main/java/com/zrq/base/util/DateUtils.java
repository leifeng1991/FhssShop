package com.zrq.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 描述:和时间相关的工具类
 *
 * @author zhangrq
 * 2017/1/3 10:46
 */

@SuppressWarnings("unused")
public class DateUtils {
    /**
     * 获取时间戳timeInMillis的星期几
     *
     * @param timeInMillis 毫秒的时间戳
     * @return 返回格式为：星期XX
     */
    public static String getDayOfWeek(long timeInMillis) {
        //设置时间值
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(timeInMillis);
        int dayOfWeek = ca.get(Calendar.DAY_OF_WEEK) - 1;
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekDays[dayOfWeek];
    }

    /**
     * @return 格式： MM-dd HH:mm
     */
    public static String getMonthDayHourMinute(long timeInMillis) {
        return getSimpleDateFormatFormat(timeInMillis, "MM-dd HH:mm");
    }

    /**
     * @return 格式：yyyy-MM-dd HH:mm
     */
    public static String getYearMonthDayHourMinute(long timeInMillis) {
        return getSimpleDateFormatFormat(timeInMillis, "yyyy-MM-dd HH:mm");
    }

    /**
     * @return 格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getYearMonthDayHourMinuteSeconds(long timeInMillis) {
        return getSimpleDateFormatFormat(timeInMillis, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取 SimpleDateFormat.format()
     *
     * @param timeInMillis 时间
     * @param pattern      格式
     */
    @SuppressWarnings("WeakerAccess")
    public static String getSimpleDateFormatFormat(long timeInMillis, String pattern) {
        //设置时间值
        return new SimpleDateFormat(pattern, Locale.CHINA).format(timeInMillis);
    }

    /**
     * 获取时间戳timeInMillis的年月日
     *
     * @param timeInMillis 毫秒的时间戳
     * @return 返回说明：int[0]:年、int[1]:月（1-12）、int[2]:日
     */
    public static int[] getYearMonthDate(long timeInMillis) {
        int[] ints = new int[3];
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(timeInMillis);
        ints[0] = ca.get(Calendar.YEAR);//获取年份
        ints[1] = ca.get(Calendar.MONTH) + 1;//获取月份
        ints[2] = ca.get(Calendar.DATE);//获取日
        return ints;
    }

    /**
     * 将时间字符串转换为时间值（毫秒）
     */
    public static long getTimeInMillis(String source, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.CHINA).parse(source).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
