package com.jd.saas.pdacommon.printer.urovo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.device.PrinterManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.activity.BaseActivity;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jingdong.amon.router.annotation.JDRouteUri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Urovo pda 打印机测试页面
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = UrovoPrinterTestRouter.UROVO_PRINTER_MODULE, path = UrovoPrinterTestRouter.UROVO_PRINTER_MAIN_PAGE)
public class UrovoPrinterTestActivity extends BaseActivity {

    private Button mPrinteBtn;
    private Button mPrinte1;
    private Button mPrinte2;
    private Button mForWard;
    private Button mBack;
    private EditText printInfo;
    private SimpleDateFormat formatter = new SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss:SSS    ");
    private final static String PRNT_ACTION = "android.prnt.message";
    PrinterManager printer = new PrinterManager();
    public final static String CfgStr = "商户号:812110045110001\r\n"
            + "商户名:北京宅急送快运股份有限公司\r\n" + "终端号:45851701\r\n" + "批次号:000001\r\n"
            + "流水号:000001\r\n" + "票据号:000001\r\n" + "TPDU:6000000090\r\n"
            + "VER:0100\r\n" + "MkeyIndex:0\r\n" + "EkeyIndex:1\r\n"
            + "PinkeyIndex:2\r\n" + "MACkeyIndex:3\r\n" + "MACkeyIndex_H:4\r\n"
            + "MACkeyIndex_L:5\r\n" + "通信超时时间:30\r\n" + "冲正超时时间:30\r\n"
            + "冲正次数:3\r\n" + "重试次数:3\r\n" + "小票数:2\r\n"
            + "IP地址:218.242.247.7\r\n" + "IP端号:7002\r\n"
            + "交易总笔数:100\r\n" + "当前交易笔数:0\r\n" + "需要重置密码:0\r\n"
            + "冲正或POS查询:0\r\n" + "需要签到:1\r\n" + "手输卡号使能:0\r\n" + "快钱通知:\r\n"
            + "商户广告:\r\n";

    private static final String[] mTempThresholdTable ={
            "80", "75", "70", "65", "60",
            "55", "50", "45", "40", "35",
            "30", "25", "20", "15", "10",
            "5", "0", "-5", "-10", "-15",
            "-20", "-25", "-30", "-35", "-40",
    };

    private static final String[] mBarTypeTable ={
            "3", "20", "25",
            "29", "34", "55", "58",
            "71", "84", "92",
    };

    private Spinner mBarcodeType;
    private int mBarcodeTypeValue;

    private final static String SPINNER_PREFERENCES_FILE = "SprinterPrefs";
    private final static String SPINNER_SELECT_POSITION_KEY = "spinnerPositions";
    private final static int DEF_SPINNER_SELECT_POSITION = 6;
    private final static String SPINNER_SELECT_VAULE_KEY = "spinnerValue";
    private final static String DEF_SPINNER_SELECT_VAULE = mTempThresholdTable[DEF_SPINNER_SELECT_POSITION];

    private int mSpinnerSelectPosition;
    private String mSpinnerSelectValue;
    private Button mBtSetSpeed;
    private EditText mEtSpeed;
    private int  mPrinterSpeed =0;
    // printer hue threshold value
    private final static int DEF_PRINTER_HUE_VALUE = 2;
    private final static int MIN_PRINTER_HUE_VALUE = 0;
    private final static int MAX_PRINTER_HUE_VALUE = 4;

    private final static int DEF_PRINTER_SPEED_VALUE = 9;
    private final static int MIN_PRINTER_SPEED_VALUE = 0;
    private final static int MAX_PRINTER_SPEED_VALUE = 9;
    private Button mBtSetBlack;
    private EditText mEtBlack;
    private int mPrinterBlack = DEF_PRINTER_HUE_VALUE;

    private BroadcastReceiver mPrtReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            int ret = intent.getIntExtra("ret", 0);
            if(ret == -1) {
                MyToast.show("NO Paper!!",false);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer_test);

        mPrinteBtn = findViewById(R.id.print);
        printInfo = findViewById(R.id.printer_info);

        // set up Black, value: 0~4, default: 2;
        mEtBlack = findViewById(R.id.et_black);
        mBtSetBlack = findViewById(R.id.bt_set_black);
        mBtSetBlack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!mEtBlack.getText().toString().equals("")){
                    mPrinterBlack = Integer.parseInt(mEtBlack.getText().toString());
                }else{
                    mPrinterBlack = DEF_PRINTER_HUE_VALUE;
                }

                if(mPrinterBlack < 0 || mPrinterBlack < MIN_PRINTER_HUE_VALUE ||
                        mPrinterBlack > MAX_PRINTER_HUE_VALUE){
                    mPrinterBlack = DEF_PRINTER_HUE_VALUE;
                }

                if(printer == null){
                    printer = new PrinterManager();
                }

                printer.setGrayLevel(mPrinterBlack);
                mEtBlack.setText(String.valueOf(mPrinterBlack));
            }

        });

        mPrinteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messgae = printInfo.getText().toString();
                if(messgae.length() > 0) {
                    doprintwork(messgae);
                } else {
                    //Toast.makeText(PrinterManagerActivity.this, "开始打印预设信息！", Toast.LENGTH_SHORT).show();
                    doprintwork(CfgStr);
                }
            }
        });
        mForWard =findViewById(R.id.button3);

        mForWard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printer.prn_paperForWard(50);
            }
        });
        mBack =findViewById(R.id.button4);
//        mBack.setVisibility(View.GONE);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printer.prn_paperBack(10);
            }
        });
        mPrinte1 = findViewById(R.id.button1);
        mPrinte1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long curS =System.currentTimeMillis();
                Date curDate   =   new   Date(curS);
                String   str   =   formatter.format(curDate);
                printInfo.append("start time :  "+str+"\n");
                doPrint(2);
                long curE = System.currentTimeMillis();
                Date   edcurDate   =   new   Date(curE);
                String   sstr   =   formatter.format(edcurDate);
                long t_tame =curE-curS;
                printInfo.append("stop time :: "+sstr+"\n");
                printInfo.append("take time :: "+t_tame+"\n\n");

            }
        });
        mPrinte2 = findViewById(R.id.button2);
        mPrinte2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messgae = printInfo.getText().toString();
                if(messgae.length() > 0) {
                    doPrint(1);
                } else {
                    MyToast.show("Enter the content",false);
                }
            }
        });
        mBarcodeType = findViewById(R.id.spinner_barcode);
        ArrayAdapter mBarcodeTypeAdapter = ArrayAdapter.createFromResource(
                this, R.array.barcode_type,
                android.R.layout.simple_spinner_item);

        mBarcodeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBarcodeType.setAdapter(mBarcodeTypeAdapter);

        mBarcodeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                Log.i("Hz", "------- position -------" + position);
                mBarcodeTypeValue = Integer.parseInt(mBarTypeTable[position]);
                Log.i("Hz", "------- mBarcodeTypeValue -------" + mBarcodeTypeValue);

                switch(mBarcodeTypeValue){
                    case 34:// UPCA, no., UPCA needs short length of No.
                        //case 2:// Chinese25MATRIX, no.
                    case 3:// Chinese25INTER, no.
                    case 29:// RSS14, no.
                        printInfo.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case 20:// CODE128, alphabet + no.
                    case 25:// CODE93, alphabet + no.
                    case 55:// PDF417, setHue: 3
                    case 58:// QRCODE
                    case 71:// DATAMATRIX
                    case 84:// uPDF417
                    case 92:// AZTEC
                        printInfo.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mEtSpeed = findViewById(R.id.et_speed);
        mBtSetSpeed = findViewById(R.id.bt_set_speed);
        mBtSetSpeed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(isNumeric(mEtSpeed.getText().toString(), UrovoPrinterTestActivity.this)){
                    try {
                        mPrinterSpeed = Integer.parseInt(mEtSpeed.getText().toString());
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    mPrinterSpeed = DEF_PRINTER_SPEED_VALUE;
                }

                if(mPrinterSpeed < MIN_PRINTER_SPEED_VALUE || mPrinterSpeed > MAX_PRINTER_SPEED_VALUE){
                    mPrinterSpeed = DEF_PRINTER_SPEED_VALUE;
                }

                if(printer == null){
                    printer = new PrinterManager();
                }

                Log.d("Hz", "---------set PrinterSpeed = " + mPrinterSpeed);
                printer.prn_setSpeed(mPrinterSpeed);
                mEtSpeed.setText(String.valueOf(mPrinterSpeed));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mPrtReceiver);
        writeSpinnerPrefsState(this);
    }

    private boolean hasChineseChar(String text) {
        boolean hasChar = false;
        int length = text.length();
        int byteSize = text.getBytes().length;
        hasChar = (length != byteSize);
        return hasChar;
    }

    void doprintwork(String msg) {
        printer.setupPage(384, -1);
        int ret = printer.drawTextEx(msg, 5, 0, 384, -1, "simsun", 24, 0, 0, 0);
        printer.printPage(0);
        Intent i = new Intent("android.prnt.message");
        i.putExtra("ret", ret);
        this.sendBroadcast(i);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PRNT_ACTION);
        registerReceiver(mPrtReceiver, filter);
        readSpinnerPrefsState(this);
    }

    void doPrint(int type) {

        printer.setupPage(384, -1);
        switch (type) {
            case 1:
	           /*String text = printInfo.getText().toString();
	           if(hasChineseChar(text)){
	               printer.drawBarcode(text, 50, 10, 58, 8, 120, 0);

	           } else {
	               printer.drawBarcode(text, 196, 300, 20, 2, 70, 0);

	               printer.drawBarcode(text, 196, 300, 20, 2, 70, 1);

	               printer.drawBarcode(text, 196, 300, 20, 2, 70, 2);

	               printer.drawBarcode(text, 196, 300, 20, 2, 70, 3);
	           } */
                String text = printInfo.getText().toString();
                Log.i("Hz", "----------- text ---------- " + text);
//                String text = "1234567890";
                switch(mBarcodeTypeValue){
                    case 20:// CODE128, alphabet + no.
                    case 25:// CODE93, alphabet + no.
                        printer.prn_drawBarcode(text, 196, 300, mBarcodeTypeValue, 2, 70, 0);
                        printer.prn_drawBarcode(text, 196, 300, mBarcodeTypeValue, 2, 70, 1);
                        printer.prn_drawBarcode(text, 196, 300, mBarcodeTypeValue, 2, 70, 2);
                        printer.prn_drawBarcode(text, 196, 300, mBarcodeTypeValue, 2, 70, 3);
                        break;
                    case 34:// UPCA, no., UPCA needs short length of No.
                        //case 2:// Chinese25MATRIX, no.
                        if(isNumeric(text,this.getApplicationContext())){
//                           printer.prn_drawBarcode(text, 50, 10, mBarcodeTypeValue, 2, 70, 0);
                            printer.prn_drawBarcode(text, 196, 300, mBarcodeTypeValue, 2, 70, 0);
                            printer.prn_drawBarcode(text, 196, 300, mBarcodeTypeValue, 2, 70, 1);
                            printer.prn_drawBarcode(text, 196, 300, mBarcodeTypeValue, 2, 70, 2);
                            printer.prn_drawBarcode(text, 196, 300, mBarcodeTypeValue, 2, 70, 3);
                        }else{
                            MyToast.show("Not support for non-numeric!!!",false);
                            printInfo.requestFocus();
                            return;
                        }
                        break;

                    case 3:// Chinese25INTER, no.
                    case 29:// RSS14, no.
                        if(isNumeric(text,this.getApplicationContext())){
                            printer.prn_drawBarcode(text, 50, 10, mBarcodeTypeValue, 2, 40, 0);
                        }else{
                            MyToast.show("Not support for non-numeric!!!",false);
                            printInfo.requestFocus();
                            return;
                        }
                        break;
                    case 55:// PDF417, setHue: 3
                        printer.prn_drawBarcode(text, 25, 5, mBarcodeTypeValue, 3, 60, 0);
                        break;
                    case 58:// QRCODE
                    case 71:// DATAMATRIX
                        printer.prn_drawBarcode(text, 50, 10, mBarcodeTypeValue, 8, 120, 0);
                        break;
                    case 84:// uPDF417
                        printer.prn_drawBarcode(text, 25, 5, mBarcodeTypeValue, 4, 60, 0);
                        break;
                    case 92:// AZTEC
                        printer.prn_drawBarcode(text, 50, 10, mBarcodeTypeValue, 8, 120, 0);
                        break;
                }
                break;

            case 2:
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
                opts.inDensity = getResources().getDisplayMetrics().densityDpi;
                opts.inTargetDensity = getResources().getDisplayMetrics().densityDpi;
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add, opts);
                printer.drawBitmap(img, 30, 0);
                break;
            case 3:
                printer.drawLine(264, 50, 48, 50, 4);
                printer.drawLine(156, 0, 156, 120, 2);
                printer.drawLine(16, 0, 300, 100, 2);
                printer.drawLine(16, 100, 300, 0, 2);
                break;
        }

        int ret= printer.printPage(0);
        Intent intent = new Intent(PRNT_ACTION);
        intent.putExtra("ret", ret);
        this.sendBroadcast(intent);
    }

    // read prefs to restore
    private boolean readSpinnerPrefsState(Context c){
        SharedPreferences sharedPrefs = c.getSharedPreferences(SPINNER_PREFERENCES_FILE, MODE_PRIVATE);
        mSpinnerSelectPosition = sharedPrefs.getInt(SPINNER_SELECT_POSITION_KEY, DEF_SPINNER_SELECT_POSITION);
        mSpinnerSelectValue = sharedPrefs.getString(SPINNER_SELECT_VAULE_KEY, DEF_SPINNER_SELECT_VAULE);

        return (sharedPrefs.contains(SPINNER_SELECT_POSITION_KEY));
    }

    // write prefs to file for restroing
    private boolean writeSpinnerPrefsState(Context c){
        SharedPreferences sharedPrefs = c.getSharedPreferences(SPINNER_PREFERENCES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putInt(SPINNER_SELECT_POSITION_KEY, mSpinnerSelectPosition);
        editor.putString(SPINNER_SELECT_VAULE_KEY, mSpinnerSelectValue);

        return (editor.commit());
    }

    public boolean isNumeric(String string, Context context) {
        if(!string.equals("") && string.matches("\\d*")){
            if(String.valueOf(Integer.MAX_VALUE).length() < string.length()){
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
}
