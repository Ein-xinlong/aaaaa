package com.jd.saas.pdadelivery.detail.adapter;

import android.app.DatePickerDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.utils.EditTextHelper;
import com.jd.saas.pdadelivery.R;
import com.jd.saas.pdadelivery.databinding.DetailDialogItemDataBinding;
import com.jd.saas.pdadelivery.detail.bean.DeliveryShelfLifeInfoBean;
import com.jd.saas.pdadelivery.detail.bean.DeliverySkuBean;
import com.jd.saas.pdadelivery.detail.bean.DeliveryStockTypeBean;
import com.jd.saas.pdadelivery.detail.ui.DeliverySelectStockTypeDialog;
import com.jd.saas.pdadelivery.net.enums.SaleModeEnum;
import com.jd.saas.pdadelivery.util.Formatter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * dialog  recyclerview适配器
 *
 * @author: ext.anxinlong
 * @date: 2021/6/5
 */
public class DeliveryShelfLiftInfoAdapter extends RecyclerView.Adapter<DeliveryShelfLiftInfoAdapter.ItemViewHolder> {
    private final int saleMode;
    private final BigDecimal limit;
    private final DeliveryShelfLifeInfoBeanFactory factory;

    private final List<DeliveryShelfLifeInfoBean> mList;

    private final FragmentManager fragmentManager;

    private OnInputQtyChangeListener onInputQtyChangeListener;
    private OnProductDateChangeListener productDateChangeListener;
    private OnAddItemListener onAddItemListener;

    public DeliveryShelfLiftInfoAdapter(FragmentManager fragmentManager, DeliverySkuBean bean, DeliveryShelfLifeInfoBeanFactory factory, List<DeliveryShelfLifeInfoBean> mList) {
        this.saleMode = bean.getSaleMode();
        this.limit = BigDecimal.ZERO.max(bean.getUpperLimitReceived().subtract(bean.getActualQty()));
        this.factory = factory;
        this.fragmentManager = fragmentManager;
        this.mList = mList;
    }

    public void setProductDateChangeListener(OnProductDateChangeListener productDateChangeListener) {
        this.productDateChangeListener = productDateChangeListener;
    }

    public void setOnInputQtyChangeListener(OnInputQtyChangeListener onInputQtyChangeListener) {
        this.onInputQtyChangeListener = onInputQtyChangeListener;
    }

    public void setOnAddItemListener(OnAddItemListener onAddItemListener) {
        this.onAddItemListener = onAddItemListener;
    }

    @NonNull
    @Override
    public DeliveryShelfLiftInfoAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DetailDialogItemDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.delivery_detail_dialog_adapter, parent, false);
        return new ItemViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryShelfLiftInfoAdapter.ItemViewHolder holder, int position) {
        DeliveryShelfLifeInfoBean bean = mList.get(position);
        holder.bind(bean);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private void addNewItem(DeliveryShelfLifeInfoBean bean) {
        int index = mList.indexOf(bean);
        if (index >= 0) {
            int addPosition = index + 1;
            if (addPosition < mList.size()) {
                mList.add(addPosition, factory.create());
            } else {
                mList.add(factory.create());
            }
            notifyItemInserted(addPosition);
        }
        if (onAddItemListener != null) {
            onAddItemListener.onAddItem();
        }
    }

    private void removeItem(DeliveryShelfLifeInfoBean bean) {
        if (mList.size() == 1) {
            return;
        }
        int index = mList.indexOf(bean);
        if (index >= 0) {
            mList.remove(index);
            notifyItemRemoved(index);
        }
    }


    public BigDecimal getSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (DeliveryShelfLifeInfoBean infoBean : mList) {
            BigDecimal inputQty = infoBean.getInputQty();
            if (inputQty != null) {
                sum = sum.add(inputQty);
            }
        }
        return sum;
    }

    private void onItemProductDateChange(DeliveryShelfLifeInfoBean bean, Date selectDate) {
        int index = -1;
        for (int i = 0; i < mList.size(); i++) {
            DeliveryShelfLifeInfoBean item = mList.get(i);
            boolean isSame = item.getLocalId() == bean.getLocalId();
            if (isSame) {
                index = i;
            } else {
                //库位已选择后才比较是否存在库位和生产时期都相同都
                boolean isContentSameAfterChange =
                        !TextUtils.isEmpty(bean.getLocId())
                                && bean.getLocId().equals(item.getLocId())
                                && selectDate.equals(item.getCreateDate());
                if (isContentSameAfterChange) {
                    MyToast.show(R.string.delivery_same_shelf_life_info_tips, false);
                    return;
                }
            }
        }
        bean.setCreateDate(selectDate);
        if (index >= 0) {
            notifyItemChanged(index);
        }
        productDateChangeListener.productDateChange(bean);
    }

    private void onItemLocChange(DeliveryShelfLifeInfoBean bean, DeliveryStockTypeBean stockTypeBean) {
        int index = -1;
        for (int i = 0; i < mList.size(); i++) {
            DeliveryShelfLifeInfoBean item = mList.get(i);
            boolean isSame = item.getLocalId() == bean.getLocalId();
            if (isSame) {
                index = i;
            } else {
                //生产时期已选择后才比较是否存在库位和生产时期都相同都
                boolean isContentSameAfterChange =
                        bean.getCreateDate() != null
                                && bean.getCreateDate().equals(item.getCreateDate())
                                && stockTypeBean.getLocId().equals(item.getLocId());
                if (isContentSameAfterChange) {
                    MyToast.show(R.string.delivery_same_shelf_life_info_tips, false);
                    return;
                }
            }
        }
        bean.setLocId(stockTypeBean.getLocId());
        bean.setLocName(stockTypeBean.getLocName());
        bean.setLocType(stockTypeBean.getLocType());
        if (index >= 0) {
            notifyItemChanged(index);
        }
    }

    private void onItemInputQtyChange(BigDecimal newNum, DeliveryShelfLifeInfoBean bean) {
        onItemInputQtyChange(false, newNum, bean);
    }

    private void onItemInputQtyChange(boolean skipNotify, BigDecimal newNum, DeliveryShelfLifeInfoBean bean) {
        if (newNum.compareTo(BigDecimal.ZERO) < 0) {
            MyToast.show(R.string.delivery_input_qty_less_than_zero, false);
            return;
        }
        BigDecimal sum = getSum();
        BigDecimal newSum = sum.subtract(bean.getInputQty()).add(newNum);
        BigDecimal diff = newSum.subtract(limit);
        BigDecimal inputQty;
        boolean forbidSkipNotify = false;
        if (diff.compareTo(BigDecimal.ZERO) > 0) {
            MyToast.show(R.string.delivery_input_qty_more_than_limit, false);
            inputQty = newNum.subtract(diff);
            forbidSkipNotify = true;
        } else {
            inputQty = newNum;
        }
        if (saleMode == SaleModeEnum.PIECE.getValue()) {
            bean.setInputQtyStr(inputQty.toBigInteger().toString());
        } else {
            bean.setInputQtyStr(Formatter.format(inputQty));
        }
        if (forbidSkipNotify || !skipNotify) {
            int index = mList.indexOf(bean);
            if (index >= 0) {
                notifyItemChanged(index);
            }
        }
        onInputQtyChangeListener.checkNumAndChange(bean.getInputQty(), bean);
    }

    /**
     * 子布局DataBinding绑定
     *
     * @author: ext.anxinlong
     * @date: 2021/5/31
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final DetailDialogItemDataBinding mDataBinding;

        public ItemViewHolder(DetailDialogItemDataBinding mDataBinding) {
            super(mDataBinding.getRoot());
            this.mDataBinding = mDataBinding;
            if (saleMode == SaleModeEnum.PIECE.getValue()) {
                mDataBinding.etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                mDataBinding.addImage.setVisibility(View.VISIBLE);
                mDataBinding.ivMinus.setVisibility(View.VISIBLE);
            } else {
                EditTextHelper.INSTANCE.limitDecimalPlaces(3, mDataBinding.etInput);
                mDataBinding.addImage.setVisibility(View.GONE);
                mDataBinding.ivMinus.setVisibility(View.GONE);
            }
        }

        public void bind(DeliveryShelfLifeInfoBean bean) {
            mDataBinding.setBean(bean);
            mDataBinding.setOnAddItemClick(v -> addNewItem(bean));
            mDataBinding.setOnRemoveItemClick(v -> removeItem(bean));
            mDataBinding.setOnPlusClick(v -> onItemInputQtyChange(bean.getInputQty().add(BigDecimal.ONE), bean));
            mDataBinding.setOnMinusClick(v -> onItemInputQtyChange(bean.getInputQty().subtract(BigDecimal.ONE), bean));
            mDataBinding.setAfterTextChange(s -> {
                if (!mDataBinding.etInput.hasFocus()) {
                    return;
                }
                if (TextUtils.isEmpty(s) || ".".equals(s.toString())) {
                    return;
                }
                onItemInputQtyChange(true, new BigDecimal(s.toString()), bean);
            });

            mDataBinding.setOnSelectStockClick(v -> {
                DeliverySelectStockTypeDialog selectStockTypeDialog = new DeliverySelectStockTypeDialog();
                selectStockTypeDialog.setLocId(bean.getLocId());
                selectStockTypeDialog.setOnSelectStockTypeListener(stockTypeBean -> onItemLocChange(bean, stockTypeBean));
                selectStockTypeDialog.show(fragmentManager, "SelectStockType");
            });
            mDataBinding.setOnSelectProductDate(v -> {
                Date createDate = bean.getCreateDate();
                if (createDate == null) {
                    createDate = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createDate);
                new DatePickerDialog(v.getContext(), (view, year, month, dayOfMonth) -> {
                    Date selectDate = Formatter.parseYMDDate(year + "-" + (month + 1) + "-" + dayOfMonth);
                    if (selectDate != null) {
                        onItemProductDateChange(bean, selectDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            });
            mDataBinding.executePendingBindings();
        }
    }

    public interface OnInputQtyChangeListener {
        void checkNumAndChange(BigDecimal newNum, DeliveryShelfLifeInfoBean bean);
    }

    public interface OnProductDateChangeListener {
        void productDateChange(DeliveryShelfLifeInfoBean bean);
    }

    public interface OnAddItemListener {
        void onAddItem();
    }

    public interface DeliveryShelfLifeInfoBeanFactory {
        DeliveryShelfLifeInfoBean create();
    }
}
