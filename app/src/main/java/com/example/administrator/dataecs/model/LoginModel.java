package com.example.administrator.dataecs.model;

/**
 * Created by Administrator on 2018/7/17.
 */

public class LoginModel {
    private static final LoginModel ourInstance = new LoginModel();

    public static LoginModel getInstance() {
        return ourInstance;
    }

    private LoginModel() {
    }
}
