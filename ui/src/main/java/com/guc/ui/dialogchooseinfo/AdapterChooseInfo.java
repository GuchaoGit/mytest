package com.guc.ui.dialogchooseinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guc.ui.R;

import java.util.List;

public class AdapterChooseInfo extends RecyclerView.Adapter<AdapterChooseInfo.MyViewHolder> {
    private Context mContext;
    private List<BeanChooseOption> mBeanChooseInfoList;
    private OnChooseInfoClickListener mOnChooseInfoClickListener;

    public AdapterChooseInfo(Context context, List<BeanChooseOption> list) {
        mContext = context;
        mBeanChooseInfoList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_choose_info, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder MyViewHolder, final int position) {
        if (mBeanChooseInfoList.get(position).selected == true) {
            MyViewHolder.mIvselect.setBackgroundResource(R.drawable.icon_selected2);
        } else {
            MyViewHolder.mIvselect.setBackgroundResource(R.drawable.icon_unselected);
        }
        MyViewHolder.mTvInfo.setText(mBeanChooseInfoList.get(position).key);
        MyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnChooseInfoClickListener.click(position, mBeanChooseInfoList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanChooseInfoList.size();
    }

    public void setOnChooseInfoClickListener(OnChooseInfoClickListener listener) {
        mOnChooseInfoClickListener = listener;
    }

    public interface OnChooseInfoClickListener {
        void click(int position, BeanChooseOption beanChooseInfo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTvInfo;
        public ImageView mIvselect;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvInfo = itemView.findViewById(R.id.tv_info);
            mIvselect = itemView.findViewById(R.id.iv_select_lable);

//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            int position = getAdapterPosition();
//            mOnChooseInfoClickListener.click(position, mBeanChooseInfoList.get(position));
        }
    }
}
