package com.jd.saas.pdacommon.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import com.jd.saas.pdacommon.R;

/**
 * 全局加载框
 *
 * @author majiheng
 */
public class ProgressDialog extends AppCompatDialog {

    public ProgressDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_progress);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        if (window == null) {
            return;
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public ProgressDialog(@NonNull Context context, CharSequence message) {
        this(context);
        setMessage(message);
    }

    public void setMessage(CharSequence message) {
        if (TextUtils.isEmpty(message)) return;
        TextView textView = getDelegate().findViewById(R.id.tv_msg);
        textView.setText(message);
    }
}
