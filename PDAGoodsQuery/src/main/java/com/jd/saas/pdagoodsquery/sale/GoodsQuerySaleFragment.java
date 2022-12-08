package com.jd.saas.pdagoodsquery.sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.jd.saas.pdacommon.fragment.SimpleFragment;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.databinding.GoodsQuerySaleLayoutBinding;
import com.jd.saas.pdagoodsquery.sale.adapter.GoodsQuerySaleAdapter;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQuerySale;
import com.jd.saas.pdagoodsquery.sale.utils.DateUtil;
import java.util.Date;

/**
 * 进销存Fragment
 */
public class GoodsQuerySaleFragment extends SimpleFragment {
    public static final String SALE_DATE_DAY = "day";
    public static final String SALE_DATE_WEEK = "week";
    public static final String SALE_DATE_MONTH = "month";
    private GoodsQuerySaleLayoutBinding mBinding;
    private GoodsQuerySaleViewModel mViewModel;
    private SearchResultBean mInfoResponseData;
    public static GoodsQuerySaleFragment getInstance(){
        return new GoodsQuerySaleFragment();
    }
    @Override
    protected int getLayout() {
        return R.layout.goods_query_sale_layout;
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mInfoResponseData = (SearchResultBean) getActivity().getIntent().getSerializableExtra("bean");
        mBinding = DataBindingUtil.inflate(inflater,getLayout(),container,false);
        mViewModel = new ViewModelProvider(this).get(GoodsQuerySaleViewModel.class);
//        mBinding.setVm(mViewModel);
        mBinding.setBean(mInfoResponseData);
        return mBinding.getRoot();

    }

    private int DELAY_TIME = 2000;
    private boolean isCanClick = true;
    private long lastClickTime = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.recyclerview.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recyclerview.setLayoutManager(manager);
        mBinding.recyclerview.setNestedScrollingEnabled(false);
        mBinding.recyclerview.setAdapter(mViewModel.getSaleAdapter());
        mViewModel.getSaleAdapter().setOnItemClick(new GoodsQuerySaleAdapter.OnItemClick() {
            @Override
            public void onClick(GoodsQuerySale sale) {

                long timeDiffer = System.currentTimeMillis() - lastClickTime;
                if(timeDiffer>DELAY_TIME){
                    isCanClick = true;
                }else {
                    isCanClick = false;
                }
                if(isCanClick){
                    lastClickTime = System.currentTimeMillis();
                    mViewModel.getReceiptDetails(mContext,sale);
                }
            }
        });
        mViewModel.getStockSale(mInfoResponseData.getSkuId(),SALE_DATE_DAY
                ,DateUtil.getBeforeDay(60),DateUtil.getCurrentDate(new Date()));
        mBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioDay) {
                    //默认60数据
                    mViewModel.radioSelected.set(SALE_DATE_DAY);
                    mViewModel.getStockSale(mInfoResponseData.getSkuId(),SALE_DATE_DAY
                    ,DateUtil.getBeforeDay(60),DateUtil.getCurrentDate(new Date()));
                }  else if (checkedId == R.id.radioWeek) {
                    mViewModel.radioSelected.set(SALE_DATE_WEEK);
                    mViewModel.getStockSale(mInfoResponseData.getSkuId(),SALE_DATE_WEEK
                            ,DateUtil.getBeforeMonth(3),DateUtil.getCurrentDate(new Date()));

                } else if (checkedId == R.id.radioMonth) {
                    mViewModel.radioSelected.set(SALE_DATE_MONTH);
                    mViewModel.getStockSale(mInfoResponseData.getSkuId(),SALE_DATE_MONTH
                            ,DateUtil.getBeforeMonth(11),DateUtil.getCurrentDate(new Date()));
//                            ,DateUtil.getBeforeYear(1),DateUtil.getCurrentDate(new Date()));
                }
            }
        });

    }

    @Override
    protected void reload() {

    }
}
