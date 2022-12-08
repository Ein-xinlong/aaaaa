package com.jd.saas.pdacommon.printer_ble;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.gengcon.www.jcprintersdk.callback.PrintCallback;
import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.printer_ble.adapter.BlueToothPrintAdapter;
import com.jd.saas.pdacommon.printer_ble.bean.BlueDeviceInfo;
import com.jd.saas.pdacommon.printer_ble.bean.BluePrintBean;
import com.jd.saas.pdacommon.printer_ble.bean.DirectDeliveryOrderBean;
import com.jd.saas.pdacommon.printer_ble.utils.ClsUtils;
import com.jd.saas.pdacommon.printer_ble.utils.PrintUtil;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class BlueToothPrintViewModel extends NetViewModel {
    // 是否显示刷新状态
    public ObservableField<Boolean> mRefresh = new ObservableField<>(false);

    private BlueToothPrintAdapter mBlueToothPrintAdapter;//蓝牙列表适配器

    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//蓝牙适配器

    private List<BlueDeviceInfo> blueDeviceList = new ArrayList<>();//蓝牙列表数据

    private DirectDeliveryOrderBean bean;

    private int itemPosition = -1;

    public ExecutorService executorService;//线程池

    private List<BluePrintBean> printBeanList = new ArrayList<>();
    /**
     * 打印类型
     */
    private int printType;

    public MutableLiveData<Boolean> isShowDialog = new MutableLiveData<>(false);
    /**
     * 图像数据
     */
    private ArrayList<String> jsonList;
    /**
     * 图像处理数据
     */
    private ArrayList<String> infoList;
    /**
     * 总页数
     */
    private int pageCount = 0;

    /**
     * 页打印份数
     */
    private int quantity = 1;
    /**
     * 是否打印错误
     */
    private boolean isError = false;
    /**
     * 是否取消打印
     */
    private boolean isCancel = false;

    /**
     * 打印模式
     */
    private int printMode = 1;

    /**
     * 获取任务列表的adapter
     */
    public BlueToothPrintAdapter getTaskListAdapter() {
        if (null == mBlueToothPrintAdapter) {
            mBlueToothPrintAdapter = new BlueToothPrintAdapter();
            mBlueToothPrintAdapter.setOnItemClickListener(new BlueToothPrintAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    isShowDialog.setValue(true);
                    itemPosition = position;
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }

                    int connectState = blueDeviceList.get(position).getConnectState();
                    BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(blueDeviceList.get(position).getDeviceHardwareAddress());
                    switch (connectState) {
                        case 10:
                            Log.i("TAG", "配对: 开始");
                            boolean returnValue = false;
                            try {
                                returnValue = ClsUtils.createBond(bluetoothDevice.getClass(), bluetoothDevice);
                            } catch (Exception e) {
                                Log.i("TAG", "闪退日志" + e.getMessage());
                            }
                            Log.i("TAG", "配对: 进行中:" + returnValue);
                            if (returnValue) {//配对成功后连接打印机
                                executorService.submit(() -> {
                                    connectPrinter(position);
                                });

                            } else {
                                isShowDialog.setValue(false);
                                MyToast.show(R.string.common_blue_pairing_failed, false);
                            }


                            break;
                        case 12:

                            executorService.submit(() -> {
                                connectPrinter(position);
                            });
                            return;
                        default:
                            break;
                    }

                }
            });
        }
        return mBlueToothPrintAdapter;
    }

    /**
     * 连接打印机
     */
    public void connectPrinter(int pos) {
        int connectResult = PrintUtil.openPrinter(blueDeviceList.get(pos).getDeviceHardwareAddress());
        AppManager.getInstance().findActivity(BlueToothPrintActivity.class).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String hint = "";
                switch (connectResult) {
                    case 0:
                        hint = MyApplication.getInstance().getResources().getString(R.string.common_blue_connection_successful);
                        if (getPrintType() == 0) {
                            printLabel(printBeanList.size(), 1);
                        } else {
                            printLabel(1, 1);
                        }
                        isShowDialog.setValue(false);
                        break;
                    case -1:
                        hint = MyApplication.getInstance().getResources().getString(R.string.common_blue_connection_failed);
                        break;
                    case -2:
                        hint = MyApplication.getInstance().getResources().getString(R.string.common_blue_unsupported_model);
                        break;
                    default:
                        break;
                }
                isShowDialog.setValue(false);
                MyToast.show(hint, false);
            }
        });
    }

    public void startSearch() {
        mRefresh.set(true);
        itemPosition = -1;
        blueDeviceList.clear();
        mBlueToothPrintAdapter.deleteList();
        mBlueToothPrintAdapter.notifyDataSetChanged();
        if (mBluetoothAdapter.isDiscovering()) {
            if (mBluetoothAdapter.cancelDiscovery()) {
                try {
                    //取消后等待1s后再次搜索
                    Thread.sleep(1000);
                    mBluetoothAdapter.startDiscovery();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } else {
            mBluetoothAdapter.startDiscovery();
        }
    }

    public final BroadcastReceiver receiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //蓝牙发现
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.i("TAG", "测试:搜索中");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device != null) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress();
                    int deviceStatus = device.getBondState();

                    boolean supportBluetoothType = device.getType() == BluetoothDevice.DEVICE_TYPE_CLASSIC || device.getType() == BluetoothDevice.DEVICE_TYPE_DUAL;
                    boolean supportPrintName = deviceName != null;
                    Log.i("TAG", "onReceive:测试 " + deviceName);
                    if (supportBluetoothType && supportPrintName) {
                        BlueDeviceInfo info = new BlueDeviceInfo(deviceName, deviceHardwareAddress, deviceStatus);
                        if (!blueDeviceList.contains(info)) {
                            blueDeviceList.add(info);
                        }
                        Log.i("TAG", "onReceive:测试 " + blueDeviceList.size());
                        mBlueToothPrintAdapter.refreshList(new BlueDeviceInfo(deviceName, deviceHardwareAddress, deviceStatus));
                    }

                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.i("TAG", "测试:开始搜索");
                mRefresh.set(true);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.i("TAG", "测试:搜索结束");
                mRefresh.set(false);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);

                if (itemPosition != -1) {
                    blueDeviceList.get(itemPosition).setConnectState(state);
                    mBlueToothPrintAdapter.notifyItemChanged(itemPosition);
                }

            } else if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
//                if (fragment != null) {
//                    fragment.dismiss();
//                }
            }
        }
    };

    public void setPrintBeanList(List<BluePrintBean> printBeanList) {
        this.printBeanList = printBeanList;
    }


    /**
     * 打印标签
     *
     * @param pages  页数（页表示数据不一样）
     * @param copies 份数
     */
    public void printLabel(int pages, int copies) {
        if (PrintUtil.isConnection() != 0) {
            MyToast.show(R.string.common_blue_connection_not_successful, false);
            return;
        }
//        if ((printBeanList.size() <= 0&&getDensity()==1)||(getDensity()==3&&bean==null)) {
//            MyToast.show(R.string.common_blue_no_print_data, false);
//            return;
//        }
        //重置错误状态变量
        isError = false;
        //重置取消打印状态变量
        isCancel = false;
        //清除数据
        jsonList = new ArrayList<>();
        infoList = new ArrayList<>();
        //总打印份数

        final int[] generatedPrintDataPageCount = {0};
        pageCount = pages;
        quantity = copies;
        int totalQuantity = pageCount * quantity;

        PrintUtil.getInstance().setTotalQuantityOfPrints(totalQuantity);
        AppManager.getInstance().finishActivity();
        //参数1：打印浓度 ，参数2:纸张类型 参数3:打印模式
        PrintUtil.getInstance().startPrintJob(3, getDensity(), printMode, new PrintCallback() {
            @Override
            public void onProgress(int pageIndex, int quantityIndex, HashMap<String, Object> hashMap) {
                //打印进度回调
                if (pageIndex == pageCount && quantityIndex == quantity) {
                    MyToast.show(R.string.common_blue_success_print, false);
                    PrintUtil.getInstance().endJob();
                }
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onError(int errorCode, int printState) {
                isError = true;
                String errorMsg = "";
                //错误回调
                switch (errorCode) {
                    case 1:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_1);
                        break;
                    case 2:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_2);
                        break;
                    case 3:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_3);
                        break;
                    case 4:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_4);
                        break;
                    case 5:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_5);
                        break;
                    case 6:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_6);
                        break;
                    case 7:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_7);
                        break;
                    case 8:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_8);
                        break;
                    case 9:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_9);
                        break;
                    case 10:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_10);
                        break;
                    case 11:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_11);
                        break;
                    case 12:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_12);
                        break;
                    case 13:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_13);
                        break;
                    case 14:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_14);
                        break;
                    case 15:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_15);
                        break;
                    case 16:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_16);
                        break;
                    case 17:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_17);
                        break;
                    case 18:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_18);
                        break;
                    case 19:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_19);
                        break;
                    case 20:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_20);
                        break;
                    case 21:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_21);
                        break;
                    case 22:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_22);
                        break;
                    case 23:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_23);
                        break;
                    case 24:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_24);
                        break;
                    case 25:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_25);
                        break;
                    case 26:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_26);
                        break;
                    case 27:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_27);
                        break;
                    case 28:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_28);
                        break;
                    case 29:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_29);
                        break;
                    case 30:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_30);
                        break;
                    case 31:
                        errorMsg = MyApplication.getInstance().getString(R.string.common_blue_failed_type_31);
                        break;
                    default:
                        break;
                }
                MyToast.show(errorMsg, false);
                PrintUtil.getInstance().endJob();
            }

            @Override
            public void onCancelJob(boolean isSuccess) {
                //取消打印成功回调
                isCancel = true;
            }

            @Override
            public void onBufferFree(int pageIndex, int bufferSize) {
                //pageIndex下一页的打印索引，bufferSize缓存控件

                if (isError || isCancel) {
                    return;
                }

                if (pageIndex > pageCount) {
                    return;
                }
                //已生成数据的页数小于总页数才生成数据
                if (generatedPrintDataPageCount[0] < pageCount) {
                    //需要生成的数据长度小于可缓存的长度
                    if ((pageCount - generatedPrintDataPageCount[0]) < bufferSize) {
                        //生成长度为(pageCount-generatedPrintDataPageCount[0])的数据
                        if (getPrintType() == 0) {
                            generateMultiPagePrintData(generatedPrintDataPageCount[0], pageCount);
                            PrintUtil.getInstance().commitData(
                                    jsonList.subList(generatedPrintDataPageCount[0], pageCount),
                                    infoList.subList(generatedPrintDataPageCount[0], pageCount));

                            generatedPrintDataPageCount[0] += pageCount;
                        } else {

                            generateMultiPagePrintDataTwo();
                            PrintUtil.getInstance().commitData(jsonList, infoList);
                        }


                    } else {
                        if (getPrintType() == 0) {
                            generateMultiPagePrintData(generatedPrintDataPageCount[0], generatedPrintDataPageCount[0] + bufferSize);
                            PrintUtil.getInstance().commitData(
                                    jsonList.subList(generatedPrintDataPageCount[0], generatedPrintDataPageCount[0] + bufferSize),
                                    infoList.subList(generatedPrintDataPageCount[0], generatedPrintDataPageCount[0] + bufferSize));

                            generatedPrintDataPageCount[0] += bufferSize;
                        } else {

                            generateMultiPagePrintDataTwo();
                            PrintUtil.getInstance().commitData(jsonList, infoList);
                        }


                    }
                }

            }
        });

    }

    private int getDensity() {
        if (getPrintType() == 0) {
            return 1;//间隙纸
        } else {
            return 3;//连续纸
        }
    }


    private void generateMultiPagePrintData(int index, int cycleIndex) {
        while (index < cycleIndex) {
            float width = 60;
            float height = 40;
            int orientation = 90;
            float marginY = 2.0F;
            float rectangleHeight = height - marginY * 2;
            float lineHeight = rectangleHeight / 5.0F;
            float codeHeight = rectangleHeight / 3.0F;
            float titleWidth = 60;
            float fontSize = 3.0F;
            //设置画布大小
            PrintUtil.getInstance().drawEmptyLabel(width, height, 0, "");
            //绘制小标题
            /**文本绘制
             *
             * @param × 位置x
             * @param y 位置y
             * @param width 宽
             * @param height 高
             * @param value 内容
             * @param fontFamily 宇体名称，未传输宇体为空字符串时使用默认字体，暂时用默认字体
             * @param fontsize 字体大小
             * @param rotate 旋转
             * @param textAlignHorizontal 水平对齐方式：0：左对齐 1:居中对齐 2：右对齐
             * @param textAlignVert ical 垂直 对齐方式：0：顶对齐 1:垂直居中 2：底对齐
             * @param lineModel 1:宽高固定，内容大小自适应(字号/字符间距/行问距 按比例缩放)2：宽度固定，高度自适应3：宽高固定，超出内容后面加...4:宽高固定，超出内容直裁切
             * @param letterspace 字母之间的标准间隔，单位mm
             * @param linespace 行间距（倍距），单位mm
             * @param mFontsty les 字体样式[斜体，加粗，下划线，删除下划线（预留）〕
             */
            PrintUtil.getInstance().drawLabelText(3f, 0.5f, titleWidth, lineHeight, MyApplication.getInstance().getString(R.string.common_blue_print_title), "宋体", fontSize * 2F, 0, 0, 1, 6, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance().drawLabelText(3f, 6.5f, 60, 8, MyApplication.getInstance().getString(R.string.common_blue_print_skuName) + printBeanList.get(index).getSkuName(), "宋体", fontSize, 0, 0, 1, 4, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance().drawLabelText(3f, 11f, titleWidth, lineHeight, MyApplication.getInstance().getString(R.string.common_blue_promotion_time) + printBeanList.get(index).getBeginTime() + "至" + printBeanList.get(index).getEndTime(), "宋体", fontSize, 0, 0, 1, 6, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance().drawLabelText(3f, 15f, titleWidth, lineHeight, MyApplication.getInstance().getString(R.string.common_blue_print_price) + printBeanList.get(index).getDiscountPrice() + MyApplication.getInstance().getString(R.string.common_blue_print_meta), "宋体", fontSize, 0, 0, 1, 6, 0, 1, new boolean[]{false, false, false, false});
            /***
             *   条形码绘制
             * @param ×  水平坐标
             * @param y  垂直坐标
             * @param width  宽度，单位mm
             * @param height 高度，单位mm
             * @param codeType 一维码类型20：CODE12821:UPC-A,22:UPC-E,23: EAN8, 24: EAN13, 25: CODE93, 26: CODE39, 27: CODEBAR, 28: ITF25
             * @param value 文本内容
             * @param fontsize 文本字号
             * @param rotate 旋转角度，仅支持0,90,180,270
             * @param textHeight 文本高度
             * @param textPosit ion 文本位置，int,I一维码文字识别码显示位置，0：下方显示，1：上方显示,2：不显示
             */
            PrintUtil.getInstance().drawLabelBarCode(3f, 21f, 50, codeHeight, 20, printBeanList.get(index).getDiscountCode(), fontSize * 2F, 0, 3, 0);
            //生成打印数据
            byte[] jsonByte = PrintUtil.getInstance().generateLabelJson();
            //转换为jsonStr
            String jsonStr = new String(jsonByte);
            jsonList.add(jsonStr);
            String jsonInfo = "{  " +
                    "\"printerImageProcessingInfo\": " + "{    " +
                    "\"orientation\":" + orientation + "," +
                    "   \"margin\": [      0,      0,      0,      0    ], " +
                    "   \"printQuantity\": " + quantity + ",  " +
                    "  \"horizontalOffset\": 0,  " +
                    "  \"verticalOffset\": 0,  " +
                    "  \"width\":" + width + "," +
                    "   \"height\":" + height + "," +
                    "  \"epc\": \"\"  }}";
            infoList.add(jsonInfo);
            index++;
        }
    }

    private void generateMultiPagePrintDataTwo() {
        float width = 54;
        float height = 70 + (10F * bean.getDetailList().size())+(4F*(bean.getDetailList().size()-1));//修改
        int orientation = 0;
        float marginY = 2.0F;
        float rectangleHeight = height - marginY * 2;
        float lineHeight = rectangleHeight / 5.0F;
        float codeHeight = rectangleHeight / 3.0F;
        float titleWidth = 45;
        float fontSize = 3.0F;
        float listHeight = 38;
        //设置画布大小
        PrintUtil.getInstance().drawEmptyLabel(width, height, 0, "");
        //绘制小标题
        /**文本绘制
         *
         * @param × 位置x
         * @param y 位置y
         * @param width 宽
         * @param height 高
         * @param value 内容
         * @param fontFamily 宇体名称，未传输宇体为空字符串时使用默认字体，暂时用默认字体
         * @param fontsize 字体大小
         * @param rotate 旋转
         * @param textAlignHorizontal 水平对齐方式：0：左对齐 1:居中对齐 2：右对齐
         * @param textAlignVert ical 垂直 对齐方式：0：顶对齐 1:垂直居中 2：底对齐
         * @param lineModel 1:宽高固定，内容大小自适应(字号/字符间距/行问距 按比例缩放)2：宽度固定，高度自适应3：宽高固定，超出内容后面加...4:宽高固定，超出内容直裁切
         * @param letterspace 字母之间的标准间隔，单位mm
         * @param linespace 行间距（倍距），单位mm
         * @param mFontsty les 字体样式[斜体，加粗，下划线，删除下划线（预留）〕
         */
        PrintUtil.getInstance().drawLabelText(3f, 0.5F, titleWidth, lineHeight, bean.getTenantName(), "宋体", fontSize, 0, 1, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, 5f, titleWidth, lineHeight, getOrderType(getOrder()), "宋体", fontSize, 0, 1, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, 11f, 100, 8, "门店:" + UserManager.getInstance().getUserData().getShopId() + " " + bean.getPurchaseName(), "宋体", fontSize, 0, 0, 0, 4, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, 16f, 100, lineHeight, "供应商:" + bean.getSupplierCode() + " " + bean.getSupplierName(), "宋体", fontSize, 0, 0, 0, 4, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, 21f, titleWidth, lineHeight, "订单号:" + getOrder(), "宋体", fontSize, 0, 0, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, 26f, 100, lineHeight, "制单时间:" + bean.getCreateDate(), "宋体", fontSize, 0, 0, 0, 4, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelLine(3f, 31f, titleWidth, 0.2F, 0, 1, new float[]{5.0f});
        PrintUtil.getInstance().drawLabelText(3f, 33f, titleWidth, lineHeight, "品名", "宋体", fontSize, 0, 0, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, 33f, titleWidth, lineHeight, "单价*数量", "宋体", fontSize, 0, 1, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, 33f, titleWidth, lineHeight, "含税金额", "宋体", fontSize, 0, 2, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelLine(3f, 38f, titleWidth, 0.2F, 0, 1, new float[]{5.0f});

        for (int i = 0; i < bean.getDetailList().size(); i++) {
            PrintUtil.getInstance().drawLabelText(3f, listHeight + 2f, 60, 8, bean.getDetailList().get(i).getSkuName(), "宋体", 2.7F, 0, 0, 0, 4, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance().drawLabelText(3f, listHeight + 6f, 60, 8, bean.getDetailList().get(i).getSkuId(), "宋体", 2.7F, 0, 0, 0, 4, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance().drawLabelText(3f, listHeight + 6f, titleWidth, lineHeight, bean.getDetailList().get(i).getSkuPrice() + "x" + bean.getDetailList().get(i).getSendNum(), "宋体", 2.7F, 0, 1, 0, 6, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance().drawLabelText(3f, listHeight + 6f, titleWidth, lineHeight, bean.getDetailList().get(i).getAmt(), "宋体", 2.7F, 0, 2, 0, 6, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance().drawLabelText(3f, listHeight + 10f, 60, 8, bean.getDetailList().get(i).getUpcCode(), "宋体", 2.7F, 0, 0, 0, 4, 0, 1, new boolean[]{false, false, false, false});
            listHeight = listHeight + 14f;
        }

        PrintUtil.getInstance().drawLabelLine(3f, listHeight, titleWidth, 0.2F, 0, 1, new float[]{5.0f});
        PrintUtil.getInstance().drawLabelText(3f, listHeight + 2f, titleWidth, lineHeight, "合计：", "宋体", fontSize, 0, 0, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(12f, listHeight + 2f, titleWidth, lineHeight, "数量:" + bean.getTotalNum(), "宋体", fontSize, 0, 0, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, listHeight + 2f, titleWidth, lineHeight, "金额:" + bean.getTotalSupplierActualReceiveAmt(), "宋体", fontSize, 0, 2, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelText(3f, listHeight + 8f, titleWidth, lineHeight, getSingName(getOrder()), "宋体", fontSize, 0, 0, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelLine(19f, listHeight + 12f, titleWidth / 2, 0.2F, 0, 1, new float[]{5.0f});
        PrintUtil.getInstance().drawLabelText(3f, listHeight + 14f, titleWidth, lineHeight, "送货员签字：", "宋体", fontSize, 0, 0, 0, 6, 0, 1, new boolean[]{false, false, false, false});
        PrintUtil.getInstance().drawLabelLine(19f, listHeight + 18f, titleWidth / 2, 0.2F, 0, 1, new float[]{5.0f});
        PrintUtil.getInstance().drawLabelText(3f, listHeight + 20f, 60, lineHeight, "打印时间:" + bean.getPrintDate(), "宋体", fontSize, 0, 0, 0, 6, 0, 1, new boolean[]{false, false, false, false});


        //生成打印数据
        byte[] jsonByte = PrintUtil.getInstance().generateLabelJson();
        //转换为jsonStr
        String jsonStr = new String(jsonByte);
        jsonList.add(jsonStr);
        String jsonInfo = "{  " +
                "\"printerImageProcessingInfo\": " + "{    " +
                "\"orientation\":" + orientation + "," +
                "   \"margin\": [      0,      0,      0,      0    ], " +
                "   \"printQuantity\": " + quantity + ",  " +
                "  \"horizontalOffset\": 0,  " +
                "  \"verticalOffset\": 0,  " +
                "  \"width\":" + width + "," +
                "   \"height\":" + height + "," +
                "  \"epc\": \"\"  }}";
        infoList.add(jsonInfo);
    }
    //roCode poCode 只返回一个
    public String getOrder(){
        return bean.getPoCode()==null?bean.getRoCode():bean.getPoCode();
    }
    //根据单据号返回单据类型
    public String getOrderType(String ordNum) {
        if (ordNum.contains("PO")) {
            return "直送收货单";
        } else {
            return "直送退货单";
        }
    }

    //根据单据号返回收货人类型
    public String getSingName(String ordNum) {
        if (ordNum.contains("PO")) {
            return "收货人签字";
        } else {
            return "退货人签字";
        }
    }


    public void setPrintType(int printType) {
        this.printType = printType;
    }

    public int getPrintType() {
        return printType;
    }

    public DirectDeliveryOrderBean getBean() {
        return bean;
    }

    public void setBean(DirectDeliveryOrderBean bean) {
        this.bean = bean;
    }
}

