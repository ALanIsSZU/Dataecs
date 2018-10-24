package com.example.administrator.dataecs.model;

public class PerosonInfoModel {


    /**
     * msg : success
     * code : 0
     * map : {"result":{"id":null,"number":"15625226978","name":"富贵花","sex":null,"acquisitionTime":null,"statusId":null,"age":null,"borrowing":null,"sourceId":null,"auditId":null,"frequency":null,"qq":null,"idCard":"123456123456123","appUserId":null,"loanMonery":null,"loanTime":null,"repayTime":null,"overday":null,"expectTime":null,"repayStatus":null,"remark":null},"code":0}
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
         * result : {"id":null,"number":"15625226978","name":"富贵花","sex":null,"acquisitionTime":null,"statusId":null,"age":null,"borrowing":null,"sourceId":null,"auditId":null,"frequency":null,"qq":null,"idCard":"123456123456123","appUserId":null,"loanMonery":null,"loanTime":null,"repayTime":null,"overday":null,"expectTime":null,"repayStatus":null,"remark":null}
         * code : 0
         */

        private ResultBean result;
        private int code;

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
             * id : null
             * number : 15625226978
             * name : 富贵花
             * sex : null
             * acquisitionTime : null
             * statusId : null
             * age : null
             * borrowing : null
             * sourceId : null
             * auditId : null
             * frequency : null
             * qq : null
             * idCard : 123456123456123
             * appUserId : null
             * loanMonery : null
             * loanTime : null
             * repayTime : null
             * overday : null
             * expectTime : null
             * repayStatus : null
             * remark : null
             */

            private Object id;
            private String number;
            private String name;
            private Object sex;
            private Object acquisitionTime;
            private Object statusId;
            private Object age;
            private Object borrowing;
            private Object sourceId;
            private Object auditId;
            private Object frequency;
            private Object qq;
            private String idCard;
            private Object appUserId;
            private Object loanMonery;
            private Object loanTime;
            private Object repayTime;
            private Object overday;
            private Object expectTime;
            private Object repayStatus;
            private Object remark;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getAcquisitionTime() {
                return acquisitionTime;
            }

            public void setAcquisitionTime(Object acquisitionTime) {
                this.acquisitionTime = acquisitionTime;
            }

            public Object getStatusId() {
                return statusId;
            }

            public void setStatusId(Object statusId) {
                this.statusId = statusId;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public Object getBorrowing() {
                return borrowing;
            }

            public void setBorrowing(Object borrowing) {
                this.borrowing = borrowing;
            }

            public Object getSourceId() {
                return sourceId;
            }

            public void setSourceId(Object sourceId) {
                this.sourceId = sourceId;
            }

            public Object getAuditId() {
                return auditId;
            }

            public void setAuditId(Object auditId) {
                this.auditId = auditId;
            }

            public Object getFrequency() {
                return frequency;
            }

            public void setFrequency(Object frequency) {
                this.frequency = frequency;
            }

            public Object getQq() {
                return qq;
            }

            public void setQq(Object qq) {
                this.qq = qq;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public Object getAppUserId() {
                return appUserId;
            }

            public void setAppUserId(Object appUserId) {
                this.appUserId = appUserId;
            }

            public Object getLoanMonery() {
                return loanMonery;
            }

            public void setLoanMonery(Object loanMonery) {
                this.loanMonery = loanMonery;
            }

            public Object getLoanTime() {
                return loanTime;
            }

            public void setLoanTime(Object loanTime) {
                this.loanTime = loanTime;
            }

            public Object getRepayTime() {
                return repayTime;
            }

            public void setRepayTime(Object repayTime) {
                this.repayTime = repayTime;
            }

            public Object getOverday() {
                return overday;
            }

            public void setOverday(Object overday) {
                this.overday = overday;
            }

            public Object getExpectTime() {
                return expectTime;
            }

            public void setExpectTime(Object expectTime) {
                this.expectTime = expectTime;
            }

            public Object getRepayStatus() {
                return repayStatus;
            }

            public void setRepayStatus(Object repayStatus) {
                this.repayStatus = repayStatus;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }
        }
    }
}
