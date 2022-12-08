package com.jd.saas.pdacommon.user;

import android.text.TextUtils;

/**
 * 租户全局配置信息
 *
 * @author majiheng
 */
public class UserTenantConfigBean {

    // 字符串有返回数据时为允许负库存模式，其中数据为1销售、2退货、3调拨、4报损、5领用，多选逗号分隔，返回为空时，为不支持负库存模式
    private String neg_stock_scene;
    // 是否显示进货价（采购价），默认显示
    private boolean showPurchasePrice;

    public String getNegativeStockSale() {
        if(TextUtils.isEmpty(neg_stock_scene)) {
            neg_stock_scene = "";
        }
        return neg_stock_scene;
    }

    public void setNegativeStockSale(String negativeStockSale) {
        this.neg_stock_scene = negativeStockSale;
    }

    public boolean isShowPurchasePrice() {
        // 本地先写固定几个需要隐藏进货价（采购价）的租户id
        // 中粮租户ID 100009874，牧原租户ID：100008007，青岛方元至简：100010021，宝地势坤：19255 目前这四个租户隐藏进货价（采购价）
        String tenantId = UserManager.getInstance().getUserData().getTenantId();
        if(tenantId.equalsIgnoreCase("100009874") || tenantId.equalsIgnoreCase("100008007") || tenantId.equalsIgnoreCase("100010021") || tenantId.equalsIgnoreCase("19255")) {
            return false;
        }
        return true;
    }

    public void setShowPurchasePrice(boolean showPurchasePrice) {
        this.showPurchasePrice = showPurchasePrice;
    }
}
