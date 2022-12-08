package com.jd.saas.pdagoodsquery.goods.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.goods.adapter.GoodsQueryDialogAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoodsQueryBarcodeDialog extends Dialog {
    private GoodsQueryBarcodeDialog(@NonNull Context context) {
        super(context);

    }
    private GoodsQueryBarcodeDialog(Context context, int theme) {
        super(context,theme);
    }
    public static class Builder{
        private OnClickListener mCloseClickListener;
        private GoodsQueryBarcodeDialog mDialog;
        private Context mContext;
        private String mUpcCode;
        public Builder(Context context, String upcCode){
            this.mContext = context;
            this.mUpcCode = upcCode;

        }
        public Builder setCloseListener(OnClickListener listener){
            mCloseClickListener = listener;

            return this;
        }

        public GoodsQueryBarcodeDialog create(){
            View view = LayoutInflater.from(mContext).inflate(R.layout.goods_query_dialog_more, null);
            mDialog = new GoodsQueryBarcodeDialog(mContext);
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
            GoodsQueryDialogAdapter adapter = new GoodsQueryDialogAdapter(mContext, initAdapterList(mUpcCode));
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
            String codeName = MyApplication.getInstance().getResources().getString(R.string.goods_query_code);
            String colon = MyApplication.getInstance().getResources().getString(R.string.goods_query_colon);
            String split = MyApplication.getInstance().getResources().getString(R.string.goods_query_split);
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
