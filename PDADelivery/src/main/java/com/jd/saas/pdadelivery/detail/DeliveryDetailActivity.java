package com.jd.saas.pdadelivery.detail;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.toolbar.NormalTitleBar;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.detail.ui.DeliveryDetailFragment;
import com.jd.saas.pdadelivery.router.DeliveryRouterConfig;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * @author: ext.anxinlong
 * @date: 2021/6/4
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = DeliveryRouterConfig.MODULE_NAME, path = DeliveryRouterConfig.PAGE_DETAIL_NAME)
public class DeliveryDetailActivity extends SimpleActivity implements DetailFragmentContainer {
    @Override
    protected void init() {
        setSimpleTitleBar(R.string.delivery_detail_title).setBackButtonVisible(true);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return DeliveryDetailFragment.getInstance(getIntent().getExtras());
    }

    @Override
    public void showFinishBtn(View.OnClickListener onFinishBtnClickListener) {
        NormalTitleBar normalTitleBar = findViewById(com.jd.saas.pdacommon.R.id.tb_toolbar);
        normalTitleBar.setMenuText(R.string.delivery_finish);
        normalTitleBar.setMenuClickListener(onFinishBtnClickListener);
    }

    @Override
    public void hideFinishBtn() {
        NormalTitleBar normalTitleBar = findViewById(com.jd.saas.pdacommon.R.id.tb_toolbar);
        normalTitleBar.hideMenuText();
    }

    @Override
    public void showPrintBtn(View.OnClickListener onPrintBtnClickListener) {
        NormalTitleBar normalTitleBar = findViewById(com.jd.saas.pdacommon.R.id.tb_toolbar);
        normalTitleBar.setMenuText(R.string.delivery_print);
        normalTitleBar.setMenuClickListener(onPrintBtnClickListener);
    }

    @Override
    public void onBackPressed() {
        if (mContentFragment instanceof DeliveryDetailFragment) {
            ((DeliveryDetailFragment) mContentFragment).onBackPress();
        } else {
            super.onBackPressed();
        }
    }
}

