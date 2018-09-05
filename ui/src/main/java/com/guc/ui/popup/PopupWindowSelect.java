package com.guc.ui.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.guc.ui.R;
import com.guc.ui.recyclerview.CommonRecycleAdapter;
import com.guc.ui.recyclerview.CommonViewHolder;

import java.util.List;

/**
 * Created by guc on 2018/8/22.
 * 描述：
 */
public class PopupWindowSelect extends PopupWindow {
    private Context mContext;
    private View mParent;
    private List<BeanKeyValue> mDatas;
    private RecyclerView mRcv;
    private CommonRecycleAdapter mAdapter;

    public PopupWindowSelect(Context context, View parent, List<BeanKeyValue> data) {
        super(context);
        this.mContext = context;
        this.mParent = parent;
        this.mDatas = data;
    }

    public void showBelow(int xoff, int yoff) {
        initView();
        this.showAsDropDown(mParent, xoff, yoff);
    }

    public void showBelow() {
        this.showBelow(0, 0);
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.layout_select, null);
        mRcv = view.findViewById(R.id.rcv);
        mAdapter = new CommonRecycleAdapter<BeanKeyValue>(mContext, mDatas, R.layout.item_select) {
            @Override
            public void bindData(CommonViewHolder holder, final BeanKeyValue data, int position) {
                holder.setText(R.id.tv_name, data.key);
                holder.setImageResource(R.id.iv_select, data.isSelect ? R.drawable.icon_selected : R.drawable.icon_unselected);
                holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        if (!data.isSelect) {
                            data.isSelect = true;
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onItemLongClickListener(int position) {

                    }
                });
            }

        };
        mRcv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRcv.setAdapter(mAdapter);
        setWidth(mParent.getWidth());
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new ColorDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setContentView(view);
    }


}
