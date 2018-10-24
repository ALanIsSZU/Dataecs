package com.example.administrator.dataecs.model;

/**
 * Created by Administrator on 2018/7/13.
 */

public class ContactsInfo {

    private String name;
    private String number;

    public ContactsInfo(String name, String number) {
        this.name = name;
        this.number = number;
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
