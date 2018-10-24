package com.example.administrator.dataecs.model;

public class IsPerfectModel {


    /**
     * msg : success
     * code : 0
     * map : {"isPerfectWorkInfomation":true,"isPerfectMaterial":true,"isPerfectEmergent":true}
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
         * isPerfectWorkInfomation : true
         * isPerfectMaterial : true
         * isPerfectEmergent : true
         * isPerfectPooclientInfo: true
         */

        private boolean isPerfectWorkInfomation;
        private boolean isPerfectMaterial;
        private boolean isPerfectEmergent;
        private boolean isPerfectPooclientInfo;


        public boolean isPerfectPooclientInfo() {
            return isPerfectPooclientInfo;
        }

        public void setPerfectPooclientInfo(boolean perfectPooclientInfo) {
            isPerfectPooclientInfo = perfectPooclientInfo;
        }


        public boolean isIsPerfectWorkInfomation() {
            return isPerfectWorkInfomation;
        }

        public void setIsPerfectWorkInfomation(boolean isPerfectWorkInfomation) {
            this.isPerfectWorkInfomation = isPerfectWorkInfomation;
        }

        public boolean isIsPerfectMaterial() {
            return isPerfectMaterial;
        }

        public void setIsPerfectMaterial(boolean isPerfectMaterial) {
            this.isPerfectMaterial = isPerfectMaterial;
        }

        public boolean isIsPerfectEmergent() {
            return isPerfectEmergent;
        }

        public void setIsPerfectEmergent(boolean isPerfectEmergent) {
            this.isPerfectEmergent = isPerfectEmergent;
        }
    }
}
