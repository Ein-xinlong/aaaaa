package com.jd.saas.pdadelivery.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {
    private static final DecimalFormat numberFormat = new DecimalFormat("#.###");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
    private static final SimpleDateFormat dateYMDFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final DateFormat paramDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);

    /**
     * 收货模块的日期格式化方法 yyyy-MM-dd HH:mm
     */
    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        return dateFormat.format(date);
    }

    /**
     * 保留三位小数的数量格式化方法
     */
    public static String format(BigDecimal num) {
        if (num == null) {
            return "0";
        }
        return numberFormat.format(num);
    }

    /**
     * 数量格式化方法
     */
    public static String format(int num) {
        return String.valueOf(num);
    }

    /**
     * 生产日期的格式化方法 yyyy-MM-dd
     */
    public static String formatYMDDate(Date date) {
        if (date == null) {
            return "";
        }
        return dateYMDFormat.format(date);
    }

    /**
     * 生产日期的格式化方法 yyyy-MM-dd
     */
    public static Date parseYMDDate(String str) {
        try {
            return dateYMDFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求参数的时间格式化方法
     */
    public static String formatParam(Date date) {
        return paramDateFormat.format(date);
    }

    /**
     * 请求参数的时间格式化方法
     */
    public static Date parseResult(String str) {
        if (str == null) {
            return null;
        }
        try {
            return paramDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
