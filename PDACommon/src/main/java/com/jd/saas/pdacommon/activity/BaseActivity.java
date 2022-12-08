package com.jd.saas.pdacommon.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jd.saas.pdacommon.log.Logger;

/**
 * 基础Activity
 *
 * @author majiheng
 */
public class BaseActivity extends AppCompatActivity {

    private final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        Logger.d(TAG,"##onCreate##");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(TAG,"##onStart##");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(TAG,"##onResume##");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(TAG,"##onPause##");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG,"##onStop##");
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG,"##onDestroy##");
        AppManager.getInstance().finishActivity(this);
        super.onDestroy();
    }
}
