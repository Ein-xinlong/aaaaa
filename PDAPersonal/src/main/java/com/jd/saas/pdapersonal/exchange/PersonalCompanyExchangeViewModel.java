package com.jd.saas.pdapersonal.exchange;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.exchange.adapter.PersonalCompanyExchangeAdapter;
import com.jd.saas.pdapersonal.exchange.bean.PersonalCompanyItemBean;
import com.jd.saas.pdapersonal.net.PersonalRepository;

import java.util.HashMap;
import java.util.List;

/**
 * 企业更换vm
 *
 * @author majiheng
 */
public class PersonalCompanyExchangeViewModel extends NetViewModel {

    // 网络请求
    private final PersonalRepository mPersonalRepository = new PersonalRepository();

    private LinearLayoutManager mLinearLayoutManager;
    private PersonalCompanyExchangeAdapter mCompanyExchangeAdapter;

    /**
     * 列表布局管理器
     */
    public LinearLayoutManager getLinearLayoutManager(Context context) {
        if (null == mLinearLayoutManager) {
            mLinearLayoutManager = new LinearLayoutManager(context);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        return mLinearLayoutManager;
    }

    /**
     * 创建企业列表适配器
     */
    public PersonalCompanyExchangeAdapter getCompanyExchangeListAdapter() {
        if (null == mCompanyExchangeAdapter) {
            mCompanyExchangeAdapter = new PersonalCompanyExchangeAdapter();
        }
        return mCompanyExchangeAdapter;
    }

    /**
     * 请求租户列表信息
     */
    public void requestData() {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        mPersonalRepository.select(hashMap, new NetObserver<List<PersonalCompanyItemBean>>(this) {
            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(List<PersonalCompanyItemBean> list) {
                if (!list.isEmpty()) {
                    getCompanyExchangeListAdapter().refreshList(list);
                }else {
                    MyToast.show(R.string.personal_company_empty,false);
                }
            }
        });
    }
}
