package com.example.hardcattle.i;


import com.example.hardcattle.bean.BeanRoomInfo;
import com.example.hardcattle.bean.BeanFloorInfo;

/**
 * Created by guc on 2018/5/2.
 * 描述：点击房间的监听接口
 */

public interface OnHouseRoomClickedListener {
    void onHouseRoomClicked(BeanFloorInfo lcInfo, BeanRoomInfo fwInfo);
}
