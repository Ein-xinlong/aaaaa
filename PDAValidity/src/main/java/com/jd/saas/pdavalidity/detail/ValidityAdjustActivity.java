package com.jd.saas.pdavalidity.detail;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdavalidity.R;
import com.jd.saas.pdavalidity.ValidityRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 效期调整ui-activity
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = ValidityRouterPath.HOST_VALIDITY, path = ValidityRouterPath.VALIDITY_ADJUST_DETAIL_ACTIVITY_PATH)
public class ValidityAdjustActivity extends SimpleActivity {

    @Override
    protected void init() {
        setSimpleTitleBar(R.string.validity_adjust_title);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return ValidityAdjustFragment.newInstance();
    }
}
