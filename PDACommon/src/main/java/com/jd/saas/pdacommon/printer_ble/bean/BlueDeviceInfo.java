package com.jd.saas.pdacommon.printer_ble.bean;

import androidx.annotation.Nullable;

public class BlueDeviceInfo {


    String deviceName;
    String deviceHardwareAddress;
    int connectState;
    String deviceStatus;

    public BlueDeviceInfo(String deviceName, String deviceHardwareAddress, int connectState) {
        this.deviceName = deviceName;
        this.deviceHardwareAddress = deviceHardwareAddress;
        this.connectState = connectState;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceHardwareAddress() {
        return deviceHardwareAddress;
    }

    public void setDeviceHardwareAddress(String deviceHardwareAddress) {
        this.deviceHardwareAddress = deviceHardwareAddress;
    }

    public int getConnectState() {
        return connectState;
    }

    public void setConnectState(int connectState) {
        this.connectState = connectState;
    }

    public String getDeviceStatus(){
        deviceStatus="";
        switch (connectState) {
            case 10:
                deviceStatus = "未配对";
                break;
            case 11:
                deviceStatus = "配对中";
                break;
            case 12:
                deviceStatus = "已配对";
                break;
            default:
                deviceStatus="";
                break;
        }
      return deviceStatus;
    }

    @Override
    public int hashCode() {
        return deviceName.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        BlueDeviceInfo blueDeviceInfo = (BlueDeviceInfo) obj;
        if (blueDeviceInfo != null) {
            return deviceName.equals(blueDeviceInfo.deviceName);
        }

        return false;

    }
}
