package com.jd.saas.padinventory.create;

import com.jd.saas.pdacommon.search.InventoryCreateDialogBean;

import java.util.List;

/**
 * 搜索商品点击子条目返回bean
 * @author: ext.anxinlong
 * @date: 2021/6/17
 */
public class InventoryGoodsBean {

    private int storeId;
    private String skuId;
    private String productId;
    private String upcCode;
    private Object saleSpecDesc;
    private int saleUnit;
    private String saleUnitName;
    private String skuName;
    private String logo;
    private int shelfLife;
    private int shelfLifeUnit;
    private double basePrice;
    private double taxRate;
    private Object remark;
    private Object mobiledesc;
    private Object images;
    private Object statusList;
    private Object dataType;
    private String skuShortName;
    private int tenantId;
    private int productYn;
    private Object hasPhotos;
    private Object skuStatus;
    private Object adword;
    private String placeOfProduct;
    private String storeSkuModified;
    private String created;
    private Object outerId;
    private String brandId;
    private Object brandName;
    private double weight;
    private Object weightUnit;
    private List<Integer> categoryIds;
    private int productType;
    private Object productTypeName;
    private Object categoryNames;
    private Object productYnName;
    private FinanceBean finance;
    private Object frontShowPrice;
    private String storeName;
    private Object buyNo;
    private Object source;
    private Object modelCode;
    private Object showPrice;
    private boolean isShelfLifeSup;
    private Object isBook;
    private Object isSell;
    private Object isRetreat;
    private Object storeType;
    private Object storeAttribute;
    private Object supplierCode;
    private Object supplierName;
    private Object supplyPrice;
    private Object showPriceUnit;
    private Object displayNum;
    private List<PromotionsBean> promotions;
    private String nonGoodQty;
    private String goodQty;
    private int totalQty;
    private String qytAvailable;
    private String skuQty;
    private List<InventoryCreateDialogBean> locQtyList;
    private int saleMode = 1;

    public int getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(int saleMode) {
        this.saleMode = saleMode;
    }

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

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public Object getSaleSpecDesc() {
        return saleSpecDesc;
    }

    public void setSaleSpecDesc(Object saleSpecDesc) {
        this.saleSpecDesc = saleSpecDesc;
    }

    public int getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(int saleUnit) {
        this.saleUnit = saleUnit;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public int getShelfLifeUnit() {
        return shelfLifeUnit;
    }

    public void setShelfLifeUnit(int shelfLifeUnit) {
        this.shelfLifeUnit = shelfLifeUnit;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getMobiledesc() {
        return mobiledesc;
    }

    public void setMobiledesc(Object mobiledesc) {
        this.mobiledesc = mobiledesc;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public Object getStatusList() {
        return statusList;
    }

    public void setStatusList(Object statusList) {
        this.statusList = statusList;
    }

    public Object getDataType() {
        return dataType;
    }

    public void setDataType(Object dataType) {
        this.dataType = dataType;
    }

    public String getSkuShortName() {
        return skuShortName;
    }

    public void setSkuShortName(String skuShortName) {
        this.skuShortName = skuShortName;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getProductYn() {
        return productYn;
    }

    public void setProductYn(int productYn) {
        this.productYn = productYn;
    }

    public Object getHasPhotos() {
        return hasPhotos;
    }

    public void setHasPhotos(Object hasPhotos) {
        this.hasPhotos = hasPhotos;
    }

    public Object getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(Object skuStatus) {
        this.skuStatus = skuStatus;
    }

    public Object getAdword() {
        return adword;
    }

    public void setAdword(Object adword) {
        this.adword = adword;
    }

    public String getPlaceOfProduct() {
        return placeOfProduct;
    }

    public void setPlaceOfProduct(String placeOfProduct) {
        this.placeOfProduct = placeOfProduct;
    }

    public String getStoreSkuModified() {
        return storeSkuModified;
    }

    public void setStoreSkuModified(String storeSkuModified) {
        this.storeSkuModified = storeSkuModified;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Object getOuterId() {
        return outerId;
    }

    public void setOuterId(Object outerId) {
        this.outerId = outerId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Object getBrandName() {
        return brandName;
    }

    public void setBrandName(Object brandName) {
        this.brandName = brandName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Object getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Object weightUnit) {
        this.weightUnit = weightUnit;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public Object getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(Object productTypeName) {
        this.productTypeName = productTypeName;
    }

    public Object getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(Object categoryNames) {
        this.categoryNames = categoryNames;
    }

    public Object getProductYnName() {
        return productYnName;
    }

    public void setProductYnName(Object productYnName) {
        this.productYnName = productYnName;
    }

    public FinanceBean getFinance() {
        return finance;
    }

    public void setFinance(FinanceBean finance) {
        this.finance = finance;
    }

    public Object getFrontShowPrice() {
        return frontShowPrice;
    }

    public void setFrontShowPrice(Object frontShowPrice) {
        this.frontShowPrice = frontShowPrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Object getBuyNo() {
        return buyNo;
    }

    public void setBuyNo(Object buyNo) {
        this.buyNo = buyNo;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getModelCode() {
        return modelCode;
    }

    public void setModelCode(Object modelCode) {
        this.modelCode = modelCode;
    }

    public Object getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(Object showPrice) {
        this.showPrice = showPrice;
    }

    public boolean isIsShelfLifeSup() {
        return isShelfLifeSup;
    }

    public void setIsShelfLifeSup(boolean isShelfLifeSup) {
        this.isShelfLifeSup = isShelfLifeSup;
    }

    public Object getIsBook() {
        return isBook;
    }

    public void setIsBook(Object isBook) {
        this.isBook = isBook;
    }

    public Object getIsSell() {
        return isSell;
    }

    public void setIsSell(Object isSell) {
        this.isSell = isSell;
    }

    public Object getIsRetreat() {
        return isRetreat;
    }

    public void setIsRetreat(Object isRetreat) {
        this.isRetreat = isRetreat;
    }

    public Object getStoreType() {
        return storeType;
    }

    public void setStoreType(Object storeType) {
        this.storeType = storeType;
    }

    public Object getStoreAttribute() {
        return storeAttribute;
    }

    public void setStoreAttribute(Object storeAttribute) {
        this.storeAttribute = storeAttribute;
    }

    public Object getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(Object supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Object getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(Object supplierName) {
        this.supplierName = supplierName;
    }

    public Object getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(Object supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Object getShowPriceUnit() {
        return showPriceUnit;
    }

    public void setShowPriceUnit(Object showPriceUnit) {
        this.showPriceUnit = showPriceUnit;
    }

    public Object getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(Object displayNum) {
        this.displayNum = displayNum;
    }

    public List<PromotionsBean> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionsBean> promotions) {
        this.promotions = promotions;
    }

    public String getNonGoodQty() {
        return nonGoodQty;
    }

    public void setNonGoodQty(String nonGoodQty) {
        this.nonGoodQty = nonGoodQty;
    }

    public String getGoodQty() {
        return goodQty;
    }

    public void setGoodQty(String goodQty) {
        this.goodQty = goodQty;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public String getQytAvailable() {
        return qytAvailable;
    }

    public void setQytAvailable(String qytAvailable) {
        this.qytAvailable = qytAvailable;
    }

    public static class FinanceBean {
        private double taxRate;

        public double getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(double taxRate) {
            this.taxRate = taxRate;
        }
    }

    public static class PromotionsBean {
        private Object rsn;
        private Object id;
        private Object tenantId;
        private Object channel;
        private String name;
        private Object adv;
        private Object reason;
        private String beginTime;
        private String endTime;
        private int type;
        private int subType;
        private Object commScope;
        private Object storeIds;
        private Object riskTypes;
        private Object instanceId;
        private Object checkReason;
        private Object partnerCode;
        private Object creator;
        private int checkStatus;
        private int status;

        public Object getRsn() {
            return rsn;
        }

        public void setRsn(Object rsn) {
            this.rsn = rsn;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getTenantId() {
            return tenantId;
        }

        public void setTenantId(Object tenantId) {
            this.tenantId = tenantId;
        }

        public Object getChannel() {
            return channel;
        }

        public void setChannel(Object channel) {
            this.channel = channel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAdv() {
            return adv;
        }

        public void setAdv(Object adv) {
            this.adv = adv;
        }

        public Object getReason() {
            return reason;
        }

        public void setReason(Object reason) {
            this.reason = reason;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSubType() {
            return subType;
        }

        public void setSubType(int subType) {
            this.subType = subType;
        }

        public Object getCommScope() {
            return commScope;
        }

        public void setCommScope(Object commScope) {
            this.commScope = commScope;
        }

        public Object getStoreIds() {
            return storeIds;
        }

        public void setStoreIds(Object storeIds) {
            this.storeIds = storeIds;
        }

        public Object getRiskTypes() {
            return riskTypes;
        }

        public void setRiskTypes(Object riskTypes) {
            this.riskTypes = riskTypes;
        }

        public Object getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(Object instanceId) {
            this.instanceId = instanceId;
        }

        public Object getCheckReason() {
            return checkReason;
        }

        public void setCheckReason(Object checkReason) {
            this.checkReason = checkReason;
        }

        public Object getPartnerCode() {
            return partnerCode;
        }

        public void setPartnerCode(Object partnerCode) {
            this.partnerCode = partnerCode;
        }

        public Object getCreator() {
            return creator;
        }

        public void setCreator(Object creator) {
            this.creator = creator;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
