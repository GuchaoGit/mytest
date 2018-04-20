package com.example.hardcattle.myapplication;

import android.Manifest;
import android.app.Application;
import android.widget.Toast;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.Utils;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;


/**
 * Created by guc on 2018/4/18.
 * 描述：
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        checkPermissions();
    }

    private void checkPermissions() {
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                        /*以下为自定义提示语、按钮文字
                        .setDeniedMessage()
                        .setDeniedCloseBtn()
                        .setDeniedSettingBtn()
                        .setRationalMessage()
                        .setRationalBtn()*/
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        String path = SDCardUtils.getSDCardPaths().get(0);
                        try{
                            //崩溃日志
                            CrashUtils.init(path);
                        }catch (SecurityException e){
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                    }
                });
    }

}
