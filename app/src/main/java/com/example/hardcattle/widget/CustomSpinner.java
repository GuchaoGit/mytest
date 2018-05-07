package com.example.hardcattle.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.hardcattle.Utils.AssetsMgr;
import com.example.hardcattle.bean.BeanKeyValue;
import com.example.hardcattle.myapplication.R;

import java.util.List;

/**
 * Created by guc on 2018/5/3.
 * 描述：自定义spinner
 */
public class CustomSpinner extends AppCompatSpinner {
    private String mAssetsName;
    private List<BeanKeyValue> mDatas;
    private Context mContext;
    private ArrayAdapter<BeanKeyValue> arrayAdapter;


    public CustomSpinner(Context context, AttributeSet attrs) {
       this(context,attrs, android.support.v7.appcompat.R.attr.spinnerStyle);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomSpinner, defStyleAttr, 0);
        mAssetsName = typedArray.getString(R.styleable.CustomSpinner_assetsName);
        initData();
        typedArray.recycle();
    }
    private void initData(){
        if (!TextUtils.isEmpty(mAssetsName)){
            mDatas = AssetsMgr.getKeyValuePairsList(mContext,mAssetsName);
        }
        arrayAdapter = new ArrayAdapter<BeanKeyValue>(mContext, R.layout.item_spinner, mDatas) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner, null);
                    holder = new ViewHolder();
                    holder.tvKey = convertView.findViewById(R.id.tv_spiner_text);
                    holder.tvValue = convertView.findViewById(R.id.tv_spiner_val);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tvKey.setText(mDatas.get(position).key);
                return convertView;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_dropdown, null);
                    holder = new ViewHolder();
                    holder.tvKey = convertView.findViewById(R.id.tv_spiner_text);
                    holder.tvValue = convertView.findViewById(R.id.tv_spiner_val);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tvKey.setText(mDatas.get(position).key);
                return convertView;
            }
        };
        setAdapter(arrayAdapter);
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        super.setAdapter(adapter);
    }

    private class ViewHolder{
        TextView tvKey;
        TextView tvValue;
    }
}
