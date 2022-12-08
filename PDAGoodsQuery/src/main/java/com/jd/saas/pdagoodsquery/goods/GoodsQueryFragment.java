package com.jd.saas.pdagoodsquery.goods;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.search.SearchGoodsResultAdapter;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jd.saas.pdacommon.zxing.common.Constant;
import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.databinding.GoodsQueryDataBinding;

import static com.jd.saas.pdagoodsquery.GoodsQueryRouterPath.REQUEST_CODE_SCAN;

/**
 * 商品查询fragment
 *
 * @author: ext.anxinlong
 * @date: 2021/6/3
 */
public class GoodsQueryFragment extends SimpleFragment {

    private GoodsQueryDataBinding mDataBinding;
    private GoodsQueryViewModel mViewModel;
    private SearchGoodsResultAdapter adapter;

    public static GoodsQueryFragment getInstance() {
        return new GoodsQueryFragment();
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider((GoodsQueryActivity)mContext).get(GoodsQueryViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 搜索列表ui初始化
        adapter = mViewModel.getSearchResultAdapter();
        mDataBinding.rvGoodsQuery.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.rvGoodsQuery.setAdapter(adapter);
        // 促销活动列表ui初始化
        mDataBinding.rvPromotion.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.rvPromotion.setAdapter(mViewModel.getPromotionAdapter());
        //设备的扫码广播，在输入框无焦点时获取扫码结果，有焦点时会作为键盘输入写入输入框
        // 查询商品信息
        ScanHelper.registerScanCodeListener(mContext, this, mDataBinding.etSearch, this::doSearch);
        // 监听回车键
        mDataBinding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_SEARCH
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                // 隐藏键盘
                SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
                mDataBinding.etSearch.clearFocus();
                // 查询商品信息
                doSearch(mViewModel.queryGoods.getValue());
                return true;
            }
            return false;
        });
        //2、返回商品列表，调用adapter刷新数据
        mViewModel.mSearchListLiveData.observe(getViewLifecycleOwner(), searchGoodBeans -> mViewModel.getSearchResultAdapter().submitList(searchGoodBeans));

        //adapter点击Item，查看商品详情
        adapter.setOnItemClickListener(searchGoodBean -> mViewModel.getQueryGoodsInfo(mContext, searchGoodBean.getSkuID()));
        //详情信息放在mInfoData中，更新图片
        mViewModel.mInfoData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //隐藏软键盘
                SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.goods_query_fragment;
    }

    @Override
    protected void reload() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN) {
            if (data != null) {
                String mZXingResult = data.getStringExtra(Constant.CODED_CONTENT);
                //扫码后，显示商品detail
                doSearch(mZXingResult);
            }
        }
    }

    private void doSearch(String str) {
        if (!TextUtils.isEmpty(str)) {
            mDataBinding.etSearch.setText(str);
            mViewModel.getQueryGoodsName(str);
            mDataBinding.morePromotion.setText(getResources().getString(R.string.goods_query_promotion_more));//复位
        }
    }
}
