package com.jd.saas.pdacommon.zxing.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.activity.BaseActivity;
import com.jd.saas.pdacommon.databinding.CaptureActivityDataBinding;
import com.jd.saas.pdacommon.screen.ScreenUtil;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * 通用条码扫码页面
 *
 * @author majiheng
 */
public class CaptureActivity extends BaseActivity {

    private CaptureManager mCapture;
    private DecoratedBarcodeView mBarcodeScannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        ScreenUtil.setTranslucentStatusBar(this);
        CaptureActivityDataBinding dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_capture);
        mBarcodeScannerView = dataBinding.dbvCustom;
        mCapture = new CaptureManager(this, mBarcodeScannerView);
        mCapture.initializeFromIntent(getIntent(), savedInstanceState);
        mCapture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCapture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCapture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCapture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mCapture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mCapture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mBarcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
