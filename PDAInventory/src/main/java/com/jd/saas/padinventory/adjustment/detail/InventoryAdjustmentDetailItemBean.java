package com.jd.saas.padinventory.adjustment.detail;

import com.jd.saas.padinventory.R;
import com.jd.saas.pdacommon.application.MyApplication;

import java.io.Serializable;

/**
 * 订单相信bean类
 * @author: ext.anxinlong
 * @date: 2021/5/31
 */
public class InventoryAdjustmentDetailItemBean implements Serializable{

    private static final long serialVersionUID = 6278220024999810031L;
    private String title;//标题
    private String imageUrl;//logo
    private String number;//商品条码
    private String specification;//规格
    private String storage_location;//库位
    private String storage_location_name;//库位名称
    private String storage_location_number;//报溢数量
    private String Storage_location_cause;//报溢原因
    private String Storage_location_cause_name;//报溢名称
    private String qytAvailable;//库存
    private String skuID;//商品编号
    private String profitOrLossCode;//报溢类型

    public String getProfitOrLossCode() {
        return profitOrLossCode;
    }

    public void setProfitOrLossCode(String profitOrLossCode) {
        this.profitOrLossCode = profitOrLossCode;
    }

    public String getSkuID() {
        return skuID;
    }

    public void setSkuID(String skuID) {
        this.skuID = skuID;
    }

    public String getStorage_location_name() {
        return storage_location_name;
    }

    public void setStorage_location_name(String storage_location_name) {
        this.storage_location_name = storage_location_name;
    }

    public String getStorage_location_cause_name() {
        return Storage_location_cause_name;
    }

    public void setStorage_location_cause_name(String storage_location_cause_name) {
        Storage_location_cause_name = storage_location_cause_name;
    }

    public String getQytAvailable() {
        return qytAvailable;
    }

    public void setQytAvailable(String qytAvailable) {
        this.qytAvailable = qytAvailable;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getStorage_location() {
        return storage_location;
    }

    public void setStorage_location(String storage_location) {
        this.storage_location = storage_location;
    }

    public String getStorage_location_number() {
        return storage_location_number;
    }

    public void setStorage_location_number(String storage_location_number) {
        this.storage_location_number = storage_location_number;
    }

    public String getStorage_location_cause() {
        return Storage_location_cause;
    }

    public void setStorage_location_cause(String storage_location_cause) {
        Storage_location_cause = storage_location_cause;
    }


    public String status(){
        String profitOrLossCode = getProfitOrLossCode();
        if (profitOrLossCode.equals("0")){
            return MyApplication.getInstance().getString(R.string.inventory_adjustment_item_report_overflow);
        }else{
            return MyApplication.getInstance().getString(R.string.inventory_adjustment_item_overflow);
        }
    }

    public String statusCause(){
        String profitOrLossCode = getProfitOrLossCode();
        if (profitOrLossCode.equals("0")){
            return MyApplication.getInstance().getString(R.string.inventory_adjustment_item_report);
        }else{
            return MyApplication.getInstance().getString(R.string.inventory_adjustment_item_cause);
        }
    }

}
