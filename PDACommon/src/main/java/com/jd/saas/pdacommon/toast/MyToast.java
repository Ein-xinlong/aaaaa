package com.jd.saas.pdacommon.toast;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.application.MyApplication;

/**
 * 自定义toast，全局通用
 *
 * @author majiheng
 */
public class MyToast {

    public static void show(String msg, boolean longTime) {
        if (!TextUtils.isEmpty(msg)) {
            Context context = MyApplication.getInstance().getApplicationContext();
            View view = View.inflate(context, R.layout.view_toast, null);
            AppCompatTextView textView = view.findViewById(R.id.tv_toast);
            textView.setText(msg);
            Toast toast = new Toast(context);
            toast.setView(view);
            toast.setDuration(longTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        }
    }

    public static void show(@StringRes int strId, boolean longTime) {
        Context context = MyApplication.getInstance().getApplicationContext();
        View view = View.inflate(context, R.layout.view_toast, null);
        AppCompatTextView textView = view.findViewById(R.id.tv_toast);
        textView.setText(strId);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(longTime ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }
}
