package com.jd.saas.pdacheck.record;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 盘存记录
 *
 * @author ext.mengmeng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = CheckRouterPath.CHECK_MAIN, path = CheckRouterPath.RECORD_ACTIVITY_PATH)
public class CheckRecordActivity extends SimpleActivity {

    @Override
    protected void init() {
        setSimpleTitleBar(R.string.check_record).setDividerVisible(false);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return CheckRecordFragment.newInstance();
    }
}