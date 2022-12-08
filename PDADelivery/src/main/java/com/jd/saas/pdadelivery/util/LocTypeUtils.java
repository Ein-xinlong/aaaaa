package com.jd.saas.pdadelivery.util;

import com.jd.saas.pdadelivery.net.enums.LocTypeEnum;
import com.jd.saas.pdadelivery.net.enums.SkuNatureEnum;
import com.jd.saas.pdadelivery.net.enums.SkuTypeEnum;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @see LocTypeEnum
 */
public class LocTypeUtils {
    public static int getSkuNatureByLocType(String locType) {
        ArrayList<String> goods = new ArrayList<>(Arrays.asList(LocTypeEnum.SKU.getValue(),
                LocTypeEnum.RAW.getValue(), LocTypeEnum.HC.getValue()));
        ArrayList<String> bad = new ArrayList<>(Arrays.asList(LocTypeEnum.SKU_BAD.getValue(),
                LocTypeEnum.RAW_BAD.getValue(), LocTypeEnum.HC_BAD.getValue()));
        if (goods.contains(locType)) {
            return SkuNatureEnum.GOOD.getValue();
        } else if (bad.contains(locType)) {
            return SkuNatureEnum.BAD.getValue();
        } else {
            return SkuNatureEnum.GOOD.getValue();
        }
    }

    public static String getDefaultStockType(int skuType) {
        if (skuType == SkuTypeEnum.SKU.getValue()) {
            return LocTypeEnum.SKU.getValue();
        } else if (skuType == SkuTypeEnum.RAW.getValue()) {
            return LocTypeEnum.RAW.getValue();
        } else if (skuType == SkuTypeEnum.HC.getValue()) {
            return LocTypeEnum.HC.getValue();
        } else {
            return LocTypeEnum.SKU.getValue();
        }
    }

    public static boolean isMatch(int skuType, String locType) {
        if (skuType == SkuTypeEnum.SKU.getValue()) {
            return LocTypeEnum.SKU.getValue().equals(locType) || LocTypeEnum.SKU_BAD.getValue().equals(locType);
        } else if (skuType == SkuTypeEnum.RAW.getValue()) {
            return LocTypeEnum.RAW.getValue().equals(locType) || LocTypeEnum.RAW_BAD.getValue().equals(locType);
        } else if (skuType == SkuTypeEnum.HC.getValue()) {
            return LocTypeEnum.HC.getValue().equals(locType) || LocTypeEnum.HC_BAD.getValue().equals(locType);
        } else {
            return LocTypeEnum.SKU.getValue().equals(locType) || LocTypeEnum.SKU_BAD.getValue().equals(locType);
        }
    }

    public static String getLotTypeBySkuTypeAndSkuNature(int skuType, int skuNature) {
        if (skuType == SkuTypeEnum.RAW.getValue()) {
            return skuNature == SkuNatureEnum.BAD.getValue() ? LocTypeEnum.RAW_BAD.getValue() : LocTypeEnum.RAW.getValue();
        } else if (skuType == SkuTypeEnum.HC.getValue()) {
            return skuNature == SkuNatureEnum.BAD.getValue() ? LocTypeEnum.HC_BAD.getValue() : LocTypeEnum.HC.getValue();
        } else {
            return skuNature == SkuNatureEnum.BAD.getValue() ? LocTypeEnum.SKU_BAD.getValue() : LocTypeEnum.SKU.getValue();
        }
    }
}
