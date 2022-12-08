package com.jd.saas.pdacommon.flutter;

import android.content.Intent;
import android.os.Bundle;

import com.idlefish.flutterboost.containers.FlutterBoostActivity;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.flutter.channel.CaptureChannel;
import com.jd.saas.pdacommon.flutter.channel.PrinterChannel;
import com.jd.saas.pdacommon.flutter.channel.TokenExpireChannel;
import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.utils.ScanHelper;
import com.jdshare.jdf_container_plugin.assistant.JDFActivityFrameManager;
import com.jdshare.jdf_container_plugin.components.channel.api.JDFChannelHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyCustomFlutterBoostActivity extends FlutterBoostActivity {
    private TokenExpireChannel tokenExpireChannel;
    private PrinterChannel printerChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JDFActivityFrameManager.setCurrentFlutterActivity(this);
        //注册的都是接收方
        tokenExpireChannel = new TokenExpireChannel();
        printerChannel = new PrinterChannel(this);
        ScanHelper.registerScanCodeListener(this, this, code -> new CaptureChannel().send(code));
        // 注册事件总线，目前用来监听消息实现Flutter容器关闭
        EventBusManager.register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        JDFChannelHelper.registerMethodChannel(tokenExpireChannel);
        JDFChannelHelper.registerMethodChannel(printerChannel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("flutterActivity", "on flutterActivity Stop ");
        JDFChannelHelper.unRegisterListener(tokenExpireChannel.getModuleName());
        JDFChannelHelper.unRegisterListener(printerChannel.getModuleName());
    }

    @Override
    protected void onDestroy() {
        JDFActivityFrameManager.onDestroy(this);
        // 反注册事件总线
        EventBusManager.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JDFActivityFrameManager.onActivtyResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(EventBean bean) {
        if (bean.getType() == EventBean.FINISH_FLUTTER_ACTIVITY) {
            // 关闭Flutter容器
            finish();
        }
    }
}
