package com.example.administrator.dataecs.model;

import java.util.List;

public class RecordMdel {


    /**
     * msg : success
     * code : 0
     * page : {"totalCount":2,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"id":256,"number":"15557122999","name":"王励焜","sex":null,"acquisitionTime":"2018-08-24 09:33:19","statusId":null,"age":null,"borrowing":null,"sourceId":"福到了","auditId":4,"frequency":0,"qq":null,"idCard":"230524199209210212","appUserId":null,"loanMonery":100.8,"loanTime":null,"repayTime":null,"overday":0,"expectTime":null,"repayStatus":null,"remark":null},{"id":257,"number":"15557122999","name":"王励焜","sex":null,"acquisitionTime":"2018-08-24 09:33:46","statusId":null,"age":null,"borrowing":null,"sourceId":"福到了","auditId":2,"frequency":0,"qq":null,"idCard":"230524199209210212","appUserId":null,"loanMonery":12.21,"loanTime":null,"repayTime":null,"overday":2,"expectTime":null,"repayStatus":null,"remark":null}]}
     */

    private String msg;
    private int code;
    private PageBean page;

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

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * totalCount : 2
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"id":256,"number":"15557122999","name":"王励焜","sex":null,"acquisitionTime":"2018-08-24 09:33:19","statusId":null,"age":null,"borrowing":null,"sourceId":"福到了","auditId":4,"frequency":0,"qq":null,"idCard":"230524199209210212","appUserId":null,"loanMonery":100.8,"loanTime":null,"repayTime":null,"overday":0,"expectTime":null,"repayStatus":null,"remark":null},{"id":257,"number":"15557122999","name":"王励焜","sex":null,"acquisitionTime":"2018-08-24 09:33:46","statusId":null,"age":null,"borrowing":null,"sourceId":"福到了","auditId":2,"frequency":0,"qq":null,"idCard":"230524199209210212","appUserId":null,"loanMonery":12.21,"loanTime":null,"repayTime":null,"overday":2,"expectTime":null,"repayStatus":null,"remark":null}]
         */

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<ListBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 256
             * number : 15557122999
             * name : 王励焜
             * sex : null
             * acquisitionTime : 2018-08-24 09:33:19
             * statusId : null
             * age : null
             * borrowing : null
             * sourceId : 福到了
             * auditId : 4
             * frequency : 0
             * qq : null
             * idCard : 230524199209210212
             * appUserId : null
             * loanMonery : 100.8
             * loanTime : null
             * repayTime : null
             * overday : 0
             * expectTime : null
             * repayStatus : null
             * remark : null
             */

            private Long id;
            private String number;
            private String name;
            private String sex;
            private String acquisitionTime;
            private Long statusId;
            private Long age;
            private Long borrowing;
            private String sourceId;
            private int auditId;
            private int frequency;
            private String qq;
            private String idCard;
            private Long appUserId;
            private double loanMonery;
            private String loanTime;
            private String repayTime;
            private Long overday;
            private String expectTime;
            private String repayStatus;
            private String remark;


            public Long getId() {
                return id;
            }

            public void setId(Long id) {
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

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getAcquisitionTime() {
                return acquisitionTime;
            }

            public void setAcquisitionTime(String acquisitionTime) {
                this.acquisitionTime = acquisitionTime;
            }

            public Long getStatusId() {
                return statusId;
            }

            public void setStatusId(Long statusId) {
                this.statusId = statusId;
            }

            public Long getAge() {
                return age;
            }

            public void setAge(Long age) {
                this.age = age;
            }

            public Long getBorrowing() {
                return borrowing;
            }

            public void setBorrowing(Long borrowing) {
                this.borrowing = borrowing;
            }

            public String getSourceId() {
                return sourceId;
            }

            public void setSourceId(String sourceId) {
                this.sourceId = sourceId;
            }

            public int getAuditId() {
                return auditId;
            }

            public void setAuditId(int auditId) {
                this.auditId = auditId;
            }

            public int getFrequency() {
                return frequency;
            }

            public void setFrequency(int frequency) {
                this.frequency = frequency;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public Long getAppUserId() {
                return appUserId;
            }

            public void setAppUserId(Long appUserId) {
                this.appUserId = appUserId;
            }

            public double getLoanMonery() {
                return loanMonery;
            }

            public void setLoanMonery(double loanMonery) {
                this.loanMonery = loanMonery;
            }

            public String getLoanTime() {
                return loanTime;
            }

            public void setLoanTime(String loanTime) {
                this.loanTime = loanTime;
            }

            public String getRepayTime() {
                return repayTime;
            }

            public void setRepayTime(String repayTime) {
                this.repayTime = repayTime;
            }

            public Long getOverday() {
                return overday;
            }

            public void setOverday(Long overday) {
                this.overday = overday;
            }

            public String getExpectTime() {
                return expectTime;
            }

            public void setExpectTime(String expectTime) {
                this.expectTime = expectTime;
            }

            public String getRepayStatus() {
                return repayStatus;
            }

            public void setRepayStatus(String repayStatus) {
                this.repayStatus = repayStatus;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
