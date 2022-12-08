package com.jd.saas.pdadelivery.detail.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jd.saas.pdacommon.zxing.common.Constant;
import com.jd.saas.pdadelivery.Inject;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.base.DeliveryBaseFragment;
import com.jd.saas.pdadelivery.databinding.DeliveryDetailDataBinding;
import com.jd.saas.pdadelivery.detail.DeliveryDetailViewModel;
import com.jd.saas.pdadelivery.detail.DetailFragmentContainer;
import com.jd.saas.pdadelivery.detail.adapter.DeliveryDetailSkuAdapter;
import com.jd.saas.pdadelivery.detail.bean.DeliveryBoxProductBean;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;
import com.jd.saas.pdadelivery.manage.bean.DeliveryBean;
import com.jd.saas.pdadelivery.router.DeliveryRouter;
import com.jd.saas.pdadelivery.router.DeliveryRouterConfig;
import com.jd.saas.pdadelivery.util.ScannerUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: ext.anxinlong
 * @date: 2021/6/4
 */
public class DeliveryDetailFragment extends DeliveryBaseFragment {

    private DeliveryDetailDataBinding mDataBinding;
    private DeliveryDetailViewModel mViewModel;
    private DeliveryDetailSkuAdapter mAdapter;
    private DetailFragmentContainer detailFragmentContainer;
    private DeliveryBean bean;
    private ProgressDialog progressDialog;

    public static DeliveryDetailFragment getInstance(Bundle bundle) {
        DeliveryDetailFragment fragment = new DeliveryDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragmentContainer) {
            detailFragmentContainer = (DetailFragmentContainer) context;
        } else {
            throw new IllegalArgumentException("DeliveryDetailFragment's container must implements DetailFragmentContainer");
        }
    }


    @Override
    protected void reload() {
        mViewModel.initData(bean);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(getActivity(), Inject.injectViewModelFactory()).get(DeliveryDetailViewModel.class);
        mDataBinding.setVm(mViewModel);
        mViewModel.handleBaseNetUI(this);
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected int getLayout() {
        return R.layout.delivery_detail_fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DeliveryRouterConfig.REQUEST_SCAN_CODE) {
            if (data != null) {
                String str = data.getStringExtra(Constant.CODED_CONTENT);
                doSearch(str);
            }
        }
    }

    @Override
    protected void initView() {
        progressDialog = new ProgressDialog(mContext);
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mAdapter = new DeliveryDetailSkuAdapter(new DeliveryDetailSkuAdapter.OnEditItemClickListener() {
            @Override
            public void onEditItem(DeliverySkuBean skuBean) {
                mViewModel.editSku.postValue(skuBean);
            }

            @Override
            public void onExplainSkuLog(DeliverySkuBean skuBean) {
                mViewModel.getSkuLogList(skuBean.getSkuId());
            }
        });
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.zxingImage.setOnClickListener(v -> DeliveryRouter.goScanForResult(getContext()));
        ScannerUtil.registerEditorListener(mDataBinding.etSearch, this::doSearch);
        ScanHelper.registerScanCodeListener(mContext, this, mDataBinding.etSearch, code -> {
            //这里延时50ms是等待PDA设备模拟输入扫码结果结束，防止其他输入框获取焦点后把内容输入到输入框中
            mDataBinding.etSearch.postDelayed(() -> doSearch(code), 50);
        });
    }


    private void doSearch(String s) {
        SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
        mDataBinding.etSearch.clearFocus();
        if (!TextUtils.isEmpty(s)) {
            mDataBinding.etSearch.setText(s);
            mViewModel.searchCombinedSku(s);
        }
    }

    @Override
    protected void observeLiveData() {
        mViewModel.editSku.observe(this, bean -> {
            if (bean != null) {
                showEditItemDialog(bean);
            }
        });
        mViewModel.selectTabType.observe(this, select -> {
            if (select == 0) {
                ArrayList<DeliverySkuBean> list = mViewModel.skuList.getValue();
                if (list != null) {
                    mAdapter.refreshList(list);
                }
            } else {
                mAdapter.refreshList(mViewModel.combinedSkuList);
            }
        });
        mViewModel.oneKeyFillNotifyEvent.observe(this, result -> {
            if (result != null && result) {
                //此处延时200ms是为了等待水波纹动画结束
                mDataBinding.getRoot().postDelayed(() -> mViewModel.selectTabType.postValue(0), 200);
            }
        });
        mViewModel.skuList.observe(this, deliverySkuBeans -> {
            if (mViewModel.isTabShowSkuList()) {
                mAdapter.refreshList(deliverySkuBeans);
            }
        });
        mViewModel.isFinish.observe(this, isFinish -> {
            if (isFinish) {
                if(mViewModel.isContainsPo()){
                    detailFragmentContainer.showPrintBtn(v->{
                        mViewModel.startPrint();
                    });
                }else{
                    detailFragmentContainer.hideFinishBtn();
                }
            } else {
                detailFragmentContainer.showFinishBtn(v -> {
                    mViewModel.queryDiff(bean.getAsnNo());
                });
            }
        });

        mViewModel.showLoadingDialog.observe(this, aBoolean -> {
            if (aBoolean) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
        mViewModel.logsBeanEvent.observe(this, pair -> mAdapter.changeItem(pair.second));
        mViewModel.showToastEvent.observe(this, msg -> MyToast.show(msg, true));
        mViewModel.submitSuccessEvent.observe(this, isSuccess -> {
            if (isSuccess) {
                MyToast.show(R.string.delivery_submit_success_tips, false);
                mViewModel.submitList.postValue(new ArrayList<>());
                mViewModel.combinedSkuList.clear();
                //关单成功后需要隐藏tab
                mViewModel.needShowCombinedSkuTab.postValue(false);
                //关单成功后需要切换为显示单品列表
                mViewModel.selectTabType.postValue(0);
                mViewModel.getDetailByNo(bean);
                notifyListRefresh();
            }
        });
        mViewModel.finishAsnSuccessEvent.observe(this, s -> {
            MyToast.show(R.string.delivery_close_success_tips, false);
            //关单成功后需要切换为显示单品列表
            mViewModel.selectTabType.setValue(0);
            mViewModel.getDetailByNo(bean);
            notifyListRefresh();
        });
        mViewModel.expiryDateGoodsStatusEvent.observe(this, pair -> {
            Fragment editShelfLifeSkuDialog = getChildFragmentManager().findFragmentByTag("editShelfLifeSku");
            if (editShelfLifeSkuDialog instanceof DeliveryDetailEditShelfLifeSkuDialog) {
                ((DeliveryDetailEditShelfLifeSkuDialog) editShelfLifeSkuDialog).updateExpiryDateGoodsStatus(pair);
            }
        });
        mViewModel.deliveryDiffSkuList.observe(this, deliveryDiffSkuBeans -> {
            if (deliveryDiffSkuBeans == null || deliveryDiffSkuBeans.isEmpty()) {
                new SimpleAlertDialog.Builder(mContext, R.string.delivery_dialog_ensure_close_tips)
                        .setPositiveButton(btn -> mViewModel.finishAsn(bean.getAsnNo()))
                        .build().show();
            } else {
                DeliveryDiffDialog deliveryDiffDialog = new DeliveryDiffDialog();
                deliveryDiffDialog.show(getChildFragmentManager(), "DeliveryDiffDialog");
            }
        });
    }

    private void notifyListRefresh() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setResult(Activity.RESULT_OK);
        }
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            bean = getArguments().getParcelable(DeliveryRouterConfig.PARAM_DELIVERY_BEAN);
        }
        mViewModel.initData(bean);
    }

    private void showEditItemDialog(DeliverySkuBean bean) {
        if (bean.isCombined()) {
            showEditCombinedSkuDialog(bean);
        } else if (bean.isShelfLifeSup()) {
            showEditShelfLifeSkuDialog(bean);
        } else {
            showEditNormalSkuDialog(bean);
        }
    }

    private void showEditShelfLifeSkuDialog(DeliverySkuBean bean) {
        DeliveryDetailEditShelfLifeSkuDialog instance = DeliveryDetailEditShelfLifeSkuDialog.getInstance();
        instance.setOnEnsureClickListener(this::onEditSkuEnsure);
        instance.setOnDismissListener(this::hideSoftInput);
        instance.show(getChildFragmentManager(), "editShelfLifeSku");
    }

    private void showEditCombinedSkuDialog(DeliverySkuBean bean) {
        if (bean.isShelfLifeSup()) {
            MyToast.show(R.string.delivery_detail_not_support_shelf_life_combined_sku, false);
            return;
        }
        DeliveryDetailEditCombinedSkuDialog instance = DeliveryDetailEditCombinedSkuDialog.getInstance();
        instance.setOnEnsureClickListener(this::onEditSkuEnsure);
        instance.setOnDismissListener(this::hideSoftInput);
        instance.show(getChildFragmentManager(), "editCombinedSku");
    }

    private void showEditNormalSkuDialog(DeliverySkuBean bean) {
        DeliveryDetailEditSkuDialog instance = DeliveryDetailEditSkuDialog.getInstance();
        instance.setOnEnsureClickListener(this::onEditSkuEnsure);
        instance.setOnDismissListener(this::hideSoftInput);
        instance.show(getChildFragmentManager(), "editSku");
    }

    private void hideSoftInput() {
        if (mDataBinding == null) {
            return;
        }
        mDataBinding.getRoot().postDelayed(() -> {
            View view = getView();
            Context context = getContext();
            if (view != null && context != null) {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }, 50);
    }

    /**
     * 修改商品 后 的回调，用于更新当前页面的展示和向待提交的列表中添加信息
     * 对于组合商品 并不保存在提交列表中，而是拆分成子品保存到提交信息中
     *
     * @param bean
     */
    private void onEditSkuEnsure(DeliverySkuBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.isCombined()) {
            onEditCombinedSkuEnsure(bean);
            return;
        }
        if (mViewModel.isTabShowSkuList()) {
            //修改页面中的item顺序
            mAdapter.move2Top(bean);
        }
        //修改数据源中的对象顺序
        mViewModel.move2Top(bean);
        //添加到待提交信息中
        mViewModel.addSku2SubmitList(bean);
    }


    private void onEditCombinedSkuEnsure(DeliverySkuBean bean) {
        if (bean == null) {
            return;
        }
        ArrayList<DeliverySkuBean> skuList = mViewModel.skuList.getValue();
        if (skuList == null) {
            return;
        }
        //更新显示的信息和顺序
        if (mViewModel.isTabShowSkuList()) {
            for (DeliveryBoxProductBean boxProduct : bean.getBoxProducts()) {
                for (DeliverySkuBean skuBean : skuList) {
                    if (boxProduct.getSkuId().equals(skuBean.getSkuId())) {
                        mAdapter.move2Top(skuBean);
                    }
                }
            }
        } else {
            mAdapter.move2TopOrAdd(bean);
        }
        //修改数据源中的组合商品列表顺序
        mViewModel.move2Top(bean);
        //更新数据源中普通商品列表顺序
        for (DeliveryBoxProductBean boxProduct : bean.getBoxProducts()) {
            DeliverySkuBean boxSku = null;
            for (DeliverySkuBean skuBean : skuList) {
                if (boxProduct.getSkuId().equals(skuBean.getSkuId())) {
                    boxSku = skuBean;
                    break;
                }
            }
            if (boxSku != null) {
                mViewModel.move2Top(boxSku);
                mViewModel.addSku2SubmitList(boxSku);
            }
        }
    }

    public void onBackPress() {
        if (mViewModel != null) {
            List<DeliverySkuBean> submitListValue = mViewModel.submitList.getValue();
            if (submitListValue != null && !submitListValue.isEmpty()) {
                new SimpleAlertDialog.Builder(mContext, R.string.delivery_submit_list_not_empty_tips)
                        .setPositiveButton(v -> finish())
                        .build().show();
                return;
            }
        }
        if (mDataBinding != null) {
            SoftInputUtil.hideSoftInput(mDataBinding.etSearch, mContext);
        }
        finish();
    }
}

