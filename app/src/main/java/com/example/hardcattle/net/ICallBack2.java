package com.example.hardcattle.net;

/**
 * Created by guc on 2018/4/18.
 * 描述：
 */
public  interface ICallBack2{
    <T> void onSuccess(T result);
    <T> void onFailure(String message);

}
