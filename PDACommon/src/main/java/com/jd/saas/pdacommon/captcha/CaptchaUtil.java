package com.jd.saas.pdacommon.captcha;

import android.content.Context;
import android.text.TextUtils;

import com.jdjr.captcha.IJdjrCaptchaCallback;
import com.jdjr.captcha.dialog.JdjrCaptchaDialog;
import com.jdjr.captcha.views.JdjrCaptcha;

/**
 * 京东数科验证码滑块工具类
 *
 * @author majiheng
 */
public class CaptchaUtil {

    public static void go(Context context, OnCaptchaResultCallback callback) {

        //创建验证码回调监视器
        IJdjrCaptchaCallback captchaCallback = new IJdjrCaptchaCallback() {

            /**
             * @param verifyResult 验证是否成功
             * @param validate 验证成功（服务器返回的校验码，需要上传这个校验码到服务器进行校验, 服务器接入文档：滑块验证[人机识别、防刷]），验证失败（空）
             * @param code 错误码
             * */

            @Override
            public void verifyComplete(boolean verifyResult, String validate, int code) {
                if(verifyResult){
                    if(!TextUtils.isEmpty(validate)) {
                        callback.onCaptchaResult(validate);
                    }
                }
            }
        };

        //创建对话框形式的验证码
        JdjrCaptchaDialog scd = new JdjrCaptchaDialog.Builder()
                // 设置使用场景，请联系sdk提供方申请获得
                .setSence("JNOS")
                // 设置APPID，请联系sdk提供方申请获得
                .setAppid("17b710feba4")
                // 设置验证码类型，可选（PRODUCT_SEMANTIC、PRODUCT_SLIDE、PRODUCT_AUTO）三种类型
                .setProduct(JdjrCaptcha.PRODUCT_SLIDE)
                // 设置token
                .setToken("da1645af22b1472b8ad4eb0922260588")
                // 设置校验回调
                .setCallback(captchaCallback)
                .build(context);
        //弹出验证码对话框
        scd.show() ;
    }
}
