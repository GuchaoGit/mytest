package com.example.hardcattle.widget.autocompleteview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hardcattle.Utils.AssetsMgr;
import com.example.hardcattle.bean.BeanKeyValue;
import com.example.hardcattle.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guc on 2018/3/22.
 * 描述：
 */

public class AutoCompleteView extends FrameLayout {
    private AutoCompleteTextView actv;
    private AutoCompleteTvAdapter autoCompleteTvAdapter;
    private Context mCxt;
    private OnSelectedListenner  mListener;
    private boolean isFirstClick = true;//默认首次点击

    private NestedScrollView rootView;
    private BeanKeyValue mSelectData;

    private List<BeanKeyValue > data;
    public AutoCompleteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public AutoCompleteView(@NonNull Context context) {
        this(context,null);
    }

    public AutoCompleteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCxt = context;
        inflate(context, R.layout.view_auto_complete,this);
        actv = findViewById(R.id.auto_complete_tv_root);
        initView();

    }

    private void initView(){
        data = new ArrayList<>();
        actv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actv.isPopupShowing()){
                    actv.showDropDown();
                }
                softInputControl(v);
            }
        });
        actv.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
//                if (!isFirstClick){
//                    actv.clearFocus();
//                }
            }
        });
        actv.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    actv.showDropDown();
                    softInputControl(v);
                }else {
                    isFirstClick = true;
                    hideSoftInput(v);
                    if (!isRightSelect()){
                      actv.setText("");
                      Toast.makeText(mCxt,"请正确输入信息",Toast.LENGTH_SHORT).show();
                      mSelectData = null;
                      if (mListener!=null){
                          mListener.onSelected(null);
                      }
                  }
                }
            }
        });
    }

    /**
     * 获取选择项
     * @return  mSelectData
     */
    public BeanKeyValue getSelectData(){
        return mSelectData;
    }

    /**
     * 清除选中状态
     */
    public void clearFocus(){
        actv.clearFocus();
    }

    /**
     * 是否是正确的选择
     * @return
     */
    private  boolean isRightSelect(){
        boolean isright = false;
        String hasselect = actv.getText().toString();
        for (BeanKeyValue item:data){
            if (item.key.equals(hasselect)||item.value.equals(hasselect)){
                mSelectData = item;
                actv.setText(item.key);
                if (mListener!=null){
                    mListener.onSelected(item);
                }
                isright = true;
                break;
            }
        }
        return isright;
    }
    /**
     * 是否是正确的选择
     * @return
     */
    private  boolean isRightSelect(BeanKeyValue mSelect){
        boolean isright = false;
        for (BeanKeyValue item:data){
            if (item.value.equals(mSelect.value)||(!TextUtils.isEmpty(mSelect.key) && item.key.equals(mSelect.key))){
                mSelectData = item;
                actv.setText(item.key);
                if (mListener!=null){
                    mListener.onSelected(item);
                }
                isright = true;
                break;
            }
        }
        return isright;
    }
    /**
     * 设置数据
     * @param dataSrc
     */
    public void setData(List<BeanKeyValue> dataSrc){
        initData(dataSrc);
    }

    public void setData(String assetsName){
        if (!TextUtils.isEmpty(assetsName)){
            List<BeanKeyValue> dataSrc =  AssetsMgr.getKeyValuePairsList(mCxt,assetsName);
            if (dataSrc!=null){
                setData(dataSrc);
            }
        }
    }

    /**
     * 设置数据并绑定外层布局
     * @param dataSrc   数据
     * @param rootview  根布局
     */
    public void setData(List<BeanKeyValue> dataSrc, NestedScrollView rootview){
        this.rootView = rootview;
        initData(dataSrc);
    }

    private void initData(List<BeanKeyValue> dataSrc){
        if(dataSrc == null) return;
        if (data == null){
            data = new ArrayList<>();
        }else {
            data.clear();
            data.addAll(dataSrc);
        }
        autoCompleteTvAdapter = new AutoCompleteTvAdapter(mCxt,R.layout.item_auto_complete_view,data);
        actv.setAdapter(autoCompleteTvAdapter);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actv.setText(autoCompleteTvAdapter.getItem(position).key);
                actv.clearFocus();
//                if (mListener!=null){
//                    mListener.onSelected(autoCompleteTvAdapter.getItem(position));
//                }
            }
        });
    }

    /**
     * 设置初始值
     */
    public void setOriginalValue(BeanKeyValue originalValue){
        if (originalValue!=null) {
            isRightSelect(originalValue);
        }
    }

    /**
     * 设置选中监听
     * @param listenner
     */
    public void setOnSelectedListenner(OnSelectedListenner listenner){
        this.mListener = listenner;
    }


    public interface OnSelectedListenner{
        /**
         * selectedData == null 表示选择信息错误   否则正确
         * @param selectedData
         */
        void  onSelected(BeanKeyValue selectedData);
    }

    /**
     * 隐藏键盘
     * @param view
     */
    public  void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) mCxt.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示键盘
     * @param view
     */
    public  void showSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) mCxt.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 键盘控制
     */
    private void softInputControl(final View v){
        if (isFirstClick){
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideSoftInput(v);
                }
            },50);

            isFirstClick = false;
        }else {
            showSoftInput(v);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (rootView!=null){
            int y = (int) ev.getRawY();
            int ys = getScreenH((Activity) mCxt);
            if (y>ys*7/12){
                rootView.scrollBy(0,y*1/4);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private  int getScreenH(Activity mActivity){
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        return metric.widthPixels;
        return metric.heightPixels;
    }
}
