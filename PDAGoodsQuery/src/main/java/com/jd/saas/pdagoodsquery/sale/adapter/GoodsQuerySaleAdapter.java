package com.jd.saas.pdagoodsquery.sale.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jd.saas.pdagoodsquery.databinding.GoodsQuerySaleItemBinding;
import com.jd.saas.pdagoodsquery.sale.GoodsQuerySaleFragment;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQuerySale;
import com.jd.saas.pdagoodsquery.sale.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品进销存adapter
 */
public class GoodsQuerySaleAdapter extends RecyclerView.Adapter<GoodsQuerySaleAdapter.ItemViewHolder>{
    private List<GoodsQuerySale> saleList = new ArrayList<>();
    private String mDayType = "day";
    public void refreshSaleList(List<GoodsQuerySale> list){
        if(null!=list && list.size()!=0) {
            saleList.clear();
            saleList.addAll(list);
            notifyDataSetChanged();
        }
    }
    public void refreshSaleList(List<GoodsQuerySale> list,String dayType){
        this.mDayType = dayType;
        if(null!=list && list.size()!=0) {
            saleList.clear();
            saleList.addAll(list);
            notifyDataSetChanged();
        }
    }
    public GoodsQuerySaleAdapter(){}
    public GoodsQuerySaleAdapter(List<GoodsQuerySale> list){
        saleList = list;
    }
    private OnItemClick mOnItemClick;
    public void setOnItemClick(OnItemClick onItemClick){
        this.mOnItemClick = onItemClick;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GoodsQuerySaleItemBinding binding = GoodsQuerySaleItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false);
        ItemViewHolder holder = new ItemViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            GoodsQuerySale sale = saleList.get(position);
            holder.mBinding.setBean(sale);


            Log.e("mDayType",mDayType+",,,,,,,,");
            if(mDayType.equals(GoodsQuerySaleFragment.SALE_DATE_WEEK)){
                if(!sale.getDate().isEmpty()){
                    String startTime = sale.getDate().split("-")[0];
                    String endTime = sale.getDate().split("-")[1];
                    if(!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)){
                        Date startValue = DateUtil.parseString(startTime);
                        Date endValue = DateUtil.parseString(endTime);
                        String startDate = DateUtil.formatToMonth(startValue);
                        String endDate = DateUtil.formatToMonth(endValue);
                        String date = startDate +"-"+endDate;
                        holder.mBinding.tvDate.setText(date);
                    }
                }
            }else{
                holder.mBinding.tvDate.setText(sale.getDate());
            }
            if(mDayType.equals(GoodsQuerySaleFragment.SALE_DATE_DAY)){
                holder.mBinding.ivArrow.setVisibility(View.VISIBLE);
                holder.mBinding.llInNum.setClickable(true);
                holder.mBinding.llInNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClick.onClick(sale);
                    }
                });
            }else {
                holder.mBinding.llInNum.setClickable(false);
                holder.mBinding.ivArrow.setVisibility(View.INVISIBLE);
            }
    }

    @Override
    public int getItemCount() {
        return saleList.size();
    }
    public interface OnItemClick {
        void onClick(GoodsQuerySale sale);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        GoodsQuerySaleItemBinding mBinding;
        public ItemViewHolder(@NonNull GoodsQuerySaleItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }

}
