package com.jd.saas.pdacommon.flutter.channel;

import com.jdshare.jdf_container_plugin.components.channel.protocol.IJDFMessageResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 扫码消息通道
 * 用于向flutter发送硬件扫码的结果
 * 不接收信息
 */
public class CaptureChannel extends FlutterChannel {
    public static final String MODULE_NAME = "pda_capture_channel";
    public static final String METHOD_NATIVE_SCAN_BROADCAST = "method_native_scan_broadcast";
    public static final String PARAM_CODE = "code";

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }


    @Override
    void onReceive(String methodName, Map<String, Object> map, IJDFMessageResult<Map> result) {
        //
    }


    public void send(String code) {
        HashMap<String, Object> param = new HashMap<>();
        param.put(CaptureChannel.PARAM_CODE, code);
        send(METHOD_NATIVE_SCAN_BROADCAST, param, new IJDFMessageResult() {
            @Override
            public void success(Object o) {

            }

            @Override
            public void error(String s, String s1, Object o) {
            }

            @Override
            public void notImplemented() {
            }
        });
    }
}
