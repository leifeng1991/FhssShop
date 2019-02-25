package com.zrq.base.net;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述:
 *
 * @author zhangrq
 * 2018/3/23 15:35
 */

public class RetrofitFactory {
    private static RetrofitFactory mRetrofitFactory;
    private Retrofit mRetrofit;

    private RetrofitFactory(String baseUrl, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)// 读超时
                .writeTimeout(60, TimeUnit.SECONDS);// 写超时
        // 增加额外的拦截器
        if (interceptors != null && interceptors.length > 0)
            for (Interceptor interceptor : interceptors) {
                if (interceptor instanceof NetworkInterceptorFlag)
                    builder.addNetworkInterceptor(interceptor);
                else
                    builder.addInterceptor(interceptor);
            }
        // 创建OkHttpClient
        OkHttpClient mOkHttpClient = builder.build();
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .client(mOkHttpClient)
                .build();
    }

    public static RetrofitFactory getInstance(String baseUrl, Interceptor... interceptors) {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory(baseUrl, interceptors);
            }
        }
        return mRetrofitFactory;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
