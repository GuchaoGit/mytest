package com.example.hardcattle.bean;

import java.io.Serializable;

/**
 * 楼层房间信息
 */

public class BeanRoomInfo implements Serializable {
    private String ZZBH;
    private String fjhMC;//房间号名称
    private String fjh;
    private String fzxm;
    private FWStatus fwstatus;
    private boolean isRL;//是否认领
    private String BZDYHDM;//房间单元号
    private String DZMC;//详细地址
    private int DW_COUNT;

    public enum FWStatus {
        BLANK,
        ZY,
        CZ,
        JIE,
        HUN,
        KONG,
        XU,
        YBDW,
        QT
    }
    public int getDW_COUNT() {
        return DW_COUNT;
    }

    public void setDW_COUNT(int DW_COUNT) {
        this.DW_COUNT = DW_COUNT;
    }
    public String getDZMC() {
        return DZMC;
    }

    public void setDZMC(String DZMC) {
        this.DZMC = DZMC;
    }

    public String getBZDYHDM() {
        return BZDYHDM;
    }

    public void setBZDYHDM(String BZDYHDM) {
        this.BZDYHDM = BZDYHDM;
    }

    public String getZZBH() {
        return ZZBH;
    }

    public void setZZBH(String ZZBH) {
        this.ZZBH = ZZBH;
    }

    public String getFjhMC() {
        return fjhMC;
    }

    public void setFjhMC(String fjhMC) {
        this.fjhMC = fjhMC;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getFzxm() {
        return fzxm;
    }

    public void setFzxm(String fzxm) {
        this.fzxm = fzxm;
    }

    public FWStatus getFwStatus() {
        return fwstatus;
    }

    public void setFwStatus(FWStatus status) {
        this.fwstatus = status;
    }

    public boolean isRL() {
        return isRL;
    }

    public void setRL(boolean RL) {
        isRL = RL;
    }

}
