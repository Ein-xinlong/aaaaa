package com.jd.saas.pdagoodsquery.goods;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdagoodsquery.GoodsQueryRouterPath;
import com.jd.saas.pdagoodsquery.R;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 商品查询activity
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = GoodsQueryRouterPath.HOST_GOODS_QUERY, path = GoodsQueryRouterPath.GOODS_QUERY_PATH)
public class GoodsQueryActivity extends SimpleActivity {
    @Override
    protected void init() {
        setSimpleTitleBar(R.string.goods_query_title)
//                .setMenuText(R.string.goods_query_look_sale)
//                .setMenuClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                })
                .setBackButtonVisible(true);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return GoodsQueryFragment.getInstance();
    }
}
