package com.jd.saas.pdacommon.enums;


import androidx.annotation.StringRes;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.apptype.AppTypeUtil;


/**
 * 库存交易类型枚举
 */
public enum StockTransTypeEnum {
    /**
     * 本地属性 全部
     */
    ALL(R.string.stock_trans_type_all),
    /**
     * 入库单
     */
    REC_NORM(R.string.stock_trans_type_in),

    /**
     * 出库
     */
    OUT(R.string.stock_trans_type_out),

    /**
     * 批次效期调整
     */
    LOT_ADJUST(R.string.stock_trans_type_lot_adjust),
    /**
     * 盘点
     */
    CHECK(R.string.stock_trans_type_check),
    /**
     * 好坏品转换
     */
    MV(R.string.stock_trans_type_move),
    /**
     * 调整
     */
    ADJUST(R.string.stock_trans_type_adjust),

    /**
     * 分配
     */
    ALLOC(R.string.stock_trans_type_alloc),

    //================入库子类型 start==============//
    /**
     * 入库单的 直送入库
     */
    PURCHASE_ORDER(AppTypeUtil.getAppType() == 1 ? R.string.stock_trans_type_purchase : R.string.stock_trans_type_cang_purchase, REC_NORM),
    /**
     * 入库单的 配送入
     */
    DISTRIBUTION(R.string.stock_trans_type_distribution, REC_NORM),
    /**
     * 调拨单入
     */
    ALLOTIN(R.string.stock_trans_type_allocation, REC_NORM),
    /**
     * 入库单的 收货差异入
     */
    INCOME_DIFFERENCE(R.string.stock_trans_type_income_diff, REC_NORM),

    /**
     * 入库单的 配送拒收入
     */
    DELIVERY_REJECT(R.string.stock_trans_type_reject, REC_NORM),
    /**
     * 入库单的  售后入库
     */
    USER_RETURNS(R.string.stock_trans_type_user_return, REC_NORM),

    /**
     * 入库单的 期初入库
     */
    INITIALLY(R.string.stock_trans_type_initially, REC_NORM),


    /**
     * 门店退货给配送中心，配送中心入库
     */
    RETURN_DC(R.string.stock_trans_type_store_return, REC_NORM),

    //================入库 end==============//

    //================出库 start==============//
    /**
     * 线下 POS 订单
     */
    POS(R.string.stock_trans_type_pos, OUT),
    /**
     * 退供单  退仓跟退供应商用同一个退供单
     */
    RTV(R.string.stock_trans_type_rtv, OUT),
    /**
     * 退仓
     */
    RMV(R.string.stock_trans_type_rmv, OUT),
    /**
     * 调拨单出
     */
    ALLOTOUT(R.string.stock_trans_type_allot_out, OUT),
    /**
     * 线上销售订单
     */
    SALE(R.string.stock_trans_type_sale, OUT),

    /**
     * 配送中心出库单
     */
    DCOUT(R.string.stock_trans_type_cang_out, OUT),

    //================出库 end==============//
    ;

    @StringRes
    private final int nameRes;
    private final StockTransTypeEnum parent;

    private static final StockTransTypeEnum[] storeTypes = {
            ALL,
            PURCHASE_ORDER, DISTRIBUTION, ALLOTIN, DELIVERY_REJECT, USER_RETURNS, INITIALLY,
            MV,
            ADJUST,
            LOT_ADJUST,
            CHECK,
            POS, RTV, RMV, ALLOTOUT, SALE
    };
    private static final StockTransTypeEnum[] cangTypes = {
            ALL,
            PURCHASE_ORDER, RETURN_DC, INCOME_DIFFERENCE, INITIALLY,
            ADJUST,
            DCOUT, RTV
    };

    StockTransTypeEnum(@StringRes int nameRes, StockTransTypeEnum parent) {
        this.nameRes = nameRes;
        this.parent = parent;
    }

    StockTransTypeEnum(@StringRes int nameRes) {
        this.nameRes = nameRes;
        this.parent = null;
    }

    public StockTransTypeEnum getParent() {
        return parent;
    }

    public int getNameRes() {
        return nameRes;
    }

    public static StockTransTypeEnum[] getActiveStockTransTypes() {
        if (AppTypeUtil.getAppType() == 1) {
            return storeTypes;
        } else {
            return cangTypes;
        }
    }
}