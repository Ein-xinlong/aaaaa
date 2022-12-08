package com.jd.saas.pdacommon.webview;

import androidx.databinding.ObservableField;

import com.jd.saas.pdacommon.fragment.NetViewModel;

/**
 * PDA通用网页容器vm
 *
 * @author majiheng
 */
public class PDAWebViewViewModel extends NetViewModel {

    public ObservableField<Boolean> mProgressVisible = new ObservableField<>(true);


}
