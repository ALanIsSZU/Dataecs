package com.example.administrator.dataecs.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class PhoneListModel {


    /**
     * ab : {"mobile":"13798523654","smscode":"123456"}
     * bc : [{"name":"钉钉DING消息","number":"01053912831"},{"name":"钉钉DING消息","number":"01053912851"}]
     */

    private AbBean ab;
    private List<BcBean> bc;

    public AbBean getAb() {
        return ab;
    }

    public void setAb(AbBean ab) {
        this.ab = ab;
    }

    public List<BcBean> getBc() {
        return bc;
    }

    public void setBc(List<BcBean> bc) {
        this.bc = bc;
    }

    public static class AbBean {
        /**
         * mobile : 13798523654
         * smscode : 123456
         */

        private String mobile;
        private String smscode;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSmscode() {
            return smscode;
        }

        public void setSmscode(String smscode) {
            this.smscode = smscode;
        }
    }

    public static class BcBean {
        /**
         * name : 钉钉DING消息
         * number : 01053912831
         */

        private String name;
        private String number;

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
}
