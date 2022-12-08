package com.jd.saas.pdapersonal.exchange;

import androidx.fragment.app.Fragment;

import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.router.PersonalRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 企业切换ui-activity
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = PersonalRouterPath.HOST_PERSONAL, path = PersonalRouterPath.COMPANY_EXCHANGE_ACTIVITY_PATH)
public class PersonalCompanyExchangeActivity extends SimpleActivity {

    @Override
    protected void init() {
        setSimpleTitleBar(R.string.personal_company_exchange);
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return PersonalCompanyExchangeFragment.newInstance();
    }
}
