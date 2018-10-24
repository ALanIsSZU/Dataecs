package com.example.administrator.dataecs.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public class ClassiFicationModel {


    /**
     * msg : success
     * result : [{"borrowBalance":"10000","explain_detail":"钱庄秒借","source_id":17,"source":"academy","remark1":"/home/appicon/2018-08-09/1.png","borrowBalances":"1000"},{"borrowBalance":"5000","explain_detail":"系统审核","source_id":39,"source":"55红包","remark1":"/home/appicon/2018-08-09/2.png","borrowBalances":"1000"},{"borrowBalance":"5000","explain_detail":"系统审核","source_id":40,"source":"不差钱","remark1":"/home/appicon/2018-08-09/3.png","borrowBalances":"1000"},{"borrowBalance":"1500","explain_detail":"系统自动审核","source_id":41,"source":"福到了","remark1":"/home/appicon/2018-08-09/4.png","borrowBalances":"1000"},{"borrowBalance":"5000","explain_detail":"系统自动审核","source_id":42,"source":"金库","remark1":"/home/appicon/2018-08-09/5.png","borrowBalances":"1500"},{"borrowBalance":"10000","explain_detail":"系统自动审核","source_id":43,"source":"钱袋","remark1":"/home/appicon/2018-08-09/6.png","borrowBalances":"1000"},{"borrowBalance":"10000","explain_detail":"全自动审批","source_id":44,"source":"钱箱","remark1":"/home/appicon/2018-08-09/7.png","borrowBalances":"5000"},{"borrowBalance":"10000","explain_detail":"身份证正反面，银行卡，运营商认证","source_id":45,"source":"速借","remark1":"/home/appicon/2018-08-09/8.png","borrowBalances":"800"},{"borrowBalance":"10000","explain_detail":"系统审核","source_id":46,"source":"运财","remark1":"/home/appicon/2018-08-09/9.png","borrowBalances":"5000"}]
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
         * borrowBalance : 10000
         * explain_detail : 钱庄秒借
         * source_id : 17
         * source : academy
         * remark1 : /home/appicon/2018-08-09/1.png
         * borrowBalances : 1000
         */

        private String borrowBalance;
        private String explain_detail;
        private int source_id;
        private String source;
        private String remark1;
        private String borrowBalances;

        public String getBorrowBalance() {
            return borrowBalance;
        }

        public void setBorrowBalance(String borrowBalance) {
            this.borrowBalance = borrowBalance;
        }

        public String getExplain_detail() {
            return explain_detail;
        }

        public void setExplain_detail(String explain_detail) {
            this.explain_detail = explain_detail;
        }

        public int getSource_id() {
            return source_id;
        }

        public void setSource_id(int source_id) {
            this.source_id = source_id;
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

        public String getBorrowBalances() {
            return borrowBalances;
        }

        public void setBorrowBalances(String borrowBalances) {
            this.borrowBalances = borrowBalances;
        }
    }
}
