package com.example.flink.tools;

import android.text.TextUtils;

import com.example.flink.common.MyConstants;
import com.example.flink.tools.data.DataManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.flink.tools.data.DataManager.DataManagerEnum.RAM_DATA_MANAGER;

public class DateUtil {

    /**
     * 24小时制时分秒
     */
    public static String FORMAT_Hms = "HHmmss";

    /**
     * 12小时制时分秒
     */
    public static String FORMAT_hms = "hhmmss";

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

    public static String[] WEEK_DAYS_CHI = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 英文的周和月
     */
    public static String[] WEEK_DAYS_ENGLISH = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};

    public static String[] MONTH_NAME_ENGLISH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static String[] MONTH_NAME_CHI = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    public static final long SECOND_IN_MILLIS = 1000L;
    public static final long MINUTE_IN_MILLIS = 60000L;
    public static final long HOUR_IN_MILLIS = 3600000L;
    public static final long DAY_IN_MILLIS = 86400000L;
    public static final long WEEK_IN_MILLIS = 604800000L;
    public static final long YEAR_IN_MILLIS = 31449600000L;

    private static final DataManager dataManager = new DataManager(RAM_DATA_MANAGER);

    /**
     * 获得默认的 date pattern
     */
    public static String getDefaultDatePattern() {
        return FORMAT_SHORT;
    }

    public static String[] getDefaultWeekDaysStr() {
        return WEEK_DAYS_CHI;
    }

    /**
     * 根据预设格式返回当前日期
     *
     * @return 对应的日期
     */
    public static String getNowDateStr() {
        return format(getNowDate());
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static long getNowMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static Calendar getNowCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getNowDate());
        return calendar;
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date 日期
     * @return 格式化的日期
     */
    public static String format(Date date) {
        return format(date, getDefaultDatePattern());
    }

    /**
     * 日期转字符串
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 对应的字符串
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.getDefault());
            returnValue = df.format(date);
        }
        return (returnValue);
    }


    /**
     * 字符串转日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 对应的日期
     */
    public static Date parse(String strDate, String pattern) {
        if (TextUtils.isEmpty(strDate)) {
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

    public static Date parse(int year, int month, int dayOfMonth) {
        return parse(year + "-" + month + "-" + dayOfMonth);
    }

    /**
     * 按照预设格式将字符串转成日期
     *
     * @param strDate 要转换的字符串
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDefaultDatePattern());
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static Date addMonth(Date date) {
        return addMonth(date, 1);
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    public static Date addDay(Date date) {
        return addDay(date, 1);
    }

    public static boolean checkDate(String dateStr) {
        return checkDate(dateStr, getDefaultDatePattern());
    }

    /**
     * 检查字符串日期是否符合格式
     *
     * @param dateStr 日期对应的字符串
     * @param pattern 格式
     * @return true:符合
     */
    public static boolean checkDate(String dateStr, String pattern) {
        SimpleDateFormat sd = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            sd.setLenient(false);
            sd.parse(dateStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前date对应的星期几的字符串
     *
     * @param date     日期
     * @param weekDays 星期1到7对应的字符串
     * @return 星期几对应的字符串
     */
    public static String getWeekOfDateStr(Date date, String[] weekDays) {
        if (date == null || weekDays == null || weekDays.length != 7) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return weekDays[dayOfWeek];
    }

    public static String getWeekOfDateStr(Date date) {
        return getWeekOfDateStr(date, getDefaultWeekDaysStr());
    }

    /**
     * 判断时间是上午还是下午
     *
     * @param date 日期
     * @return true：上午
     */
    public static boolean isAM(Date date) {
        long time = date.getTime();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        return mCalendar.get(Calendar.AM_PM) == Calendar.AM;
    }

    /**
     * 获取当前date对应的日期数字的字符串
     *
     * @param date 日期
     * @return 日期数字对应的字符串
     */
    public static String getDayOfMonth(Date date) {
        if (date == null) return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取当前date对应的月份英文的字符串
     *
     * @param date 日期
     * @return 月份英文对应的字符串
     */
    public static String getMonthEnglish(Date date) {
        if (date == null) return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return MONTH_NAME_ENGLISH[cal.get(Calendar.MONTH)];
    }

    public static String getMonthChi(Date date) {
        if (date == null) return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return MONTH_NAME_CHI[cal.get(Calendar.MONTH)];
    }

    /**
     * 获取当前date对应的年份的字符串
     *
     * @param date 日期
     * @return 年份对应的字符串
     */
    public static String getYear(Date date) {
        if (date == null) return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    /**
     * 清空时间中的时分秒
     *
     * @param date 日期
     * @return 清空时分秒后的日期
     */
    public static Date clearDateHMS(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取程序当前选中的日期
     *
     * @return 日期
     */
    public static Date getNowSelectedDate() {
        if (dataManager.getObject(MyConstants.KEY_NOW_DATE) == null
                || !(dataManager.getObject(MyConstants.KEY_NOW_DATE) instanceof Date)) {
            return DateUtil.getNowDate();
        } else {
            return (Date) dataManager.getObject(MyConstants.KEY_NOW_DATE);
        }
    }

    /**
     * 保存日期为当前选择日期
     *
     * @param date 需要保存的日期
     */
    public static void saveDateAsSelectedDate(Date date) {
        dataManager.saveObject(MyConstants.KEY_NOW_DATE, date);
    }

    /**
     * 通过Date获取Calendar
     */
    public static Calendar getCalendarByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 将util里面的Calendar转译成日历框架的calendar
     *
     * @param calendar java.util.Calendar
     * @return com.haibin.calendarview.Calendar
     */
    public static com.haibin.calendarview.Calendar calendarTrans(java.util.Calendar calendar) {
        return calendarTrans(calendar.get(java.util.Calendar.YEAR)
                , calendar.get(java.util.Calendar.MONTH) + 1
                , calendar.get(java.util.Calendar.DAY_OF_MONTH));
    }

    public static com.haibin.calendarview.Calendar calendarTrans(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendarTrans(calendar);
    }

    private static com.haibin.calendarview.Calendar calendarTrans(int year, int month, int day) {
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        return calendar;
    }
}
