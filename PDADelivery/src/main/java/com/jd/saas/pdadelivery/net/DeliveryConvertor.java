package com.jd.saas.pdadelivery.net;

import android.text.TextUtils;

import com.jd.saas.pdacommon.enums.AppType;
import com.jd.saas.pdacommon.imageloader.ImageUrlUtil;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.utils.ListUtils;
import com.jd.saas.pdadelivery.detail.bean.DeliveryBoxProductBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDetailBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDiffReasonBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryDiffSkuBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryShelfLifeInfoBean;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuLogBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryStockTypeBean;
import com.jd.saas.pdadelivery.detail.ui.DeliveryDetailEditCombinedSkuDialog;
import com.jd.saas.pdadelivery.manage.bean.DeliveryBean;
import com.jd.saas.pdadelivery.net.enums.AsnStatusEnum;
import com.jd.saas.pdadelivery.net.enums.DiffTypeEnum;
import com.jd.saas.pdadelivery.net.enums.SaleModeEnum;
import com.jd.saas.pdadelivery.net.enums.ShelfLifeUnitEnum;
import com.jd.saas.pdadelivery.net.param.DiffInfoParam;
import com.jd.saas.pdadelivery.net.param.SubmitRcvSkuParam;
import com.jd.saas.pdadelivery.net.result.QueryDiffReasonResult;
import com.jd.saas.pdadelivery.net.result.QueryLocationsResult;
import com.jd.saas.pdadelivery.net.result.QueryRcvDiffListResult;
import com.jd.saas.pdadelivery.net.result.RcvStockInfoResult;
import com.jd.saas.pdadelivery.net.result.StockDetailResult;
import com.jd.saas.pdadelivery.net.result.StockExchangeResult;
import com.jd.saas.pdadelivery.net.result.StockItemResult;
import com.jd.saas.pdadelivery.util.DeliveryConfigProvider;
import com.jd.saas.pdadelivery.util.Formatter;
import com.jd.saas.pdadelivery.util.LocTypeUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DeliveryConvertor {


    public static DeliveryBean convert(StockItemResult item) {
        DeliveryBean bean = new DeliveryBean();
        bean.setAsnType(item.getAsnType());
        bean.setAsnNo(item.getAsnNo());
        bean.setStatus(item.getStatus());
        bean.setSupplierName(item.getSupplierName());
        bean.setCreateDate(Formatter.parseResult(item.getCreateDate()));
        bean.setUpdateDate(Formatter.parseResult(item.getUpdateDate()));
        bean.setContactName(item.getContactName());
        bean.setContactTelephone(item.getContactTelephone());
        bean.setRcvTypeSize(item.getRcvTypeSize());
        bean.setRcvFinished(item.getRcvFinished());
        bean.setRcvNone(item.getRcvNone());
        bean.setRcvSome(item.getRcvSome());
        bean.setAsnRefNo(item.getAsnRefNo());
//        bean.setReceivingQty(item.getActualQty());
        return bean;
    }

    public static DeliveryDetailBean convert(StockDetailResult result) {
        DeliveryDetailBean bean = new DeliveryDetailBean();
        bean.setAsnNo(result.getRcvHeaderInfoDto().getAsnNo());
        bean.setAsnType(result.getRcvHeaderInfoDto().getAsnType());
        bean.setStatus(result.getRcvHeaderInfoDto().getStatus());
        bean.setSupplierName(result.getRcvHeaderInfoDto().getSupplierName());
        bean.setCreateDate(Formatter.parseResult(result.getRcvHeaderInfoDto().getCreateDate()));
        bean.setCloseTime(Formatter.parseResult(result.getRcvHeaderInfoDto().getCloseTime()));
        bean.setRcvTypeSize(result.getRcvHeaderInfoDto().getRcvTypeSize());
        BigDecimal actualQty = result.getRcvHeaderInfoDto().getActualQty();
        if (actualQty != null) {
            bean.setActualQty(actualQty);
        }
        bean.setExpectedQty(result.getRcvHeaderInfoDto().getExpectedQty());
        int asnStatus = bean.getStatus();
        bean.setAsnRefNo(result.getRcvHeaderInfoDto().getAsnRefNo());
        bean.setSkuList(ListUtils.map(result.getRcvStockInfoDtos(), from -> convert(asnStatus, from)));
        return bean;
    }

    public static DeliverySkuBean convert(int asnStatus, SearchResultBean result, ListUtils.MapTo<SearchResultBean.BoxProducts, DeliveryBoxProductBean> mapTo) {
        DeliverySkuBean skuBean = new DeliverySkuBean();
        skuBean.setAsnStatus(asnStatus);
        skuBean.setSkuId(result.getSkuId());
        skuBean.setSkuName(result.getSkuName());
        skuBean.setSkuType(result.getProductType());
        String upcCodes = result.getUpcCode();
        if (!TextUtils.isEmpty(upcCodes) && upcCodes.contains(";")) {
            skuBean.setUpcCodes(upcCodes.split(";"));
        } else {
            skuBean.setUpcCodes(new String[]{upcCodes});
        }
        skuBean.setUom(result.getSaleUnitName());
        skuBean.setActualQty(BigDecimal.ZERO);
        skuBean.setExpectedQty(BigDecimal.ZERO);
        skuBean.setShelfLife(result.getShelfLife());
        skuBean.setShelfLifeUnit(result.getShelfLifeUnit());
        skuBean.setShelfLifeUnitDesc(result.getShelfLifeUnitDesc());
        skuBean.setLogo(ImageUrlUtil.convertImageURL(result.getLogo()));
        //目前只有门店版才会识别效期商品，仓版本默认全部按照普通商品处理
        skuBean.setShelfLifeSup(DeliveryConfigProvider.getClientType() == AppType.STORE && result.isShelfLifeSup());
        skuBean.setSaleMode(result.getSaleMode());
        skuBean.setCanFold(false);
        //最大收货数量
        skuBean.setUpperLimitReceived(BigDecimal.ZERO);
        skuBean.setLocName(DeliveryDetailEditCombinedSkuDialog.GOOD_NAME);
        skuBean.setBoxProducts(ListUtils.filter(ListUtils.map(result.getBoxProducts(), mapTo), a -> a != null));
        return skuBean;
    }

    public static DeliveryBoxProductBean convert(SearchResultBean.BoxProducts result, DeliverySkuBean skuBean, List<DeliveryStockTypeBean> stockTypeList) {
        DeliveryBoxProductBean boxProductBean = new DeliveryBoxProductBean();
        boxProductBean.setBoxSkuId(result.getBoxSkuId());
        boxProductBean.setSkuId(result.getSkuId());
        boxProductBean.setBoxNum(new BigDecimal(result.getBoxNum()));
        boxProductBean.setSkuName(result.getSkuName());
        boxProductBean.setUpcCode(skuBean.getUpcCode());
        boxProductBean.setSkuType(skuBean.getSkuType());
        boxProductBean.setActualQty(skuBean.getActualQty());
        boxProductBean.setExpectedQty(skuBean.getExpectedQty());
        String stockType = LocTypeUtils.getDefaultStockType(skuBean.getSkuType());
        //设置默认好库位
        for (DeliveryStockTypeBean stockTypeBean : stockTypeList) {
            if (stockType.equals(stockTypeBean.getLocType())) {
                boxProductBean.setLocName(stockTypeBean.getLocName());
                boxProductBean.setLocType(stockTypeBean.getLocType());
                boxProductBean.setLocId(stockTypeBean.getLocId());
            }
        }
        boxProductBean.setUpperLimitReceived(skuBean.getUpperLimitReceived());
        return boxProductBean;
    }

    public static DeliverySkuBean convert(int asnStatus, RcvStockInfoResult result) {
        DeliverySkuBean skuBean = new DeliverySkuBean();
        skuBean.setAsnStatus(asnStatus);
        skuBean.setSkuId(result.getSkuId());
        skuBean.setSkuName(result.getSkuName());
        skuBean.setSkuType(result.getSkuType());
        String upcCodes = result.getUpcCodes();
        if (!TextUtils.isEmpty(upcCodes) && upcCodes.contains(";")) {
            skuBean.setUpcCodes(upcCodes.split(";"));
        } else {
            skuBean.setUpcCodes(new String[]{upcCodes});
        }
        skuBean.setUom(result.getUom());
        skuBean.setActualQty(result.getActualQty() != null ? result.getActualQty() : BigDecimal.ZERO);
        skuBean.setExpectedQty(result.getExpectedQty() != null ? result.getExpectedQty() : BigDecimal.ZERO);
        skuBean.setShelfLife(result.getShelfLife() != null ? result.getShelfLife() : 0);
        skuBean.setShelfLifeUnit(result.getShelfLifeUnit() != null ? result.getShelfLifeUnit() : 1);
        skuBean.setShelfLifeUnitDesc(ShelfLifeUnitEnum.fromValue(skuBean.getShelfLifeUnit()).getDesc());
        skuBean.setLogo(ImageUrlUtil.convertImageURL(result.getLogo()));
        //目前只有门店版才会识别效期商品，仓版本默认全部按照普通商品处理
        skuBean.setShelfLifeSup(DeliveryConfigProvider.getClientType() == AppType.STORE && result.isShelfLifeSup());
        skuBean.setSaleMode(result.getSaleMode() == null ? SaleModeEnum.PIECE.getValue() : result.getSaleMode());
        boolean canFold = (asnStatus == AsnStatusEnum.RECEIVED.getValue()
                || asnStatus == AsnStatusEnum.DIFF_AUDIT.getValue())
                && skuBean.getActualQty().compareTo(skuBean.getExpectedQty()) != 0
                && skuBean.getActualQty().compareTo(BigDecimal.ZERO) != 0;
        skuBean.setCanFold(canFold);
        if (result.getUpperLimitReceived() == null) {
            skuBean.setUpperLimitReceived(BigDecimal.ZERO.max(skuBean.getExpectedQty().subtract(skuBean.getActualQty())));
        } else {
            skuBean.setUpperLimitReceived(result.getUpperLimitReceived());
        }
        return skuBean;
    }

    public static DeliveryStockTypeBean convert(QueryLocationsResult result) {
        DeliveryStockTypeBean bean = new DeliveryStockTypeBean();
        bean.setLocId(result.getLocId() + "");
        if (DeliveryConfigProvider.getClientType() == AppType.STORE) {
            bean.setLocName(result.getLocName());
        } else {
            bean.setLocName(result.getLocCode());
        }
        bean.setLocType(result.getLocType());
        return bean;
    }

    public static DeliverySkuLogBean convert(StockExchangeResult result) {
        DeliverySkuLogBean bean = new DeliverySkuLogBean();
        bean.setOperateTime(Formatter.parseResult(result.getOperateTime()));
        bean.setQty(result.getQty());
        bean.setOperator(result.getOperator());
        return bean;
    }

    public static ArrayList<SubmitRcvSkuParam> convert(List<DeliverySkuBean> submitListValue) {
        ArrayList<SubmitRcvSkuParam> submitRcvSkuDtos = new ArrayList<>();
        for (DeliverySkuBean skuBean : submitListValue) {
            if (skuBean.isShelfLifeSup()) {
                List<DeliveryShelfLifeInfoBean> shelfLifeInfoList = skuBean.getShelfLifeInfoList();
                if (shelfLifeInfoList != null) {
                    for (DeliveryShelfLifeInfoBean shelfLifeInfoBean : shelfLifeInfoList) {
                        submitRcvSkuDtos.add(convertShelfLifeSku(skuBean, shelfLifeInfoBean));
                    }
                }
            } else {
                submitRcvSkuDtos.add(convertNormalSku(skuBean));
            }
        }
        return submitRcvSkuDtos;
    }

    /**
     * 将组合商品的信息合并到提交信息中
     * 相同商品的相同库位数量相加，不同库位拆为两条记录
     *
     * @param source          单个商品的收货信息
     * @param combinedSkuList 组合商品的收货信息
     * @return 修改和合并后的source
     */
    public static void mergeCombinedSkuList(ArrayList<SubmitRcvSkuParam> source, List<DeliverySkuBean> combinedSkuList) {
        if (combinedSkuList == null || combinedSkuList.isEmpty()) {
            //不需要合并
            return;
        }
        for (DeliverySkuBean combinedSkuBean : combinedSkuList) {
            for (DeliveryBoxProductBean boxProduct : combinedSkuBean.getBoxProducts()) {
                boolean needAdd = true;
                for (SubmitRcvSkuParam rcvSkuParam : source) {
                    if (rcvSkuParam.getSkuId().equals(boxProduct.getSkuId()) && rcvSkuParam.getLocId().equals(boxProduct.getLocId())) {
                        //如果存在相同的库位的相同商品，则直接增加数量
                        needAdd = false;
                        rcvSkuParam.setQty(rcvSkuParam.getQty().add(combinedSkuBean.getInputQty().multiply(boxProduct.getBoxNum())));
                        break;
                    }
                }
                if (needAdd) {
                    //追加一条新的收货记录
                    boxProduct.setInputQty(combinedSkuBean.getInputQty().multiply(boxProduct.getBoxNum()));
                    source.add(convertBoxProduct(boxProduct));
                }
            }
        }
    }

    private static SubmitRcvSkuParam convertBoxProduct(DeliveryBoxProductBean boxProduct) {
        SubmitRcvSkuParam rcvSkuParam = new SubmitRcvSkuParam();
        rcvSkuParam.setSkuId(boxProduct.getSkuId());
        rcvSkuParam.setSkuType(boxProduct.getSkuType());
        rcvSkuParam.setQty(boxProduct.getInputQty());
        rcvSkuParam.setLocId(boxProduct.getLocId());
        rcvSkuParam.setSkuNature(LocTypeUtils.getSkuNatureByLocType(boxProduct.getLocType()));
        return rcvSkuParam;
    }

    private static SubmitRcvSkuParam convertNormalSku(DeliverySkuBean skuBean) {
        SubmitRcvSkuParam rcvSkuParam = new SubmitRcvSkuParam();
        rcvSkuParam.setSkuId(skuBean.getSkuId());
        rcvSkuParam.setSkuType(skuBean.getSkuType());
        rcvSkuParam.setQty(skuBean.getInputQty());
        rcvSkuParam.setLocId(skuBean.getLocId());
        rcvSkuParam.setSkuNature(LocTypeUtils.getSkuNatureByLocType(skuBean.getLocType()));
        return rcvSkuParam;
    }

    private static SubmitRcvSkuParam convertShelfLifeSku(DeliverySkuBean skuBean, DeliveryShelfLifeInfoBean shelfLifeInfoBean) {
        SubmitRcvSkuParam rcvSkuParam = new SubmitRcvSkuParam();
        rcvSkuParam.setSkuId(skuBean.getSkuId());
        rcvSkuParam.setSkuType(skuBean.getSkuType());
        rcvSkuParam.setQty(shelfLifeInfoBean.getInputQty());
        rcvSkuParam.setLocId(shelfLifeInfoBean.getLocId());
        rcvSkuParam.setSkuNature(LocTypeUtils.getSkuNatureByLocType(shelfLifeInfoBean.getLocType()));
        rcvSkuParam.setProduceDate(Formatter.formatParam(shelfLifeInfoBean.getCreateDate()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(shelfLifeInfoBean.getCreateDate());
        if (skuBean.getShelfLifeUnit() == ShelfLifeUnitEnum.MONTH.getValue()) {
            calendar.add(Calendar.DATE, skuBean.getShelfLife() * 30);
        } else if (skuBean.getShelfLifeUnit() == ShelfLifeUnitEnum.YEAR.getValue()) {
            calendar.add(Calendar.DATE, skuBean.getShelfLife() * 365);
        } else if (skuBean.getShelfLifeUnit() == ShelfLifeUnitEnum.HOUR.getValue()) {
            calendar.add(Calendar.HOUR_OF_DAY, skuBean.getShelfLife());
        } else {
            calendar.add(Calendar.DATE, skuBean.getShelfLife());
        }
        rcvSkuParam.setExpireDate(Formatter.formatParam(calendar.getTime()));
        return rcvSkuParam;
    }

    public static DeliveryDiffSkuBean convert(QueryRcvDiffListResult result) {
        DeliveryDiffSkuBean bean = new DeliveryDiffSkuBean();
        bean.setSkuId(result.getSkuId());
        bean.setSkuName(result.getSkuName());
        String upcCodes = result.getUpcCodes();
        if (!TextUtils.isEmpty(upcCodes) && upcCodes.contains(";")) {
            bean.setUpcCodes(upcCodes.split(";"));
        } else {
            bean.setUpcCodes(new String[]{upcCodes});
        }
        bean.setUom(result.getSaleUnitName());
        bean.setLogo(ImageUrlUtil.convertImageURL(result.getLogo()));
        bean.setActualQty(result.getActualQty());
        bean.setExpectedQty(result.getExpectedQty());
        bean.setDiffQty(result.getDiffQty());
        bean.setReasonCode(result.getReasonCode());
        bean.setReasonDesc(result.getReasonDesc());
        bean.setAsnDetailId(result.getAsnDetailId());
        bean.setDiffType(result.getDiffType() == null ? DiffTypeEnum.NONE.getValue() : result.getDiffType());
        return bean;
    }

    public static DeliveryDiffReasonBean convert(QueryDiffReasonResult result) {
        DeliveryDiffReasonBean bean = new DeliveryDiffReasonBean();
        bean.setReasonCode(result.getDictCode());
        bean.setReasonDesc(result.getDictName());
        return bean;
    }

    public static DiffInfoParam convert(DeliveryDiffSkuBean from) {
        DiffInfoParam diffInfoParam = new DiffInfoParam();
        diffInfoParam.setSkuId(from.getSkuId());
        diffInfoParam.setSkuName(from.getSkuName());
        diffInfoParam.setDiffType(from.getDiffType());
        diffInfoParam.setAsnDetailId(from.getAsnDetailId());
        diffInfoParam.setActualQty(from.getActualQty());
        diffInfoParam.setExpectedQty(from.getExpectedQty());
        diffInfoParam.setDiffQty(from.getDiffQty());
        diffInfoParam.setReasonCode(from.getReasonCode());
        diffInfoParam.setReasonDesc(from.getReasonDesc());
        diffInfoParam.setUpcCodes(joinToString(from.getUpcCodes()));
        return diffInfoParam;
    }

    private static String joinToString(String[] str) {
        if (str == null || str.length == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length; i++) {
            if (i != 0) {
                builder.append(";");
            }
            builder.append(str[i]);
        }
        return builder.toString();
    }
}
