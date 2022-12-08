package com.jd.saas.pdacommon.calendar;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.toast.MyToast;

/**
 * 自定义日历，使用BottomSheetDialog
 *
 * @author majiheng
 */
public class CalendarBottomSheetDialog extends BottomSheetDialog {

    // 时间回调
    private OnDateSelectedListener mDateSelectedListener;
    // 开始时间
    private String mStartTime;
    // 结束时间
    private String mEndTime;

    public CalendarBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        View view = LayoutInflater.from(context).inflate(R.layout.view_calendar,null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        CalendarList calendar = view.findViewById(R.id.calendar);

        calendar.setOnDateSelected(new CalendarList.OnDateSelected() {
            @Override
            public void selected(String startDate, String endDate) {
                mStartTime = startDate;
                mEndTime = endDate;
            }
        });
        // 关闭按钮
        AppCompatImageView close = view.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 确定按钮
        AppCompatTextView confirm = view.findViewById(R.id.tv_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mStartTime)) {
                    MyToast.show(context.getString(R.string.calendar_start_time_null),false);
                    return;
                }
                if(TextUtils.isEmpty(mEndTime)) {
                    MyToast.show(context.getString(R.string.calendar_end_time_null),false);
                    return;
                }
                mDateSelectedListener.onDateSelected(mStartTime,mEndTime);
                dismiss();
            }
        });
        // 重制按钮
        AppCompatTextView reset = view.findViewById(R.id.tv_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateSelectedListener.onDateSelected("","");
                dismiss();
            }
        });
    }

    public void setOnDateSelected(OnDateSelectedListener dateSelectedListener) {
        this.mDateSelectedListener = dateSelectedListener;
    }

    public interface OnDateSelectedListener {
        void onDateSelected(String startDate, String endDate);
    }
}
