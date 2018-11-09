package com.example.administrator.dataecs.model;

import java.util.List;

public class RecordMdel {


    /**
     * msg : success
     * code : 0
     * page : {"totalCount":3,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"id":12,"userMobile":"13763589512","loanMonery":789798,"loanTime":"2018-10-04 00:00:00","repayTime":"2018-10-19 00:00:00","overday":0,"expectTime":null,"createTime":"2018-10-31 16:46:04","name":"任建余","source":null},{"id":14,"userMobile":"13763589512","loanMonery":798798,"loanTime":"2018-10-02 00:00:00","repayTime":"2018-10-11 00:00:00","overday":0,"expectTime":null,"createTime":"2018-10-31 17:14:46","name":"任建余","source":null},{"id":15,"userMobile":"13763589512","loanMonery":8798798,"loanTime":"2018-10-10 00:00:00","repayTime":"2018-10-05 00:00:00","overday":0,"expectTime":null,"createTime":"2018-10-31 17:16:24","name":"任建余","source":null}]}
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
         * totalCount : 3
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"id":12,"userMobile":"13763589512","loanMonery":789798,"loanTime":"2018-10-04 00:00:00","repayTime":"2018-10-19 00:00:00","overday":0,"expectTime":null,"createTime":"2018-10-31 16:46:04","name":"任建余","source":null},{"id":14,"userMobile":"13763589512","loanMonery":798798,"loanTime":"2018-10-02 00:00:00","repayTime":"2018-10-11 00:00:00","overday":0,"expectTime":null,"createTime":"2018-10-31 17:14:46","name":"任建余","source":null},{"id":15,"userMobile":"13763589512","loanMonery":8798798,"loanTime":"2018-10-10 00:00:00","repayTime":"2018-10-05 00:00:00","overday":0,"expectTime":null,"createTime":"2018-10-31 17:16:24","name":"任建余","source":null}]
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
             * id : 12
             * userMobile : 13763589512
             * loanMonery : 789798
             * loanTime : 2018-10-04 00:00:00
             * repayTime : 2018-10-19 00:00:00
             * overday : 0
             * expectTime : null
             * createTime : 2018-10-31 16:46:04
             * name : 任建余
             * source : null
             */

            private int id;
            private String userMobile;
            private int loanMonery;
            private String loanTime;
            private String repayTime;
            private int overday;
            private Object expectTime;
            private String createTime;
            private String name;
            private Object source;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserMobile() {
                return userMobile;
            }

            public void setUserMobile(String userMobile) {
                this.userMobile = userMobile;
            }

            public int getLoanMonery() {
                return loanMonery;
            }

            public void setLoanMonery(int loanMonery) {
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

            public int getOverday() {
                return overday;
            }

            public void setOverday(int overday) {
                this.overday = overday;
            }

            public Object getExpectTime() {
                return expectTime;
            }

            public void setExpectTime(Object expectTime) {
                this.expectTime = expectTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }
        }
    }
}
