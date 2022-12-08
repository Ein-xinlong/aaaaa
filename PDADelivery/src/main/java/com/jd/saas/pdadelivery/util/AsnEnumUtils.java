package com.jd.saas.pdadelivery.util;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.enums.AppType;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.net.enums.AsnTypeEnum;

public class AsnEnumUtils {

//    private static final List<AsnTypeEnum> allValidType = new ArrayList<>(Arrays.asList(AsnTypeEnum.PURCHASE_ORDER, AsnTypeEnum.DISTRIBUTION, AsnTypeEnum.ALLOTIN));
//    private static final List<AsnTypeEnum> allWareHouseValidType = new ArrayList<>(Arrays.asList(AsnTypeEnum.PURCHASE_ORDER, AsnTypeEnum.RETURN_DC));

    public static String getAsnTypeName(int asnType) {
        if (asnType == AsnTypeEnum.PURCHASE_ORDER.getValue()) {
            return MyApplication.getInstance().getString(DeliveryConfigProvider.getClientType() == AppType.STORE ? R.string.delivery_direct : R.string.delivery_purchase);
        } else if (asnType == AsnTypeEnum.USER_RETURNS.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_user_return);
        } else if (asnType == AsnTypeEnum.DELIVERY_REJECT.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_reject);
        } else if (asnType == AsnTypeEnum.DISTRIBUTION.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_send);
        } else if (asnType == AsnTypeEnum.ALLOTIN.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_allot);
        } else if (asnType == AsnTypeEnum.RETURN_DC.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_return);
        } else if (asnType == AsnTypeEnum.INCOME_DIFFERENCE.getValue()) {
            return MyApplication.getInstance().getString(R.string.delivery_income_difference);
        } else {
            return "";
        }
    }

//    public static List<AsnTypeEnum> getAllAsnTypes() {
//        return DeliveryConfigProvider.getClientType() == AppType.STORE ? allValidType : allWareHouseValidType;
//    }
}
