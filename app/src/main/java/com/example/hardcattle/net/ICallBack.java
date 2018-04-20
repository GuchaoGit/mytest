package com.example.hardcattle.net;

/**
 * Created by guc on 2018/3/29.
 * 描述：
 */

public interface ICallBack <ResultBean>{
    void onCompleted();
    void onError(Throwable e);
    void onNext(ResultBean resultBean);
}
