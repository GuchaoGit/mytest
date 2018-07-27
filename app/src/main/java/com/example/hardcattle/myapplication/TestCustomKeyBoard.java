package com.example.hardcattle.myapplication;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.hardcattle.Utils.KeyBoardCarNumberUtil;
import com.guc.ui.KeyBoardCarNumberView;
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
    @BindView(R.id.et_input2)
    EditText mEtInput2;
    @BindView(R.id.my_kv)
    MyKeyBoardView mMyKv;
    @BindView(R.id.my_kv2)
    KeyBoardCarNumberView mMyKv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_keyboard_test);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mEtInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                mMyKv2.setVisibility(View.VISIBLE);
//                KeyBoardUtil.showKeyBoard(TestCustomKeyBoard.this, mEtInput);
//                mMyKv2.setStrReceiver(mEtInput);
                KeyBoardCarNumberUtil.showKeyBoard(TestCustomKeyBoard.this, mEtInput);
                return false;
            }
        });
        mEtInput2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                mMyKv2.setVisibility(View.VISIBLE);
//                KeyBoardUtil.showKeyBoard(TestCustomKeyBoard.this, mEtInput2);
//                mMyKv2.setStrReceiver(mEtInput2);
                KeyBoardCarNumberUtil.showKeyBoard(TestCustomKeyBoard.this, mEtInput2);
                return false;
            }
        });
//        mEtInput.setInputType(InputType.TYPE_NULL);
//        mEtInput.setInputType(InputType.TYPE_NULL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mEtInput.setShowSoftInputOnFocus(false);
            mEtInput2.setShowSoftInputOnFocus(false);
        }
        mMyKv2.setOnInputFinishListener(new KeyBoardCarNumberView.OnInputFinishListener() {
            @Override
            public void onFinish() {
                mMyKv2.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (KeyBoardCarNumberUtil.isShowing()) {
            KeyBoardCarNumberUtil.hideKeyBoard();
        } else {
            super.onBackPressed();
        }

    }
}
