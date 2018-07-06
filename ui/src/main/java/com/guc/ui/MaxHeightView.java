package com.hnhy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MaxHeightView extends LinearLayout {

    private int mMaxHeight;

    public MaxHeightView(Context context) {
        this(context, null);
    }

    public MaxHeightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightView);
        mMaxHeight = a.getDimensionPixelOffset(R.styleable.MaxHeightView_maxHeight, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.AT_MOST;
        int heightSize = mMaxHeight;
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }
}
