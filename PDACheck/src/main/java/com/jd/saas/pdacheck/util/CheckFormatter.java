package com.jd.saas.pdacheck.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 格式化工具
 *
 * @author gouhetong
 */
public class CheckFormatter {
    private static final DecimalFormat numberFormat = new DecimalFormat("#.###");
    /**
     * 保留三位小数的数量格式化方法
     */
    public static String format(BigDecimal num) {
        if (num == null) {
            return "0";
        }
        return numberFormat.format(num);
    }
    public static String formatEmpty(String str){
        return TextUtils.isEmpty(str) ? "--" : str;
    }
}
