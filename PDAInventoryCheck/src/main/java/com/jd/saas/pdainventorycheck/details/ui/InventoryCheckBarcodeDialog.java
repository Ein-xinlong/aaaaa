package com.jd.saas.pdainventorycheck.details.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.screen.ScreenUtil;
import com.jd.saas.pdainventorycheck.R;
import com.jd.saas.pdainventorycheck.details.adapter.InventoryCheckDialogAdapter;

import java.util.ArrayList;
import java.util.List;

public class InventoryCheckBarcodeDialog extends Dialog {
    private InventoryCheckBarcodeDialog(@NonNull Context context) {
        super(context);

    }
    private InventoryCheckBarcodeDialog(Context context,int theme) {
        super(context,theme);
    }
    public static class Builder{
        private DialogInterface.OnClickListener mCloseClickListener;
        private InventoryCheckBarcodeDialog mDialog;
        private Context mContext;
        private String mUpcCode;
        public Builder(Context context, String upcCode){
            this.mContext = context;
            this.mUpcCode = upcCode;

        }
        public Builder setCloseListener(DialogInterface.OnClickListener listener){
            mCloseClickListener = listener;

            return this;
        }

        public InventoryCheckBarcodeDialog create(){
            View view = LayoutInflater.from(mContext).inflate(R.layout.inventorycheck_dialog_more, null);
            mDialog = new InventoryCheckBarcodeDialog(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mDialog.addContentView(view, layoutParams);
            //设置外边距30dp
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            lp.width =  mContext.getResources().getDisplayMetrics().widthPixels-60;
            mDialog.getWindow().setAttributes(lp);
            Window window = mDialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //控件初始化：ListView、
            ListView listView = view.findViewById(R.id.listview);
            InventoryCheckDialogAdapter adapter = new InventoryCheckDialogAdapter(mContext, initAdapterList(mUpcCode));
            listView.setAdapter(adapter);
            //关闭按钮 监听事件
            if(null!=mCloseClickListener){
                TextView close = view.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCloseClickListener.onClick(mDialog,0);
                    }
                });
            }
            return mDialog;
        }
        /*
        adpater数据转换：string 以;分割为 List
         */
        private List initAdapterList(String upcCode){
            List<String> list = new ArrayList<>();
            String codeName = MyApplication.getInstance().getResources().getString(R.string.inventory_check_barcode2);
            String colon = MyApplication.getInstance().getResources().getString(R.string.inventory_check_colon);
            String split = MyApplication.getInstance().getResources().getString(R.string.inventory_check_split);
            if(!TextUtils.isEmpty(upcCode)){
                String[] items = upcCode.split(split) ;
                for (int i=0;i<items.length;i++) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(codeName).append(i+1).append(colon).append(items[i]);
                    list.add(builder.toString());
                }
            }
            return list;
        }
    }
}
