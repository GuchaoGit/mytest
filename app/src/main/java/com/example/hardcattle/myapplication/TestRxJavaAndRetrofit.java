package com.example.hardcattle.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.example.hardcattle.net.ICallBack;
import com.example.hardcattle.net.ICallBack2;
import com.example.hardcattle.net.NetUtil;
import com.example.hardcattle.net.NetUtils;
import com.example.hardcattle.net.ResultBean;
import com.example.hardcattle.net.RetrofitHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guc on 2018/3/29.
 * 描述：测试Rxjava
 */

public class TestRxJavaAndRetrofit extends AppCompatActivity implements View.OnClickListener{
    private EditText etUserName,etPwd;
    private Button btnLogin,btnExit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_testrxjava);
        findView();
        initListener();
        test();
        testAsyn();
    }

    private void findView(){
        etUserName = findViewById(R.id.et_username);
        etPwd = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnExit = findViewById(R.id.btn_exit);
    }

    private void initListener(){
        btnLogin.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    /**
     * 模拟同步请求
     */
    private  void test(){
        String[] cities = new String[]{"上海","北京","广州","深圳"
        };

       Observable.from(cities)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e("rxjava_onCompleted","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("rxjava_onError",e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("rxjava_onnext",s);
                    }
                });
    }

    /**
     * 模拟异步网络请求
     */
    private void testAsyn(){
        Observable.create(new Observable.OnSubscribe<ResultBean>() {
            @Override
            public void call(Subscriber<? super ResultBean> subscriber) {
                ResultBean resultBean = new ResultBean();
                try{
                    Thread.sleep(1000);
                    resultBean.success = true;
                    resultBean.message = "获取数据成功";
                }catch (InterruptedException e){
                    resultBean.success = false;
                    resultBean.message = "获取数据失败";
                }
                subscriber.onNext(resultBean);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        Toast.makeText(TestRxJavaAndRetrofit.this,resultBean.message,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Map<String,String> map = new HashMap<>();
                map.put("username",etUserName.getText().toString().trim());
                map.put("password",etPwd.getText().toString().trim());
               ;
                NetUtils.doPostRequest(RetrofitHelper.getInstance(this).getServer().login2(map), new ICallBack2() {
                    @Override
                    public void onSuccess(Object result) {
                        ResultBean resultBean = (ResultBean) result;
                        if (resultBean.success){
                            Toast.makeText(TestRxJavaAndRetrofit.this,resultBean.message,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TestRxJavaAndRetrofit.this,resultBean.message,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(TestRxJavaAndRetrofit.this,message,Toast.LENGTH_SHORT).show();
                    }
                },ResultBean.class);
//                NetUtil.getInstance(this).doRequest(map, RetrofitHelper.getInstance(this).getServer().login(map), new ICallBack() {
//                    @Override
//                    public void onCompleted() {
//                        Log.e("rxjava_oncompleted","onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("rxjava_onerror",e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ResultBean resultBean) {
//                        Log.e("rxjava_onnext",resultBean.message);
//                    }
//                });
                break;
            case R.id.btn_exit:
                AppUtils.exitApp();
                break;
        }
    }
}
