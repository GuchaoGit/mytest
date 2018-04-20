package com.example.hardcattle.net;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Observer;

/**
 * Created by guc on 2018/3/29.
 * 描述：定义网络请求接口
 */

public interface IRetrofitService {

    @GET("getInfo")
    Observable<ResultBean> getInfo(@QueryMap Map<String,String> map);

    //登录接口
    @POST("app/login")
    Observable<ResultBean> login(@QueryMap Map<String,String> map);

    //登录接口2
    @POST("app/login")
    Call<ResponseBody> login2(@QueryMap Map<String,String> map);


}
