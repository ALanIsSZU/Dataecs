package com.example.administrator.dataecs.model;

public class HuanKuanInfoModle {


    /**
     * msg : success
     * code : 0
     * parameter : {"id":1,"roleId":1,"memberId":"8150716192","terminalId":"8150716192","apiuser":"8150719872","apikey":"6613d600d19941a09475","pfxpwd":"419653","pfxpath":null,"appcode":"d5e9ffafeff64a9eafdd893b376e1bc4","overdueCharge":"30%","unitPrice":101,"loanDay":"7","rate":0.02,"limits":1000}
     */

    private String msg;
    private int code;
    private ParameterBean parameter;

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

    public ParameterBean getParameter() {
        return parameter;
    }

    public void setParameter(ParameterBean parameter) {
        this.parameter = parameter;
    }

    public static class ParameterBean {
        /**
         * id : 1
         * roleId : 1
         * memberId : 8150716192
         * terminalId : 8150716192
         * apiuser : 8150719872
         * apikey : 6613d600d19941a09475
         * pfxpwd : 419653
         * pfxpath : null
         * appcode : d5e9ffafeff64a9eafdd893b376e1bc4
         * overdueCharge : 30%
         * unitPrice : 101
         * loanDay : 7
         * rate : 0.02
         * limits : 1000
         */

        private int id;
        private int roleId;
        private String memberId;
        private String terminalId;
        private String apiuser;
        private String apikey;
        private String pfxpwd;
        private Object pfxpath;
        private String appcode;
        private String overdueCharge;
        private int unitPrice;
        private String loanDay;

        private float rate;

        private int limits;

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

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getTerminalId() {
            return terminalId;
        }

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        public String getApiuser() {
            return apiuser;
        }

        public void setApiuser(String apiuser) {
            this.apiuser = apiuser;
        }

        public String getApikey() {
            return apikey;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public String getPfxpwd() {
            return pfxpwd;
        }

        public void setPfxpwd(String pfxpwd) {
            this.pfxpwd = pfxpwd;
        }

        public Object getPfxpath() {
            return pfxpath;
        }

        public void setPfxpath(Object pfxpath) {
            this.pfxpath = pfxpath;
        }

        public String getAppcode() {
            return appcode;
        }

        public void setAppcode(String appcode) {
            this.appcode = appcode;
        }

        public String getOverdueCharge() {
            return overdueCharge;
        }

        public void setOverdueCharge(String overdueCharge) {
            this.overdueCharge = overdueCharge;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getLoanDay() {
            return loanDay;
        }

        public void setLoanDay(String loanDay) {
            this.loanDay = loanDay;
        }


        public int getLimits() {
            return limits;
        }

        public void setLimits(int limits) {
            this.limits = limits;
        }
    }
}
