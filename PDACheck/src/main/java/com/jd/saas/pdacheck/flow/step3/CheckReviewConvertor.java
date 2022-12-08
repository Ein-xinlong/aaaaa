package com.jd.saas.pdacheck.flow.step3;

import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderBean;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewPreOrderResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntBean;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewQtyTypedCntResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuBean;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSkuResult;
import com.jd.saas.pdacheck.flow.step3.bean.CheckReviewSubmitOrderParam;

import java.math.BigDecimal;

public class CheckReviewConvertor {
    public static CheckReviewSkuBean convert(CheckReviewSkuResult result) {
        CheckReviewSkuBean bean = new CheckReviewSkuBean();
        bean.setSkuId(result.getSkuId());
        bean.setSkuName(result.getSkuName());
        String upcCodes = result.getUpcCodes();
        if (upcCodes == null || upcCodes.isEmpty()) {
            bean.setUpcCodes(null);
        } else {
            bean.setUpcCodes(upcCodes.split(";"));
        }
        bean.setLocName(result.getLocName());
        bean.setLocNo(result.getLocNo());
        bean.setLocType(result.getLocType());
        bean.setStockQty(result.getQty());
        bean.setCheckQty(result.getActualQty());
        bean.setDiffQty(result.getDiffQty());
        bean.setAvgAmount(result.getInPrice());
        bean.setDiffAmount(result.getDiffAmount());
        bean.setOperator(result.getOperator());
        bean.setSaleMode(result.getSaleMode());
        bean.setUom(result.getUom());
        return bean;
    }

    public static CheckReviewPreOrderBean convert(CheckReviewPreOrderResult result) {
        CheckReviewPreOrderBean bean = new CheckReviewPreOrderBean();
        bean.setTenantId(result.getTenantId());
        bean.setWhId(result.getWhId());
        bean.setTaskNo(result.getTaskNo());
        bean.setCouHNo(result.getCouHNo());
        bean.setSkuId(result.getSkuId());
        bean.setQty(result.getQty());
        bean.setActualQty(new BigDecimal(result.getActualQty()));
        bean.setInputQty(new BigDecimal(result.getActualQty()));
        bean.setOperator(result.getOperator());
        bean.setId(result.getId());
        bean.setCreateDate(result.getCreateDate());
        bean.setLocNo(result.getLocNo());
        return bean;
    }

    public static CheckReviewSubmitOrderParam convert(CheckReviewPreOrderBean bean) {
        CheckReviewSubmitOrderParam param = new CheckReviewSubmitOrderParam();
        param.setTenantId(bean.getTenantId());
        param.setWhId(bean.getWhId());
        param.setTaskNo(bean.getTaskNo());
        param.setCouHNo(bean.getCouHNo());
        param.setId(Integer.parseInt(bean.getId()));
        param.setSkuId(bean.getSkuId());
        param.setLocNo(bean.getLocNo());
        param.setActualQty(bean.getInputQty());
        return param;
    }

    public static CheckReviewQtyTypedCntBean convert(CheckReviewQtyTypedCntResult result) {
        CheckReviewQtyTypedCntBean bean = new CheckReviewQtyTypedCntBean();
        bean.setLossCnt(result.getLossCouCount());
        bean.setOverCnt(result.getGainCouCount());
        bean.setEqualCnt(result.getNoDiffCount());
        return bean;
    }
}
