package com.example.hardcattle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by guc on 2018/3/28.
 * 描述：
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas;
    private int layoutID;

    public CommonAdapter(Context context,List<T> datas,int layoutID){
        this.mContext = context;
        this.mDatas = datas;
        this.layoutID = layoutID;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getInstance(mContext,layoutID,position,convertView,parent);
        convert(viewHolder,mDatas.get(position));
        return viewHolder.getConvertView();
    }

    /**
     * 填充holder里面控件的数据
     * @param holder
     * @param bean
     */
    public abstract void convert(CommonViewHolder holder,T bean);
}
