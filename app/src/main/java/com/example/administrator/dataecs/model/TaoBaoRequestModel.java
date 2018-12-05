package com.example.administrator.dataecs.model;

public class TaoBaoRequestModel {


    /**
     * msg : success
     * code : 0
     * map : {"member_id":"8150716192","terminal_id":"8150716192","prepay_id":"201811201747429815552186"}
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
         * member_id : 8150716192
         * terminal_id : 8150716192
         * prepay_id : 201811201747429815552186
         */

        private String member_id;
        private String terminal_id;
        private String prepay_id;

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getTerminal_id() {
            return terminal_id;
        }

        public void setTerminal_id(String terminal_id) {
            this.terminal_id = terminal_id;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }
    }
}
