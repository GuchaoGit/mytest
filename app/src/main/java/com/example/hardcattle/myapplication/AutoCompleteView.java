package com.example.hardcattle.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guc on 2018/3/22.
 * 描述：
 */

public class AutoCompleteView extends FrameLayout {
    private AutoCompleteTextView actv;
    private AutoCompleteTvAdapter<String> autoCompleteTvAdapter;
    private Context mCxt;
    private OnSelectedListenner  mListener;

    private List<String > data;
    public AutoCompleteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public AutoCompleteView(@NonNull Context context) {
        this(context,null);
    }

    public AutoCompleteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCxt = context;
        inflate(context,R.layout.view_auto_complete,this);
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
            }
        });
        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actv.isPopupShowing()){
                    actv.showDropDown();
                }
            }
        });
        actv.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    actv.showDropDown();
                }else {
                  if (!isRightSelect()){
                      actv.setText("");
                      Toast.makeText(mCxt,"请正确输入信息",Toast.LENGTH_SHORT).show();
                      if (mListener!=null){
                          mListener.onSelected(null);
                      }
                  }
                }
            }
        });
    }

    /**
     * 是否是正确的选择
     * @return
     */
    private  boolean isRightSelect(){
        boolean isright = false;
        String hasselect = actv.getText().toString();
        for (String item:data){
            if (item.equals(hasselect)){
                if (mListener!=null){
                    mListener.onSelected(hasselect);
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
    public void setData(List<String> dataSrc){
        if(dataSrc == null) return;
        if (data == null){
            data = new ArrayList<>();
        }else {
            data.clear();
            data.addAll(dataSrc);
        }
        autoCompleteTvAdapter = new AutoCompleteTvAdapter<>(mCxt,R.layout.item_auto_complete_view,data);
        actv.setAdapter(autoCompleteTvAdapter);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener!=null){
                    mListener.onSelected(autoCompleteTvAdapter.getItem(position));
                }
            }
        });
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
        void  onSelected(String selectedData);
    }
}
