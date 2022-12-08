package com.jd.saas.pdacommon.imageloader;

import android.text.TextUtils;

/**
 * 图片URL工具类
 *
 * @author gouhetong
 */
public class ImageUrlUtil {
    /**
     * 将后端的logo字段转换称可以使用的imageURL
     *
     * @param logo 返回的图片路径
     * @return 完整的URL
     */
    public static String convertImageURL(String logo) {
        //URL没有以http开头需要兼容处理
        if (!TextUtils.isEmpty(logo) && !logo.startsWith("https://") && !logo.startsWith("http://")) {
            if (logo.startsWith("//")) {
                //网页上传图片可能不带http前缀仅以双斜杠开头
                return "https:" + logo;
            } else if (logo.startsWith("http:")) {
                //拼接缺失 不以http://开头，却以http开头 缺少双斜杠
                return logo.replace("http:", "https://");
            } else if (logo.startsWith("https:")) {
                //拼接缺失 不以https://开头，却以https开头 缺少双斜杠
                return logo.replace("https:", "https://");
            }else {
                //拼接缺失
                return "https://" + logo;
            }
        }
        return logo;
    }
}
