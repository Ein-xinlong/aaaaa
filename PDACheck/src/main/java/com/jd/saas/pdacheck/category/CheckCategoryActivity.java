package com.jd.saas.pdacheck.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 指定类目 容器
 *
 * @author gouhetong
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = CheckRouterPath.CHECK_MAIN, path = CheckRouterPath.CHECK_CATEGORY_ACTIVITY_PATH)
public class CheckCategoryActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        setSimpleTitleBar(0).setTitle(R.string.check_category_title).setDividerVisible(true);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        CheckCategoryFragment checkCategoryFragment = CheckCategoryFragment.newInstance();
        Intent intent = getIntent();
        if (intent != null) {
            checkCategoryFragment.setArguments(intent.getExtras());
        }
        return checkCategoryFragment;
    }


}