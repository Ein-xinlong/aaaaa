package com.jd.saas.pdacommon.webview;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * PDA通用网页容器UI
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = PDAWebViewRouterPath.HOST_WEB_VIEW, path = PDAWebViewRouterPath.PDA_WEB_VIEW_ACTIVITY_PATH)
public class PDAWebViewActivity extends SimpleActivity {

    private PDAWebViewFragment mFragment;

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        PDAWebViewConfigBean bean = (PDAWebViewConfigBean) bundle.get(PDAWebViewRouterPath.WEB_CONFIG);
        boolean showTitle = bean.isShowTitle();
        setSimpleTitleBar(bean.getTitle())
                .setToolBarVisible(showTitle)
                .showShutdownBtn()
                .setOnShutdownClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackOrFinish();
            }
        });
    }

    @Override
    protected Fragment maybeCreateFragment() {
        mFragment = PDAWebViewFragment.newInstance();
        mFragment.setArguments(getIntent().getExtras());
        return mFragment;
    }

    /**
     * 判断页面关闭还是网页回退
     */
    private void goBackOrFinish() {
        if(null != mFragment && null != mFragment.getDataBinding().web && mFragment.getDataBinding().web.canGoBack()) {
            mFragment.getDataBinding().web.goBack();
        }else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        goBackOrFinish();
    }
}