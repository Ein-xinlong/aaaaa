package com.jd.saas.pdapersonal.proxy;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.jd.saas.pdacommon.activity.BaseActivity;
import com.jd.saas.pdacommon.keyboard.SoftInputUtil;
import com.jd.saas.pdacommon.router.RouterBasePath;
import com.jd.saas.pdacommon.sp.SPUtils;
import com.jd.saas.pdacommon.toast.MyToast;
import com.jd.saas.pdacommon.toolbar.NormalTitleBar;
import com.jd.saas.pdapersonal.R;
import com.jd.saas.pdapersonal.router.PersonalRouterPath;
import com.jingdong.amon.router.annotation.JDRouteUri;

/**
 * 个人中心设置Flutter网路代理的一个调试页面
 *
 * @author majiheng
 */
@JDRouteUri(scheme = RouterBasePath.SCHAME, host = PersonalRouterPath.HOST_PERSONAL, path = PersonalRouterPath.FLUTTER_PROXY_ACTIVITY_PATH)
public class PersonalFlutterProxyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_personal_proxy);
        // init tool bar
        NormalTitleBar titleBar = findViewById(R.id.flutter_proxy_toolbar);
        titleBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitle(R.string.personal_tv_flutter_proxy);
        // init EditText
        AppCompatEditText editText = findViewById(R.id.tv_flutter_proxy_input);
        // init save btn
        AppCompatTextView btnSave = findViewById(R.id.tv_flutter_proxy_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proxy = editText.getText().toString().trim();
                if(TextUtils.isEmpty(proxy)) {
                    // 恢复默认地址
                    MyToast.show(R.string.personal_flutter_proxy_btn_clear_success,false);
                }else {
                    // 设置代理地址
                    MyToast.show(R.string.personal_flutter_proxy_btn_save_success,false);
                }
                SPUtils.saveString("FlutterProxy",proxy);
                // 隐藏键盘
                SoftInputUtil.hideSoftInput(editText,PersonalFlutterProxyActivity.this);
            }
        });
    }
}