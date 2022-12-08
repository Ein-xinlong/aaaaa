package com.jd.saas.pdacommon.net;

import com.jd.saas.pdacommon.BuildConfig;
import com.jd.saas.pdacommon.dir.DirConfig;
import com.jd.saas.pdacommon.log.Logger;
import com.jd.saas.pdacommon.net.interceptor.NetMonitorInterceptor;
import com.jd.saas.pdacommon.net.interceptor.RequestHeaderInterceptor;
import com.jd.saas.pdacommon.net.interceptor.TokenExpireInterceptor;
import com.jd.saas.pdacommon.sdcard.SDCardUtil;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络访问
 *
 * @author majiheng
 */
public class ApiMgr {

    private static final String TAG = ApiMgr.class.getSimpleName();
    private static final String BASE_URL = BuildConfig.API_HOST;
    private static final int NET_CACHE_SIZE = 60 * 1000;
    private static final int DEFAULT_TIMEOUT = 20;
    private final Retrofit mRetrofit;

    /**
     * 构造函数私有化，只创建一个实例
     */
    private ApiMgr() {
        //打印请求日志（默认关闭，打开可以在logcat中看到请求&返回日志，打开需要将NONE更换为BODY）
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                this::showRetrofitLog
        );
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new NetMonitorInterceptor())
                .followRedirects(false)
                .addInterceptor(new TokenExpireInterceptor())
                .proxy(Proxy.NO_PROXY)
                .addInterceptor(new RequestHeaderInterceptor())
                //添加日志
                .addNetworkInterceptor(loggingInterceptor);
        String cachePath = SDCardUtil.getCachePath(DirConfig.net_cache);
        Cache cache = null;
        if (null != cachePath) {
            cache = new Cache(new File(cachePath), NET_CACHE_SIZE);
        }
        if (null != cache) {
            okBuilder.cache(cache);
        }

        mRetrofit = new Retrofit.Builder()
                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static <T> T getApi(Class<T> service) {
        return SingletonHolder.getInstance().mRetrofit.create(service);
    }

    /**
     * 单例对象持有
     */
    private final static class SingletonHolder {

        static volatile ApiMgr instance = null;

        static synchronized ApiMgr getInstance() {
            if (instance == null) {
                instance = new ApiMgr();
            }
            return instance;
        }
    }

    /**
     * 打印日志
     * 返回的是json，就打印格式化好了的json，不是json就原样打印
     *
     * @param message 信息
     */
    private void showRetrofitLog(String message) {
        Logger.eLong(TAG, message);
    }
}
