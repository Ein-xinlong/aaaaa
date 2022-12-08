package com.jd.saas.pdacommon.dialog.linkage.net;

import com.jd.saas.pdacommon.apptype.AppTypeUtil;
import com.jd.saas.pdacommon.dialog.linkage.model.ClientInfoBean;
import com.jd.saas.pdacommon.dialog.linkage.model.ParentTansType;
import com.jd.saas.pdacommon.net.ApiMgr;
import com.jd.saas.pdacommon.net.BaseObserver;
import com.jd.saas.pdacommon.net.BaseRepository;
import com.jd.saas.pdacommon.user.UserManager;

import java.util.HashMap;
import java.util.List;

public class LinkageDialogRepository extends BaseRepository {

    LinkageDialogApi api = ApiMgr.getApi(LinkageDialogApi.class);

    public void getStockTansType(BaseObserver<List<ParentTansType>> observer) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tenantId", UserManager.getInstance().getUserData().getTenantId());
        hashMap.put("pin", UserManager.getInstance().getUserData().getUserPin());
        ClientInfoBean clientInfo = new ClientInfoBean();
        clientInfo.setBizCode(AppTypeUtil.getAppType());
        hashMap.put("clientInfo", clientInfo);
        request(api.getStockTansType(hashMap), observer);
    }
}
