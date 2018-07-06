package com.example.hardcattle.Utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.hardcattle.myapplication.R;

/**
 * Created by guc on 2018/6/29.
 * 描述：无任务管理
 */
public class NoTaskUtil {
    private static PopupWindow popupWindow;

    public static void showNoTaskView(final Activity activity, View rootView, String tips) {
        final View rootContentView = LayoutInflater.from(activity).inflate(R.layout.layout_notask, null);
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        popupWindow = new PopupWindow(rootContentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        if (!TextUtils.isEmpty(tips)) {
            ((TextView) rootContentView.findViewById(R.id.tv_tips)).setText(tips);
        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        popupWindow.showAtLocation(rootView == null ? activity.getWindow().getDecorView() : rootView, Gravity.CENTER, 0, -SizeUtils.dp2px(50));
        rootContentView.findViewById(R.id.iv_notask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 显示无任务布局
     *
     * @param activity
     * @param rootView
     */
    public static void showNoTaskView(final Activity activity, View rootView) {
        showNoTaskView(activity, rootView, null);
    }

    /**
     * 隐藏
     */
    public static void hideNoTaskView() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }
}
