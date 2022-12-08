package com.jd.saas.pdacommon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.jd.saas.pdacommon.R;
import com.jd.saas.pdacommon.toolbar.NormalTitleBar;

/**
 * 基础Activity，用来适配数据绑定
 *
 * @author majiheng
 */
public abstract class SimpleActivity extends BaseActivity{

    protected Fragment mContentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContentFragment = findOrCreateViewFragment();
        init();
    }

    protected abstract void init();

    /**
     * 设置标题栏（int类型）
     */
    protected NormalTitleBar setSimpleTitleBar(int title) {
        return setSimpleTitleBar(title, null);
    }

    /**
     * 设置标题栏（int类型）& 返回操作
     */
    protected NormalTitleBar setSimpleTitleBar(int title, View.OnClickListener backListener) {
        NormalTitleBar normalTitleBar = findViewById(R.id.tb_toolbar);
        normalTitleBar.setTitle(title);
        if (backListener != null) {
            normalTitleBar.setOnBackClickListener(backListener);
        } else {
            normalTitleBar.setOnBackClickListener(view -> {
                if (SimpleActivity.this != null && !SimpleActivity.this.isDestroyed()) {
                    onBackPressed();
                }
            });
        }
        return normalTitleBar;
    }

    /**
     * 设置标题栏（string类型）
     */
    protected NormalTitleBar setSimpleTitleBar(String title) {
        return setSimpleTitleBar(title, null);
    }

    /**
     * 设置标题栏（string类型）& 返回操作
     */
    protected NormalTitleBar setSimpleTitleBar(String title, View.OnClickListener backListener) {
        NormalTitleBar normalTitleBar = findViewById(R.id.tb_toolbar);
        normalTitleBar.setTitle(title);
        if (backListener != null) {
            normalTitleBar.setOnBackClickListener(backListener);
        } else {
            normalTitleBar.setOnBackClickListener(view -> {
                if (SimpleActivity.this != null && !SimpleActivity.this.isDestroyed()) {
                    onBackPressed();
                }
            });
        }
        return normalTitleBar;
    }

    protected abstract Fragment maybeCreateFragment();

    protected Fragment findOrCreateViewFragment() {
        Fragment contentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (null == contentFragment) {
            contentFragment = maybeCreateFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_content, contentFragment);
            transaction.commit();
        }
        return contentFragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mContentFragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        mContentFragment.onActivityResult(requestCode,resultCode,data);
    }
}
