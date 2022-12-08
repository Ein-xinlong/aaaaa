package com.jd.saas.pdacommon.flutter.channel;

import com.jdshare.jdf_container_plugin.components.channel.api.JDFChannelHelper;
import com.jdshare.jdf_container_plugin.components.channel.protocol.IJDFChannelHandler;
import com.jdshare.jdf_container_plugin.components.channel.protocol.IJDFMessageResult;

import java.util.Map;

public abstract class FlutterChannel implements IJDFChannelHandler {

    @Override
    public void onChannel(String moduleName, String methodName, Map<String, Object> map, IJDFMessageResult<Map> result) {
        if (getModuleName().equals(moduleName)) {
            onReceive(methodName, map, result);
        }
    }

    abstract void onReceive(String methodName, Map<String, Object> map, IJDFMessageResult<Map> result);

    public void send(String methodName, Map<String, Object> intentMap, IJDFMessageResult<Map> result) {
        JDFChannelHelper.callFlutterMethod(getModuleName(), methodName, intentMap, result);
    }

}
