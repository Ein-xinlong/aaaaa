package com.jd.saas.pdacommon.date;

import android.util.Log;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * 日期工具类
 * 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 * @author majiheng
 */
public final class DateUtils {
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    public static String FORMAT_SHORT_NUM = "yyyyMMdd";
    public static String FORMAT_TODAY = "yyyyMMdd000000";
    public static String FORMAT_WEEK = "yyyy.MM.dd HH:mm:ss(E)";
    public static String FORMAT_WEEK_NO_TIME = "yyyy.MM.dd (E)";

    /**
     * 英文全称  如：20101201231506
     */
    public static String FORMAT_MIDDLE = "yyyyMMddHHmmss";

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

    public static String FORMAT_VIDEO_NAME = "yyyy-MM-dd_HH-mm-ss";

    /**
     * 获得默认的 date pattern
     */


    private static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     */
    public static String getNow() {
        return format(System.currentTimeMillis());
    }

    public static long getNowInMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 根据用户格式返回当前日期
     */
    public static String getNow(String format) {
        return format(System.currentTimeMillis(), format);
    }

    /**
     * 获取时分
     *
     * @param timestamp 时间戳（单位：毫秒）
     */
    public static String getHM(long timestamp) {
        return format(timestamp, "HH:mm");
    }

    public static String format(long timestamp) {
        return format(timestamp, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param timestamp 日期
     * @param pattern   日期格式
     */
    public static String format(long timestamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return sdf.format(new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(timestamp);
    }
    /*
    带星期几格式化
     */
    public static String formatWeek(Date date,String pattern) {
        if ( null == date ){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
           return  format.format(date).replace("周","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.toString();
    }
    /*
       带星期几格式化
        */
    public static String formatLongDate(Date date,String pattern) {
        if(null==date){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            return  format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.toString();
    }
    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date format(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param timestamp 日期
     */
    public static String getYear(long timestamp) {
        return format(timestamp).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date 日期字符串
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 时间转化为聊天界面显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getChatTimeStr(long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        String timeStr = timeStamp + "";
        if (timeStr.length() == 10) {
            timeStamp = timeStamp * 1000;
        }
        inputTime.setTimeInMillis(timeStamp);
        Date currentTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm", Locale.getDefault());
            return timeFormatStr(inputTime, sdf.format(currentTimeZone));
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm", Locale.getDefault());
            return "昨天" + timeFormatStr(inputTime, sdf.format(currentTimeZone));
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M" + "/" + "d" + " ", Locale.getDefault());
                String temp1 = sdf.format(currentTimeZone);
                SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm", Locale.getDefault());
                String temp2 = timeFormatStr(inputTime, sdf1.format(currentTimeZone));
                return temp1 + temp2;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "/" + "M" + "/" + "d" + " ", Locale.getDefault());
                String temp1 = sdf.format(currentTimeZone);
                SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm", Locale.getDefault());
                String temp2 = timeFormatStr(inputTime, sdf1.format(currentTimeZone));
                return temp1 + temp2;
            }

        }

    }

    /**
     * 24小时制转化成12小时制
     */
    private static String timeFormatStr(Calendar calendar, String strDay) {
        String tempStr;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 11) {
            tempStr = "下午" + " " + strDay;
        } else {
            tempStr = "上午" + " " + strDay;
        }
        return tempStr;
    }

    public static String friendlyTime(long time) {
        //获取time距离当前的秒数
        long ct = (System.currentTimeMillis() - time) / 1000;

        if (ct == 0) {
            return "刚刚";
        }

        if (ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }

        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";

        if (ct >= 86400 && ct < 2592000) {
            int day = (int) (ct / 86400);
            if (day == 1) {
                return "昨天";
            } else {
                return day + "天前";
            }
        }
        if (ct >= 2592000 && ct < 31104000) {
            return ct / 2592000 + "月前";
        }

        return ct / 31104000 + "年前";
    }

    /**
     * 24小时制转化成12小时制
     */
    public static String timeFormatStr(long time) {
        String tempStr;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.getDefault());
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(time);
        int hour = inputTime.get(Calendar.HOUR_OF_DAY);
        String strDay = sdf.format(inputTime.getTime());
        if (hour > 11) {
            tempStr = "下午" + " " + strDay;
        } else {
            tempStr = "上午" + " " + strDay;
        }
        return tempStr;
    }

    /**
     * 将时间戳格式化，13位的转为10位
     */
    public static long timestampFormate(long timestamp) {
        String timestampStr = timestamp + "";
        if (timestampStr.length() == 13) {
            timestamp = timestamp / 1000;
        }
        return timestamp;
    }

    /**
     * 设置时间格式
     *
     * @param tv          TextView
     * @param millisecond 毫秒数
     */
    public static void updateTimeFormat(TextView tv, int millisecond) {
        int second = millisecond / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        tv.setText(String.format(Locale.CHINA, "%02d:%02d:%02d", hh, mm, ss));
    }

    public static String getTimeFormat(long millisecond){
        long second = millisecond / 1000;
        long hh = second / 3600;
        long mm = second % 3600 / 60;
        long ss = second % 60;

        return String.format(Locale.CHINA, "%02d:%02d:%02d", hh, mm, ss);
    }

    public static String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

}