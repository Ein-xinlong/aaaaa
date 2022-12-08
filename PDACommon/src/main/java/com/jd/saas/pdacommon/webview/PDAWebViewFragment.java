package com.jd.saas.pdacommon.webview;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.databinding.PDAWebViewDataBinding;
import com.jd.saas.pdacommon.fragment.SimpleFragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * PDA通用网页容器fragment
 *
 * @author majiheng
 */
public class PDAWebViewFragment extends SimpleFragment {

    private PDAWebViewDataBinding mDataBinding;
    private PDAWebViewViewModel mViewModel;
    private ValueCallback<Uri[]> mValueCallback;

    public static PDAWebViewFragment newInstance(){
        return new PDAWebViewFragment();
    }

    public PDAWebViewDataBinding getDataBinding() {
        return mDataBinding;
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        mViewModel = new ViewModelProvider(this).get(PDAWebViewViewModel.class);
        mDataBinding.setVm(mViewModel);
        return mDataBinding.getRoot();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 获取网页地址
        Bundle bundle = getArguments();
        PDAWebViewConfigBean bean = (PDAWebViewConfigBean) bundle.get(PDAWebViewRouterPath.WEB_CONFIG);
        String url = bean.getUrl();
        // 初始化WebView
        mDataBinding.web.setHorizontalScrollBarEnabled(true);
        mDataBinding.web.setVerticalScrollBarEnabled(true);
        mDataBinding.web.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings webSettings = mDataBinding.web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAllowFileAccess(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        mDataBinding.web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mViewModel.mProgressVisible.set(newProgress != 100);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                openFileChooseProcess5(filePathCallback,fileChooserParams);
                return true;
            }
        });
        mDataBinding.web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler,
                                           android.net.http.SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        mDataBinding.web.loadUrl(url);
    }

    /**
     * Android5.0及以上的图片上传
     */
    private void openFileChooseProcess5(ValueCallback<Uri[]> filePathCallback,WebChromeClient.FileChooserParams fileChooserParams) {
        mValueCallback = filePathCallback;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (fileChooserParams != null && fileChooserParams.getMode() == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE) {
            //关键在这
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_CANCELED) {
            if (mValueCallback != null) {
                mValueCallback.onReceiveValue(null);
                mValueCallback = null;
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (mValueCallback != null) {
                        //处理相关数据
                        onActivityResultAboveL(data);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 选中图片并传给js
     */
    private void onActivityResultAboveL(Intent intent) {
        Uri[] results = null;
        if (intent != null) {
            String dataString = intent.getDataString();
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                results = new Uri[clipData.getItemCount()];
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    results[i] = item.getUri();
                }
            }
            if (dataString != null) {
                results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mValueCallback.onReceiveValue(results);
        mValueCallback = null;
    }

    @Override
    protected void reload() {
        // do nothing
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_pda_web_view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataBinding.web.destroy();
    }
}
