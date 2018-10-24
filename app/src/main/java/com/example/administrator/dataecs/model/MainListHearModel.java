package com.example.administrator.dataecs.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16.
 */

public class MainListHearModel {


    /**
     * msg : success
     * result : {"poolSource":[{"sourceid":39,"source":"55红包","remark1":"/home/appicon/2018-08-09/8.png","explainDetail":"系统审核","borrowBalances":"1000","borrowBalance":"10000"},{"sourceid":40,"source":"不差钱","remark1":"/home/appicon/2018-08-09/2.png","explainDetail":"系统审核","borrowBalances":"1000","borrowBalance":"5000"},{"sourceid":41,"source":"福到了","remark1":"/home/appicon/2018-08-09/4.png","explainDetail":"系统自动审核","borrowBalances":"1000","borrowBalance":"5000"},{"sourceid":42,"source":"金库","remark1":"/home/appicon/2018-08-09/7.png","explainDetail":"系统自动审核","borrowBalances":"1500","borrowBalance":"1500"},{"sourceid":43,"source":"钱袋","remark1":"/home/appicon/2018-08-09/1.png","explainDetail":"系统自动审核","borrowBalances":"1000","borrowBalance":"5000"},{"sourceid":44,"source":"钱箱","remark1":"/home/appicon/2018-08-09/3.png","explainDetail":"全自动审批","borrowBalances":"5000","borrowBalance":"10000"},{"sourceid":45,"source":"速借","remark1":"/home/appicon/2018-08-09/6.png","explainDetail":"身份证正反面，银行卡，运营商认证","borrowBalances":"800","borrowBalance":"10000"},{"sourceid":46,"source":"运财","remark1":"/home/appicon/2018-08-09/5.png","explainDetail":"系统审核","borrowBalances":"5000","borrowBalance":"10000"}],"loantype":[{"loan_type_code":"01","icon":"/home/appicon/2018-08-09/14.png","loan_type_name":"千元秒放","primary_id":1},{"loan_type_code":"02","icon":"/home/appicon/2018-08-09/12.png","loan_type_name":"苹果手机贷","primary_id":2},{"loan_type_code":"03","icon":"/home/appicon/2018-08-09/13.png","loan_type_name":"芝麻分贷","primary_id":3}]}
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
        private List<PoolSourceBean> poolSource;
        private List<LoantypeBean> loantype;

        public List<PoolSourceBean> getPoolSource() {
            return poolSource;
        }

        public void setPoolSource(List<PoolSourceBean> poolSource) {
            this.poolSource = poolSource;
        }

        public List<LoantypeBean> getLoantype() {
            return loantype;
        }

        public void setLoantype(List<LoantypeBean> loantype) {
            this.loantype = loantype;
        }

        public static class PoolSourceBean {
            /**
             * sourceid : 39
             * source : 55红包
             * remark1 : /home/appicon/2018-08-09/8.png
             * explainDetail : 系统审核
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

        public static class LoantypeBean {
            /**
             * loan_type_code : 01
             * icon : /home/appicon/2018-08-09/14.png
             * loan_type_name : 千元秒放
             * primary_id : 1
             */

            private String loan_type_code;
            private String icon;
            private String loan_type_name;
            private int primary_id;

            public String getLoan_type_code() {
                return loan_type_code;
            }

            public void setLoan_type_code(String loan_type_code) {
                this.loan_type_code = loan_type_code;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getLoan_type_name() {
                return loan_type_name;
            }

            public void setLoan_type_name(String loan_type_name) {
                this.loan_type_name = loan_type_name;
            }

            public int getPrimary_id() {
                return primary_id;
            }

            public void setPrimary_id(int primary_id) {
                this.primary_id = primary_id;
            }
        }
    }
}
