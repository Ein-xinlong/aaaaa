package com.jd.saas.pdagoodsquery.goods.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.databinding.GoodsQueryAdapterPromotionBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class GoodsQueryPromotionAdapter extends RecyclerView.Adapter<GoodsQueryPromotionAdapter.ItemHolder>{
    public GoodsQueryAdapterPromotionBinding binding;
    private List<SearchResultBean.Promotion> mList = new ArrayList<>();
    private int mNumber=2;

    public GoodsQueryPromotionAdapter(List<SearchResultBean.Promotion> list) {
        this.mList = list;
    }
    public GoodsQueryPromotionAdapter() {

    }

    public void refreshList(List<SearchResultBean.Promotion> list,int number) {
        Log.e("promotion","promotinList="+list.size());
        if (null != list && list.size() != 0) {
            mNumber = number;
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

    }
    @NonNull
    @NotNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.goods_query_adapter_promotion,parent,false);
        ItemHolder holder = new ItemHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemHolder holder, int position) {
        if(mList.size()!=0){

            holder.binding.setPromotion(mList.get(position));
            holder.binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        if(mList.size()==1){
            return mList.size();
        }else {
            return mList.size()==0?mList.size():mNumber;

        }

    }


    class ItemHolder extends RecyclerView.ViewHolder{
        GoodsQueryAdapterPromotionBinding binding;

        public void setBinding(GoodsQueryAdapterPromotionBinding binding) {
            this.binding = binding;
        }

        public ItemHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
