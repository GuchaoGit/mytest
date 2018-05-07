package com.example.hardcattle.widget.autocompleteview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.hardcattle.bean.BeanKeyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * 带提示的TextView数据适配器（自定义过滤器）
 * @param
 */
public class AutoCompleteTvAdapter extends BaseAdapter implements Filterable {
    private List<BeanKeyValue> mOriginalValues;
    private List<BeanKeyValue> mObject;
    private final Object mLock = new Object();
    private int mResouce;
    private MyFilter myFilter = null;
    private LayoutInflater inflater;

    public AutoCompleteTvAdapter(Context context, int TextViewResouceId, List<BeanKeyValue> objects)
    {
        init(context,TextViewResouceId,objects);
    }

    private void init(Context context, int textViewResouceId, List<BeanKeyValue> objects)
    {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mObject = objects;
        mResouce = textViewResouceId;
        myFilter = new MyFilter();
    }

    @Override
    public int getCount() {
        return mObject.size();
    }

    @Override
    public BeanKeyValue getItem(int position) {
        return mObject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewFromResouce(position,convertView,parent,mResouce);
    }

    private View getViewFromResouce(int position, View convertView,
                                    ViewGroup parent, int mResouce2) {
        if(convertView == null)
        {
            convertView = inflater.inflate(mResouce, parent,false);
        }
        TextView tv = (TextView)convertView;
        BeanKeyValue item = getItem(position);
        tv.setText(item.value+"-"+item.key);
        return tv;
    }

    //返回过滤器
    @Override
    public Filter getFilter() {
        return myFilter;
    }

    //自定义过滤器
    private class MyFilter extends Filter
    {
        //得到过滤结果
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(mOriginalValues == null)
            {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<BeanKeyValue>(mObject);
                }
            }

            int count = mOriginalValues.size();
            ArrayList<BeanKeyValue> values = new ArrayList<BeanKeyValue>();
            for(int i = 0;i < count;i++)
            {
                BeanKeyValue value = mOriginalValues.get(i);
                String valueText = value.key+value.value;
                //自定义匹配规则
                if (constraint == null ||constraint.toString().trim().equals("")){
                    values.add(value);
                }else {
                    if (valueText != null&&valueText.contains(constraint)){
                        values.add(value);
                    }
                }
//                if(valueText != null && constraint != null && valueText.contains(constraint))
//                {
//                    values.add(value);
//                }
            }
            results.values = values;
            results.count = values.size();
            return results;
        }
        //发布过滤结果
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //把搜索结果赋值给mObject这样每次输入字符串的时候就不必
            //从所有的字符串中查找，从而提高了效率
            mObject = (List<BeanKeyValue>)results.values;
            if(results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
                notifyDataSetInvalidated();
            }
        }

    }

}
