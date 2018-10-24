package com.example.administrator.dataecs.model;

public class JinJiRequestModle {

    /**
     * msg : success
     * code : 0
     * map : {"result":"保存成功！","code":0}
     */

    private String msg;
    private int code;
    private ShenQinCommitModel.MapBean map;

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

    public ShenQinCommitModel.MapBean getMap() {
        return map;
    }

    public void setMap(ShenQinCommitModel.MapBean map) {
        this.map = map;
    }

    public static class MapBean {
        /**
         * result : 保存成功！
         * code : 0
         */

        private String result;
        private int code;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

}
