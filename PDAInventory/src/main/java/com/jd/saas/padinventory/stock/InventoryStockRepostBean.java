package com.jd.saas.padinventory.stock;

import android.os.Bundle;

import com.jd.saas.padinventory.adjustment.InventoryAdjustmentRouterPath;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.router.RouterClient;

import java.util.List;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/15
 */
public class InventoryStockRepostBean {


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
        private int tenantId;
        private int warehouseId;
        private String galNo;
        private int status;
        private int galBizType;
        private int galType;
        private String createDate;
        private String createBy;
        private double totalGalPrice;
        private double totalGalQty;

        public int getTenantId() {
            return tenantId;
        }

        public void setTenantId(int tenantId) {
            this.tenantId = tenantId;
        }

        public int getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(int warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getGalNo() {
            return galNo;
        }

        public void setGalNo(String galNo) {
            this.galNo = galNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getGalBizType() {
            return galBizType;
        }

        public void setGalBizType(int galBizType) {
            this.galBizType = galBizType;
        }

        public int getGalType() {
            return galType;
        }

        public void setGalType(int galType) {
            this.galType = galType;
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

        public double getTotalGalPrice() {
            return totalGalPrice;
        }

        public void setTotalGalPrice(double totalGalPrice) {
            this.totalGalPrice = totalGalPrice;
        }

        public double getTotalGalQty() {
            return totalGalQty;
        }

        public void setTotalGalQty(double totalGalQty) {
            this.totalGalQty = totalGalQty;
        }
        public void onLookDetail(){
            Bundle bundle=new Bundle();
            String galNoa = getGalNo();
            bundle.putString("galno", galNoa);
            RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(),
                    InventoryAdjustmentRouterPath.HOST_PATH_LOGIN,bundle);
        }
    }

}
