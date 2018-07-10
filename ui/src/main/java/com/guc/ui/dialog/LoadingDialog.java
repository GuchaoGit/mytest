package com.guc.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.guc.ui.R;

/**
 * Author:guc
 * Time:2018/5/20
 * Description:加载框
 */

public class LoadingDialog extends Dialog {
    private TextView txtvTip;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
        init();
    }

    private void init() {
        setContentView(R.layout.view_loading_dialog);
        setCancelable(false);
        txtvTip = (TextView) findViewById(R.id.txtv_mprogress_dialog);
    }

    public LoadingDialog setTip(String tip) {
        if (!tip.isEmpty()) {
            txtvTip.setText(tip);
        }
        return this;
    }

    public LoadingDialog setCanCancle(boolean canCancel) {
        setCancelable(canCancel);
        return this;
    }
}


