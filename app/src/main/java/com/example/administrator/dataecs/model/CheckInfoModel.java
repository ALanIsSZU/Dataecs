package com.example.administrator.dataecs.model;

public class CheckInfoModel {


    /**
     * msg : success
     * code : 0
     * map : {"repayTime":"2018-09-28 11:20:08","loanMonery":2.2,"status":3}
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
         * repayTime : 2018-09-28 11:20:08
         * loanMonery : 2.2
         * status : 3
         */

        private String repayTime;
        private Double loanMonery;
        private int status;

        public String getRepayTime() {
            return repayTime;
        }

        public void setRepayTime(String repayTime) {
            this.repayTime = repayTime;
        }

        public Double getLoanMonery() {
            return loanMonery;
        }

        public void setLoanMonery(Double loanMonery) {
            this.loanMonery = loanMonery;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
