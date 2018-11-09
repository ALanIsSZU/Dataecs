package com.example.administrator.dataecs.model;

/**
 * Created by Administrator on 2018/7/17.
 */

public class LoginNewModle {


    /**
     * msg : success
     * result : {"msg":"登录成功！","res":"sucess"}
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
         * msg : 登录成功！
         * res : sucess
         */

        private String msg;
        private String res;
        private String usrname;
        private boolean isPerfectIdentity;
        private boolean isPerfectMaterial;

        public boolean isPerfectIdentity() {
            return isPerfectIdentity;
        }

        public void setPerfectIdentity(boolean perfectIdentity) {
            isPerfectIdentity = perfectIdentity;
        }

        public boolean isPerfectMaterial() {
            return isPerfectMaterial;
        }

        public void setPerfectMaterial(boolean perfectMaterial) {
            isPerfectMaterial = perfectMaterial;
        }

        private int status;

        private String repayTime;

        private Double loanMonery;

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


        public String getUsrname() {
            return usrname;
        }

        public void setUsrname(String usrname) {
            this.usrname = usrname;
        }

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
