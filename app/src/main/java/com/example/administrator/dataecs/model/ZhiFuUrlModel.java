package com.example.administrator.dataecs.model;

public class ZhiFuUrlModel {


    /**
     * msg : success
     * code : 0
     * map : {"result":"sdhkjshdkjf"}
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
         * result : sdhkjshdkjf
         */

        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
