package com.example.administrator.dataecs.model;

public class RegisterCommitModel {


    /**
     * mobile : 钉钉DING消息
     * password  : 01053912831
     * smscode : 钉钉DING消息
     */

    private String mobile;
    private String password;
    private String smscode;
    //角色id
    private long roleid;
    //渠道id
    private long sourceid;


    public long getSourceid() {
        return sourceid;
    }

    public void setSourceid(long sourceid) {
        this.sourceid = sourceid;
    }


    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
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

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }
}
