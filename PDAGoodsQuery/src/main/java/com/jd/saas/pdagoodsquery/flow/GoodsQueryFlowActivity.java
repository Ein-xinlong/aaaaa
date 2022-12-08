package com.jd.saas.pdagoodsquery.flow;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdagoodsquery.GoodsQueryRouterPath;
import com.jd.saas.pdagoodsquery.R;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 库存流水ui-activity
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = GoodsQueryRouterPath.HOST_GOODS_QUERY, path = GoodsQueryRouterPath.GOODS_QUERY_FLOW_ACTIVITY_PATH)
public class GoodsQueryFlowActivity extends SimpleActivity {

    @Override
    protected void init() {
        setSimpleTitleBar(R.string.goods_query_flow_title);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return GoodsQueryFlowFragment.newInstance();
    }
}
