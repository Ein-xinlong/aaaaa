package com.jd.saas.pdacommon.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.jd.saas.pdacommon.net.IBaseView;
import com.jd.saas.pdacommon.net.NetCodeConstant;
import com.jd.saas.pdacommon.net.NetError;
import com.jd.saas.pdacommon.toast.MyToast;

/**
 * 全局加载框的封装，和普通view绑定
 *
 * @author majiheng
 */
public class DialogBaseView implements IBaseView {

    private ProgressDialog mProgressDialog;
    private Context mContext;

    public DialogBaseView(Context context, String msg) {
        mContext = context;
        mProgressDialog = new ProgressDialog(context, msg);
    }

    public DialogBaseView(Context context, int msg) {
        this(context, context.getResources().getString(msg));
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener listener) {
        mProgressDialog.setOnCancelListener(listener);
    }

    @Override
    public void loading(boolean show) {
        if (show) {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } else {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void showNetError(NetError error) {
        if (error.getCode() == NetCodeConstant.ERROR_401) {
            // 重新登录
//            MyApp.getInstance().restartLogin();
        } else {
            MyToast.show(error.getMsg(),false);
        }
    }
}
