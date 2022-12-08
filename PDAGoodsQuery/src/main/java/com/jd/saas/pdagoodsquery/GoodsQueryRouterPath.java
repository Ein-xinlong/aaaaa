package com.jd.saas.pdagoodsquery;

import static com.jd.saas.pdacommon.router.RouterBasePath.NATIVE_TYPE;
import static com.jd.saas.pdacommon.router.RouterBasePath.SCHAME;

/**
 * 商品查询路由配置路径
 *
 * @author majiheng
 */
public class GoodsQueryRouterPath {
    public static final String HOST_GOODS_QUERY = "GoodsQueryModule";
    public static final String GOODS_QUERY_PATH = "/AdjustmentDetailNewPage";
    public static final String HOST_PATH_LOGIN = SCHAME + "://" + NATIVE_TYPE + HOST_GOODS_QUERY + GOODS_QUERY_PATH;


    public static final String GOODS_QUERY_FLOW_ACTIVITY_PATH = "/GoodsQueryFlowNewPage";
    public static final String HOST_PATH_GOODS_QUERY_FLOW = SCHAME + "://" + NATIVE_TYPE + HOST_GOODS_QUERY + GOODS_QUERY_FLOW_ACTIVITY_PATH;

    //进销存
    public static final String GOODS_QUERY_SALE_ACTIVITY_PATH = "/GoodsQuerySaleNewPage";
    public static final String HOST_PATH_GOODS_QUERY_SALE = SCHAME + "://" + NATIVE_TYPE + HOST_GOODS_QUERY + GOODS_QUERY_SALE_ACTIVITY_PATH;

    public static final int REQUEST_CODE_SCAN = 100;
}
