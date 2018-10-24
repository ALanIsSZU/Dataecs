package com.example.administrator.dataecs.model;

public class ZhiFuAutoInfoModel {


    /**
     * msg : success
     * code : 0
     * map : {"orderInfo":"hjhsdjhf"}
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
         * orderInfo : hjhsdjhf
         */

        private String orderInfo;

        public String getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
        }
    }
}
