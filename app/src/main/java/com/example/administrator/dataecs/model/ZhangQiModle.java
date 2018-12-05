package com.example.administrator.dataecs.model;

public class ZhangQiModle {


    /**
     * msg : success
     * code : 0
     * map : {"msg":"查无此还款订单(tb_user_indent_id)：null！","res":"fail"}
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
         * msg : 查无此还款订单(tb_user_indent_id)：null！
         * res : fail
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
