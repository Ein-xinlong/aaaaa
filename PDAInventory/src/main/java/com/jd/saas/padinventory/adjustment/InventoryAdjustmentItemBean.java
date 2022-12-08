package com.jd.saas.padinventory.adjustment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.jd.saas.padinventory.R;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;

import java.util.List;

/**
 * 订单详情bean类
 *
 * @author: ext.anxinlong
 * @date: 2021/5/31
 */
public class InventoryAdjustmentItemBean {


    private Object tenantId;
    private Object warehouseId;
    private String galNo;
    private String status;
    private Object galType;
    private String createStartTime;
    private Object createEndTime;
    private String createName;
    private SkuBoPageExtBean skuBoPageExt;


    public Object getTenantId() {
        return tenantId;
    }

    public void setTenantId(Object tenantId) {
        this.tenantId = tenantId;
    }

    public Object getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Object warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getGalNo() {
        return galNo;
    }

    public void setGalNo(String galNo) {
        this.galNo = galNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getGalType() {
        return galType;
    }

    public void setGalType(Object galType) {
        this.galType = galType;
    }

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Object getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Object createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public SkuBoPageExtBean getSkuBoPageExt() {
        return skuBoPageExt;
    }

    public void setSkuBoPageExt(SkuBoPageExtBean skuBoPageExt) {
        this.skuBoPageExt = skuBoPageExt;
    }

    public static class SkuBoPageExtBean {
        private List<DataBean> data;
        private int total;
        private int pageSize;
        private int page;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public static class DataBean {
            private String upcCode;
            private String logo;
            private String unit;
            private String profitOrLossStatus;
            private int locId;
            private String locName;
            private double qty;
            private Object skuId;
            private String skuName;
            private int skuType;
            private Object stockQty;
            private Object actualQty;
            private Object avgPrice;
            private String reasonName;
            private Object reasonCode;

            public String getSkuName() {
                return skuName;
            }

            public void setSkuName(String skuName) {
                this.skuName = skuName;
            }

            public String getUpcCode() {
                return upcCode;
            }

            public void setUpcCode(String upcCode) {
                this.upcCode = upcCode;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getProfitOrLossStatus() {
                return profitOrLossStatus;
            }

            public void setProfitOrLossStatus(String profitOrLossStatus) {
                this.profitOrLossStatus = profitOrLossStatus;
            }

            public int getLocId() {
                return locId;
            }

            public void setLocId(int locId) {
                this.locId = locId;
            }

            public String getLocName() {
                return locName;
            }

            public void setLocName(String locName) {
                this.locName = locName;
            }

            public double getQty() {
                return qty;
            }

            public void setQty(double qty) {
                this.qty = qty;
            }

            public Object getSkuId() {
                return skuId;
            }

            public void setSkuId(Object skuId) {
                this.skuId = skuId;
            }

            public int getSkuType() {
                return skuType;
            }

            public void setSkuType(int skuType) {
                this.skuType = skuType;
            }

            public Object getStockQty() {
                return stockQty;
            }

            public void setStockQty(Object stockQty) {
                this.stockQty = stockQty;
            }

            public Object getActualQty() {
                return actualQty;
            }

            public void setActualQty(Object actualQty) {
                this.actualQty = actualQty;
            }

            public Object getAvgPrice() {
                return avgPrice;
            }

            public void setAvgPrice(Object avgPrice) {
                this.avgPrice = avgPrice;
            }

            public String getReasonName() {
                return reasonName;
            }

            public void setReasonName(String reasonName) {
                this.reasonName = reasonName;
            }

            public Object getReasonCode() {
                return reasonCode;
            }

            public void setReasonCode(Object reasonCode) {
                this.reasonCode = reasonCode;
            }

            /**
             * 点击复制商品条码
             *
             * @author: ext.anxinlong
             * @date: 2021/5/31
             */
            public void copy(View view) {
                ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                String number = getUpcCode();
                ClipData mClipData = ClipData.newPlainText("Label", number);
                cm.setPrimaryClip(mClipData);
                MyToast.show(R.string.inventory_create_copy_success, false);
            }

            public String returnReasonName() {
                if (getReasonName() == null) {
                    return "";
                }
                Object reasonName = getReasonName();
                String s = reasonName.toString();
                return s;
            }

            public String returnUnit() {
                if (getUnit() == null) {
                    return "";
                }
                Object unit = getUnit();
                String s = unit.toString();
                return s;
            }

            public String returnQty() {
                Object qty = getQty();
                String s = qty.toString();
                if (getUnit().equals("kg")||getUnit().equals("g")){
                    return s;
                }else{
                    String substring = s.substring(0, s.indexOf("."));
                    return substring;
                }

            }
            public String lossReport(){
                String profitOrLossStatus = getProfitOrLossStatus();
                if (profitOrLossStatus==null){
                    return null;
                }
                if (profitOrLossStatus.equals("0")){
                    return MyApplication.getInstance().getString(R.string.inventory_adjustment_item_report_overflow);
                }else{
                    return MyApplication.getInstance().getString(R.string.inventory_adjustment_item_overflow);
                }

            }
            public String lossReportCause(){
                String profitOrLossStatus = getProfitOrLossStatus();
                if (profitOrLossStatus==null){
                    return null;
                }
                if (profitOrLossStatus.equals("0")){
                    return MyApplication.getInstance().getString(R.string.inventory_adjustment_item_report);
                }else{
                    return MyApplication.getInstance().getString(R.string.inventory_adjustment_item_cause);
                }

            }
            public boolean hide(){
                String upcCode = getUpcCode();
                if (TextUtils.isEmpty(upcCode)){
                    return false;
                }else{
                    return true;
                }
            }


        }
    }
}
