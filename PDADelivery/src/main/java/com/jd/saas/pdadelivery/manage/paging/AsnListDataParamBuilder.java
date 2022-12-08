package com.jd.saas.pdadelivery.manage.paging;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.net.enums.AsnTypeEnum;
import com.jd.saas.pdadelivery.net.param.AsnListDataParam;
import com.jd.saas.pdadelivery.util.Formatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AsnListDataParamBuilder {
    @NonNull
    private final AsnStatusEnum asnStatusEnum;
    @Nullable
    private AsnTypeEnum selectType;
    @Nullable
    private Pair<Date, Date> timeRange;
    @Nullable
    private String docNo;

    public AsnListDataParamBuilder(@NonNull AsnStatusEnum states) {
        this.asnStatusEnum = states;
    }

    public void setSelectTypes(@Nullable AsnTypeEnum selectType) {
        this.selectType = selectType;
    }

    public void setTimeRange(@Nullable Pair<Date, Date> timeRange) {
        this.timeRange = timeRange;
    }

    public void setDocNo(@Nullable String docNo) {
        this.docNo = docNo;
    }

    public AsnListDataParam build() {
        AsnListDataParam asnListDataParam = new AsnListDataParam();
        ArrayList<Integer> statusEnumList = new ArrayList<>();
        //这里特殊处理 当显示已收货时 实际查看的是已收货和差异处理中的收货单
        if (asnStatusEnum == AsnStatusEnum.RECEIVED) {
            statusEnumList.add(asnStatusEnum.getValue());
            statusEnumList.add(AsnStatusEnum.DIFF_AUDIT.getValue());
        } else {
            statusEnumList.add(asnStatusEnum.getValue());
        }
        asnListDataParam.setStatusEnumsList(statusEnumList);
        if (timeRange != null) {
            asnListDataParam.setCreateStartTime(Formatter.formatParam(timeRange.first));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timeRange.second);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            calendar.add(Calendar.MILLISECOND, -1);
            asnListDataParam.setCreateEndTime(Formatter.formatParam(calendar.getTime()));
        }
        if (selectType != null) {
            asnListDataParam.setAsnType(String.valueOf(selectType.getValue()));
        }
        if (docNo != null) {
            asnListDataParam.setDocNo(docNo);
        }
        return asnListDataParam;
    }
}
