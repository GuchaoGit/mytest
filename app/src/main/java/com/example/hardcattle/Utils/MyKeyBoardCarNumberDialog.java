package com.example.hardcattle.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hardcattle.myapplication.R;
import com.guc.ui.KeyBoardCarNumberView;

/**
 * Created by guc on 2018/7/12.
 * 描述：
 */
public class MyKeyBoardCarNumberDialog extends Dialog {
    private KeyBoardCarNumberView myKeyBoardView;
    private EditText mEtInput;
    private RadioButton mTvProvince, mTvLetter, mTvNumber;
    private RadioGroup mRg;
    private boolean showPreView = true;

    public MyKeyBoardCarNumberDialog(@NonNull Context context) {
        this(context, com.guc.ui.R.style.KeyboardDialog);
    }

    public MyKeyBoardCarNumberDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.layout_keyboard_car_number);
        initView();
    }

    public MyKeyBoardCarNumberDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView(R.layout.layout_keyboard_car_number);
        initView();
    }

    public MyKeyBoardCarNumberDialog setInput(EditText etInput) {
        this.mEtInput = etInput;
        myKeyBoardView.setStrReceiver(mEtInput);
        return this;
    }

    public MyKeyBoardCarNumberDialog setShowPreView(boolean showPreView) {
        this.showPreView = showPreView;
        myKeyBoardView.setShowPreview(showPreView);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.BOTTOM); //显示在底部
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        p.dimAmount = 0f;
        getWindow().setAttributes(p);
        //不获取焦点
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);


    }

    private void initView() {
        myKeyBoardView = findViewById(R.id.my_kv);
        getWindow().setBackgroundDrawable(new ColorDrawable());
        myKeyBoardView.setOnInputFinishListener(new KeyBoardCarNumberView.OnInputFinishListener() {
            @Override
            public void onFinish() {
                MyKeyBoardCarNumberDialog.this.dismiss();
            }
        });

        mTvProvince = findViewById(R.id.tv_province);
        mTvLetter = findViewById(R.id.tv_letter);
        mTvNumber = findViewById(R.id.tv_number);
//        mTvProvince.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myKeyBoardView.changeMode(0);
//            }
//        });
//        mTvLetter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myKeyBoardView.changeMode(1);
//            }
//        });
//        mTvNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myKeyBoardView.changeMode(2);
//            }
//        });
        mRg = findViewById(R.id.radio_group);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tv_province:
                        myKeyBoardView.changeMode(0);
                        break;
                    case R.id.tv_letter:
                        myKeyBoardView.changeMode(1);
                        break;
                    case R.id.tv_number:
                        myKeyBoardView.changeMode(2);
                        break;
                }
            }
        });


    }
}
