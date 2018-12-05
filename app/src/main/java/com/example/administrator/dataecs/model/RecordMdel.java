package com.example.administrator.dataecs.model;

import java.util.List;

public class RecordMdel {


    /**
     * msg : success
     * code : 0
     * page : {"totalCount":5,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"status":"已逾期","statusCode":7,"id":1,"userid":3,"indentStatus":3,"statusId":3,"annotation":"终审通过","financeid":1,"financeStatus":1,"repaymentsid":1,"repaymentsStatus":1,"overdueid":1,"idCard":null,"moeny":null,"name":"fucker","number":"111111111","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null},{"status":"审核中","statusCode":1,"id":2,"userid":3,"indentStatus":1,"statusId":1,"annotation":"审核中","financeid":null,"financeStatus":0,"repaymentsid":null,"repaymentsStatus":0,"overdueid":null,"idCard":null,"moeny":null,"name":"金阳","number":"15625226978","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null},{"status":"审核中","statusCode":1,"id":3,"userid":3,"indentStatus":1,"statusId":1,"annotation":"审核中","financeid":null,"financeStatus":0,"repaymentsid":null,"repaymentsStatus":0,"overdueid":null,"idCard":null,"moeny":null,"name":"金阳","number":"15625226978","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null},{"status":"审核中","statusCode":1,"id":4,"userid":3,"indentStatus":1,"statusId":1,"annotation":"审核中","financeid":null,"financeStatus":0,"repaymentsid":null,"repaymentsStatus":0,"overdueid":null,"idCard":null,"moeny":null,"name":"金阳","number":"15625226978","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null},{"status":"审核中","statusCode":1,"id":5,"userid":3,"indentStatus":1,"statusId":1,"annotation":"审核中","financeid":null,"financeStatus":0,"repaymentsid":null,"repaymentsStatus":0,"overdueid":null,"idCard":null,"moeny":null,"name":"金阳","number":"15625226978","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null}]}
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
         * totalCount : 5
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"status":"已逾期","statusCode":7,"id":1,"userid":3,"indentStatus":3,"statusId":3,"annotation":"终审通过","financeid":1,"financeStatus":1,"repaymentsid":1,"repaymentsStatus":1,"overdueid":1,"idCard":null,"moeny":null,"name":"fucker","number":"111111111","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null},{"status":"审核中","statusCode":1,"id":2,"userid":3,"indentStatus":1,"statusId":1,"annotation":"审核中","financeid":null,"financeStatus":0,"repaymentsid":null,"repaymentsStatus":0,"overdueid":null,"idCard":null,"moeny":null,"name":"金阳","number":"15625226978","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null},{"status":"审核中","statusCode":1,"id":3,"userid":3,"indentStatus":1,"statusId":1,"annotation":"审核中","financeid":null,"financeStatus":0,"repaymentsid":null,"repaymentsStatus":0,"overdueid":null,"idCard":null,"moeny":null,"name":"金阳","number":"15625226978","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null},{"status":"审核中","statusCode":1,"id":4,"userid":3,"indentStatus":1,"statusId":1,"annotation":"审核中","financeid":null,"financeStatus":0,"repaymentsid":null,"repaymentsStatus":0,"overdueid":null,"idCard":null,"moeny":null,"name":"金阳","number":"15625226978","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null},{"status":"审核中","statusCode":1,"id":5,"userid":3,"indentStatus":1,"statusId":1,"annotation":"审核中","financeid":null,"financeStatus":0,"repaymentsid":null,"repaymentsStatus":0,"overdueid":null,"idCard":null,"moeny":null,"name":"金阳","number":"15625226978","orderId":null,"days":0,"postponeTime":null,"repaymentsTime":null,"advance":null,"passingTime":null,"overdueDays":0,"serviceCharge":null}]
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
             * status : 已逾期
             * statusCode : 7
             * id : 1
             * userid : 3
             * indentStatus : 3
             * statusId : 3
             * annotation : 终审通过
             * financeid : 1
             * financeStatus : 1
             * repaymentsid : 1
             * repaymentsStatus : 1
             * overdueid : 1
             * idCard : null
             * moeny : null
             * name : fucker
             * number : 111111111
             * orderId : 订单编号
             * days : 日期
             * postponeTime : 展期时间
             * repaymentsTime : 还款时间
             * advance : null
             * passingTime : null
             * overdueDays : //逾期天数
             * serviceCharge : null
             */

            private String status;
            private int statusCode;

            private long id;

            private int userid;
            private int indentStatus;
            private int statusId;
            private String annotation;
            private int financeid;
            private int financeStatus;
            private int repaymentsid;
            private int repaymentsStatus;
            private int overdueid;
            private Object idCard;
            private double moeny;
            private String repaymentsTime;

            private String name;
            private String number;

            private String orderId;

            private int days;

            private
            String postponeTime;
            private Object advance;
            private Object passingTime;
            private int overdueDays;
            private double serviceCharge;


            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public double getServiceCharge() {
                return serviceCharge;
            }

            public void setServiceCharge(double serviceCharge) {
                this.serviceCharge = serviceCharge;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public double getMoeny() {
                return moeny;
            }

            public void setMoeny(double moeny) {
                this.moeny = moeny;
            }

            public String getRepaymentsTime() {
                return repaymentsTime;
            }

            public void setRepaymentsTime(String repaymentsTime) {
                this.repaymentsTime = repaymentsTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getStatusCode() {
                return statusCode;
            }

            public void setStatusCode(int statusCode) {
                this.statusCode = statusCode;
            }


            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }

            public int getIndentStatus() {
                return indentStatus;
            }

            public void setIndentStatus(int indentStatus) {
                this.indentStatus = indentStatus;
            }

            public int getStatusId() {
                return statusId;
            }

            public void setStatusId(int statusId) {
                this.statusId = statusId;
            }

            public String getAnnotation() {
                return annotation;
            }

            public void setAnnotation(String annotation) {
                this.annotation = annotation;
            }

            public int getFinanceid() {
                return financeid;
            }

            public void setFinanceid(int financeid) {
                this.financeid = financeid;
            }

            public int getFinanceStatus() {
                return financeStatus;
            }

            public void setFinanceStatus(int financeStatus) {
                this.financeStatus = financeStatus;
            }

            public int getRepaymentsid() {
                return repaymentsid;
            }

            public void setRepaymentsid(int repaymentsid) {
                this.repaymentsid = repaymentsid;
            }

            public int getRepaymentsStatus() {
                return repaymentsStatus;
            }

            public void setRepaymentsStatus(int repaymentsStatus) {
                this.repaymentsStatus = repaymentsStatus;
            }

            public int getOverdueid() {
                return overdueid;
            }

            public void setOverdueid(int overdueid) {
                this.overdueid = overdueid;
            }

            public Object getIdCard() {
                return idCard;
            }

            public void setIdCard(Object idCard) {
                this.idCard = idCard;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }


            public Object getAdvance() {
                return advance;
            }

            public void setAdvance(Object advance) {
                this.advance = advance;
            }

            public Object getPassingTime() {
                return passingTime;
            }

            public void setPassingTime(Object passingTime) {
                this.passingTime = passingTime;
            }

            public int getOverdueDays() {
                return overdueDays;
            }

            public void setOverdueDays(int overdueDays) {
                this.overdueDays = overdueDays;
            }

            public String getPostponeTime() {
                return postponeTime;
            }

            public void setPostponeTime(String postponeTime) {
                this.postponeTime = postponeTime;
            }
        }
    }
}
