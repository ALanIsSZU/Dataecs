package com.example.administrator.dataecs.model;

public class LoginCommitModel {


    /**
     * mobile : 145454
     * password : 234234
     * roleid : 1
     */

    private String mobile;
    private String password;
    private Long roleid;


    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
