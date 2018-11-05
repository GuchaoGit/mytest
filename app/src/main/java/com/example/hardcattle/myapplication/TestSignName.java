package com.example.hardcattle.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.hardcattle.widget.SignNameView;

import java.io.IOException;

/**
 * Created by guc on 2018/11/2.
 * 描述：签名
 */
public class TestSignName extends AppCompatActivity {
    private SignNameView mSignNameView;
    private ImageView mIvPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_sign_name_test);
        mSignNameView = findViewById(R.id.snv);
        mIvPreview = findViewById(R.id.iv_preview);
    }

    public void onBtnClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                mSignNameView.clearAll();
                break;
            case R.id.btn_finish:
                if (mSignNameView.hasSign()) {
                    mIvPreview.setImageBitmap(mSignNameView.getBitMap());
                    try {
                        mSignNameView.save("/sdcard/guc/qm.png");
                        finish();
                        ToastUtils.showShort("保存成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showShort("你尚未签名");
                }
                break;
        }
    }
}
