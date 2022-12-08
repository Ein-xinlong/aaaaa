package com.jd.saas.pdacommon.printer_ble.utils;

import com.gengcon.www.jcprintersdk.JCPrintApi;
import com.gengcon.www.jcprintersdk.callback.Callback;
import com.jd.saas.pdacommon.application.MyApplication;

/**
 * 打印工具类
 *
 * @author anxinlong
 */
public class PrintUtil {
    private static Callback callback = new Callback() {
        @Override
        public void onConnectSuccess(String s) {

        }

        @Override
        public void onDisConnect() {

        }

        @Override
        public void onElectricityChange(int i) {

        }

        @Override
        public void onCoverStatus(int i) {

        }

        @Override
        public void onPaperStatus(int i) {

        }

        @Override
        public void onRfidReadStatus(int i) {

        }

        @Override
        public void onPrinterIsFree(int i) {

        }

        @Override
        public void onHeartDisConnect() {

        }

        @Override
        public void onFirmErrors() {

        }
    };

    private static JCPrintApi api;


    public static JCPrintApi getInstance() {
        if (api == null) {
            api = JCPrintApi.getInstance(callback);
            api.init(MyApplication.getInstance());
            api.initImageProcessingDefault("", "");
        }

        return api;

    }

    public static int openPrinter(String address) {
        getInstance();
        return api.openPrinterByAddress(address);
    }

    public static void close() {
        getInstance();
        api.close();
    }

    public static int isConnection() {
        getInstance();
        return api.isConnection();
    }


}
