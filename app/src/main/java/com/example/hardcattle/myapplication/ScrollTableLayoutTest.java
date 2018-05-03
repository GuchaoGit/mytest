package com.example.hardcattle.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.example.hardcattle.adapter.ScrollPanelAdapter;
import com.example.hardcattle.bean.BeanRoomInfo;
import com.example.hardcattle.bean.BeanFloorInfo;
import com.example.hardcattle.i.OnHouseRoomClickedListener;
import com.example.hardcattle.widget.ScrollPanel.ScrollablePanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guc on 2018/5/2.
 * 描述：可上下左右滑动的ScrolView
 */
public class ScrollTableLayoutTest extends AppCompatActivity{
    private ScrollablePanel scrollablePanel;
    private ScrollPanelAdapter scrollablePanelAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_table_test);
        initView();
    }
    private void initView(){
        scrollablePanel = findViewById(R.id.scrollable_panel);
        scrollablePanelAdapter = new ScrollPanelAdapter(this);
        generateTestData(scrollablePanelAdapter);
        scrollablePanelAdapter.setOnHouseRoomClickedListener(new OnHouseRoomClickedListener() {
            @Override
            public void onHouseRoomClicked(BeanFloorInfo lcInfo, BeanRoomInfo fwInfo) {
                ToastUtils.showLong(fwInfo.getFjhMC());
            }
        });
        scrollablePanel.setPanelAdapter(scrollablePanelAdapter);
    }

    private void generateTestData(ScrollPanelAdapter scrollablePanelAdapter) {
        String[] lcsArr = "23,23,23".split(",");
        int lcs = 1;
        if (lcsArr.length > 0)
            lcs = Integer.parseInt(lcsArr[0]);
        String[] fwsArr = "16,5,5".split(",");
        int hs = 1;
        if (fwsArr.length > 0)
            hs = Integer.parseInt(fwsArr[0]);
        scrollablePanelAdapter.setColumnCount(hs);
        List<BeanFloorInfo> LCInfoList = new ArrayList<>();
        for (int i = 0; i < lcs; i++) {
            BeanFloorInfo LCInfo = new BeanFloorInfo();
            LCInfo.setLcType("楼层");
            LCInfo.setLcId(i+1);
            LCInfo.setLcName((i + 1) + "楼");
            LCInfoList.add(LCInfo);
        }
        scrollablePanelAdapter.setFloorInfoList(LCInfoList);
        List<List<BeanRoomInfo>> ordersList = new ArrayList<>();
        for (int i = 0; i < lcs; i++) {
            List<BeanRoomInfo> fwInfoList = new ArrayList<>();
            for (int j = 0; j < hs; j++) {
                BeanRoomInfo fwInfo = new BeanRoomInfo();
                fwInfo.setFjhMC((i + 1) + "0" + (j + 1));
                fwInfo.setRL(true);
                fwInfo.setFwStatus(BeanRoomInfo.FWStatus.BLANK);
                fwInfoList.add(fwInfo);
            }
            ordersList.add(fwInfoList);
        }
        scrollablePanelAdapter.setHouseRoomList(ordersList);
    }
}
