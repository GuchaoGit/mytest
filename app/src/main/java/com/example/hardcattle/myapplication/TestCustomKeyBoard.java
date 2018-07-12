package com.example.hardcattle.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.hardcattle.Utils.KeyBoardUtil;
import com.guc.ui.MyKeyBoardView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guc on 2018/7/11.
 * 描述：
 */
public class TestCustomKeyBoard extends Activity {
    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.my_kv)
    MyKeyBoardView mMyKv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_keyboard_test);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mMyKv.setStrReceiver(mEtInput);
        mEtInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMyKv.setVisibility(View.VISIBLE);
                KeyBoardUtil.showKeyBoard(TestCustomKeyBoard.this, mEtInput);
                return true;
            }
        });
        mMyKv.setOnInputFinishListener(new MyKeyBoardView.OnInputFinishListener() {
            @Override
            public void onFinish() {
                mMyKv.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (KeyBoardUtil.isShowing()) {
            KeyBoardUtil.hideKeyBoard();
        } else {
            super.onBackPressed();
        }

    }
}
