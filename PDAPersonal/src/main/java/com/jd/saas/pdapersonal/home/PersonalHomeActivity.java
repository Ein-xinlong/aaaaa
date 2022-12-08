package com.jd.saas.pdapersonal.home;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.router.PersonalRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;


/**
 * 个人中心界面
 *
 * @author mengmeng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = PersonalRouterPath.HOST_PERSONAL, path = PersonalRouterPath.HOME_ACTIVITY_PATH)
public class PersonalHomeActivity extends SimpleActivity {

    @Override
    protected void init() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        setSimpleTitleBar(R.string.personal_title_center)
                .setBackButtonVisible(false)
                .setToolBarVisible(false);

    }

    @Override
    protected Fragment maybeCreateFragment() {
        return PersonalHomeFragment.getInstance();
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}