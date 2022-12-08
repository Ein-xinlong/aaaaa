package com.jd.saas.pdacommon.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.screen.ScreenUtil;

public class SimpleCancelDialog {
    private final AlertDialog alertDialog;

    public static class Builder {
        private final Context mContext;
        @StringRes
        private final int textRes;
        @Nullable
        private final String text;
        @StringRes
        private int negativeRes = R.string.common_close;
        private View.OnClickListener onNegativeBtnClickListener;

        public Builder(Context mContext, @StringRes int textRes) {
            this.mContext = mContext;
            this.textRes = textRes;
            this.text = null;
        }

        public Builder(Context mContext, @StringRes int textRes, Object... objects) {
            this.mContext = mContext;
            this.textRes = textRes;
            this.text = mContext.getString(textRes, objects);
        }


        public SimpleCancelDialog.Builder setNegativeButton(@StringRes int negativeRes, View.OnClickListener onNegativeBtnClickListener) {
            this.negativeRes = negativeRes;
            this.onNegativeBtnClickListener = onNegativeBtnClickListener;
            return this;
        }

        public SimpleCancelDialog.Builder setNegativeButton(View.OnClickListener onNegativeBtnClickListener) {
            this.onNegativeBtnClickListener = onNegativeBtnClickListener;
            return this;
        }

        public SimpleCancelDialog build() {
            return new SimpleCancelDialog(mContext, textRes, text, negativeRes, onNegativeBtnClickListener);
        }
    }


    private SimpleCancelDialog(Context mContext, @StringRes int textRes, @Nullable String text, @StringRes int negativeRes, View.OnClickListener onNegativeBtnClickListener) {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.common_simple_cancel_dialog_layout, null);
        TextView tvText = dialogView.findViewById(R.id.tv_text);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        if (TextUtils.isEmpty(text)) {
            tvText.setText(textRes);
        } else {
            tvText.setText(text);
        }
        alertDialog = new AlertDialog.Builder(mContext)
                .setView(dialogView)
                .create();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvCancel.setText(negativeRes);
        tvCancel.setOnClickListener(v -> {
            if (onNegativeBtnClickListener != null) {
                onNegativeBtnClickListener.onClick(v);
            }
            alertDialog.dismiss();
        });
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }




    public void show() {
        alertDialog.show();
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = ScreenUtil.dp2px(alertDialog.getContext(), 300);
        alertDialog.getWindow().setAttributes(layoutParams);
    }

    public void dismiss() {
        alertDialog.dismiss();
    }

}
