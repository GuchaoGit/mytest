package com.example.hardcattle.widget.autocompleteview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;


/**
 * Created by guc on 2018/3/22.
 * 描述：自定义实现空状态时，显示所有选择项
 */

public class MyAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    public MyAutoCompleteTextView(Context context) {
        super(context);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }
    @Override
 protected void onFocusChanged(boolean focused,int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction,previouslyFocusedRect);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            performFiltering(getText(), KeyEvent.KEYCODE_UNKNOWN);
        }
    },200);
 }
}
