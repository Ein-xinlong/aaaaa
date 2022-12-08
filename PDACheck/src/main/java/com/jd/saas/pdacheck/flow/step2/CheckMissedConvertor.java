package com.jd.saas.pdacheck.flow.step2;

import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuBean;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSkuResp;
import com.jd.saas.pdacheck.flow.step2.bean.CheckMissedSubmitSkuParam;

/**
 * 实体转换方法
 * 从接口实体转换为页面Bean实体
 *
 * @author gouhetong
 */
public class CheckMissedConvertor {

    public static CheckMissedSkuBean convert(CheckMissedSkuResp result) {
        CheckMissedSkuBean bean = new CheckMissedSkuBean();
        bean.setSkuId(result.getSkuId());
        bean.setSkuName(result.getSkuName());
        String upcCodes = result.getUpcCodes();
        if (upcCodes == null || upcCodes.isEmpty()) {
            bean.setUpcCodes(null);
        } else {
            bean.setUpcCodes(upcCodes.split(";"));
        }
        bean.setUom(result.getUom());
        Integer skuType = result.getSkuType();
        bean.setSkuType(skuType == null ? 1 : skuType);
        bean.setSaleMode(result.getSaleMode());
        bean.setLocCode(result.getLocCode());
        bean.setLocName(result.getLocName());
        bean.setLocNo(result.getLocNo());
        bean.setStockQty(result.getQty());
        return bean;
    }


    public static CheckMissedSubmitSkuParam convert(String taskNo, CheckMissedSkuBean bean) {
        CheckMissedSubmitSkuParam param = new CheckMissedSubmitSkuParam();
        param.setTaskNo(taskNo);
        param.setSkuId(bean.getSkuId());
        param.setSkuType(bean.getSkuType());
        param.setLocCode(bean.getLocCode());
        param.setLocNo(bean.getLocNo());
        param.setActualQty(bean.getInputQty());
        return param;
    }
}
