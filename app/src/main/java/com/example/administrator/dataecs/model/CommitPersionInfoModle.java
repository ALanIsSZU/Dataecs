package com.example.administrator.dataecs.model;

public class CommitPersionInfoModle {

    /**
     * tbUserId : 角色id
     * name : 名字
     * idCard : 身份证
     * nation : 民族
     * birthday : 生日
     * sex : 性别
     * location : 地址
     * store : 签发机关
     * idCardFront : 身份证正面
     * idCardBack : 身份证反面
     */

    private Long tbUserId;
    private String name;
    private String idCard;
    private String nation;
    private String birthday;
    private String sex;
    private String location;
    private String store;
    private String idCardFront;
    private String idCardBack;
    private String timeOfValidity;

    public String getTimeOfValidity() {
        return timeOfValidity;
    }

    public void setTimeOfValidity(String timeOfValidity) {
        this.timeOfValidity = timeOfValidity;
    }

    public Long getTbUserId() {
        return tbUserId;
    }

    public void setTbUserId(Long tbUserId) {
        this.tbUserId = tbUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }
}
