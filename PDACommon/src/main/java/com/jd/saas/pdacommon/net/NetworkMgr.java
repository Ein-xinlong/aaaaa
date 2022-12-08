package com.jd.saas.pdacommon.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.jd.saas.pdacommon.application.MyApplication;


/**
 * 网络状态管理类
 *
 * @author majiheng
 */
public class NetworkMgr {

    private WifiInfo mWifiInfo;

    public static NetworkMgr getInstance() {
        return new NetworkMgr();
    }

    /**
     * 实例
     */
    private NetworkMgr() {
        WifiManager wifiManager = (WifiManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            mWifiInfo = wifiManager.getConnectionInfo();
        }
    }

    private int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    /**
     * 获取ip地址
     */
    public String getIPAddressString() {
        String ipStr = "";
        int ip = getIPAddress();
        if (ip != 0) {
            ipStr = ((ip & 0xff) + "." + (ip >> 8 & 0xff) + "." + (ip >> 16 & 0xff)
                    + "." + (ip >> 24 & 0xff));
        }
        return ipStr;
    }

    /**
     * 当前网络是否可用
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    /**
     * 是否wifi
     */
    public static boolean isWifiActive() {
        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info;
        if (connectivity != null) {
            info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getTypeName().equals("WIFI") && anInfo.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}