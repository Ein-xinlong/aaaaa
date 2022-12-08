package com.jd.saas.pdagoodsquery.sale.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar calendar = Calendar.getInstance();
    public static String getCurrentDate(Date date){
        return  format.format(date);
    }
    public static Date parseString(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
    /*
    格式化为 月-日
     */
    public static String formatToMonth(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");
        return dateFormat.format(date);
    }
    /*
    获取月初时间
     */
    public static String getFirstDayOfMonth(int year,int month){
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        int firstDay = calendar.getMinimum(Calendar.DATE);
        calendar.set(Calendar.DAY_OF_MONTH,firstDay);
        return format.format(calendar.getTime());
    }
    /*
  获取月最后一天
   */
    public static String getLastDayOfMonth(int year,int month){
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        int lastDay = calendar.getMaximum(Calendar.DATE);
        calendar.set(Calendar.DAY_OF_MONTH,lastDay);
        return format.format(calendar.getTime());
    }

    /*
    往前多少天
     */
    public static String getBeforeDay(int dayNum){
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-dayNum);
        Date d = calendar.getTime();
        System.out.println("过去："+dayNum+":"+format.format(d));
        return format.format(d);
    }
    public static String parseDate(Date date){
       return format.format(date);
    }
    /*
   往后多少天
    */
    public static String getAfterDay(Date dateStr,int dayNum) {

        calendar.setTime(dateStr);
        calendar.add(Calendar.DATE,dayNum);
        Date d = calendar.getTime();
        System.out.println("以后："+dayNum+":"+format.format(d));
        return format.format(d);
    }

    /*
    往前几月
     */
    public static String getBeforeMonth(int monthNum){
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -monthNum);
        Date d = calendar.getTime();
        String mon = format.format(d);
        System.out.println("过去"+monthNum+":"+d);
        return mon;
    }
    /*
    往前几年
     */
    public static String getBeforeYear(int yearNum){
        calendar.setTime(new Date());
        calendar.add(calendar.YEAR,-yearNum);
        Date y = calendar.getTime();
        String year = format.format(y);
        System.out.println("过去"+yearNum+":"+year);
        return  year;
    }
}
