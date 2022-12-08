package com.jd.saas.pdacommon.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalUtil {

    public static String formatToDouble(String decString){
        if(TextUtils.isEmpty(decString)){
            return "";
        }
        DecimalFormat format = new DecimalFormat("0.00");
        String formatString = format.format(new BigDecimal(decString));
        return  formatString;

    }
}
