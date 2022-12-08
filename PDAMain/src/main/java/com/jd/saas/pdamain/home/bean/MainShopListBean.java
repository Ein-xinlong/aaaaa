package com.jd.saas.pdamain.home.bean;

import android.text.TextUtils;

import com.jd.saas.pdacommon.imageloader.ImageUrlUtil;

/**
 * 门店列表返回数据
 *
 * @author majiheng
 */
public class MainShopListBean {

    // 租户id
    private int tenantId;
    // 门店id
    private int storeId;
    // 门店名称
    private String storeName;
    // 门店类型
    private int storeType;
    // 创建者
    private String creator;
    // 修改者
    private String modifier;
    // 门店icon
    private String pictureUrl;

    public String getStoreIcon() {
        if(!TextUtils.isEmpty(pictureUrl)) {
            return ImageUrlUtil.convertImageURL(pictureUrl);
        }else {
            return "";
        }
    }

    public void setStoreIcon(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getStoreId() {
        return storeId;
    }
    public String getStringStoreId() {
        return storeId+"";
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
