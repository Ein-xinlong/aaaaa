package com.jd.saas.pdacheck.flow.step1;

import com.jd.saas.pdacheck.flow.step1.bean.CheckCouResult;
import com.jd.saas.pdacheck.flow.step1.bean.CheckPreOrderBean;

/**
 * 预盘单列表 转换器
 * 将网络返回的数据Resp信息转换为显示使用的Bean
 * 转换一般发生在网络请求结束后
 *
 * @author gouhetong
 */
public class CheckPreOrderListConvertor {

    public static CheckPreOrderBean convert(CheckCouResult result) {
        CheckPreOrderBean checkPreOrderBean = new CheckPreOrderBean();
        checkPreOrderBean.setCouNo(result.getCouNo());
        checkPreOrderBean.setCreateUser(result.getPin());
        checkPreOrderBean.setCreateTime(result.getCreateDateStr());
        return checkPreOrderBean;
    }
}
