package com.jd.saas.pdacommon.printer;

import android.content.Context;
import android.device.PrinterManager;
import android.os.Build;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.dialog.SimpleAlertDialog;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.DeviceUtil;

/**
 * 打印机代理工具
 *
 * @author majiheng
 */
public class PrinterUtil {

    // 打印小票模版测试
    public static void print(Context context,PrinterBean bean,boolean showNoPrinterDialog) {
        if(null == bean) {
            MyToast.show(R.string.common_printer_content_err,false);
            return;
        }
        if(DeviceUtil.PDA_MODEL_UROVO_I9000S.equalsIgnoreCase(Build.MODEL)) {
            // 有小票打印的设备型号
            // 创建Urovo打印对象
            PrinterManager printer = new PrinterManager();
            int printerStatus = printer.getStatus();
            if(printerStatus == -1) {
                // 打印机没纸了
                showInfoDialog(context,R.string.common_printer_err_no_paper);
                return;
            }else if(printerStatus == -2) {
                // 打印机过热
                showInfoDialog(context,R.string.common_printer_err_over_heat);
                return;
            }else if(printerStatus == -4) {
                // 打印机繁忙
                showInfoDialog(context,R.string.common_printer_err_busy);
                return;
            }
            // 设置灰度级别0～30
            printer.setGrayLevel(30);
            // 设置打印速度0～80
            printer.setSpeedLevel(80);
            // 设置打印宽度，Urovo PDA是58宽度小票
            printer.setupPage(384, -1);

            // 开始打印小票模版数据
            // 打印订单类型
            printer.setupPage(384, -1);
            printer.drawTextEx(bean.getOrderSourceDesc(), 0, 0, 384, -1, "simsun", 32, 0, 0, 0);
            printer.printPage(0);
            // 打印换行-高度20
            printer.setupPage(384, -1);
            printer.drawTextEx(" ", 60, 0, 384, -1, "simsun", 20, 0, 0, 0);
            printer.printPage(0);
            // 打印超市
            printer.setupPage(384, -1);
            printer.drawTextEx(bean.getWhName(), 0, 0, 384, -1, "simsun", 28, 0, 0, 0);
            printer.printPage(0);
            // 打印换行-高度25
            printer.setupPage(384, -1);
            printer.drawTextEx(" ", 100, 0, 384, -1, "simsun", 25, 0, 0, 0);
            printer.printPage(0);
            // 打印条码
            printer.setupPage(384, -1);
            printer.prn_drawBarcode(bean.getExtNo(), 0, 0, 20, 2, 70, 0);
            printer.printPage(0);
            // 打印换行-高度25
            printer.setupPage(384, -1);
            printer.drawTextEx(" ", 100, 0, 384, -1, "simsun", 10, 0, 0, 0);
            printer.printPage(0);
            // 打印虚线
            printer.setupPage(384, -1);
            printer.drawTextEx("-----------------------------", 0, 0, 384, -1, "simsun", 25, 0, 0, 0);
            printer.printPage(0);
            // 打印换行-高度15
            printer.setupPage(384, -1);
            printer.drawTextEx(" ", 100, 0, 384, -1, "simsun", 15, 0, 0, 0);
            printer.printPage(0);
            // 打印客户信息
            printer.setupPage(384, -1);
            String title1;
            String title2;
            if(bean.getDeliveryType() == 1) {
                // 配送单
                title1 = "送达时间：";
                title2 = "收货地址：";
            }else {
                // 自提单
                title1 = "自提时间：";
                title2 = "自提点名称：";
            }
            String content = "平台订单号：" + bean.getExtNo() + "\n" + "订单编号：" + bean.getRefNo() + "\n" + title1 + bean.getExpectedArriveTime() + "\n" + "客户姓名：" + bean.getConsigneeName() + "\n" + "联系电话：" + bean.getMobile() + "\n" + title2 + bean.getAddress() + "\n" + "客户备注：" + bean.getRemark();
            printer.drawTextEx(content, 0, 0, 384, -1, "simsun", 24, 0, 0, 0);
            printer.printPage(0);
            // 打印换行-高度15
            printer.setupPage(384, -1);
            printer.drawTextEx(" ", 100, 0, 384, -1, "simsun", 15, 0, 0, 0);
            printer.printPage(0);
            // 打印虚线
            printer.setupPage(384, -1);
            printer.drawTextEx("-----------------------------", 0, 0, 384, -1, "simsun", 25, 0, 0, 0);
            printer.printPage(0);
            // 打印换行-高度15
            printer.setupPage(384, -1);
            printer.drawTextEx(" ", 100, 0, 384, -1, "simsun", 15, 0, 0, 0);
            printer.printPage(0);
            // 打印商品信息
            printer.setupPage(384, -1);
            String productTitle = "单价        数量        金额";
            printer.drawTextEx(productTitle, 0, 0, 384, -1, "simsun", 24, 0, 0, 0);
            printer.printPage(0);
            for (int i = 0; i < bean.getDetailVOList().size(); i++) {
                // 打印一条商品信息
                PrinterBean.GoodDetail goodDetail = bean.getDetailVOList().get(i);
                String goodStr = goodDetail.getSkuName() + "\n" + goodDetail.getGoodCode() + "\n" + goodDetail.getPrice() + "        " + goodDetail.getPickQty() + "        " + goodDetail.getProductAmount() + "\n";
                printer.setupPage(384, -1);
                printer.drawTextEx(goodStr, 0, 0, 384, -1, "simsun", 24, 0, 0, 0);
                printer.printPage(0);
            }
            // 打印虚线
            printer.setupPage(384, -1);
            printer.drawTextEx("-----------------------------", 0, 0, 384, -1, "simsun", 25, 0, 0, 0);
            printer.printPage(0);
            // 打印换行-高度15
            printer.setupPage(384, -1);
            printer.drawTextEx(" ", 100, 0, 384, -1, "simsun", 15, 0, 0, 0);
            printer.printPage(0);
            // 打印总结部分
            printer.setupPage(384, -1);
            String summary = "数量合计：" + bean.getTotalQty() + "\n";
            if(bean.getDeliveryType() == 1) {
                // 配送单-需要多打印「商品金额」「配送费」「打包费」
                summary = summary + "商品金额：¥" + bean.getPromotionPrice() + "\n" + "配送费：¥" + bean.getShippingFee() + "\n" + "打包费：¥" + bean.getPackIngFee() + "\n";
            }
            summary = summary + "应付总计：¥" + bean.getTotalAmount() + "\n" + "优惠总计：¥" + bean.getTotalDiscount() + "\n" + "实付总计：¥" + bean.getReceivableAmount();
            printer.drawTextEx(summary, 0, 0, 384, -1, "simsun", 24, 0, 0, 0);
            printer.printPage(0);
            // 打印虚线
            printer.setupPage(384, -1);
            printer.drawTextEx("-----------------------------", 0, 0, 384, -1, "simsun", 25, 0, 0, 0);
            printer.printPage(0);
            // 打印换行-高度15
            printer.setupPage(384, -1);
            printer.drawTextEx(" ", 100, 0, 384, -1, "simsun", 15, 0, 0, 0);
            printer.printPage(0);
            // 打印打印时间
            printer.setupPage(384, -1);
            printer.drawTextEx("打印时间：" + bean.getPrintDateStr() + "\n" + "门店客服电话：" + bean.getStorePhone(), 0, 0, 384, -1, "simsun", 24, 0, 0, 0);
            printer.printPage(0);
            // 推进20长度
            printer.prn_paperForWard(20);
            printer.close();
        }else {
            // 无小票打印的设备-弹窗提示用户
            if(showNoPrinterDialog) {
                showInfoDialog(context,R.string.common_printer_err_no_printer);
            }
        }
    }

    // 弹窗提示用户
    private static void showInfoDialog(Context context,int msg) {
        new SimpleAlertDialog.Builder(context,msg).build().show();
    }
}
