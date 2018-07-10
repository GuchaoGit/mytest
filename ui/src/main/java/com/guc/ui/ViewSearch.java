package com.guc.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by guc on 2018/6/25.
 * 描述：
 */
public class ViewSearch extends FrameLayout implements View.OnClickListener {
    private Context mCxt;
    private String mHintStr;
    private EditText mEtSearch;
    private Button mBtnSearch, mBtnClear;
    private String mSearchContent;
    private OnSearchClickListener mOnSearchClickListener;
    /**
     * 样式
     */
    private int mStyle = 0;

    public ViewSearch(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewSearch(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCxt = context;
        initAttrs(attrs, defStyleAttr);
        if (mStyle == 0) {
            inflate(mCxt, R.layout.view_search0, this);
        } else {
            inflate(mCxt, R.layout.view_search1, this);
        }
        initView();
    }

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.mOnSearchClickListener = onSearchClickListener;
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mCxt.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ViewSearch, defStyleAttr, 0);
        mHintStr = array.getString(R.styleable.ViewSearch_edit_hint);
        mStyle = array.getInt(R.styleable.ViewSearch_style, 0);
        array.recycle();
    }

    private void initView() {
        mEtSearch = findViewById(R.id.et_search);
        mBtnSearch = findViewById(R.id.btn_search_view);
        mBtnClear = findViewById(R.id.btn_clear_search_view);
        mBtnClear.setVisibility(View.GONE);
        mEtSearch.setHint(mHintStr);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mBtnClear.setVisibility(View.GONE);
                } else {
                    mBtnClear.setVisibility(View.VISIBLE);
                }
            }
        });
        mBtnClear.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search();
                return true;
            }
        });
    }

    public void setHintStr(String hintStr) {
        this.mHintStr = hintStr;
        mEtSearch.setHint(mHintStr);
    }

    public void setHintStr(int hintStrId) {
        this.mHintStr = getResources().getString(hintStrId);
        mEtSearch.setHint(mHintStr);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search_view) {
            search();
        } else if (v.getId() == R.id.btn_clear_search_view) {
            mEtSearch.setText("");
            mSearchContent = "";
            if (mOnSearchClickListener != null) {
                mOnSearchClickListener.onClearClicked();
            }
        }
    }

    private void search() {
        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
            Toast.makeText(getContext(), R.string.tips_null_key_words, Toast.LENGTH_SHORT).show();
        } else {
            mSearchContent = mEtSearch.getText().toString();
            hideSoftInput(mEtSearch);
            if (mOnSearchClickListener != null) {
                mOnSearchClickListener.onSearchClicked(mSearchContent);
            }
        }

    }

    private void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) mCxt.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public interface OnSearchClickListener {
        void onSearchClicked(String keyWorks);

        void onClearClicked();
    }


}
