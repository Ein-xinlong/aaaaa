package com.jd.saas.pdavalidity.list;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdavalidity.R;
import com.jd.saas.pdavalidity.ValidityRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 效期调整ui-activity
 * @author: ext.anxinlong
 * @date: 2021/6/2
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = ValidityRouterPath.HOST_VALIDITY, path = ValidityRouterPath.VALIDITY_ADJUSTMENT_LIST_ACTIVITY_PATH)
public class ValidityAdjustmentListActivity extends SimpleActivity {
    @Override
    protected void init() {
        setSimpleTitleBar(R.string.validity_adjustment_list_title)
                .setBackButtonVisible(true);

    }

    @Override
    protected Fragment maybeCreateFragment() {
        return ValidityAdjustmentListFragment.getInstance();
    }
}
