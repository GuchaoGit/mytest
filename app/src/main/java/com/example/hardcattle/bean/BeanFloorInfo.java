package com.example.hardcattle.bean;

import java.io.Serializable;

/**
 *
 * 楼层信息
 */

public class BeanFloorInfo implements Serializable {
    public String getLcType() {
        return lcType;
    }

    public void setLcType(String lcType) {
        this.lcType = lcType;
    }

    public String getLcName() {
        return lcName;
    }

    public void setLcName(String lcName) {
        this.lcName = lcName;
    }

    public long getLcId() {
        return lcId;
    }

    public void setLcId(long lcId) {
        this.lcId = lcId;
    }

    private String lcType;
    private String lcName;
    private long lcId;


}
