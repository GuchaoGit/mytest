package com.example.hardcattle.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guc on 2018/3/29.
 * 描述：网络请求封装
 */

public class RetrofitHelper {
    private Context mContext;
    OkHttpClient client;
    GsonConverterFactory factory;
    private static RetrofitHelper mInstance;
    private Retrofit mRetrofit;

    public static RetrofitHelper getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new RetrofitHelper(mContext);
        }
        return mInstance;
    }

    private RetrofitHelper(Context mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        client = getOkHttpClient();
        factory = GsonConverterFactory.create(new GsonBuilder().create());
        mRetrofit = new Retrofit.Builder().addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .baseUrl(Url.BASE_URL)
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("ok-http", "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();
    }

    //getServer方法就是为了获取RetrofitService接口类的实例化
    public IRetrofitService getServer() {
        return mRetrofit.create(IRetrofitService.class);
    }


}
