package com.jd.saas.padinventory.create;

import android.view.View;

import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.padinventory.R;
import com.jd.saas.padinventory.adjustment.detail.InventoryAdjustmentDetailRouterPath;
import com.jd.saas.pdacommon.activity.SimpleActivity;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toolbar.NormalTitleBar;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 新建调整单Activity
 *
 * @author ext.mengmeng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = InventoryCreateRouterPath.CREATE_MAIN, path = InventoryCreateRouterPath.CREATE_ACTIVITY_PATH)
public class InventoryCreateActivity extends SimpleActivity {

    @Override
    protected void init() {
        NormalTitleBar titleBar = setSimpleTitleBar(R.string.inventory_create_adjustment);
        titleBar.setMenuText(R.string.inventory_create_detail)
                .setMenuClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), InventoryAdjustmentDetailRouterPath.HOST_PATH_LOGIN);
                    }
                }).setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InventoryCreateViewModel viewModel = new ViewModelProvider(InventoryCreateActivity.this).get(InventoryCreateViewModel.class);
                viewModel.showExitDialog(InventoryCreateActivity.this);
            }
        });
        // 待提交列表数据变化监听
        InventoryCreateViewModel.mAdjustmentList.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                titleBar.setMenuText(InventoryCreateViewModel.mAdjustmentList.get().size() == 0 ? getString(R.string.inventory_create_detail) : getString(R.string.inventory_create_detail) + "(" + InventoryCreateViewModel.mAdjustmentList.get().size() + ")");
            }
        });
    }

    @Override
    protected Fragment maybeCreateFragment() {
        return InventoryCreateFragment.getInstance();
    }

    @Override
    public void onBackPressed() {
        InventoryCreateViewModel viewModel = new ViewModelProvider(InventoryCreateActivity.this).get(InventoryCreateViewModel.class);
        viewModel.showExitDialog(this);
    }
}