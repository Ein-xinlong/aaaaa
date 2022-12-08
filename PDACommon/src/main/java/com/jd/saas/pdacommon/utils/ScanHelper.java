package com.jd.saas.pdacommon.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.jd.saas.pdacommon.log.Logger;

import org.jetbrains.annotations.NotNull;

/**
 * 硬件扫码工具类
 */
public class ScanHelper {
    private static final String SUNMI_SCAN_CODE_ACTION =
            "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED";

    private static final String UROVO_SCAN_CODE_ACTION =
            "android.intent.ACTION_DECODE_DATA";

    private static final String SUNMI_PARAM = "data";
    private static final String UROVO_PARAM = "barcode_string";

    /**
     * 硬件扫码回调监听
     */
    public interface OnScanCodeListener {
        /**
         * 硬件扫码时返回扫描到的文字信息
         */
        void onScanCode(String code);
    }

    /**
     * 注册硬件扫码的监听
     *
     * @param context          上下文 用于注册广播监听
     * @param lifecycleOwner   关联生命周期，自动注册和取消注册
     * @param editText         输入框
     * @param scanCodeListener 扫码回调
     */
    public static void registerScanCodeListener(@NonNull final Context context,
                                                @NonNull LifecycleOwner lifecycleOwner,
                                                @NonNull EditText editText,
                                                @NonNull OnScanCodeListener scanCodeListener) {
        final ScanCodeBroadcastReceiver receiver;
        //商米同时存在键盘输入和广播 需要特殊处理 当输入框有焦点时不再响应广播的回调
        if (DeviceUtil.getDeviceType() == DeviceUtil.DeviceType.SUNMI) {
            receiver = new IgnoreInputScanCodeBroadcastReceiver(editText, scanCodeListener);
        } else {
            receiver = new ScanCodeBroadcastReceiver(scanCodeListener);
        }
        register(context, lifecycleOwner, receiver);
    }


    /**
     * 注册硬件扫码的监听
     *
     * @param context          上下文 用于注册广播监听
     * @param lifecycleOwner   关联生命周期，自动注册和取消注册
     * @param scanCodeListener 扫码回调
     */
    public static void registerScanCodeListener(@NonNull final Context context, @NonNull LifecycleOwner lifecycleOwner, @NonNull OnScanCodeListener scanCodeListener) {
        final ScanCodeBroadcastReceiver receiver = new ScanCodeBroadcastReceiver(scanCodeListener);
        register(context, lifecycleOwner, receiver);
    }

    private static void register(@NonNull final Context context, @NonNull LifecycleOwner lifecycleOwner, @NonNull ScanCodeBroadcastReceiver receiver) {
        lifecycleOwner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onResume(@NonNull @NotNull LifecycleOwner owner) {
                IntentFilter filter = new IntentFilter();
                //型号较少时使用全部监听的方式
                filter.addAction(SUNMI_SCAN_CODE_ACTION);
                filter.addAction(UROVO_SCAN_CODE_ACTION);
                context.registerReceiver(receiver, filter);
            }

            @Override
            public void onPause(@NonNull @NotNull LifecycleOwner owner) {
                context.unregisterReceiver(receiver);
            }
        });
    }

    private static class IgnoreInputScanCodeBroadcastReceiver extends ScanCodeBroadcastReceiver {
        final EditText editText;

        public IgnoreInputScanCodeBroadcastReceiver(EditText editText, @NonNull OnScanCodeListener scanCodeListener) {
            super(scanCodeListener);
            this.editText = editText;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (editText != null && editText.hasFocus()) {
                return;
            }
            super.onReceive(context, intent);
        }
    }

    private static class ScanCodeBroadcastReceiver extends BroadcastReceiver {
        @NonNull
        private final OnScanCodeListener scanCodeListener;

        public ScanCodeBroadcastReceiver(@NonNull OnScanCodeListener scanCodeListener) {
            this.scanCodeListener = scanCodeListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.d("ScanHelper", "onReceive action = " + intent.getAction());
            String code;
            if (SUNMI_SCAN_CODE_ACTION.equals(intent.getAction())) {
                code = intent.getStringExtra(SUNMI_PARAM);
            } else if (UROVO_SCAN_CODE_ACTION.equals(intent.getAction())) {
                code = intent.getStringExtra(UROVO_PARAM);
            } else {
                code = null;
            }
            if (code != null && !code.isEmpty()) {
                Logger.d("ScanHelper", "code = " + code);
                scanCodeListener.onScanCode(code);
            }
        }
    }
}
