package com.hnhy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by guc on 2018/4/26.
 * 描述：
 */
public class ViewCommonTitle extends FrameLayout implements View.OnClickListener {
    private Context mCxt;
    private String[] mTitles;
    private String[] mNums;
    private LinearLayout mLlRoot;
    private RelativeLayout mRl_1, mRl_2, mRl_3, mRl_4;
    private TextView mTvTitle1, mTvTitle2, mTvTitle3, mTvTitle4;
    private ImageView mIv1, mIv2, mIv3, mIv4;
    private TextView mTvNum1, mTvNum2, mTvNum3, mTvNum4;
    private int mPostion = 0;
    private boolean mIndexTypeIsNum = false;
    private int mBgColor, mTitleColor, mTitleSelctColor;
    private OnTitleChangedListener mListener;

    public ViewCommonTitle(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewCommonTitle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCxt = context;
        inflate(mCxt, R.layout.view_common_title, this);
        initAttrs(attrs, defStyleAttr);
        initView();
    }

    public void setOnTitleChangedListener(OnTitleChangedListener onTitleChangedListener) {
        this.mListener = onTitleChangedListener;
    }

    /**
     * 设置当前位置
     *
     * @param postion
     */
    public void setPostion(int postion) {
        if (postion < 0 || postion > 3) return;
        if (mListener != null) {
            if (postion != mPostion) {
                mListener.onTitleChanged(postion);
            } else {
                mListener.onTitleClicked(postion);
            }
        }
        this.mPostion = postion;
        initCurPostion(mPostion);
    }

    /**
     * 设置个数
     *
     * @param positon
     * @param numStr
     */
    public void setNum(int positon, String numStr) {
        switch (positon) {
            case 0:
                mTvNum1.setText(numStr);
                break;
            case 1:
                mTvNum2.setText(numStr);
                break;
            case 2:
                mTvNum3.setText(numStr);
                break;
            case 3:
                mTvNum4.setText(numStr);
                break;
        }
    }

    private void initAttrs(@Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray attributes = mCxt.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ViewCommonTitle, defStyleAttr, 0);
        int titleId = attributes.getResourceId(R.styleable.ViewCommonTitle_titles, -1);
        if (titleId != -1) {
            mTitles = mCxt.getResources().getStringArray(titleId);
            if (mTitles.length != 4) {
                mTitles = new String[]{"全部", "已通过", "未通过", "在审核"};
            }
        } else {
            mTitles = new String[]{"全部", "已通过", "未通过", "在审核"};
        }
        mIndexTypeIsNum = attributes.getBoolean(R.styleable.ViewCommonTitle_index_type_is_num, false);
        mBgColor = attributes.getColor(R.styleable.ViewCommonTitle_background_color, Color.WHITE);
        mTitleColor = attributes.getColor(R.styleable.ViewCommonTitle_title_color, Color.BLACK);
        mTitleSelctColor = attributes.getColor(R.styleable.ViewCommonTitle_title_select_color, Color.BLACK);
        attributes.recycle();
    }

    private void initView() {
        mLlRoot = findViewById(R.id.ll_root_view);
        mRl_1 = findViewById(R.id.rl_1);
        mRl_2 = findViewById(R.id.rl_2);
        mRl_3 = findViewById(R.id.rl_3);
        mRl_4 = findViewById(R.id.rl_4);
        mTvTitle1 = findViewById(R.id.tv_1);
        mTvTitle2 = findViewById(R.id.tv_2);
        mTvTitle3 = findViewById(R.id.tv_3);
        mTvTitle4 = findViewById(R.id.tv_4);
        mTvNum1 = findViewById(R.id.tv_num_1);
        mTvNum2 = findViewById(R.id.tv_num_2);
        mTvNum3 = findViewById(R.id.tv_num_3);
        mTvNum4 = findViewById(R.id.tv_num_4);

        mIv1 = findViewById(R.id.iv_1);
        mIv2 = findViewById(R.id.iv_2);
        mIv3 = findViewById(R.id.iv_3);
        mIv4 = findViewById(R.id.iv_4);
        if (mTitles != null) {
            mTvTitle1.setText(mTitles[0]);
            mTvTitle2.setText(mTitles[1]);
            mTvTitle3.setText(mTitles[2]);
            mTvTitle4.setText(mTitles[3]);
        }
        if (mNums != null) {
            mTvNum1.setText(mNums[0]);
            mTvNum2.setText(mNums[1]);
            mTvNum3.setText(mNums[2]);
            mTvNum4.setText(mNums[3]);
        }
        if (mIndexTypeIsNum) {
            setImageIndexStatus(-1);
            initNumIndexStatus(true);
        } else {
            setImageIndexStatus(mPostion);
            initNumIndexStatus(false);
        }
        mLlRoot.setBackgroundColor(mBgColor);
        mRl_1.setOnClickListener(this);
        mRl_2.setOnClickListener(this);
        mRl_3.setOnClickListener(this);
        mRl_4.setOnClickListener(this);
    }

    private void setImageIndexStatus(int index) {
        initTitleTextColor();
        initImageIndexStatus();
        switch (index) {
            case 0:
                mIv1.setVisibility(View.VISIBLE);
                mTvTitle1.setTextColor(mTitleSelctColor);
                break;
            case 1:
                mIv2.setVisibility(View.VISIBLE);
                mTvTitle2.setTextColor(mTitleSelctColor);
                break;
            case 2:
                mIv3.setVisibility(View.VISIBLE);
                mTvTitle3.setTextColor(mTitleSelctColor);
                break;
            case 3:
                mIv4.setVisibility(View.VISIBLE);
                mTvTitle4.setTextColor(mTitleSelctColor);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        int tempPostin = -1;
        int id = v.getId();
        if (id == R.id.rl_1) {
            tempPostin = 0;
        } else if (id == R.id.rl_2) {
            tempPostin = 1;
        } else if (id == R.id.rl_3) {
            tempPostin = 2;

        } else if (id == R.id.rl_4) {
            tempPostin = 3;
        }
        initCurPostion(tempPostin);
        if (mListener == null) return;
        mListener.onTitleClicked(tempPostin);
        if (mPostion != tempPostin) {
            mListener.onTitleChanged(tempPostin);
            mPostion = tempPostin;
        }
    }

    private void initCurPostion(int tempPostin) {
        if (mIndexTypeIsNum) {
            initNumIndexStatus(true);
        } else {
            setImageIndexStatus(tempPostin);
        }
    }

    private void initTitleTextColor() {
        mTvTitle1.setTextColor(mTitleColor);
        mTvTitle2.setTextColor(mTitleColor);
        mTvTitle3.setTextColor(mTitleColor);
        mTvTitle4.setTextColor(mTitleColor);
    }

    private void initImageIndexStatus() {
        mIv1.setVisibility(View.INVISIBLE);
        mIv2.setVisibility(View.INVISIBLE);
        mIv4.setVisibility(View.INVISIBLE);
        mIv3.setVisibility(View.INVISIBLE);
    }

    private void initNumIndexStatus(boolean isShow) {
        int visibility;
        if (isShow) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.INVISIBLE;
        }
        mTvNum1.setVisibility(visibility);
        mTvNum2.setVisibility(visibility);
        mTvNum3.setVisibility(visibility);
        mTvNum4.setVisibility(visibility);
    }

    public interface OnTitleChangedListener {
        void onTitleChanged(int postion);

        void onTitleClicked(int postion);
    }
}
