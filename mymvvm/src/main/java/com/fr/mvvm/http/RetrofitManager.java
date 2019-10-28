package com.fr.mvvm.http;

import android.app.Application;
import android.util.Log;

import com.fr.mvvm.http.intercepter.CacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static final String TAG = "RetrofitManager";
    private static RetrofitManager mInstance;
    private static Retrofit retrofit;
    //超时时间
    private static final int DEFAULT_TIMEOUT = 20;
    //缓存时间
    private static final int CACHE_TIMEOUT = 1024 * 1024 * 10;
    private static Application mContext;                            //全局Context
//    private static String mBaseUrl = "https://wanandroid.com/";     //全局BaseUrl

    private static Cache cache = null;
    private static File httpCacheDirectory;

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public RetrofitManager init(Application application,final String baseUrl) {
        mContext = application;
        retrofit = new Retrofit.Builder()
                .client(createOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return this;
    }

    private static OkHttpClient createOkHttpClient() {

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "http_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            Log.d(TAG, "Could not create http cache: " + e);
        }
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)  //连接超时时间阈值
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)   //数据获取时间阈值
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)  //写数据超时时间阈值
                .retryOnConnectionFailure(true)     //错误重连
                .cache(cache)
                .addInterceptor(new CacheInterceptor(mContext))
//                .addInterceptor(new LoggingInterceptor
//                        .Builder()//构建者模式
//                        .loggable(BuildConfig.DEBUG) //是否开启日志打印
//                        .setLevel(Level.BASIC) //打印的等级
//                        .log(Platform.INFO) // 打印类型
//                        .request("Request") // request的Tag
//                        .response("Response")// Response的Tag
//                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
//                        .build()
//                )
                .build();
    }

    /**
     * 创建Api接口实例
     * @param service Api接口类
     * @param <T>     Api接口
     * @return        Api接口实例
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null");
        }
        return retrofit.create(service);
    }

}
