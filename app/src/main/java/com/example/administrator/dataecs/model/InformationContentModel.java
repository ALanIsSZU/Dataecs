package com.example.administrator.dataecs.model;

import java.util.List;

public class InformationContentModel {


    /**
     * msg : success
     * code : 0
     * page : {"totalCount":3,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"id":1,"roleId":1,"newsTitle":"gg","newsContent":"ggggg","creationTime":"2018-11-29 10:42:05"},{"id":2,"roleId":1,"newsTitle":"看看","newsContent":"狂狂狂狂狂狂","creationTime":"2018-11-29 10:57:54"},{"id":3,"roleId":1,"newsTitle":null,"newsContent":null,"creationTime":"2018-11-29 11:28:20"}]}
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
         * list : [{"id":1,"roleId":1,"newsTitle":"gg","newsContent":"ggggg","creationTime":"2018-11-29 10:42:05"},{"id":2,"roleId":1,"newsTitle":"看看","newsContent":"狂狂狂狂狂狂","creationTime":"2018-11-29 10:57:54"},{"id":3,"roleId":1,"newsTitle":null,"newsContent":null,"creationTime":"2018-11-29 11:28:20"}]
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
             * roleId : 1
             * newsTitle : gg
             * newsContent : ggggg
             * creationTime : 2018-11-29 10:42:05
             */

            private int id;
            private int roleId;
            private String newsTitle;
            private String newsContent;
            private String creationTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getRoleId() {
                return roleId;
            }

            public void setRoleId(int roleId) {
                this.roleId = roleId;
            }

            public String getNewsTitle() {
                return newsTitle;
            }

            public void setNewsTitle(String newsTitle) {
                this.newsTitle = newsTitle;
            }

            public String getNewsContent() {
                return newsContent;
            }

            public void setNewsContent(String newsContent) {
                this.newsContent = newsContent;
            }

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
            }
        }
    }
}
