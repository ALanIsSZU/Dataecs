package com.example.administrator.dataecs.model;

import java.util.List;

public class BankInfoModel {


    /**
     * msg : success
     * code : 0
     * list : [{"id":1,"name":"fucker","idNumber":"123456789012345678","card":"1234567890123456","bankname":"中国银行","banktype":"储蓄卡","phone":"12345678901","userId":null,"createTime":"2018-10-08 10:02:55","userMobile":"15625226978"},{"id":2,"name":"fucker","idNumber":"123456789012345678","card":"1234567890123454","bankname":"中国银行","banktype":"信用卡","phone":"12345678902","userId":null,"createTime":"2018-10-08 10:05:41","userMobile":"15625226978"}]
     */

    private String msg;
    private int code;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * name : fucker
         * idNumber : 123456789012345678
         * card : 1234567890123456
         * bankname : 中国银行
         * banktype : 储蓄卡
         * phone : 12345678901
         * userId : null
         * createTime : 2018-10-08 10:02:55
         * userMobile : 15625226978
         */

        private int id;
        private String name;
        private String idNumber;
        private String card;
        private String bankname;
        private String banktype;
        private String phone;
        private Object userId;
        private String createTime;
        private String userMobile;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getBanktype() {
            return banktype;
        }

        public void setBanktype(String banktype) {
            this.banktype = banktype;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }
    }
}
