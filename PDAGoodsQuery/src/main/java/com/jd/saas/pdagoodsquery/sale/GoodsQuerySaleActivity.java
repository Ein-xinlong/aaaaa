package com.jd.saas.pdagoodsquery.sale;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdagoodsquery.GoodsQueryRouterPath;
import com.jd.saas.pdagoodsquery.R;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 商品进销存
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = GoodsQueryRouterPath.HOST_GOODS_QUERY, path = GoodsQueryRouterPath.GOODS_QUERY_SALE_ACTIVITY_PATH)
public class GoodsQuerySaleActivity extends SimpleActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        setSimpleTitleBar(R.string.goods_query_sale);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return GoodsQuerySaleFragment.getInstance();
    }

}
