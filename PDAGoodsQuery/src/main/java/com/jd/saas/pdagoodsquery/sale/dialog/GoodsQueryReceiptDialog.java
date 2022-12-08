package com.jd.saas.pdagoodsquery.sale.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.sale.adapter.GoodsQueryReceiptAdapter;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQueryReceipt;
import java.util.List;

/**
 * 单据明细Dialog
 *
 */
public class GoodsQueryReceiptDialog {
    private BottomSheetDialog bottomSheetDialog;
    private final List<GoodsQueryReceipt> mReceiptList;

    public GoodsQueryReceiptDialog(Context context, List<GoodsQueryReceipt> receiptList) {

        mReceiptList = receiptList;
        initView(context);
    }

    private void initView(Context context){
        View dialogView = LayoutInflater.from(context).inflate(R.layout.goods_query_receipt_dialog, null);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int heightPixels = dm.heightPixels;
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        BottomSheetBehavior bottomSheetBehavior = bottomSheetDialog.getBehavior();
//        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDEDTATE_EXPANDED);
        bottomSheetBehavior.setPeekHeight(heightPixels);//没有了抖动效果
        bottomSheetDialog.setCanceledOnTouchOutside(false);//点击框外，不关闭弹出框
        ImageView close = dialogView.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        TextView back = dialogView.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        //父类型rv
        RecyclerView receiptRv = dialogView.findViewById(R.id.receiptRv);
        receiptRv.getLayoutParams().height = heightPixels/2;
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        receiptRv.setLayoutManager(mLinearLayoutManager);
        GoodsQueryReceiptAdapter adapter = new GoodsQueryReceiptAdapter(mReceiptList);
        receiptRv.setAdapter(adapter);

    }

    public BottomSheetDialog getDialog() {
        return bottomSheetDialog;
    }

    public void show() {
        bottomSheetDialog.show();
    }

    public void dismiss() {
        bottomSheetDialog.dismiss();
    }


}
