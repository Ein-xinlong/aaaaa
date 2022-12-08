package com.jd.saas.pdagoodsquery.sale;

import android.content.Context;
import android.text.TextUtils;
import androidx.databinding.ObservableField;

import com.jd.saas.pdacommon.dialog.ProgressDialog;
import com.jd.saas.pdacommon.fragment.NetViewModel;
import com.jd.saas.pdacommon.net.NetObserver;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.user.UserData;
import com.jd.saas.pdacommon.user.UserManager;
import com.jd.saas.pdagoodsquery.R;
import com.jd.saas.pdagoodsquery.sale.adapter.GoodsQuerySaleAdapter;
import com.jd.saas.pdagoodsquery.sale.dialog.GoodsQueryReceiptDialog;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQueryReceipt;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQueryReceiptRequest;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQuerySale;
import com.jd.saas.pdagoodsquery.sale.model.GoodsQuerySaleRequest;
import com.jd.saas.pdagoodsquery.sale.net.GoodsQuerySaleRepository;
import com.jd.saas.pdagoodsquery.sale.utils.DateUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 商品进销存viewmodel
 */
public class GoodsQuerySaleViewModel extends NetViewModel {
    //日期类型(day:日别   week:周别   month:月别)

    public ObservableField<String> radioSelected = new ObservableField<>(GoodsQuerySaleFragment.SALE_DATE_DAY);
    private GoodsQuerySaleAdapter adapter;
    public GoodsQuerySaleAdapter getSaleAdapter(){
        if (adapter == null) {
            adapter = new GoodsQuerySaleAdapter();
        }
        return adapter;
    }

    private final GoodsQuerySaleRepository repository = new GoodsQuerySaleRepository();

    /*
    接口请求-dialog单据明细
     */
    public void getReceiptDetails(Context context, GoodsQuerySale sale) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        HashMap<String,Object> hashMap = new HashMap<>();
        GoodsQueryReceiptRequest data = new GoodsQueryReceiptRequest();
        data.setTenantId(UserManager.getInstance().getUserData().getTenantId());
//        data.setTenantId("100001601");
//        data.setStoreId("10014604");
//        data.setSkuId("10000000015402");
        data.setStoreId(UserManager.getInstance().getUserData().getShopId());
        data.setSkuId(sale.getSkuId());
        String radioValue = radioSelected.get();
        if(radioValue.equals(GoodsQuerySaleFragment.SALE_DATE_DAY)){

            Date dataValue = DateUtil.parseString(sale.getDate());
            data.setStartTime(DateUtil.parseDate(dataValue));
            data.setEndTime(DateUtil.parseDate(dataValue));
        }
        else if (radioValue.equals(GoodsQuerySaleFragment.SALE_DATE_WEEK)){

            String startTime = sale.getDate().split("-")[0];
            String endTime = sale.getDate().split("-")[1];
            if(!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)){
                Date startValue = DateUtil.parseString(startTime);
                Date endValue = DateUtil.parseString(endTime);
                data.setStartTime(DateUtil.parseDate(startValue));
                data.setEndTime(DateUtil.parseDate(endValue));
            }
        } else if (radioValue.equals(GoodsQuerySaleFragment.SALE_DATE_MONTH)){
            String year = sale.getDate().split("-")[0];
            String month = sale.getDate().split("-")[1];
            int yearValue = Integer.parseInt(year);
            int monthValue = Integer.parseInt(month);
            String firstDay = DateUtil.getFirstDayOfMonth(yearValue,monthValue);
            String lastDay = DateUtil.getLastDayOfMonth(yearValue,monthValue);
            data.setStartTime(firstDay);
            data.setEndTime(lastDay);
        }
        hashMap.put("data",data);
        repository.getReceiptDetails(hashMap, new NetObserver<List<GoodsQueryReceipt>>(this) {
            @Override
            protected void onComplete(boolean error) {
                super.onComplete(error);
                progressDialog.dismiss();
            }

            @Override
            protected void onSuccess(List<GoodsQueryReceipt> goodsQueryReceipts) {

                if(goodsQueryReceipts.size()!=0){
                    GoodsQueryReceiptDialog dialog = new GoodsQueryReceiptDialog(context,goodsQueryReceipts);
                    dialog.show();
                } else {
                    MyToast.show(R.string.goods_query_sale_toast_info,false);
                }
            }
        });
    }
    /*
        请求接口-获取销存列表
     */
    public void getStockSale(String skuId,String dateType,String startTime,String endTime){
        HashMap<String,Object> hashMap = new HashMap<>();
        GoodsQuerySaleRequest data = new GoodsQuerySaleRequest();
        UserData userData = UserManager.getInstance().getUserData();
        data.setTenantId(userData.getTenantId());
        data.setStoreId(userData.getShopId());
        data.setSkuId(skuId);
        data.setDateType(dateType);
        data.setStartTime(startTime);
        data.setEndTime(endTime);
        hashMap.put("data",data);
        repository.getStockSale(hashMap, new NetObserver<List<GoodsQuerySale>>(this) {
            @Override
            protected void onSuccess(List<GoodsQuerySale> goodsQuerySales) {
                if(goodsQuerySales.size()!=0)
                getSaleAdapter().refreshSaleList(goodsQuerySales,dateType);
            }
        });
    }
}
