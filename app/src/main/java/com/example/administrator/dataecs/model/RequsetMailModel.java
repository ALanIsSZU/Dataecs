package com.example.administrator.dataecs.model;

public class RequsetMailModel {


    /**
     * msg : success
     * code : 0
     * userData : {"id":1,"tbUserId":3,"compan":"儿童","position":"让人","income":"打的","seniority":"嫡妃","use":"","unitAddress":"","addressDetails":"让人","education":"","residence":"","residenceDetails":"","relation1":"母子","name1":"刚刚","number1":"15625226987","relation2":"兄弟","name2":"刚刚","number2":"15625226321","relation3":"朋友","name3":"刚刚","number3":"13798587334"}
     */

    private String msg;
    private int code;
    private UserDataBean userData;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserDataBean getUserData() {
        return userData;
    }

    public void setUserData(UserDataBean userData) {
        this.userData = userData;
    }

    public static class UserDataBean {
        /**
         * id : 1
         * tbUserId : 3
         * compan : 儿童
         * position : 让人
         * income : 打的
         * seniority : 嫡妃
         * use :
         * unitAddress :
         * addressDetails : 让人
         * education :
         * residence :
         * residenceDetails :
         * relation1 : 母子
         * name1 : 刚刚
         * number1 : 15625226987
         * relation2 : 兄弟
         * name2 : 刚刚
         * number2 : 15625226321
         * relation3 : 朋友
         * name3 : 刚刚
         * number3 : 13798587334
         */

        private int id;
        private int tbUserId;
        private String compan;
        private String position;
        private String income;
        private String seniority;
        private String use;
        private String unitAddress;
        private String addressDetails;
        private String education;
        private String residence;
        private String residenceDetails;
        private String relation1;
        private String name1;
        private String number1;
        private String relation2;
        private String name2;
        private String number2;
        private String relation3;
        private String name3;
        private String number3;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTbUserId() {
            return tbUserId;
        }

        public void setTbUserId(int tbUserId) {
            this.tbUserId = tbUserId;
        }

        public String getCompan() {
            return compan;
        }

        public void setCompan(String compan) {
            this.compan = compan;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getSeniority() {
            return seniority;
        }

        public void setSeniority(String seniority) {
            this.seniority = seniority;
        }

        public String getUse() {
            return use;
        }

        public void setUse(String use) {
            this.use = use;
        }

        public String getUnitAddress() {
            return unitAddress;
        }

        public void setUnitAddress(String unitAddress) {
            this.unitAddress = unitAddress;
        }

        public String getAddressDetails() {
            return addressDetails;
        }

        public void setAddressDetails(String addressDetails) {
            this.addressDetails = addressDetails;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getResidence() {
            return residence;
        }

        public void setResidence(String residence) {
            this.residence = residence;
        }

        public String getResidenceDetails() {
            return residenceDetails;
        }

        public void setResidenceDetails(String residenceDetails) {
            this.residenceDetails = residenceDetails;
        }

        public String getRelation1() {
            return relation1;
        }

        public void setRelation1(String relation1) {
            this.relation1 = relation1;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getNumber1() {
            return number1;
        }

        public void setNumber1(String number1) {
            this.number1 = number1;
        }

        public String getRelation2() {
            return relation2;
        }

        public void setRelation2(String relation2) {
            this.relation2 = relation2;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getNumber2() {
            return number2;
        }

        public void setNumber2(String number2) {
            this.number2 = number2;
        }

        public String getRelation3() {
            return relation3;
        }

        public void setRelation3(String relation3) {
            this.relation3 = relation3;
        }

        public String getName3() {
            return name3;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public String getNumber3() {
            return number3;
        }

        public void setNumber3(String number3) {
            this.number3 = number3;
        }
    }
}
