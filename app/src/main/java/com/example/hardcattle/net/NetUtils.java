package com.example.hardcattle.net;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guc on 2018/4/18.
 * 描述：
 */
public class NetUtils {
    public static <T>void doPostRequest(final Call<ResponseBody> resp, final ICallBack2 mCallback, final Class<T> cls){
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    String result = response.body().string().trim();
                    Gson gson = new Gson();
                    T t = gson.fromJson(result,cls);
                    mCallback.onSuccess(t);
                }catch (IOException e){
                    mCallback.onFailure(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mCallback.onFailure(t.getMessage());
            }
        });

    }
}
