package com.example.administrator.dataecs.model;

/**
 * Created by Administrator on 2018/7/16.
 */

public class XiangQingModel {


    /**
     * msg : success
     * result : {"sourceurl":"www.baidu.com","condition":"借款0门槛，3分钟下款4000","interest":"12","remark":"/appicon/1.png","borrowbalances":"10000","detail":"借款0门槛，3分钟下款4000","source":"academy","borrowbalance":"99999","introduction":"借款0门槛，3分钟下款4000"}
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
         * sourceurl : www.baidu.com
         * condition : 借款0门槛，3分钟下款4000
         * interest : 12
         * remark : /appicon/1.png
         * borrowbalances : 10000
         * detail : 借款0门槛，3分钟下款4000
         * source : academy
         * borrowbalance : 99999
         * introduction : 借款0门槛，3分钟下款4000
         */

        private String sourceurl;
        private String condition;
        private String interest;
        private String remark;
        private String borrowbalances;
        private String detail;
        private String source;
        private String borrowbalance;
        private String introduction;

        public String getSourceurl() {
            return sourceurl;
        }

        public void setSourceurl(String sourceurl) {
            this.sourceurl = sourceurl;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getBorrowbalances() {
            return borrowbalances;
        }

        public void setBorrowbalances(String borrowbalances) {
            this.borrowbalances = borrowbalances;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getBorrowbalance() {
            return borrowbalance;
        }

        public void setBorrowbalance(String borrowbalance) {
            this.borrowbalance = borrowbalance;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
}
