package com.example.hardcattle.net;

import android.content.Context;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by guc on 2018/3/29.
 * 描述：
 */

public class NetUtil {

    private static  NetUtil mInstance;

    protected  IRetrofitService retrofitService;
    protected  CompositeSubscription mCompositeSubscription;
    public  void init(Context context){
        if (retrofitService == null || mCompositeSubscription == null){
            retrofitService = RetrofitHelper.getInstance(context).getServer();
            mCompositeSubscription = new CompositeSubscription();
        }
    }
    public static NetUtil getInstance(Context context){
        if (mInstance == null){
            synchronized (NetUtil.class){
                if (mInstance == null){
                    mInstance = new NetUtil(context);
                }
            }
        }
        return mInstance;
    }
    private NetUtil(Context context){
        init(context);
    }

    public  void doRequest(Map<String,String> map, Observable<ResultBean> observable, final ICallBack mCallBack){
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onCompleted() {
                        if (mCallBack!=null)
                        mCallBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mCallBack!=null) mCallBack.onError(e);
                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (mCallBack!=null)mCallBack.onNext(resultBean);
                    }
                })
        );
    }
}
