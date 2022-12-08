package com.jd.saas.pdamain.home;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.jd.saas.pdamain.R;

public enum MenuResource {
    /**
     * 缺省值
     */
    UNKNOWN(R.drawable.main_ic_unknow, "", ""),
    /**
     * 按单据收货
     */
    DELIVERY(R.drawable.main_ic_take_delivery, "2cc0f497-1fd0-4a64-b73d-fa324af96851", "pda://native.DeliveryModule/ManagerPage"),
    /**
     * 盘点
     */
    CHECK(R.drawable.main_ic_check, "b4003c28-3cfc-49c9-baed-73e91257ac76", "pda://native.CheckModule/CheckListPage"),
    /**
     * 库存查询
     */
    INVENTORY_CHECK(R.drawable.main_ic_stock, "47d2467b-5e42-48da-b04a-05c767ad2b1f", "pda://native.InventoryCheckModule/CheckNewPage"),
    /**
     * 库存调整
     */
    INVENTORY(R.drawable.main_ic_adjust, "d79d113d-f77f-49c0-9e24-2a170e0f73f7", "pda://native.InventoryModule/StockNewPage"),
    /**
     * 效期调整
     */
    VALIDITY(R.drawable.main_ic_validity, "a8dcec35-c590-4e58-9acd-769da31088c0", "pda://native.ValidityModule/ValidityAdjustmentListNewPage"),
    /**
     * 商品查询
     */
    GOODS_QUERY(R.drawable.main_ic_goods_query, "42d8039c-f3b8-4adc-b745-2cb836066849", "pda://native.GoodsQueryModule/AdjustmentDetailNewPage"),
    /**
     * 无单据收货
     */
    DELIVERY_NO_DOCUMENT(R.drawable.main_ic_delivery_no_document, "4a5a50d3-a107-437b-b3dc-03b43e9beb78", "flutter://pda_delivery_no_document/main"),
    /**
     * 差异处理
     */
    DIFFERENCE_HANDLE(R.drawable.main_ic_difference_handle, "89dccb0d-a40f-460b-86de-f149d4d85f04", "flutter://pda_difference_handle/main"),
    /**
     * 门店进货
     */
    PURCHASE(R.drawable.main_ic_store_goods_in, "fa08d158-0916-47be-a378-edd6aeb9cfce", "flutter://pda_store_purchase/main"),
    /**
     * 扫码补货
     */
    REPLENISH(R.drawable.main_ic_scan_goods_supply, "812ab712-10a0-4d6c-8ff4-358815491d23", "flutter://pda_replenish/main"),
    /**
     * 门店退货
     */
    REJECT(R.drawable.main_ic_store_goods_return, "3257b340-de59-49e9-ae3f-c8293ba00f8c", "flutter://pda_reject/main"),
    /**
     * 门店调拨
     */
    ALLOT(R.drawable.main_ic_store_allot, "6816037c-1c84-4e7e-a65a-b556ddb77e44", "flutter://pda_allot/main"),
    /**
     * 单据查询
     */
    DOCUMENT_QUERY(R.drawable.main_ic_document_query, "83e884a4-9492-4612-a28d-45e7dca382ed", "flutter://pda_document_plugin/main"),
    /**
     * 门店拣货
     */
    PICKING(R.drawable.main_ic_picking, "83e884a4-9492-4612-a28d-45e7dca382ef", "flutter://pda_picking/main"),
    /**
     * 门店自提
     */
    SELF_MENTION(R.drawable.main_ic_self_mention, "83e884a4-9492-4612-a28d-45e7dca382ee", "flutter://pda_selfmention/main"),
    /**
     * 差异调整
     */
    DIFFERENCE_ADJUST(R.drawable.main_ic_difference_adjust, "6816037c-1c84-4e7e-a65a-b556ddb77e45", "flutter://pda_difference_adjust/main"),
    /**
     * 报损
     */
    LOSS(R.drawable.main_ic_loss, "4f7bce5f-ff33-4624-9952-d21538d88ac4", "flutter://pda_loss/main"),
    /**
     * 实时盘点
     */
    CHECK_IN_TIME(R.drawable.main_ic_check_in_time, "a1b06bff-866a-481c-93ee-c8b71d4c198c", "flutter://pda_check_real_time/main"),
    /**
     * 好坏品转换
     * */
    CONVERT(R.drawable.main_ic_convert, "4f7bce5f-ff33-4624-9952-d21538d88ac5", "flutter://pda_convert_plugin/main"),
    /**
     * 打折促销
     * */
    DISCOUNT(R.drawable.main_ic_discount,"4f7bce5f-ff33-4624-9952-d21538d88ac6","flutter://pda_discount/main"),
    /**
     * 价签绑定
     * */
    PRICE_TAG(R.drawable.main_ic_price_tag,"4f7bce5f-ff33-4624-9952-d21538d88ac7","flutter://pda_price_tag/main"),
    /**
     * 店内领用
     * */
    COLLECT(R.drawable.main_ic_collect,"4f7bce5f-ff33-4624-9952-d21538d88ac8","flutter://pda_collect/main");

    @DrawableRes
    private final int icon;
    private final String resourceCode;
    private final String router;

    MenuResource(@DrawableRes int drawableId, @NonNull String resourceCode, @NonNull String router) {
        this.resourceCode = resourceCode;
        this.icon = drawableId;
        this.router = router;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public String getRouter() {
        return router;
    }

    @NonNull
    public static MenuResource fromResourceCode(String resourceCode) {
        for (MenuResource value : MenuResource.values()) {
            if (value.getResourceCode().equals(resourceCode)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
