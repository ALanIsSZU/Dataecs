package com.example.administrator.dataecs.model;

import java.util.List;

public class HelpModel {


    /**
     * msg : success
     * code : 0
     * helpList : [{"firstid":1,"firstTitle":"一级标题111","secondTitleList":[{"secondid":19,"helpid":1,"secondTitle":"二级标题","secondTitleContent":"二级菜单内容3"}]},{"firstid":7,"firstTitle":"一级标题2","secondTitleList":[]},{"firstid":9,"firstTitle":"一级标题2","secondTitleList":[{"secondid":17,"helpid":9,"secondTitle":"二级标题","secondTitleContent":"二级"}]},{"firstid":18,"firstTitle":"一级标题445","secondTitleList":[]},{"firstid":20,"firstTitle":"一级阿道夫","secondTitleList":[]}]
     */

    private String msg;
    private int code;
    private List<HelpListBean> helpList;

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

    public List<HelpListBean> getHelpList() {
        return helpList;
    }

    public void setHelpList(List<HelpListBean> helpList) {
        this.helpList = helpList;
    }

    public static class HelpListBean {
        /**
         * firstid : 1
         * firstTitle : 一级标题111
         * secondTitleList : [{"secondid":19,"helpid":1,"secondTitle":"二级标题","secondTitleContent":"二级菜单内容3"}]
         */

        private int firstid;
        private String firstTitle;
        private List<SecondTitleListBean> secondTitleList;

        public int getFirstid() {
            return firstid;
        }

        public void setFirstid(int firstid) {
            this.firstid = firstid;
        }

        public String getFirstTitle() {
            return firstTitle;
        }

        public void setFirstTitle(String firstTitle) {
            this.firstTitle = firstTitle;
        }

        public List<SecondTitleListBean> getSecondTitleList() {
            return secondTitleList;
        }

        public void setSecondTitleList(List<SecondTitleListBean> secondTitleList) {
            this.secondTitleList = secondTitleList;
        }

        public static class SecondTitleListBean {
            /**
             * secondid : 19
             * helpid : 1
             * secondTitle : 二级标题
             * secondTitleContent : 二级菜单内容3
             */

            private int secondid;
            private int helpid;
            private String secondTitle;
            private String secondTitleContent;

            public int getSecondid() {
                return secondid;
            }

            public void setSecondid(int secondid) {
                this.secondid = secondid;
            }

            public int getHelpid() {
                return helpid;
            }

            public void setHelpid(int helpid) {
                this.helpid = helpid;
            }

            public String getSecondTitle() {
                return secondTitle;
            }

            public void setSecondTitle(String secondTitle) {
                this.secondTitle = secondTitle;
            }

            public String getSecondTitleContent() {
                return secondTitleContent;
            }

            public void setSecondTitleContent(String secondTitleContent) {
                this.secondTitleContent = secondTitleContent;
            }
        }
    }
}
