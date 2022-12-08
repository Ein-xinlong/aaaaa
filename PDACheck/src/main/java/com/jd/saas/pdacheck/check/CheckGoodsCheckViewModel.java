package com.jd.saas.pdacheck.check;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.jd.saas.pdacheck.CheckRouterPath;
import com.jd.saas.pdacheck.R;
import com.jd.saas.pdacheck.check.adapter.CheckBoxProductsAdapter;
import com.jd.saas.pdacheck.check.bean.CheckGoodDetailRequestBean;
import com.jd.saas.pdacheck.check.bean.CheckGoodResultRequestBean;
import com.jd.saas.pdacheck.list.model.CheckListBean;
import com.jd.saas.pdacheck.net.CheckCommitRequestBean;
import com.jd.saas.pdacheck.net.CheckDetailConNoRequestBean;
import com.jd.saas.pdacheck.net.CheckListRepository;
import com.jd.saas.pdacommon.activity.AppManager;
import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.loctype.LocTypeUtils;
import com.jd.saas.pdacommon.loctype.SkuNatureEnum;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.router.RouterClient;
import com.jd.saas.pdacommon.search.SearchResultBean;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdacommon.zxing.router.ZxingRouterPath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品盘点VM
 *
 * @author majiheng
 */
public class CheckGoodsCheckViewModel extends NetViewModel {

    // 网络
    private final CheckListRepository mRepository = new CheckListRepository();
    // 从列表传过来的任务详情bean
    public ObservableField<CheckListBean> mDetail = new ObservableField<>();
    // 商品详情是否显示
    public ObservableField<Integer> mGoodVisible = new ObservableField<>(View.GONE);
    // 显示悬浮加载框
    public MutableLiveData<Boolean> mShowProgress = new MutableLiveData<>(false);
    // 组合商品列表是否显示
    public ObservableField<Integer> mSonGoodVisible = new ObservableField<>(View.GONE);
    // 预盘点单号
    public ObservableField<String> mConNo = new ObservableField<>();
    // 商品logo
    public ObservableField<String> mSkuLogo = new ObservableField<>();
    // 商品名称
    public ObservableField<String> mSkuName = new ObservableField<>();
    // 商品条码
    public ObservableField<String> mSkuCode = new ObservableField<>();
    // 商品单位/盒/个
    public ObservableField<String> mSkuUnit = new ObservableField<>();
    // 库存数量
    public ObservableField<String> mStockNum = new ObservableField<>("0");
    // 商品销售方式：1-计件；2-称重
    public ObservableField<Integer> mSaleMode = new ObservableField<>(1);
    // 好坏品，0：好 1：坏 默认0
    private String mStorageType = "TOP";
    public ObservableField<String> mStorageName = new ObservableField<>(MyApplication.getInstance().getApplicationContext().getString(R.string.check_good));
    private String[] storageName;
    private String[] storageId;
    // 库存总数量展示用-整型
    public ObservableField<Integer> mNum = new ObservableField<>(0);
    // 库存总数量展示用-有小数点后三位
    public ObservableField<String> mNumFloat = new ObservableField<>("0");
    // 当前扫出来的商品
    private SearchResultBean mCurrentGood;
    // 需要搜索的字段
    private String mSearchContent;
    // 用来展示搜索框的内容
    public ObservableField<String> mEditContent = new ObservableField<>("");
    // 扫描code
    public final int SCREEN_REQUEST_CODE = 233;
    // 需要提交的盘点列表-共用
    public static List<CheckCommitRequestBean.PdaStockTakeInfoDetailBO> mCheckedListForCommon = new ArrayList<>();
    // 箱柜商品列表adapter
    private CheckBoxProductsAdapter mBoxProductsAdapter;
    // 显示存在盘点记录时退出弹窗
    public MutableLiveData<Boolean> mShowExitDialog = new MutableLiveData<>(false);

    /**
     * 刷新ui
     */
    public void refreshUI(CheckListBean bean) {
        mDetail.set(bean);
    }

    /**
     * 扫描
     */
    public void screen(Context context) {
        RouterClient.getInstance().forward(context, ZxingRouterPath.PATH_ZXING, new Bundle(), SCREEN_REQUEST_CODE);
    }

    /**
     * 更换库位
     */
    public void storageClick(Context context) {
        AlertDialog.Builder storageDialog = new AlertDialog.Builder(context);
        storageDialog.setItems(storageName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                // 刷新「库位」列表
                mStorageType = storageId[position];
                mStorageName.set(storageName[position]);
            }
        });
        storageDialog.show();
    }

    /**
     * 实时更新库存数量
     */
    public void updateStorageNum(Editable editable) {
        if(!TextUtils.isEmpty(editable.toString())) {
            if(mSaleMode.get() == 1) {
                // 计件品
                try {
                    mNum.set(Integer.parseInt(editable.toString()));
                }catch (Exception e) {
                    mNum.set(0);
                    MyToast.show(R.string.check_max_value_err,false);
                }
                // 更新子品实盘数量（如果是组合商品）
                getBoxProductAdapter().setCheckNumber(mNum.get() + "");
            }else {
                // 称重品
                if(editable.toString().equals(".")) {
                    // 如果输入的只是个"."，那就设置为0
                    mNumFloat.set("0");
                    mNum.notifyChange();
                }else {
                    mNumFloat.set(editable.toString());
                }
                // 更新子品实盘数量（如果是组合商品）
                getBoxProductAdapter().setCheckNumber(mNumFloat.get());
            }
        }else {
            // 如果用户手动删除了内容，重置为0，并通知ui更新
            mNum.set(0);
            mNumFloat.set("0");
            mNum.notifyChange();
            mNumFloat.notifyChange();
        }
    }

    /**
     * 加
     */
    public void plus() {
        if(mNum.get() < Integer.MAX_VALUE) {
            mNum.set(mNum.get() + 1);
        }
    }

    /**
     * 减
     */
    public void reduce() {
        if(mNum.get() > 0) {
            mNum.set(mNum.get() - 1);
        }
    }


    /**
     * 获取箱柜列表适配器
     */
    public CheckBoxProductsAdapter getBoxProductAdapter() {
        if(null == mBoxProductsAdapter) {
            mBoxProductsAdapter = new CheckBoxProductsAdapter();
        }
        return mBoxProductsAdapter;
    }

    /**
     * 获取预盘点单号
     */
    public void getConNo() {
        showProgress.set(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId", userData.getTenantId());
        hashMap.put("pin", userData.getUserPin());
        CheckDetailConNoRequestBean bean = new CheckDetailConNoRequestBean();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setSkuType(mDetail.get().getSkuType());
        hashMap.put("data",bean);
        mRepository.getCouNo(hashMap, new NetObserver<String>(this) {

            @Override
            protected void onComplete(boolean error) {
                showProgress.set(false);
            }

            @Override
            protected void onSuccess(String couNo) {
                mConNo.set(couNo);
                // 同时设置到当前页面的detail bean中，方便传值到下一个页面
                mDetail.get().setConNo(couNo);
            }
        });
    }

    /**
     * et每次输入字符都调用该方法
     */
    public void updateSearchContent(Editable editable) {
        mSearchContent = editable.toString();
    }

    /**
     * 通过扫描获取商品详情
     */
    public void getGoodFromScreen(Context context,String upcCode) {
        // 设置到EditText中的文字
        mEditContent.set(upcCode);
        // 搜索对应扫描出的商品
        mSearchContent = upcCode;
        getGood(context);
    }

    /**
     * 获取商品信息
     */
    public void getGood(Context context) {
        // 先隐藏商品展示ui并且清空数据
        removeCurrentGoods();
        mShowProgress.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId", userData.getTenantId());
        hashMap.put("pin", userData.getUserPin());
        CheckGoodDetailRequestBean bean = new CheckGoodDetailRequestBean();
        bean.setCondition(mSearchContent);
        bean.setPin(userData.getUserPin());
        bean.setStoreId(userData.getShopId());
        hashMap.put("data",bean);
        mRepository.getGoodsDetail(hashMap, new BaseObserver<SearchResultBean>() {

            @Override
            protected void onComplete(boolean error) {
                mShowProgress.setValue(false);
            }

            @Override
            protected void onSuccess(SearchResultBean searchResultBean) {
                if(null != searchResultBean && null == searchResultBean.getData()) {
                    // 判断是否是组合商品
                    if(null != searchResultBean.getBoxProducts() && searchResultBean.getBoxProducts().size() != 0) {
                        // 组合商品（无库位信息，需要手动写固定两个库位，TOP和DM）
                        storageName = new String[]{context.getString(R.string.check_good_loc),context.getString(R.string.check_bad_loc)};
                        storageId = new String[]{"TOP","DM"};
                        mStorageName.set(storageName[0]);
                        mStorageType = storageId[0];
                        // 赋值给全局当前商品
                        mCurrentGood = searchResultBean;
                        // 显示商品详情ui
                        mGoodVisible.set(View.VISIBLE);
                        // 设置商品信息
                        mSkuLogo.set(searchResultBean.getLogo());
                        mSkuName.set(searchResultBean.getSkuName());
                        mSkuCode.set(searchResultBean.getUpcCode());
                        mSkuUnit.set(searchResultBean.getSaleUnitName());
                        mStockNum.set(searchResultBean.getTotalQty());
                        mSaleMode.set(searchResultBean.getSaleMode());
                        if(mSaleMode.get() == 1) {
                            // 计件品
                            mNum.set(0);
                        }else {
                            // 称重品
                            mNumFloat.set("0");
                        }
                        // 设置箱柜列表信息（假如有）
                        getBoxProductAdapter().refreshList(searchResultBean.getBoxProducts());
                        // 显示组合商品列表
                        mSonGoodVisible.set(View.VISIBLE);
                    }else {
                        // 单品（可有可无库位信息）
                        // 判断是否有库位信息
                        if(null != searchResultBean.getLocQtyList() && searchResultBean.getLocQtyList().size() != 0) {
                            // 设置库位信息
                            storageName = new String[searchResultBean.getLocQtyList().size()];
                            storageId = new String[searchResultBean.getLocQtyList().size()];
                            for (int i = 0; i < searchResultBean.getLocQtyList().size(); i++) {
                                storageName[i] = searchResultBean.getLocQtyList().get(i).getLocName();
                                storageId[i] = searchResultBean.getLocQtyList().get(i).getLocType();
                            }
                            mStorageName.set(searchResultBean.getLocQtyList().get(0).getLocName());
                            mStorageType = searchResultBean.getLocQtyList().get(0).getLocType();
                            // 赋值给全局当前商品
                            mCurrentGood = searchResultBean;
                            // 显示商品详情ui
                            mGoodVisible.set(View.VISIBLE);
                            // 设置商品信息
                            mSkuLogo.set(searchResultBean.getLogo());
                            mSkuName.set(searchResultBean.getSkuName());
                            mSkuCode.set(searchResultBean.getUpcCode());
                            mSkuUnit.set(searchResultBean.getSaleUnitName());
                            mStockNum.set(searchResultBean.getTotalQty());
                            mSaleMode.set(searchResultBean.getSaleMode());
                            if(mSaleMode.get() == 1) {
                                // 计件品
                                mNum.set(0);
                            }else {
                                // 称重品
                                mNumFloat.set("0");
                            }
                        }else {
                            MyToast.show(R.string.check_storage_no_err,false);
                        }
                        // 隐藏组合商品列表
                        mSonGoodVisible.set(View.GONE);
                    }
                }else {
                    MyToast.show(R.string.check_searching_err,false);
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);
            }
        });
    }

    /**
     * 清楚当前商品信息
     */
    private void removeCurrentGoods() {
        // 清空子品列表
        getBoxProductAdapter().clear();
        mSonGoodVisible.set(View.GONE);
        // 清空搜索出的商品信息
        mGoodVisible.set(View.GONE);
        mSkuLogo.set("");
        mSkuName.set("");
        mSkuCode.set("");
        mSkuUnit.set("");
        mStockNum.set("0");
        mSaleMode.set(1);
        mNum.set(0);
        mNumFloat.set("0");
        mCurrentGood = null;
    }

    /**
     * 校验当前商品
     */
    public void getCheckResult() {
        mShowProgress.setValue(true);
        HashMap<String, Object> hashMap = new HashMap<>();
        UserData userData = UserManager.getInstance().getUserData();
        hashMap.put("tenantId",userData.getTenantId());
        hashMap.put("pin",userData.getUserPin());
        CheckGoodResultRequestBean bean = new CheckGoodResultRequestBean();
        bean.setTenantId(userData.getTenantId());
        bean.setWhId(userData.getShopId());
        bean.setTaskNo(mDetail.get().getTaskNo());
        List<String> skuIds = new ArrayList<>();
        // 判断是否是组合商品
        if(null != mCurrentGood.getBoxProducts() && mCurrentGood.getBoxProducts().size() != 0) {
            // 组合商品-只校验子品skuid
            for(int i=0;i<mCurrentGood.getBoxProducts().size();i++) {
                skuIds.add(mCurrentGood.getBoxProducts().get(i).getSkuId());
            }
        }else {
            // 普通商品-校验该商品skuid
            skuIds.add(mCurrentGood.getSkuId());
        }
        bean.setSkuIds(skuIds);
        bean.setLocType(mStorageType);
        hashMap.put("data",bean);
        mRepository.getCheckResult(hashMap, new BaseObserver<Map<String,String>>() {

            @Override
            protected void onComplete(boolean error) {
                mShowProgress.setValue(false);
            }

            @Override
            protected void onSuccess(Map<String,String> locCodeMap) {
                if(locCodeMap == null || locCodeMap.isEmpty()) {
                    MyToast.show(R.string.check_not_in_err,false);
                }else {
                    if(null == mCurrentGood) {
                        // 补丁：如果由于异步造成的mCurrentGood为空，return掉
                        return;
                    }
                    // 添加当前商品到全局list中
                    CheckCommitRequestBean.PdaStockTakeInfoDetailBO fatherBean = new CheckCommitRequestBean.PdaStockTakeInfoDetailBO();
                    // 设置需要提交的元素
                    // 先设置父商品需要提交的元素
                    fatherBean.setSkuType(mDetail.get().getSkuType());
                    fatherBean.setLocType(mStorageType);
                    fatherBean.setActualQty(mSaleMode.get() == 1 ? mNum.get() + "" : mNumFloat.get());
                    fatherBean.setSkuId(mCurrentGood.getSkuId());
                    fatherBean.setTaskNo(mDetail.get().getTaskNo());
                    fatherBean.setLocCode(locCodeMap.get(mCurrentGood.getSkuId()));
                    // 再设置子商品需要提交的元素（如果是组合商品，将来如果需要把拆单过程放到网关，下面这坨代码就可以删掉！拆单本来就应该在网关！）
                    if(null != mCurrentGood.getBoxProducts() && mCurrentGood.getBoxProducts().size() != 0) {
                        List<SearchResultBean.BoxProducts> boxProducts = mCurrentGood.getBoxProducts();
                        for(SearchResultBean.BoxProducts boxProduct : boxProducts) {
                            // 子商品的bean和组合商品的bean一样，所以用同一个
                            CheckCommitRequestBean.PdaStockTakeInfoDetailBO sonBean = new CheckCommitRequestBean.PdaStockTakeInfoDetailBO();
                            // 设置skuType
                            sonBean.setSkuType(boxProduct.getSkuType());
                            // 设置子商品库位信息-重点
                            if(SkuNatureEnum.GOOD.getValue() == LocTypeUtils.getSkuNatureByLocType(mStorageType)) {
                                // 好品库位-设置所有的子商品都为好品库位
                                sonBean.setLocType(LocTypeUtils.getGoodStockType(Integer.parseInt(boxProduct.getSkuType())));
                            }else {
                                // 坏品库位-设置所有的子商品都为坏品库位
                                sonBean.setLocType(LocTypeUtils.getBadStockType(Integer.parseInt(boxProduct.getSkuType())));
                            }
                            // 设置数量信息-重点
                            if(mSaleMode.get() == 1) {
                                // 计件商品的实际盘点数量
                                BigDecimal mNumBigDecimal = new BigDecimal(mNum.get());
                                BigDecimal mBoxNumBigDecimal = new BigDecimal(boxProduct.getBoxNum());
                                sonBean.setActualQty(mNumBigDecimal.multiply(mBoxNumBigDecimal).toString());
                            }else {
                                // 称重商品的实际盘点数量
                                BigDecimal mNumFloatBigDecimal = new BigDecimal(mNumFloat.get());
                                BigDecimal mBoxNumBigDecimal = new BigDecimal(boxProduct.getBoxNum());
                                sonBean.setActualQty(mNumFloatBigDecimal.multiply(mBoxNumBigDecimal).toString());
                            }
                            sonBean.setSkuId(boxProduct.getSkuId());
                            sonBean.setTaskNo(mDetail.get().getTaskNo());
                            sonBean.setLocCode(locCodeMap.get(boxProduct.getSkuId()));
                            // 添加到父商品中的子商品列表中
                            fatherBean.getBoxProducts().add(sonBean);
                        }
                    }
                    // ui元素
                    fatherBean.setIcon(mSkuLogo.get());
                    fatherBean.setName(mSkuName.get());
                    fatherBean.setUpcCode(mSkuCode.get());
                    fatherBean.setUnitName(mSkuUnit.get());
                    fatherBean.setLocTypeName(mStorageName.get());
                    mCheckedListForCommon.add(fatherBean);
                    // 清除商品ui和信息
                    removeCurrentGoods();
                    // 提示用户
                    MyToast.show(R.string.check_next_complete,false);
                }
            }

            @Override
            protected void onError(NetError error) {
                MyToast.show(error.getMsg(),false);
            }
        });
    }

    /**
     * 点击下一个
     */
    public void next() {
        if(null != mCurrentGood) {
            if(mCheckedListForCommon.size() == 200) {
                MyToast.show(R.string.check_next_max,false);
            }else {
                // 校验当前商品
                getCheckResult();
            }
        }else {
            MyToast.show(R.string.check_next_err,false);
        }
    }

    /**
     * 跳转到「盘点记录」页面
     */
    public void checkRecordGo() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",mDetail.get());
        RouterClient.getInstance().forward(MyApplication.getInstance().getApplicationContext(), CheckRouterPath.RECORD_PATH_MAIN,bundle);
    }

    /**
     * 页面关闭时判断是否有本地已经盘点的商品
     */
    public void exitJudge() {
        if(null != mCheckedListForCommon && mCheckedListForCommon.size() != 0) {
            mShowExitDialog.setValue(true);
        }else {
            AppManager.getInstance().finishActivity(CheckGoodsCheckActivity.class);
        }
    }

    /**
     * 关闭当前页面时的操作
     */
    public void exit() {
        mCheckedListForCommon.clear();
        AppManager.getInstance().finishActivity(CheckGoodsCheckActivity.class);
    }
}
