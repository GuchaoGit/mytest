package com.example.hardcattle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.example.hardcattle.myapplication.R;

/**
 * Created by guc on 2018/5/7.
 * 描述：可设置drawable大小的RadioButton
 */
public class IconFriendlyRadioButton extends AppCompatRadioButton {
    private int mDrawableSize;
    public IconFriendlyRadioButton(Context context) {
        this(context,null);
    }

    public IconFriendlyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IconFriendlyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDrawables(context,attrs,defStyleAttr);
    }

    private void initDrawables(Context context,AttributeSet attrs, int defStyleAttr){
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.IconFriendlyRadioButton);

        mDrawableSize = a.getDimensionPixelOffset(R.styleable.IconFriendlyRadioButton_drawableSize, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,context.getResources().getDisplayMetrics()));
        drawableLeft = a.getDrawable(R.styleable.IconFriendlyRadioButton_drawableLeft);
        drawableRight = a.getDrawable(R.styleable.IconFriendlyRadioButton_drawableRight);
        drawableTop = a.getDrawable(R.styleable.IconFriendlyRadioButton_drawableTop);
        drawableBottom = a.getDrawable(R.styleable.IconFriendlyRadioButton_drawableBottom);
        a.recycle();
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);
    }
    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
