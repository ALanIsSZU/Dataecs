package com.example.administrator.dataecs.model;

/**
 * Created by Administrator on 2018/7/18.
 */

public class UpdateModel {


    /**
     * msg : success
     * result : {"iosscrollbar1":"false","androidscroll":"false","androidvesion":"1"}
     * code : 0
     */

    private String msg;
    private ResultBean result;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * iosscrollbar1 : false
         * androidscroll : false
         * androidvesion : 1
         */

        private String iosscrollbar1;
        private String androidscroll;
        private String androidvesion;

        public String getIosscrollbar1() {
            return iosscrollbar1;
        }

        public void setIosscrollbar1(String iosscrollbar1) {
            this.iosscrollbar1 = iosscrollbar1;
        }

        public String getAndroidscroll() {
            return androidscroll;
        }

        public void setAndroidscroll(String androidscroll) {
            this.androidscroll = androidscroll;
        }

        public String getAndroidvesion() {
            return androidvesion;
        }

        public void setAndroidvesion(String androidvesion) {
            this.androidvesion = androidvesion;
        }
    }
}
