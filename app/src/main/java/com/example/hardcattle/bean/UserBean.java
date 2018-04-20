package com.example.hardcattle.bean;

import java.io.Serializable;

/**
 * Created by guc on 2018/4/16.
 * 描述：用户对象
 */
public class UserBean implements Serializable{
    private String id;
    private String name;
    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
