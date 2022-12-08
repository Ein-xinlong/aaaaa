package com.jd.saas.pdacommon.loctype;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * LocType好坏工具类
 * @author majiheng
 */
public class LocTypeUtils {

    /**
     * 根据locType判断库位信息是否是好/坏，好：0，坏1
     */
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

    /**
     * 根据skuType返回好品库位类型locType
     */
    public static String getGoodStockType(int skuType) {
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

    /**
     * 根据skuType返回坏品库位类型locType
     */
    public static String getBadStockType(int skuType) {
        if (skuType == SkuTypeEnum.SKU.getValue()) {
            return LocTypeEnum.SKU_BAD.getValue();
        } else if (skuType == SkuTypeEnum.RAW.getValue()) {
            return LocTypeEnum.RAW_BAD.getValue();
        } else if (skuType == SkuTypeEnum.HC.getValue()) {
            return LocTypeEnum.HC_BAD.getValue();
        } else {
            return LocTypeEnum.SKU_BAD.getValue();
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
}
