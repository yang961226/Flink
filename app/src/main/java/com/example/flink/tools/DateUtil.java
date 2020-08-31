package com.example.flink.tools;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_DIY = "yyyy/MM/dd";

    /**
     * 英文全称  如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 中文简写  如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";

    /**
     * 中文全称  如：2010年12月01日  23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 获得默认的 date pattern
     */
    public static String getDefaultDatePattern() {
        return FORMAT_SHORT;
    }

    /**
     * 根据预设格式返回当前日期
     * @return 对应的日期
     */
    public static String getNow() {
        return format(new Date());
    }

    public static Date getDate(){
        return new Date();
    }

    /**
     * 使用预设格式格式化日期
     * @param date 日期
     * @return 格式化的日期
     */
    public static String format(Date date) {
        return format(date, getDefaultDatePattern());
    }

    /**
     * 日期转字符串
     * @param date 日期
     * @param pattern 日期格式
     * @return 对应的字符串
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern,Locale.getDefault());
            returnValue = df.format(date);
        }
        return (returnValue);
    }



    /**
     * 字符串转日期
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 对应的日期
     */
    public static Date parse(String strDate, String pattern) {
        if(TextUtils.isEmpty(strDate)){
            return new Date();
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 按照预设格式将字符串转成日期
     * @param strDate
     * @return
     */
    public static Date parse(String strDate){
        return parse(strDate,getDefaultDatePattern());
    }

    /**
     * 在日期上增加数个整月
     * @param date 日期
     * @param n 要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static Date addMonth(Date date){
        return addMonth(date,1);
    }

    /**
     * 在日期上增加天数
     * @param date 日期
     * @param n 要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    public static Date addDay(Date date){
        return addDay(date,1);
    }
}
