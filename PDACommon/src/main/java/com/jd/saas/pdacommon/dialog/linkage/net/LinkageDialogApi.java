package com.jd.saas.pdacommon.dialog.linkage.net;

import io.reactivex.Observable;
import com.jd.saas.pdacommon.dialog.linkage.model.ParentTansType;
import com.jd.saas.pdacommon.net.BaseResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LinkageDialogApi {

    @POST("/app/api/Inventory/queryStockTransType")
    Observable<BaseResponse<List<ParentTansType>>> getStockTansType(@Body HashMap<String,Object> body);
}
