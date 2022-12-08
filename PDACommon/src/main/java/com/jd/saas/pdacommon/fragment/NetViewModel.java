package com.jd.saas.pdacommon.fragment;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.jd.saas.pdacommon.net.IBaseView;
import com.jd.saas.pdacommon.net.INetErrorHandler;
import com.jd.saas.pdacommon.net.NetError;

/**
 * 产生网络请求时，使用该vm，无网络时，可直接使用原生ViewModel
 *
 * @author majiheng
 */
public class NetViewModel extends ViewModel implements INetErrorHandler {

    public final ObservableField<Boolean> showProgress;
    public final ObservableField<NetError> netError;

    public NetViewModel() {
        showProgress = new ObservableField<>();
        netError = new ObservableField<>();
    }

    @Override
    public void handleNetError(NetError error) {
        netError.set(error);
    }

    public void handleBaseNetUI(final IBaseView baseView) {

        showProgress.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                baseView.loading(showProgress.get());
            }
        });

        netError.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                baseView.showNetError(netError.get());
            }
        });
    }
}
