package com.example.hardcattle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hardcattle.i.OnDeleteClickListener;
import com.example.hardcattle.i.OnImgClickListener;
import com.example.hardcattle.myapplication.R;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guc on 2018/5/9.
 */

public class CanDeleteImgAdapter extends RecyclerView.Adapter<CanDeleteImgAdapter.ViewHolder> {
    public Map<Integer, String> map = new HashMap<>();
    private List<ImageItem> datas;
    private Context mCtx;
    private OnImgClickListener onImgClickListener;
    private OnDeleteClickListener onDeleteClickListener;
    private int mMaxSelectNum = 10;

    public CanDeleteImgAdapter(List<ImageItem> datas, Context mCtx, int mMaxSelectNum) {
        if (mMaxSelectNum < 1) mMaxSelectNum = 1;
        this.datas = datas;
        this.mCtx = mCtx;
        this.mMaxSelectNum = mMaxSelectNum;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnImgClickListener(OnImgClickListener onImgClickListener) {
        this.onImgClickListener = onImgClickListener;
    }

    public void setDatas(List<ImageItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public CanDeleteImgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CanDeleteImgAdapter.ViewHolder(View.inflate(mCtx, R.layout.item_can_delete_img, null));
    }

    @Override
    public void onBindViewHolder(final CanDeleteImgAdapter.ViewHolder holder, final int position) {
        if (position < datas.size()) {
            Glide.with(mCtx).load(datas.get(position).path).into(holder.img);
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            Glide.with(mCtx).load(R.drawable.add_pic).into(holder.img);
            holder.delete.setVisibility(View.GONE);
        }
//        if (datas.get(position).path.startsWith("/storage/")) {
//            Glide.with(mCtx).load(datas.get(position).path).into(holder.img);
//        } else {
//            Glide.with(mCtx).load(R.drawable.add_pic).into(holder.img);
//        }
//        if (!datas.get(position).path.equals("defaultCamera")) {
//            holder.delete.setVisibility(View.VISIBLE);
//        } else {
//            holder.delete.setVisibility(View.GONE);
//        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClickListener.OnDeleteClick(position);
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImgClickListener.OnImgClick(position, position >= datas.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size() == mMaxSelectNum ? mMaxSelectNum : datas.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            delete = itemView.findViewById(R.id.deleteImg);
        }
    }
}
