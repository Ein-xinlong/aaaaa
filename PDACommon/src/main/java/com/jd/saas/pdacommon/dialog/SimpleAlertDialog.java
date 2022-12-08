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

public class SimpleAlertDialog {
    private final AlertDialog alertDialog;

    public static class Builder {
        private final Context mContext;
        @StringRes
        private final int textRes;
        @Nullable
        private final String text;
        @StringRes
        private int positiveRes = R.string.common_ensure;
        @StringRes
        private int negativeRes = R.string.common_cancel;
        private View.OnClickListener onPositiveBtnClickListener;
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

        public Builder setPositiveButton(@StringRes int positiveRes, View.OnClickListener onPositiveBtnClickListener) {
            this.positiveRes = positiveRes;
            this.onPositiveBtnClickListener = onPositiveBtnClickListener;
            return this;
        }

        public Builder setPositiveButton(View.OnClickListener onPositiveBtnClickListener) {
            this.onPositiveBtnClickListener = onPositiveBtnClickListener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int negativeRes, View.OnClickListener onNegativeBtnClickListener) {
            this.negativeRes = negativeRes;
            this.onNegativeBtnClickListener = onNegativeBtnClickListener;
            return this;
        }

        public Builder setNegativeButton(View.OnClickListener onNegativeBtnClickListener) {
            this.onNegativeBtnClickListener = onNegativeBtnClickListener;
            return this;
        }

        public SimpleAlertDialog build() {
            return new SimpleAlertDialog(mContext, textRes, text, positiveRes, onPositiveBtnClickListener, negativeRes, onNegativeBtnClickListener);
        }
    }


    private SimpleAlertDialog(Context mContext, @StringRes int textRes, @Nullable String text, @StringRes int positiveRes, View.OnClickListener onPositiveBtnClickListener, @StringRes int negativeRes, View.OnClickListener onNegativeBtnClickListener) {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.common_simple_dialog_layout, null);
        TextView tvText = dialogView.findViewById(R.id.tv_text);
        TextView tvEnsure = dialogView.findViewById(R.id.tv_ensure);
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
        tvEnsure.setText(positiveRes);
        tvEnsure.setOnClickListener(v -> {
            if (onPositiveBtnClickListener != null) {
                onPositiveBtnClickListener.onClick(v);
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
