package com.jd.saas.pdadelivery.util;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;
import com.jd.saas.pdadelivery.R;

public class TabUtils {
    public static TabLayout.Tab createTab(TabLayout tableLayout, @StringRes int strId, boolean select) {
        TextView customView = new TextView(tableLayout.getContext());
        customView.setText(strId);
        customView.setGravity(Gravity.CENTER);
        customView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customView.setTextSize(TypedValue.COMPLEX_UNIT_SP, select ? 16 : 14);
        customView.setTextColor(ContextCompat.getColor(tableLayout.getContext(), select ? R.color.delivery_text_blue : R.color.delivery_text_black));
        customView.setTypeface(select ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return tableLayout.newTab().setCustomView(customView);
    }

    public static void showAsSelect(TabLayout.Tab tab) {
        TextView customView = (TextView) tab.getCustomView();
        if (customView == null) {
            return;
        }
        customView.setTextColor(ContextCompat.getColor(customView.getContext(), R.color.delivery_text_blue));
        customView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        customView.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public static void showAsUnselect(TabLayout.Tab tab) {
        TextView customView = (TextView) tab.getCustomView();
        if (customView == null) {
            return;
        }
        customView.setTextColor(ContextCompat.getColor(customView.getContext(), R.color.delivery_text_black));
        customView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        customView.setTypeface(Typeface.DEFAULT);
    }


}
