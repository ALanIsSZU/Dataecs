package com.example.administrator.dataecs.model;

/**
 * Created by Administrator on 2018/7/17.
 */

public class LoginNewModle {


    /**
     * msg : success
     * code : 0
     * map : {"msg":"注册成功！","res":"success"}
     */

    private String msg;
    private int code;
    private MapBean map;

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

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public static class MapBean {
        /**
         * msg : 注册成功！
         * res : success
         */

        private String msg;
        private String res;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }
    }
}
