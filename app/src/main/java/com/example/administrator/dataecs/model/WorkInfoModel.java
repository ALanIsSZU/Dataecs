package com.example.administrator.dataecs.model;

public class WorkInfoModel {


    /**
     * msg : success
     * code : 0
     * map : {"result":{"id":1,"userMobile":"15625226978","enterprise":"打的","enterprisePosition":"额额","position":"的","workingHours":"打的","personalTreatment":"打的"},"code":0}
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
         * result : {"id":1,"userMobile":"15625226978","enterprise":"打的","enterprisePosition":"额额","position":"的","workingHours":"打的","personalTreatment":"打的"}
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
             * id : 1
             * userMobile : 15625226978
             * enterprise : 打的
             * enterprisePosition : 额额
             * position : 的
             * workingHours : 打的
             * personalTreatment : 打的
             */

            private int id;
            private String userMobile;
            private String enterprise;
            private String enterprisePosition;
            private String position;
            private String workingHours;
            private String personalTreatment;

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

            public String getEnterprise() {
                return enterprise;
            }

            public void setEnterprise(String enterprise) {
                this.enterprise = enterprise;
            }

            public String getEnterprisePosition() {
                return enterprisePosition;
            }

            public void setEnterprisePosition(String enterprisePosition) {
                this.enterprisePosition = enterprisePosition;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getWorkingHours() {
                return workingHours;
            }

            public void setWorkingHours(String workingHours) {
                this.workingHours = workingHours;
            }

            public String getPersonalTreatment() {
                return personalTreatment;
            }

            public void setPersonalTreatment(String personalTreatment) {
                this.personalTreatment = personalTreatment;
            }
        }
    }
}
