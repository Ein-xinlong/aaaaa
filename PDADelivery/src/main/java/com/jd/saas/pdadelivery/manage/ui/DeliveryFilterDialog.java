package com.jd.saas.pdadelivery.manage.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jd.saas.pdacommon.calendar.CalendarBottomSheetDialog;
import com.jd.saas.pdacommon.screen.ScreenUtil;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DeliveryFilterBottomsheetBinding;
import com.jd.saas.pdadelivery.net.enums.AsnTypeEnum;
import com.jd.saas.pdacommon.enums.AppType;
import com.jd.saas.pdadelivery.util.DeliveryConfigProvider;
import com.jd.saas.pdadelivery.util.Formatter;

import java.util.Date;

public class DeliveryFilterDialog {
    private final Context context;
    private OnFilterChangeListener onFilterChangeListener;
    private AsnTypeEnum selectType = AsnTypeEnum.ALL;
    private Date startDate;
    private Date endDate;

    private final boolean isWareHouse = DeliveryConfigProvider.getClientType() == AppType.WAREHOUSE;

    public DeliveryFilterDialog(@NonNull Context context) {
        this.context = context;
    }

    public void setSelectTypes(AsnTypeEnum selectType) {
        if (selectType == null) {
            this.selectType = AsnTypeEnum.ALL;
        }
        this.selectType = selectType;
    }

    public void setRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setOnFilterChangeListener(OnFilterChangeListener onFilterChangeListener) {
        this.onFilterChangeListener = onFilterChangeListener;
    }

    private boolean isSelect(AsnTypeEnum asnTypeEnum) {
        return selectType == asnTypeEnum;
    }

    private void updateView(DeliveryFilterBottomsheetBinding mBinding) {
        mBinding.setSelectAsnTypeAll(isSelect(AsnTypeEnum.ALL));
        mBinding.setSelectRadioDirect(isSelect(AsnTypeEnum.PURCHASE_ORDER));
        mBinding.setSelectRadioSend(isSelect(AsnTypeEnum.DISTRIBUTION));
        mBinding.setSelectRadioAllot(isSelect(AsnTypeEnum.ALLOTIN));
        mBinding.setSelectRadioReturn(isSelect(AsnTypeEnum.RETURN_DC));
        if (startDate != null && endDate != null) {
            mBinding.setTimeRageStr(context.getString(R.string.delivery_time_range, Formatter.formatYMDDate(startDate), Formatter.formatYMDDate(endDate)));
        } else {
            mBinding.setTimeRageStr(null);
        }
        mBinding.executePendingBindings();
    }

    /**
     * bottomSheetDialog创建
     */
    public void open() {
        final DeliveryFilterBottomsheetBinding mBinding = DeliveryFilterBottomsheetBinding.inflate(LayoutInflater.from(context), null, false);
        int padding = ScreenUtil.dp2px(context, 17);
        int maxPaddingSpace = ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(context, 300);
        int space = maxPaddingSpace - padding * 2;
        boolean isScreenWidthTooSmall = space < 0;
        if (isScreenWidthTooSmall) {
            mBinding.rootLayout.setPadding(maxPaddingSpace / 2, 0, maxPaddingSpace / 2, 0);
        }
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(mBinding.getRoot());
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        mBinding.setIsWareHouse(isWareHouse);
        mBinding.tvStoreAll.setOnClickListener(v -> onSelectAsnTypeChange(!v.isSelected(), AsnTypeEnum.ALL, mBinding));
        mBinding.tvWarehouseAll.setOnClickListener(v -> onSelectAsnTypeChange(!v.isSelected(), AsnTypeEnum.ALL, mBinding));
        mBinding.tvDirect.setOnClickListener(v -> onSelectAsnTypeChange(!v.isSelected(), AsnTypeEnum.PURCHASE_ORDER, mBinding));
        mBinding.tvSend.setOnClickListener(v -> onSelectAsnTypeChange(!v.isSelected(), AsnTypeEnum.DISTRIBUTION, mBinding));
        mBinding.tvAllot.setOnClickListener(v -> onSelectAsnTypeChange(!v.isSelected(), AsnTypeEnum.ALLOTIN, mBinding));
        mBinding.tvReturn.setOnClickListener(v -> onSelectAsnTypeChange(!v.isSelected(), AsnTypeEnum.RETURN_DC, mBinding));
        mBinding.setOnCloseClickListener(v -> bottomSheetDialog.dismiss());
        mBinding.setOnSubmitClickListener(v -> {

            if (onFilterChangeListener != null) {
                boolean isSelectAll = selectType == AsnTypeEnum.ALL;
                onFilterChangeListener.onFilterChange(isSelectAll ? null : selectType, startDate, endDate);
            }
            bottomSheetDialog.dismiss();
        });
        mBinding.setOnResetClickListener(v -> {
            startDate = null;
            endDate = null;
            selectType = AsnTypeEnum.ALL;
            updateView(mBinding);
        });

        mBinding.setOnTimeSelectListener(v -> {
            CalendarBottomSheetDialog calendarBottomSheetDialog = new CalendarBottomSheetDialog(v.getContext(), R.style.BottomSheetDialog);
            calendarBottomSheetDialog.setOnDateSelected((startDate, endDate) -> {
                if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
                    setRange(Formatter.parseYMDDate(startDate), Formatter.parseYMDDate(endDate));
                    mBinding.setTimeRageStr(context.getString(R.string.delivery_time_range, startDate, endDate));
                } else {
                    setRange(null, null);
                    mBinding.setTimeRageStr(null);
                }
                mBinding.executePendingBindings();
            });
            calendarBottomSheetDialog.show();
        });
        updateView(mBinding);
        bottomSheetDialog.show();
    }

    private void onSelectAsnTypeChange(boolean isChecked, AsnTypeEnum asnTypeEnum, final DeliveryFilterBottomsheetBinding mBinding) {
        if (isChecked) {
            if (selectType == asnTypeEnum) {
                return;
            }
            selectType = asnTypeEnum;
        }
        updateView(mBinding);
    }

    interface OnFilterChangeListener {
        void onFilterChange(AsnTypeEnum selectType, Date start, Date end);
    }
}
