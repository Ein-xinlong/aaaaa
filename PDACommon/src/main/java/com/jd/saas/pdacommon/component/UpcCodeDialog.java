package com.jd.saas.pdacommon.component;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.databinding.DialogUpcCodeBinding;
import com.jd.saas.pdacommon.databinding.ItemDialogUpcCodeBinding;

import org.jetbrains.annotations.NotNull;


/**
 * 展示UPC条码的弹窗
 *
 * @author gouhetong
 */
public class UpcCodeDialog {
    public static void open(View view, String[] upcCodes) {
        View dialog = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_upc_code, null);
        DialogUpcCodeBinding binding = DialogUpcCodeBinding.bind(dialog);
        final AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                .setView(dialog)
                .create();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        binding.close.setOnClickListener(v -> alertDialog.dismiss());
        binding.ivClose.setOnClickListener(v -> alertDialog.dismiss());
        binding.recyclerView.setAdapter(new UpcCodeAdapter(upcCodes));
    }


    static class UpcCodeAdapter extends RecyclerView.Adapter<UpcCodeAdapter.MyViewHolder> {
        private final String[] list;

        public UpcCodeAdapter(String[] list) {
            this.list = list;
        }

        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            ItemDialogUpcCodeBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_dialog_upc_code, parent, false);
            return new MyViewHolder(dataBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
            holder.dataBinding.tvLabel.setText(holder.itemView.getContext().getString(R.string.common_upc_code_dialog_label, position + 1));
            holder.dataBinding.tvCode.setText(list[position]);
        }

        @Override
        public int getItemCount() {
            return list.length;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            private final ItemDialogUpcCodeBinding dataBinding;

            public MyViewHolder(@NonNull @NotNull ItemDialogUpcCodeBinding dataBinding) {
                super(dataBinding.getRoot());
                this.dataBinding = dataBinding;
            }
        }
    }
}
