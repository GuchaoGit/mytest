package com.example.hardcattle.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hardcattle on 2018/3/28.
 * 描述：通用ViewHolder类
 */

public class CommonViewHolder {
    private SparseArray<View> mViews;
    private View convertView;
    private int mPosition;

    private CommonViewHolder(Context context, int position, int layoutId, ViewGroup parent){
        this.mPosition = position;
        mViews = new SparseArray<View>();
        convertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
        convertView.setTag(this);
    }

    public static CommonViewHolder getInstance(Context context,int layoutId,int position, View convertView, ViewGroup parent){
        if (convertView == null){
            return new CommonViewHolder(context,layoutId,position,parent);
        }else {
            CommonViewHolder holder = (CommonViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public View getConvertView(){
        return convertView;
    }
    /**
     * 通过resourceId获取item里面的view
     * @param resourceId    控件ID
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int resourceId) {
        View view = mViews.get(resourceId);
        if (view == null) {
            view = convertView.findViewById(resourceId);
            mViews.put(resourceId, view);
        }
        return (T) view;
    }
}
