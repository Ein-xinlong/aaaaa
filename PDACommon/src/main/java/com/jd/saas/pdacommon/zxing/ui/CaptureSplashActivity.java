package com.jd.saas.pdacommon.zxing.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jd.saas.pdacommon.activity.BaseActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.zxing.common.Constant;
import com.jd.saas.pdacommon.zxing.router.ZxingRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 通用条码扫码页面Splash页面，实际上启动的是该页面，该页面无UI，然后此页面会继续启动CaptureActivity
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = ZxingRouterPath.HOST_ZXING, path = ZxingRouterPath.PAGE_ZXING)
public class CaptureSplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity创建后，直接启动自定义的扫码页面
        new IntentIntegrator(this)
                .setCaptureActivity(CaptureActivity.class)// 自定义Activity
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型：所有类型
                .setPrompt("")// 设置提示语
                .setCameraId(0)// 选择摄像头,可使用前置或者后置
                .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                .setBarcodeImageEnabled(false)// 扫完码之后生成二维码的图片
                .initiateScan();// 初始化扫码
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null) {
                // 将扫描后的结果返回的搜索商品的页面
                Intent intent = getIntent();
                intent.putExtra(Constant.CODED_CONTENT, result.getContents());
                setResult(RESULT_OK, intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        finish();
    }
}
