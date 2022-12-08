package com.jd.saas.pdacommon.search;

import android.text.TextUtils;

import com.jd.saas.pdacommon.imageloader.ImageUrlUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 全局搜索返回结果item bean
 *
 * @author majiheng
 */
public class SearchResultBean implements Serializable {
    private static final long serialVersionUID = -5809782578272943991L;
    // 分页-当前页
    private int page;
    // 分页-单页item条数
    private int pageSize;
    // 分页-返回的当前页面的总条数
    private int total;
    // skuName列表
    private List<SearchGoodBean> data;

   /*
   生命周期
   "isShelfLifeSup":true,"isBook":0,"isBookDesc":"不可订","isSell":0,"isSellDesc":"不可售","isRetreat":0,"isRetreatDesc":"不可退","
    */
    private String lifeCycleDesc;
    private String isBookDesc;
    private String isSellDesc;
    private String isRetreatDesc;

    /* 商品Detail基本信息 */
    private String logo;
    private String skuName;//商品名称
    private List<String> categoryIds;//类目id集合
    private String categoryNames;//类目
    private String skuId;//sku编号？编码
    private String upcCode;//条码？
    private int productType;//类型id：1商品2原材料3耗材
    private String productTypeName;//类型
    private int shelfLife;//保值期-值
    private int shelfLifeUnit;//保质期-单位 1天2月3年
    private String shelfLifeUnitDesc;//保质期-单位
    private String basePrice;//售价-（接口文档-基础价）
    private int saleUnit;//[:INTEGER]销售单位，称重的目前全部为kg,参考UnitEnum,计件值范围为（1-50）：个（piece：1），盒（gaine:2），份（portion:3），袋（bag:4），瓶（bottle:5），箱（box：6），套（set:7），杯（cup:8），只(zhi:9),称重（51-100）：kg（51），g（52）,必填字段
    private String saleUnitName;
    private String saleModeDesc;//售卖方式
    private String availableStockCount;// 可用库存总数
    // 是否是效期商品
    private boolean isShelfLifeSup = false;
    // 库位列表信息
    private List<Storage> distinctLocNameLinkedList;
    // 库位编码
    private String locCode;
    // 商品销售方式：1-计件；2-称重
    private int saleMode = 1;
    // 组合（箱柜）商品
    private List<BoxProducts> boxProducts;
    private int goodsShelves;// 货架期值
    private int goodsShelvesUnit;// 货架期单位枚举：1天、2月、3年、4小时
    private String goodsShelvesUnitDesc;// 货架期-单位

    public int getGoodsShelves() {
        return goodsShelves;
    }

    public void setGoodsShelves(int goodsShelves) {
        this.goodsShelves = goodsShelves;
    }

    public int getGoodsShelvesUnit() {
        return goodsShelvesUnit;
    }

    public void setGoodsShelvesUnit(int goodsShelvesUnit) {
        this.goodsShelvesUnit = goodsShelvesUnit;
    }

    public String getGoodsShelvesUnitDesc() {
        if(TextUtils.isEmpty(goodsShelvesUnitDesc)) {
            goodsShelvesUnitDesc = "";
        }
        return goodsShelvesUnitDesc;
    }

    public void setGoodsShelvesUnitDesc(String goodsShelvesUnitDesc) {
        this.goodsShelvesUnitDesc = goodsShelvesUnitDesc;
    }

    public List<BoxProducts> getBoxProducts() {
        return boxProducts;
    }

    public void setBoxProducts(List<BoxProducts> boxProducts) {
        this.boxProducts = boxProducts;
    }

    // 多箱规商品
    public static class BoxProducts implements Serializable{
        // 箱柜skuId
        private String boxSkuId;
        // 该组合商品skuId
        private String skuId;
        // 组合系数
        private String boxNum;
        // 该组合商品名称
        private String skuName;
        // 该组合商品upcCode
        private String upcCode;
        // 该组合商品skuType
        private String skuType;

        public String getUpcCode() {
            return upcCode;
        }

        public void setUpcCode(String upcCode) {
            this.upcCode = upcCode;
        }

        public String getSkuType() {
            return skuType;
        }

        public void setSkuType(String skuType) {
            this.skuType = skuType;
        }

        public String getBoxSkuId() {
            return boxSkuId;
        }

        public void setBoxSkuId(String boxSkuId) {
            this.boxSkuId = boxSkuId;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getBoxNum() {
            return boxNum;
        }

        public void setBoxNum(String boxNum) {
            this.boxNum = boxNum;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }
    }

    public void setShelfLifeUnitDesc(String shelfLifeUnitDesc) {
        this.shelfLifeUnitDesc = shelfLifeUnitDesc;
    }

    public String getShelfLifeUnitDesc() {
        return shelfLifeUnitDesc;
    }

    /**
     * 库位bean
     */
    public static class Storage implements Serializable{

        private int skuNature;
        private String name;

        public int getSkuNature() {
            return skuNature;
        }

        public void setSkuNature(int skuNature) {
            this.skuNature = skuNature;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /* 供货商信息 */
    private String supplierCode;//供货商ID
    private String supplierName;//供货商
    private String supplyPrice;//[:BIGDECIMAL]供货价
    private String showPriceUnit;//[:INTEGER]价格展示单位,,@seeShowPriceUnitEnum

    /* 促销信息 */
    private List<Promotion> promotions;
    private List<InventoryCreateDialogBean> locQtyList;
    private String skuQty;

    public String getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(String skuQty) {
        this.skuQty = skuQty;
    }

    public List<InventoryCreateDialogBean> getLocQtyList() {
        return locQtyList;
    }

    public void setLocQtyList(List<InventoryCreateDialogBean> locQtyList) {
        this.locQtyList = locQtyList;
    }

    /* 库存信息 */
    private String totalQty ;//[:BIGDECIMAL]好坏品总数量
    private String goodQty;//[:BIGDECIMAL]好品数量
    private String nonGoodQty;//[:BIGDECIMAL]坏品数量
    private String qtyAvailable;//[:BIGDECIMAL]坏品数量
    private String qtyAllocated;//[:BIGDECIMAL]坏品数量
    private String storeOnWayStock;//[:BIGDECIMAL]在途
    private String stockUpperLimit;//[:BIGDECIMAL]库存上限
    private String stockLowerLimit;//[:BIGDECIMAL]库存下限

    public class Promotion implements Serializable{
        private static final long serialVersionUID = -5809782578272943931L;

        private String name;
        private Date beginTime;
        private Date endTime;
        private String statusDesc;
        private int status;
//        public enum PromotionStatusEnum {}

//            DELETE(0, "删除状态"),
//            ALIVE(1, "有效状态"),
//            TOSTART(4, "待开始"),
//            ONGOING(5, "进行中"),
//            HASEND(6, "已结束"),
//            HASDISCARD(7, "已作废"),
//            ALL(8, "全部"),
//            DRAFT(2, "草稿状态");
        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusDesc() {
            return statusDesc;
        }

        public int getStatus() {
            return status;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBeginTime(Date beginTime) {
            this.beginTime = beginTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getName() {
            return name;
        }

        public Date getBeginTime() {
            return beginTime;
        }

        public Date getEndTime() {
            return endTime;
        }
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public String getGoodQty() {
        if (TextUtils.isEmpty(goodQty)){
            return "0";
        }else{
            return goodQty;
        }

    }

    public void setSaleModeDesc(String saleModeDesc) {
        this.saleModeDesc = saleModeDesc;
    }

    public String getSaleModeDesc() {
        return saleModeDesc;
    }

    public void setStoreOnWayStock(String storeOnWayStock) {
        this.storeOnWayStock = storeOnWayStock;
    }

    public void setStockUpperLimit(String stockUpperLimit) {
        this.stockUpperLimit = stockUpperLimit;
    }

    public void setStockLowerLimit(String stockLowerLimit) {
        this.stockLowerLimit = stockLowerLimit;
    }

    public String getStoreOnWayStock() {
        return storeOnWayStock;
    }

    public String getStockUpperLimit() {
        return stockUpperLimit;
    }

    public String getStockLowerLimit() {
        return stockLowerLimit;
    }

    public String getNonGoodQty() {
        if (TextUtils.isEmpty(nonGoodQty)){
            return "0";
        }else{
            return nonGoodQty;
        }

    }

    public void setLifeCycleDesc(String lifeCycleDesc) {
        this.lifeCycleDesc = lifeCycleDesc;
    }

    public void setIsBookDesc(String isBookDesc) {
        this.isBookDesc = isBookDesc;
    }

    public void setIsSellDesc(String isSellDesc) {
        this.isSellDesc = isSellDesc;
    }

    public void setIsRetreatDesc(String isRetreatDesc) {
        this.isRetreatDesc = isRetreatDesc;
    }

    public String getLifeCycleDesc() {
        return lifeCycleDesc;
    }

    public String getIsBookDesc() {
        return isBookDesc;
    }

    public String getIsSellDesc() {
        return isSellDesc;
    }

    public String getIsRetreatDesc() {
        return isRetreatDesc;
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
        if(!TextUtils.isEmpty(logo)) {
            return ImageUrlUtil.convertImageURL(logo);
        }else {
            return "";
        }
    }

    public void setQtyAvailable(String qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public void setQtyAllocated(String qtyAllocated) {
        this.qtyAllocated = qtyAllocated;
    }

    public String getQtyAvailable() {
        return qtyAvailable;
    }

    public String getQtyAllocated() {
        return qtyAllocated;
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
    public List<SearchGoodBean> getData() {
        return data;
    }

    public void setData(List<SearchGoodBean> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getAvailableStockCount() {
        return availableStockCount;
    }

    public void setAvailableStockCount(String availableStockCount) {
        this.availableStockCount = availableStockCount;
    }

    public boolean isShelfLifeSup() {
        return isShelfLifeSup;
    }

    public void setShelfLifeSup(boolean shelfLifeSup) {
        isShelfLifeSup = shelfLifeSup;
    }

    public List<Storage> getDistinctLocNameLinkedList() {
        return distinctLocNameLinkedList;
    }

    public void setDistinctLocNameLinkedList(List<Storage> distinctLocNameLinkedList) {
        this.distinctLocNameLinkedList = distinctLocNameLinkedList;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public int getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(int saleMode) {
        this.saleMode = saleMode;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        if(data.size()!=0){
            for(int i=0;i<data.size();i++){
                builder.append(data.get(i).getSkuID()).append(",").append(data.get(i).getSkuName()).append("/////");
            }
            return builder.toString();
        }
       return "";

    }
}
