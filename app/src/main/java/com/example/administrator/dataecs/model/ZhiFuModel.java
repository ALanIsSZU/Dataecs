package com.example.administrator.dataecs.model;

public class ZhiFuModel {


    /**
     * msg : success
     * code : 0
     * map : {"privateKey":"asdasd","publiceKey":"askjdkals"}
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
         * privateKey : asdasd
         * publiceKey : askjdkals
         */

        private String privateKey;
        private String publiceKey;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPubliceKey() {
            return publiceKey;
        }

        public void setPubliceKey(String publiceKey) {
            this.publiceKey = publiceKey;
        }
    }
}
