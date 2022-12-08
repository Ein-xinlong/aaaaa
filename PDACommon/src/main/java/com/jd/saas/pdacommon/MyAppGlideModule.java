package com.jd.saas.pdacommon;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.jd.saas.pdacommon.dir.DirConfig;
import com.jd.saas.pdacommon.sdcard.SDCardUtil;

/**
 * Glide转用-勿删！
 *
 * @author majiheng
 */

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, GlideBuilder builder) {
        RequestOptions requestOptions = new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter();
        builder.setDefaultRequestOptions(requestOptions);
        builder.setMemoryCache(new LruResourceCache(20 * 1024 * 1024));
        builder.setDiskCache(new DiskLruCacheFactory(SDCardUtil.getCachePath(DirConfig.image)
                , 1024 * 1024 * 100));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}
