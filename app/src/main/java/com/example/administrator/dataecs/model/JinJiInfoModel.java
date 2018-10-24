package com.example.administrator.dataecs.model;

import java.util.List;

public class JinJiInfoModel {


    /**
     * msg : success
     * code : 0
     * page : {"totalCount":2,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"id":5,"emergentName":"辅导费","emergentPhone":"15140555700","relationship":"兄弟","userMobile":"15625226978","code":"","flag":0},{"id":6,"emergentName":"辅","emergentPhone":"15140555711","relationship":"兄妹","userMobile":"15625226978","code":"","flag":0}]}
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
         * list : [{"id":5,"emergentName":"辅导费","emergentPhone":"15140555700","relationship":"兄弟","userMobile":"15625226978","code":"","flag":0},{"id":6,"emergentName":"辅","emergentPhone":"15140555711","relationship":"兄妹","userMobile":"15625226978","code":"","flag":0}]
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
             * id : 5
             * emergentName : 辅导费
             * emergentPhone : 15140555700
             * relationship : 兄弟
             * userMobile : 15625226978
             * code :
             * flag : 0
             */

            private int id;
            private String emergentName;
            private String emergentPhone;
            private String relationship;
            private String userMobile;
            private String code;
            private int flag;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getEmergentName() {
                return emergentName;
            }

            public void setEmergentName(String emergentName) {
                this.emergentName = emergentName;
            }

            public String getEmergentPhone() {
                return emergentPhone;
            }

            public void setEmergentPhone(String emergentPhone) {
                this.emergentPhone = emergentPhone;
            }

            public String getRelationship() {
                return relationship;
            }

            public void setRelationship(String relationship) {
                this.relationship = relationship;
            }

            public String getUserMobile() {
                return userMobile;
            }

            public void setUserMobile(String userMobile) {
                this.userMobile = userMobile;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }
        }
    }
}
