package com.jd.saas.padinventory.create;

import java.util.List;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/15
 */
public class InventoryCreateStorageResponseBean {


    private int pageNo;
    private int pageSize;
    private int total;
    private List<ItemListBean> itemList;
    private int totalPage;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
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

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public static class ItemListBean {
        private int locId;
        private int tenantId;
        private int whId;
        private int partitionId;
        private int regionType;
        private String locCode;
        private String locName;
        private String locType;
        private Object locSubType;
        private int ownerType;
        private String ownerCode;
        private Object pickSeq;
        private int locTag;
        private int oneBit;
        private Object outCode;
        private int delFlag;
        private int version;
        private String createDate;
        private String createBy;
        private String updateDate;
        private String updateBy;
        private Object ext;
        private String partitionCode;
        private String partitionName;
        private boolean materialLocation;
        private boolean skuLocation;
        private boolean skuBadLocation;
        private boolean materialBadLocation;
        private boolean consumableLocation;
        private boolean consumableBadLocation;

        public int getLocId() {
            return locId;
        }

        public void setLocId(int locId) {
            this.locId = locId;
        }

        public int getTenantId() {
            return tenantId;
        }

        public void setTenantId(int tenantId) {
            this.tenantId = tenantId;
        }

        public int getWhId() {
            return whId;
        }

        public void setWhId(int whId) {
            this.whId = whId;
        }

        public int getPartitionId() {
            return partitionId;
        }

        public void setPartitionId(int partitionId) {
            this.partitionId = partitionId;
        }

        public int getRegionType() {
            return regionType;
        }

        public void setRegionType(int regionType) {
            this.regionType = regionType;
        }

        public String getLocCode() {
            return locCode;
        }

        public void setLocCode(String locCode) {
            this.locCode = locCode;
        }

        public String getLocName() {
            return locName;
        }

        public void setLocName(String locName) {
            this.locName = locName;
        }

        public String getLocType() {
            return locType;
        }

        public void setLocType(String locType) {
            this.locType = locType;
        }

        public Object getLocSubType() {
            return locSubType;
        }

        public void setLocSubType(Object locSubType) {
            this.locSubType = locSubType;
        }

        public int getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(int ownerType) {
            this.ownerType = ownerType;
        }

        public String getOwnerCode() {
            return ownerCode;
        }

        public void setOwnerCode(String ownerCode) {
            this.ownerCode = ownerCode;
        }

        public Object getPickSeq() {
            return pickSeq;
        }

        public void setPickSeq(Object pickSeq) {
            this.pickSeq = pickSeq;
        }

        public int getLocTag() {
            return locTag;
        }

        public void setLocTag(int locTag) {
            this.locTag = locTag;
        }

        public int getOneBit() {
            return oneBit;
        }

        public void setOneBit(int oneBit) {
            this.oneBit = oneBit;
        }

        public Object getOutCode() {
            return outCode;
        }

        public void setOutCode(Object outCode) {
            this.outCode = outCode;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public Object getExt() {
            return ext;
        }

        public void setExt(Object ext) {
            this.ext = ext;
        }

        public String getPartitionCode() {
            return partitionCode;
        }

        public void setPartitionCode(String partitionCode) {
            this.partitionCode = partitionCode;
        }

        public String getPartitionName() {
            return partitionName;
        }

        public void setPartitionName(String partitionName) {
            this.partitionName = partitionName;
        }

        public boolean isMaterialLocation() {
            return materialLocation;
        }

        public void setMaterialLocation(boolean materialLocation) {
            this.materialLocation = materialLocation;
        }

        public boolean isSkuLocation() {
            return skuLocation;
        }

        public void setSkuLocation(boolean skuLocation) {
            this.skuLocation = skuLocation;
        }

        public boolean isSkuBadLocation() {
            return skuBadLocation;
        }

        public void setSkuBadLocation(boolean skuBadLocation) {
            this.skuBadLocation = skuBadLocation;
        }

        public boolean isMaterialBadLocation() {
            return materialBadLocation;
        }

        public void setMaterialBadLocation(boolean materialBadLocation) {
            this.materialBadLocation = materialBadLocation;
        }

        public boolean isConsumableLocation() {
            return consumableLocation;
        }

        public void setConsumableLocation(boolean consumableLocation) {
            this.consumableLocation = consumableLocation;
        }

        public boolean isConsumableBadLocation() {
            return consumableBadLocation;
        }

        public void setConsumableBadLocation(boolean consumableBadLocation) {
            this.consumableBadLocation = consumableBadLocation;
        }
    }
}
