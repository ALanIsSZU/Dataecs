package com.example.administrator.dataecs.model;

public class PerfectTypeModel {


    /**
     * msg : success
     * code : 0
     * userStatus : {"id":3,"tbUserId":3,"zhifubaoStatus":0,"idCardStatus":1,"dataStatus":0,"operatorStatus":0,"faceStatus":1,"bankStatus":0}
     */

    private String msg;
    private int code;
    private UserStatusBean userStatus;

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

    public UserStatusBean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatusBean userStatus) {
        this.userStatus = userStatus;
    }

    public static class UserStatusBean {
        /**
         * id : 3
         * tbUserId : 3
         * zhifubaoStatus : 0
         * idCardStatus : 1
         * dataStatus : 0
         * operatorStatus : 0
         * faceStatus : 1
         * bankStatus : 0
         */

        private int id;
        private int tbUserId;
        private int zhifubaoStatus;
        private int idCardStatus;
        private int dataStatus;
        private int operatorStatus;
        private int faceStatus;
        private int bankStatus;

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

        public int getZhifubaoStatus() {
            return zhifubaoStatus;
        }

        public void setZhifubaoStatus(int zhifubaoStatus) {
            this.zhifubaoStatus = zhifubaoStatus;
        }

        public int getIdCardStatus() {
            return idCardStatus;
        }

        public void setIdCardStatus(int idCardStatus) {
            this.idCardStatus = idCardStatus;
        }

        public int getDataStatus() {
            return dataStatus;
        }

        public void setDataStatus(int dataStatus) {
            this.dataStatus = dataStatus;
        }

        public int getOperatorStatus() {
            return operatorStatus;
        }

        public void setOperatorStatus(int operatorStatus) {
            this.operatorStatus = operatorStatus;
        }

        public int getFaceStatus() {
            return faceStatus;
        }

        public void setFaceStatus(int faceStatus) {
            this.faceStatus = faceStatus;
        }

        public int getBankStatus() {
            return bankStatus;
        }

        public void setBankStatus(int bankStatus) {
            this.bankStatus = bankStatus;
        }
    }
}
