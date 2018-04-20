package com.example.hardcattle.net;

/**
 * Created by guc on 2018/3/29.
 * 描述：网络请求返回类
 */

public class ResultBean {

    /**
     * data : {"itoken":"c9345af9-2bcf-428e-85cc-92beb802ae95","realname":"张三丰","inWork":true,"fileServer":"http://192.168.30.172","callPolicePhone":"18899098908"}
     * message : success
     * success : true
     */
    public boolean success;
    public String message;

    public ResultBean data;
    public String itoken;
    public String realname;
    public boolean inWork;
    public String fileServer;
}
