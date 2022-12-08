package com.jd.saas.pdacheck.check;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 商品盘点Activity
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = CheckRouterPath.CHECK_MAIN, path = CheckRouterPath.PERFORM_ACTIVITY_PATH)
public class CheckGoodsCheckActivity extends SimpleActivity {

    @Override
    protected void init() {
        setSimpleTitleBar(R.string.check_goods_check_title).setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    /**
     * 关闭当前页面
     */
    private void exit() {
        CheckGoodsCheckViewModel viewModel = new ViewModelProvider(CheckGoodsCheckActivity.this).get(CheckGoodsCheckViewModel.class);
        // 判断当前页面是否有准备盘点的商品
        viewModel.exitJudge();
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return CheckGoodsCheckFragment.newInstance();
    }
}
