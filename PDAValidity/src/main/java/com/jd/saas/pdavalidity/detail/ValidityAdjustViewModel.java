package com.jd.saas.pdavalidity.detail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import androidx.databinding.ObservableField;

import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.date.DateUtils;
import com.jd.saas.pdacommon.eventbus.EventBean;
import com.jd.saas.pdacommon.eventbus.EventBusManager;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.search.SearchGoodBean;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdavalidity.R;
import com.jd.saas.pdavalidity.detail.bean.ValidityAdjustDateRequestBean;
import com.jd.saas.pdavalidity.detail.bean.ValidityAdjustDateResponseBean;
import com.jd.saas.pdavalidity.detail.bean.ValidityAdjustModifyRequestBean;
import com.jd.saas.pdavalidity.net.ValidityRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 效期调整vm
 *
 * @author majiheng
 */
public class ValidityAdjustViewModel extends NetViewModel {

    // 网络
    private final ValidityRepository mValidityRepository = new ValidityRepository();

    public ObservableField<SearchGoodBean> mDetailBean = new ObservableField<>();// 商品详情
    public ObservableField<Integer> mNum = new ObservableField<>(0);// 库存总数量展示用-整型
    public ObservableField<String> mNumFloat = new ObservableField<>();// 库存总数量展示用-有小数点后三位
    public ObservableField<Integer> mNumModify = new ObservableField<>(0);// 库存总数量用户设置变化
    public ObservableField<String> mNumModifyFloat = new ObservableField<>();// 库存总数量用户设置变化-有小数点后三位
    public ObservableField<Integer> mIcon = new ObservableField<>(0);// 商品状态

    // 日期格式
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // 生产日期
    public ObservableField<String> mBornTime = new ObservableField<>();
    // 预警日期
    public ObservableField<String> mWarnTime = new ObservableField<>();
    // 提示日期
    public ObservableField<String> mNoticeTime = new ObservableField<>();
    // 失效日期
    public ObservableField<String> mUselessTime = new ObservableField<>();
    // 商品销售方式：1-计件；2-称重
    public ObservableField<Integer> mSaleMode = new ObservableField<>(1);

    /**
     * 刷新ui
     */
    public void refreshUI(SearchGoodBean bean) {
        if(null != bean) {
            // 获取商品类型
            mSaleMode.set(bean.getSaleMode());
            // 设置全局bean
            mDetailBean.set(bean);
            // 商品类型
            if(mSaleMode.get() == 1) {
                // 计件品
                mNum.set(Integer.parseInt(bean.getQty()));
            }else {
                // 称重品
                mNumFloat.set(bean.getQty());
            }
            // 日期
            mBornTime.set(bean.getProduceDate());
            mWarnTime.set(bean.getWarnDate());
            mNoticeTime.set(bean.getHintDate());
            mUselessTime.set(bean.getExpireDate());
            // 设置商品状态icon
            if(bean.getPeriodState() == 1) {
                // 正常
                mIcon.set(R.drawable.validity_adjustment_list_item_normal);
            }else if(bean.getPeriodState() == 2) {
                // 提示
                mIcon.set(R.drawable.validity_adjustment_list_item_tip);
            }else if(bean.getPeriodState() == 3) {
                // 预警
                mIcon.set(R.drawable.validity_adjustment_list_item_warning);
            }else if(bean.getPeriodState() == 4) {
                // 失效
                mIcon.set(R.drawable.validity_adjustment_list_item_invalidation);
            }
        }
    }

    /**
     * 显示日历控件
     */
    public void dataWidget(View view) {
        // 如果生产日期不为空
        if(!TextUtils.isEmpty(mBornTime.get())) {
            // 弹出系统日历控件
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String selectedDataStr = year + "-" + (month + 1) + "-" + dayOfMonth;
                    // 选中的data
                    Date selectedDate = null;
                    // 生产的data
                    Date bornDate = null;
                    // 预警的data
                    Date warnDate = null;
                    // 提示的data
                    Date noticeDate = null;
                    // 失效的data
                    Date uselessDate = null;
                    try {
                        selectedDate = mDateFormat.parse(selectedDataStr);
                        bornDate = mDateFormat.parse(mBornTime.get());
                        warnDate = mDateFormat.parse(mWarnTime.get());
                        noticeDate = mDateFormat.parse(mNoticeTime.get());
                        uselessDate = mDateFormat.parse(mUselessTime.get());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // 选择好的日期的long类型
                    long selectedDataLong = selectedDate.getTime();
                    String selectedData = DateUtils.format(selectedDataLong,DateUtils.FORMAT_SHORT);;
                    mBornTime.set(selectedData);
                    // 生产日期的long类型
                    long bornDataLong = bornDate.getTime();
                    // 两者差值多少
                    long modifyLong = selectedDataLong - bornDataLong;
                    // 预警日期的long类型-修改后
                    long warnDataLongModify = warnDate.getTime() + modifyLong;
                    String warDataStrModify = DateUtils.format(warnDataLongModify,DateUtils.FORMAT_SHORT);
//                    mWarnTime.set(warDataStrModify);
                    // 提醒日期的long类型-修改后
                    long noticeDataLongModify = noticeDate.getTime() + modifyLong;
                    String noticeDataStrModify = DateUtils.format(noticeDataLongModify,DateUtils.FORMAT_SHORT);
//                    mNoticeTime.set(noticeDataStrModify);
                    // 失效日期的long类型-修改后
                    long uselessDataLongModify = uselessDate.getTime() + modifyLong;
                    String uselessDataStrModify = DateUtils.format(uselessDataLongModify,DateUtils.FORMAT_SHORT);
                    mUselessTime.set(uselessDataStrModify);

                    // 请求获取效期日期
                    showProgress.set(true);
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
                    hashMap.put("pin",UserManager.getInstance().getUserData().getUserPin());
                    ValidityAdjustDateRequestBean bean = new ValidityAdjustDateRequestBean();
                    bean.setProduceDate(DateUtils.parse(selectedDataStr,DateUtils.FORMAT_SHORT).getTime() + "");
                    bean.setSkuId(mDetailBean.get().getSkuId());
                    bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
                    hashMap.put("data",bean);
                    mValidityRepository.getDate(hashMap, new NetObserver<ValidityAdjustDateResponseBean>(ValidityAdjustViewModel.this) {

                        @Override
                        protected void onComplete(boolean error) {
                            showProgress.set(false);
                        }

                        @Override
                        protected void onSuccess(ValidityAdjustDateResponseBean bean) {
                            if(null != bean) {
                                // 刷新预警&提示时间ui
                                mWarnTime.set(bean.getWarnDate());
                                mNoticeTime.set(bean.getHintDate());
                            }else {
                                MyToast.show(R.string.validity_adjustment_get_date_err,false);
                            }
                        }
                    });
                }
            }, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }else {
            MyToast.show(view.getContext().getString(R.string.validity_adjust_born_time_null),false);
        }
    }

    /**
     * 加
     */
    public void plus() {
        if(mNumModify.get() < mNum.get()) {
            mNumModify.set(mNumModify.get() + 1);
        }
    }

    /**
     * 减
     */
    public void reduce() {
        if(mNumModify.get() > 0) {
            mNumModify.set(mNumModify.get() - 1);
        }
    }

    /**
     * 更新本地库存数量
     */
    public void updateStockNum(Editable editable) {
        if(!TextUtils.isEmpty(editable.toString())) {
            if(mSaleMode.get() == 1) {
                try {
                    mNumModify.set(Integer.parseInt(editable.toString()));
                }catch (Exception e) {
                    mNumModify.set(0);
                    MyToast.show(R.string.validity_max_value_err,false);
                }
            }else {
                mNumModifyFloat.set(editable.toString());
            }
        }
    }

    /**
     * 确认提交
     */
    public void confirm(Context context) {
        if(!TextUtils.isEmpty(mBornTime.get())) {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
            hashMap.put("pin",UserManager.getInstance().getUserData().getUserPin());
            ValidityAdjustModifyRequestBean bean = new ValidityAdjustModifyRequestBean();
            bean.setPin(UserManager.getInstance().getUserData().getUserPin());
            bean.setStoreId(UserManager.getInstance().getUserData().getShopId());
            bean.setSkuId(mDetailBean.get().getSkuId());
            bean.setUpcCode(mDetailBean.get().getUpcCode());
            // 日期提交
            bean.setProduceDate(mBornTime.get());
            bean.setExpireDate(mUselessTime.get());
            // 调整的批次号/库位
            bean.setLotNo(mDetailBean.get().getLotId());
            bean.setLocNo(mDetailBean.get().getLocId());
            // 调整数量提交
            if(mSaleMode.get() == 1) {
                // 计件
                if(mNumModify.get() > mNum.get()) {
                    MyToast.show(R.string.validity_adjustment_commit_err,false);
                    return;
                }else {
                    bean.setAdjustQty(mNumModify.get() + "");
                }
            }else {
                // 称重
                BigDecimal b1 = new BigDecimal(mNumModifyFloat.get());
                BigDecimal b2 = new BigDecimal(mNumFloat.get());
                if(b1.doubleValue() > b2.doubleValue()) {
                    MyToast.show(R.string.validity_adjustment_commit_err,false);
                    return;
                }else {
                    bean.setAdjustQty(mNumModifyFloat.get());
                }
            }
            // 供应商编号
            bean.setSupplierCode(mDetailBean.get().getSupplierCode());
            hashMap.put("data",bean);
            showProgress.set(true);
            mValidityRepository.commit(hashMap, new BaseObserver<Boolean>() {

                @Override
                protected void onSuccess(Boolean commit) {
                    // 延时2秒-无奈之举，后端es不是db
                    new CountDownTimer(2000,1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            showProgress.set(false);
                            // 提示成功
                            MyToast.show(context.getString(R.string.validity_adjust_confirm_success),false);
                            // 通知商品详情页面刷新列表
                            EventBean eventBean = new EventBean();
                            eventBean.setType(EventBean.EVENT_REFRESH_LIST);
                            EventBusManager.post(eventBean);
                            // 关闭当前页面
                            AppManager.getInstance().finishActivity();
                        }
                    }.start();
                }

                @Override
                protected void onError(NetError error) {
                    showProgress.set(false);
                    MyToast.show(error.getMsg(),false);
                }
            });
        }else {
            MyToast.show(context.getString(R.string.validity_adjust_born_time_null),false);
        }
    }
}
