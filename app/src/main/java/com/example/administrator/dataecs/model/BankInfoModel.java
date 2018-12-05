package com.example.administrator.dataecs.model;

import java.util.List;

public class BankInfoModel {


    /**
     * msg : success
     * code : 0
     * page : {"totalCount":1,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"id":1,"tbUserId":3,"bankName":"招商银行","bankKind":"招商银行信用卡","bankNumber":"6225756663322156","reservedNumber":"15625226978","bankType":"信用卡","bankCode":"CMB"}]}
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
         * totalCount : 1
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"id":1,"tbUserId":3,"bankName":"招商银行","bankKind":"招商银行信用卡","bankNumber":"6225756663322156","reservedNumber":"15625226978","bankType":"信用卡","bankCode":"CMB"}]
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
             * id : 1
             * tbUserId : 3
             * bankName : 招商银行
             * bankKind : 招商银行信用卡
             * bankNumber : 6225756663322156
             * reservedNumber : 15625226978
             * bankType : 信用卡
             * bankCode : CMB
             */

            private int id;
            private int tbUserId;
            private String bankName;
            private String bankKind;
            private String bankNumber;
            private String reservedNumber;
            private String bankType;
            private String bankCode;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTbUserId() {
                return tbUserId;
            }

            public void setTbUserId(int tbUserId) {
                this.tbUserId = tbUserId;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankKind() {
                return bankKind;
            }

            public void setBankKind(String bankKind) {
                this.bankKind = bankKind;
            }

            public String getBankNumber() {
                return bankNumber;
            }

            public void setBankNumber(String bankNumber) {
                this.bankNumber = bankNumber;
            }

            public String getReservedNumber() {
                return reservedNumber;
            }

            public void setReservedNumber(String reservedNumber) {
                this.reservedNumber = reservedNumber;
            }

            public String getBankType() {
                return bankType;
            }

            public void setBankType(String bankType) {
                this.bankType = bankType;
            }

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String bankCode) {
                this.bankCode = bankCode;
            }
        }
    }
}
