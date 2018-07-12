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

import com.example.hardcattle.myapplication.R;
import com.guc.ui.MyKeyBoardView;

/**
 * Created by guc on 2018/7/12.
 * 描述：
 */
public class MyKeyBoardDialog extends Dialog {
    private MyKeyBoardView myKeyBoardView;
    private EditText mEtInput;
    private boolean showPreView = true;

    public MyKeyBoardDialog(@NonNull Context context) {
        this(context, com.guc.ui.R.style.KeyboardDialog);
    }

    public MyKeyBoardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.layout_keyboard);
        initView();
    }

    public MyKeyBoardDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView(R.layout.layout_keyboard);
        initView();
    }

    public MyKeyBoardDialog setInput(EditText etInput) {
        this.mEtInput = etInput;
        myKeyBoardView.setStrReceiver(mEtInput);
        return this;
    }

    public MyKeyBoardDialog setShowPreView(boolean showPreView) {
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
        myKeyBoardView.setOnInputFinishListener(new MyKeyBoardView.OnInputFinishListener() {
            @Override
            public void onFinish() {
                MyKeyBoardDialog.this.dismiss();
            }
        });

    }
}
