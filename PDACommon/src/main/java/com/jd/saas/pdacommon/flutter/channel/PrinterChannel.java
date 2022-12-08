package com.jd.saas.pdacommon.flutter.channel;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.json.JsonUtil;
import com.jd.saas.pdacommon.printer.PrinterBean;
import com.jd.saas.pdacommon.printer.PrinterUtil;
import com.jd.saas.pdacommon.printer_ble.bean.BluePrintBean;
import com.jd.saas.pdacommon.printer_ble.bean.DirectDeliveryOrderBean;
import com.jd.saas.pdacommon.printer_ble.router.BlueToothPrintRouterPath;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jdshare.jdf_container_plugin.components.channel.protocol.IJDFMessageResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Flutter调用原生打印机方法
 *
 * @author majiheng
 * 不发送消息
 */
public class PrinterChannel extends FlutterChannel {

    private final Context mContext;

    public static final String MODULE_NAME = "pda_printer_channel";
    public static final String METHOD_PRINT = "method_print";
    public static final String PARAMS_PRINT = "params_print";
    public static final String PARAMS_PRINT_DIALOG = "params_show_no_printer_dialog";
    public static final String METHOD_BLUE_TOOTH_PRINT = "method_blue_tooth_print";
    public static final String METHOD_DIRECT_DELIVERY_ORDER_PRINT = "method_direct_delivery_order_print";
    public static final String PARAMS_BLUE_TOOTH_PRINT = "params_blue_tooth_print";
    public static final String PARAMS_DIRECT_DELIVERY_ORDER_PRINT = "params_direct_delivery_order_print";

    public PrinterChannel(Context context) {
        mContext = context;
    }

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    void onReceive(String methodName, Map<String, Object> params, IJDFMessageResult<Map> result) {
        if (METHOD_PRINT.equals(methodName)) {
            // 获取到需要打印的json数据
            String printJson = (String) params.get(PARAMS_PRINT);
            boolean showNoPrinterDialog = (boolean) params.get(PARAMS_PRINT_DIALOG);
            if (!TextUtils.isEmpty(printJson)) {
                // json转换成bean对象
                PrinterBean bean = JsonUtil.StringToObject(printJson, PrinterBean.class);
                // 开始打印
                PrinterUtil.print(mContext, bean, showNoPrinterDialog);
            } else {
                MyToast.show(R.string.common_printer_content_err, false);
            }
        }
        if (METHOD_BLUE_TOOTH_PRINT.equals(methodName)) {
            String printJson = (String) params.get(PARAMS_BLUE_TOOTH_PRINT);
            if (!TextUtils.isEmpty(printJson)) {
                List<BluePrintBean> printBeanList = JsonUtil.stringToList(printJson, new TypeToken<List<BluePrintBean>>() {
                });
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                bundle.putSerializable("list", (Serializable) printBeanList);
                RouterClient.getInstance().forward(mContext, BlueToothPrintRouterPath.COMMON_PATH__BLUETOOTH, bundle);
            } else {
                MyToast.show(R.string.common_printer_content_err, false);
            }
        }
        if (METHOD_DIRECT_DELIVERY_ORDER_PRINT.equals(methodName)) {
            String printJson = (String) params.get(PARAMS_DIRECT_DELIVERY_ORDER_PRINT);
            if (!TextUtils.isEmpty(printJson)) {
                DirectDeliveryOrderBean bean = JsonUtil.StringToObject(printJson, DirectDeliveryOrderBean.class);
                if (bean.getDetailList().isEmpty()) {
                    MyToast.show(R.string.common_blue_no_print_data, false);
                    return;
                }
                //采购和库存字段不同重新赋值
                for (int i = 0; i < bean.getDetailList().size(); i++) {
                    bean.getDetailList().get(i).setAmt(bean.getDetailList().get(i).getSupplierActualReceiveAmt());
                    bean.getDetailList().get(i).setSkuPrice(bean.getDetailList().get(i).getSupplierSkuPrice());
                }
                bean.setTotalActualReceiveUntaxedAmt(bean.getTotalSupplierActualReceiveAmt());
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putSerializable("bean", bean);
                RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), BlueToothPrintRouterPath.COMMON_PATH__BLUETOOTH, bundle);
            } else {
                MyToast.show(R.string.common_printer_content_err, false);
            }
        }

    }
}

