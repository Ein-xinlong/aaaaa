package com.jd.saas.pdacommon.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.databinding.CommonSimpleListDialogBinding;
import com.jd.saas.pdacommon.databinding.CommonSimpleListDialogItemBinding;
import com.jd.saas.pdacommon.screen.ScreenUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleListDialog<T> {
    private final CommonSimpleListDialogBinding mBinding;
    private final BottomSheetDialog bottomSheetDialog;
    private OnItemSelectListener<T> onItemSelectListener;

    public interface OnItemSelectListener<T> {
        void onItemSelect(T bean);
    }

    public void setOnItemSelectListener(OnItemSelectListener<T> onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    public void setOptions(List<Option<T>> options) {
        SimpleListDialogAdapter<T> adapter = new SimpleListDialogAdapter<>(options,
                bean -> {
                    if (onItemSelectListener != null) {
                        onItemSelectListener.onItemSelect(bean);
                    }
                    dismiss();
                });
        final int MAX_VISIBLE_ITEM_CNT = 4;
        if (adapter.getItemCount() >= MAX_VISIBLE_ITEM_CNT) {
            final int ITEM_HEIGHT = 60;
            final int offset = 8;
            mBinding.recyclerView.getLayoutParams().height = ScreenUtil.dp2px(mBinding.recyclerView.getContext(), ITEM_HEIGHT * MAX_VISIBLE_ITEM_CNT + offset);
        } else {
            mBinding.recyclerView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        mBinding.recyclerView.setAdapter(adapter);
    }

    public SimpleListDialog(Context context, @StringRes int titleStrRes) {
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.common_simple_list_dialog, null);
        mBinding = CommonSimpleListDialogBinding.bind(dialogView);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setOnShowListener(dialog -> bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED));
        mBinding.tvTitle.setText(titleStrRes);
        mBinding.ivClose.setOnClickListener(v -> dismiss());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
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


    private static class SimpleListDialogAdapter<T> extends RecyclerView.Adapter<SimpleListDialogAdapter<T>.MyViewHolder> {
        private final List<Option<T>> mList;
        private final OnItemSelectListener<T> onItemSelectListener;


        interface OnItemSelectListener<T> {
            void onItemSelect(T bean);
        }

        public SimpleListDialogAdapter(List<Option<T>> list,
                                       OnItemSelectListener<T> onItemSelectListener) {

            this.onItemSelectListener = onItemSelectListener;
            mList = list;
        }

        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            return new MyViewHolder(CommonSimpleListDialogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
            holder.bind(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private void setSelectType(Option<T> select) {
            for (Option<T> item : mList) {
                item.setSelected(select != null && select.equals(item));
            }
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final CommonSimpleListDialogItemBinding mBinding;

            public MyViewHolder(@NonNull @NotNull CommonSimpleListDialogItemBinding mBinding) {
                super(mBinding.getRoot());
                this.mBinding = mBinding;
            }

            public void bind(Option<T> bean) {
                mBinding.setBean(bean);
                mBinding.getRoot().setOnClickListener(v -> {
                    setSelectType(bean);
                    bean.setSelected(true);
                    onItemSelectListener.onItemSelect(bean.getData());
                });
                mBinding.executePendingBindings();
            }
        }
    }

    public static class Option<T> {
        private String name;
        private T data;
        private boolean isSelected = false;

        public int getSelectorRes() {
            if (isSelected) {
                return R.drawable.ic_checked;
            } else {
                return R.drawable.ic_unchecked;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
