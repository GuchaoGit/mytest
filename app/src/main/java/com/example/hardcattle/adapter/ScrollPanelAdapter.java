package com.example.hardcattle.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.hardcattle.bean.BeanRoomInfo;
import com.example.hardcattle.bean.BeanFloorInfo;
import com.example.hardcattle.i.OnHouseRoomClickedListener;
import com.example.hardcattle.myapplication.R;
import com.example.hardcattle.widget.ScrollPanel.PanelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guc on 2018/5/2.
 * 描述：可上下左右滑动的View适配器
 */
public class ScrollPanelAdapter extends PanelAdapter {
    private static final int TITLE_TYPE = 4;
    private static final int LC_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int LcFw_TYPE = 2;
    Context mContext;

    public ScrollPanelAdapter(Context context) {
        mContext = context;
    }

    private List<BeanFloorInfo> LCInfoList = new ArrayList<>();

    private List<List<BeanRoomInfo>> LcFwsList = new ArrayList<>();

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    private int columnCount;

    private OnHouseRoomClickedListener mHouseRoomClickedListener;

    public void setOnHouseRoomClickedListener(OnHouseRoomClickedListener mHouseRoomClickedListener){
        this.mHouseRoomClickedListener = mHouseRoomClickedListener;
    }

    @Override
    public int getRowCount() {
        return LCInfoList.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        int viewType = getItemViewType(row, column);
        switch (viewType) {
            case LC_TYPE:
                setLCView(row, (LCViewHolder) holder);
                break;
            case LcFw_TYPE:
            default:
                setLcFwView(row, column, (LcFwViewHolder) holder);
        }
    }

    public int getItemViewType(int row, int column) {

        if (column == 0) {
            return LC_TYPE;
        } else
            return LcFw_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case LC_TYPE:
                return new LCViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_lc_info, parent, false));
            case LcFw_TYPE:
                return new LcFwViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_lcfw_info, parent, false));

            default:
                break;
        }
        return new LcFwViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_lcfw_info, parent, false));
    }

    private void setLCView(int pos, LCViewHolder viewHolder) {
        BeanFloorInfo LCInfo = LCInfoList.get(pos);
        if (LCInfo != null) {
            viewHolder.lcTypeTextView.setText(LCInfo.getLcType());
            viewHolder.lcNameTextView.setText(LCInfo.getLcName());
        }
    }

    private void setLcFwView(final int row, final int column, LcFwViewHolder viewHolder) {
        final BeanRoomInfo fwInfo = LcFwsList.get(row).get(column - 1);
        if (fwInfo != null) {
            viewHolder.nameTextView.setText(fwInfo.getFjhMC());
            if (fwInfo.getDW_COUNT() > 0)
                viewHolder.dwSlTextView.setText(fwInfo.getDW_COUNT() + "间单位");
            if (fwInfo.getFwStatus() == BeanRoomInfo.FWStatus.BLANK) {//未操作，未认领
                viewHolder.view.setBackgroundResource(R.drawable.ic_launcher_background);
                viewHolder.statusTextView.setText("未操作");
            } else if (fwInfo.getFwStatus() == BeanRoomInfo.FWStatus.ZY) {//自用
                //viewHolder.statusTextView.setText(fwInfo.getFzxm());
                viewHolder.statusTextView.setText("自用房屋");
                viewHolder.view.setBackgroundResource(R.drawable.ic_launcher_background);
            } else if (fwInfo.getFwStatus() == BeanRoomInfo.FWStatus.CZ) {//出租
                viewHolder.statusTextView.setText("出租房");
                viewHolder.view.setBackgroundResource(R.drawable.ic_launcher_background);
            } else if (fwInfo.getFwStatus() == BeanRoomInfo.FWStatus.XU) {//虚地址
                viewHolder.statusTextView.setText("虚地址");
                viewHolder.view.setBackgroundResource(R.drawable.ic_launcher_background);
            } else if (fwInfo.getFwStatus() == BeanRoomInfo.FWStatus.JIE) {//借用
                viewHolder.statusTextView.setText("借用房屋");
                viewHolder.view.setBackgroundResource(R.drawable.ic_launcher_background);
            } else if (fwInfo.getFwStatus() == BeanRoomInfo.FWStatus.KONG) {//空房屋
                viewHolder.statusTextView.setText("空房屋");
                viewHolder.view.setBackgroundResource(R.drawable.ic_launcher_background);
            } else if (fwInfo.getFwStatus() == BeanRoomInfo.FWStatus.HUN) {//混用房屋
                viewHolder.statusTextView.setText("混用房屋");
                viewHolder.view.setBackgroundResource(R.drawable.ic_launcher_background);
            }
            viewHolder.itemView.setClickable(true);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (mHouseRoomClickedListener!=null){
                    mHouseRoomClickedListener.onHouseRoomClicked(LCInfoList.get(row),fwInfo);
                }
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    showFwEdit(1, row, column);
                    return false;
                }
            });

        }
    }

    public void showFwEdit(final int tag, final int row, final int column) {
        String[] items = new String[0];
        if (tag == 0) {
            items = new String[]{"新增房屋"};
        }
        if (tag == 1) {
            items = new String[]{"新增一般单位"};
        }
        new AlertDialog.Builder(mContext).setTitle("请选择")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tag == 0) {
                            BeanRoomInfo fwInfo = LcFwsList.get(row).get(column - 1);
                        } else if (tag == 1) {
                            BeanRoomInfo fwInfo = LcFwsList.get(row).get(column - 1);
                        }
                    }
                }).show();
    }

    private static class LCViewHolder extends RecyclerView.ViewHolder {
        public TextView lcTypeTextView;
        public TextView lcNameTextView;

        public LCViewHolder(View view) {
            super(view);
            this.lcTypeTextView = (TextView) view.findViewById(R.id.lc_type);
            this.lcNameTextView = (TextView) view.findViewById(R.id.lc_name);
        }
    }

    private static class LcFwViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView statusTextView;
        public TextView dwSlTextView;
        public View view;

        public LcFwViewHolder(View view) {
            super(view);
            this.view = view;
            this.statusTextView = (TextView) view.findViewById(R.id.fj_zt);
            this.nameTextView = (TextView) view.findViewById(R.id.fjh_mc);
            this.dwSlTextView = (TextView) view.findViewById(R.id.dw_sl);
        }
    }

    public void setFloorInfoList(List<BeanFloorInfo> LCInfoList) {
        this.LCInfoList = LCInfoList;
    }

    public void setHouseRoomList(List<List<BeanRoomInfo>> LcFwsList) {
        this.LcFwsList = LcFwsList;
    }
}
