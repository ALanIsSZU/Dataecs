package com.example.administrator.dataecs.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16.
 */

public class TabContentModel {


    /**
     * msg : success
     * result : [{"sourceid":17,"source":"academy","remark1":"/home/appicon/2018-08-09/1.png","explainDetail":"钱庄秒借","borrowBalances":"1000","borrowBalance":"10000"},{"sourceid":39,"source":"55红包","remark1":"/home/appicon/2018-08-09/2.png","explainDetail":"系统审核","borrowBalances":"1000","borrowBalance":"5000"},{"sourceid":40,"source":"不差钱","remark1":"/home/appicon/2018-08-09/3.png","explainDetail":"系统审核","borrowBalances":"1000","borrowBalance":"5000"},{"sourceid":41,"source":"福到了","remark1":"/home/appicon/2018-08-09/4.png","explainDetail":"系统自动审核","borrowBalances":"1000","borrowBalance":"1500"},{"sourceid":42,"source":"金库","remark1":"/home/appicon/2018-08-09/5.png","explainDetail":"系统自动审核","borrowBalances":"1500","borrowBalance":"5000"}]
     * code : 0
     */

    private String msg;
    private int code;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * sourceid : 17
         * source : academy
         * remark1 : /home/appicon/2018-08-09/1.png
         * explainDetail : 钱庄秒借
         * borrowBalances : 1000
         * borrowBalance : 10000
         */

        private int sourceid;
        private String source;
        private String remark1;
        private String explainDetail;
        private String borrowBalances;
        private String borrowBalance;

        public int getSourceid() {
            return sourceid;
        }

        public void setSourceid(int sourceid) {
            this.sourceid = sourceid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getRemark1() {
            return remark1;
        }

        public void setRemark1(String remark1) {
            this.remark1 = remark1;
        }

        public String getExplainDetail() {
            return explainDetail;
        }

        public void setExplainDetail(String explainDetail) {
            this.explainDetail = explainDetail;
        }

        public String getBorrowBalances() {
            return borrowBalances;
        }

        public void setBorrowBalances(String borrowBalances) {
            this.borrowBalances = borrowBalances;
        }

        public String getBorrowBalance() {
            return borrowBalance;
        }

        public void setBorrowBalance(String borrowBalance) {
            this.borrowBalance = borrowBalance;
        }
    }
}
