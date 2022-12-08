package com.jd.saas.pdagoodsquery.goods.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商品信息
 * 根据id查询sku，返回的信息
 *
 * @author ext.mengmeng
 */
public class QueryGoodsDetailBean implements Serializable {
    private static final long serialVersionUID = -5809782578272943998L;

    /* 商品基本信息 */
    private String logo;
    private String skuName;//商品名称
    private String categoryNames;//类目
    private String skuId;//sku编号？编码
    private String upcCode;//条码？
    private int productType;//类型id：1原材料2耗材3商品
    private String productTypeName;//类型
    private int shelfLife;//保值期-值
    private int shelfLifeUnit;//保质期-单位 1天2月3年
    private String basePrice;//售价-（接口文档-基础价）
    private int saleUnit;//[:INTEGER]销售单位，称重的目前全部为kg,参考UnitEnum,计件值范围为（1-50）：个（piece：1），盒（gaine:2），份（portion:3），袋（bag:4），瓶（bottle:5），箱（box：6），套（set:7），杯（cup:8），只(zhi:9),称重（51-100）：kg（51），g（52）,必填字段

    /* 供货商信息 */
    private String supplierCode;//供货商ID
    private String supplierName;//供货商
    private String supplyPrice;//[:BIGDECIMAL]供货价
    private String showPriceUnit;//[:INTEGER]价格展示单位,,@seeShowPriceUnitEnum

    /* 促销信息 */
    private List<Promotion> promotions;

    /* 库存信息 */
    private String totalQty;//[:BIGDECIMAL]好坏品总数量
    private String goodQty;//[:BIGDECIMAL]好品数量
    private String nonGoodQty;//[:BIGDECIMAL]坏品数量

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public void setGoodQty(String goodQty) {
        this.goodQty = goodQty;
    }

    public void setNonGoodQty(String nonGoodQty) {
        this.nonGoodQty = nonGoodQty;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public String getGoodQty() {
        return goodQty;
    }

    public String getNonGoodQty() {
        return nonGoodQty;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public void setShelfLifeUnit(int shelfLifeUnit) {
        this.shelfLifeUnit = shelfLifeUnit;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public void setSaleUnit(int saleUnit) {
        this.saleUnit = saleUnit;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplyPrice(String supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public void setShowPriceUnit(String showPriceUnit) {
        this.showPriceUnit = showPriceUnit;
    }

    public String getLogo() {
        return logo;
    }

    public String getSkuName() {
        return skuName;
    }

    public String getCategoryNames() {
        return categoryNames;
    }

    public String getSkuId() {
        return skuId;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public int getProductType() {
        return productType;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public int getShelfLifeUnit() {
        return shelfLifeUnit;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public int getSaleUnit() {
        return saleUnit;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplyPrice() {
        return supplyPrice;
    }

    public String getShowPriceUnit() {
        return showPriceUnit;
    }


/*
    {"storeId":111127,"skuId":"10000000026401","productId":"10000000026601",
    "upcCode":"4241432143;10000000026401","saleSpecDesc":null,"saleUnit":1,"skuName":"【测试】-测试",
    "logo":"http://img10.360buyimg.com/pop/jfs/t1/160635/27/1295/369921/5ff561a3E0d8218d0/6899310d54838938.png",
    "shelfLife":null,"shelfLifeUnit":null,"basePrice":1.00,"taxRate":13.00,"remark":null,"mobiledesc":null,
    "images":null,"statusList":null,"dataType":null,"skuShortName":"432432","tenantId":101,"productYn":1,
    "hasPhotos":null,"skuStatus":null,
    "adword":{"word":"测试","url":null,"urlKey":null},
    "placeOfProduct":"北京","storeSkuModified":"2021-05-17T03:31:31.000+0000",
    "created":"2021-01-06T07:07:32.000+0000","outerId":null,"brandId":"14001","brandName":null,
    "weight":1.00,"weightUnit":null,"categoryIds":[10006],"productType":1,"productTypeName":null,
    "categoryNames":null,"productYnName":null,"finance":{"taxRate":13.00},"frontShowPrice":null,
    "storeName":"便利店测试直营店","buyNo":null,"source":null,"modelCode":null,"showPrice":null,
    "isShelfLifeSup":false,"isBook":null,"isSell":null,"isRetreat":null,"storeType":null,
    "storeAttribute":null,"supplierCode":null,"supplierName":null,"supplyPrice":null,"showPriceUnit":null,
    "displayNum":null,"promotion":null}
    */
    public class Promotion{
        private String name;
        private String beginTime;
        private String endTime;

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
}
