package com.guc.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.guc.ui.R;


/**
 * Created by guc on 2018/5/14.
 * 描述：自定义dialog
 */
public class DialogCustom extends Dialog {

    TextView mTvContent;
    TextView mTvCancle;
    TextView mTvSure;
    private OnclickListener mListner;


    public DialogCustom(@NonNull Context context) {
        this(context, R.style.MyDialog);
    }

    public DialogCustom(@NonNull Context context, int themeResId) {
        this(context, false, null);
    }

    public DialogCustom(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public void setOnClickListener(OnclickListener mListner) {
        this.mListner = mListner;
    }

    public void setTipMsg(String msg) {
        mTvContent.setText(TextUtils.isEmpty(msg) ? "no message!" : msg);
    }

    private void initView() {
        setContentView(R.layout.layout_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable());
        mTvContent = findViewById(R.id.tv_content);
        mTvSure = findViewById(R.id.tv_sure);
        mTvCancle = findViewById(R.id.tv_cancle);
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListner != null) {
                    mListner.onYesClick();
                } else {
                    dismiss();
                }
            }
        });
        mTvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListner != null) {
                    mListner.onNoClick();
                } else {
                    dismiss();
                }
            }
        });
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface OnclickListener {
        void onYesClick();

        void onNoClick();
    }
}
