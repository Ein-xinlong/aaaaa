package com.jd.saas.pdainventorycheck.utils;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;
import com.jd.saas.pdainventorycheck.R;

public class TabUtil {

    public static TabLayout.Tab createTab(TabLayout tableLayout, @StringRes int strId, boolean select) {
        TextView customView = new TextView(tableLayout.getContext());
        customView.setText(strId);
        customView.setGravity(Gravity.CENTER);
        customView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customView.setTextSize(TypedValue.COMPLEX_UNIT_SP, select ? 16 : 14);
        customView.setTypeface(select ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        customView.setTextColor(ContextCompat.getColor(tableLayout.getContext(), select ? R.color.inventory_check_text_blue : R.color.color_text_black));
        return tableLayout.newTab().setCustomView(customView);
    }
    public static void selectTextLargen(TabLayout.Tab tab){
        TextView custonView = (TextView) tab.getCustomView();
        if (custonView == null) {
            return;
        }
        custonView.setTextColor(ContextCompat.getColor(custonView.getContext(),R.color.inventory_check_text_blue ));

        custonView.setTypeface(Typeface.DEFAULT_BOLD);
        custonView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

    }
    public static void unSelectTextNormal(TabLayout.Tab tab){
        TextView custonView = (TextView) tab.getCustomView();
        if (custonView == null) {
            return;
        }
        custonView.setTextColor(ContextCompat.getColor(custonView.getContext(),R.color.color_text_black ));
        custonView.setTypeface(Typeface.DEFAULT);
        custonView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

    }
}
