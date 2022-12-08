package com.jd.saas.pdacommon.dialog.linkage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.databinding.DialogTranstypeLayoutBinding;
import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.dialog.linkage.adapter.ChildTransTypeAdapter;
import com.jd.saas.pdacommon.dialog.linkage.adapter.ParentTransTypeAdapter;
import com.jd.saas.pdacommon.dialog.linkage.model.ChildTansType;
import com.jd.saas.pdacommon.dialog.linkage.model.ParentTansType;
import com.jd.saas.pdacommon.dialog.linkage.net.LinkageDialogRepository;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.toast.MyToast;

import java.util.List;

/**
 * 库存交易类型 联动Dialog
 */
public class StockTransTypeSelectDialog {
    private final Context context;
    private final LinkageDialogRepository repository = new LinkageDialogRepository();
    private final ParentTransTypeAdapter parentTypeAdapter;
    private final ChildTransTypeAdapter childAdapter;
    private final BottomSheetDialog bottomSheetDialog;

    /**
     * 选中后的回调
     */
    private OnSelectListener onSelectListener;

    /**
     * 选中监听
     */
    public interface OnSelectListener {
        void onItemSelect(ParentTansType parentTansType, ChildTansType childTansType);
    }

    /**
     * 设置监听
     */
    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public StockTransTypeSelectDialog(Context context) {
        this.context = context;
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_transtype_layout, null);
        DialogTranstypeLayoutBinding mBinding = DialogTranstypeLayoutBinding.bind(dialogView);
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.getBehavior().setPeekHeight(heightPixels);

        parentTypeAdapter = new ParentTransTypeAdapter(this::onParentItemSelect);
        childAdapter = new ChildTransTypeAdapter(this::onChildItemSelect);

        mBinding.ivClose.setOnClickListener(v -> bottomSheetDialog.dismiss());
        mBinding.parentTypeRv.setAdapter(parentTypeAdapter);
        mBinding.childTypeRv.setAdapter(childAdapter);
    }

    /**
     * 父级选中后更新子级数据
     */
    private void onParentItemSelect(ParentTansType tansType) {
        if (childAdapter != null) {
            childAdapter.freshList(tansType.getChildren());
        }
    }

    /**
     * 子级选中后返回
     */
    private void onChildItemSelect(ChildTansType tansType) {
        if (onSelectListener != null && parentTypeAdapter != null) {
            onSelectListener.onItemSelect(parentTypeAdapter.getSelectType(), tansType);
        }
        bottomSheetDialog.dismiss();
    }

    /**
     * 显示弹窗
     */
    public void show() {
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.show();
        repository.getStockTansType(new BaseObserver<List<ParentTansType>>() {
            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                mProgressDialog.dismiss();
                if (error) {
                    MyToast.show("查询库存交易类型失败", false);
                } else {
                    bottomSheetDialog.show();
                }
            }

            @Override
            protected void onSuccess(List<ParentTansType> typeList) {
                if (typeList != null && typeList.size() > 0) {
                    parentTypeAdapter.setData(typeList);
                }
            }

            @Override
            protected void onError(NetError error) {

            }
        });
    }
}
