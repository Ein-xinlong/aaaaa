package com.jd.saas.pdacheck.list;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 库存盘点
 *
 * @author ext.mengmeng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = CheckRouterPath.CHECK_MAIN, path = CheckRouterPath.CHECK_ACTIVITY_PATH)
public class CheckListActivity extends SimpleActivity {


    @Override
    protected void init() {
        setSimpleTitleBar(R.string.check_inventory).setBackButtonVisible(true);

    }

    @Override
    protected Fragment maybeCreateFragment() {
        return CheckListFragment.newInstance();
    }
}