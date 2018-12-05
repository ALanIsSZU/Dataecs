package com.example.administrator.dataecs.model;

public class SuperLoginModel {


    /**
     * msg : success
     * code : 0
     * map : {"msg":"登录成功！","res":"success","userStatus":{"id":3,"tbUserId":3,"zhifubaoStatus":0,"idCardStatus":1,"dataStatus":1,"operatorStatus":0,"faceStatus":1,"bankStatus":0},"parameter":{"id":1,"roleId":1,"memberId":"8150716192","terminalId":"8150716192","apiuser":"8150719872","apikey":"6613d600d19941a09475","pfxpwd":"419653","pfxpath":null,"appcode":"d5e9ffafeff64a9eafdd893b376e1bc4","overdueCharge":"30%","unitPrice":101,"loanDay":"7","rate":0.02,"limits":null},"expire":604800,"mobile":"15625226978","userId":3,"limits":1000,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNTQzNDc3OTIyLCJleHAiOjE1NDQwODI3MjJ9.BnuNmmWut6hRuPIAA8oxl9Cy6AkddD0LXBoUKYSSTXx3oKqJKy42Gu3wm9jhQBRkIwr2vkWy05mYfVp5eHsq7A"}
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
         * msg : 登录成功！
         * res : success
         * userStatus : {"id":3,"tbUserId":3,"zhifubaoStatus":0,"idCardStatus":1,"dataStatus":1,"operatorStatus":0,"faceStatus":1,"bankStatus":0}
         * parameter : {"id":1,"roleId":1,"memberId":"8150716192","terminalId":"8150716192","apiuser":"8150719872","apikey":"6613d600d19941a09475","pfxpwd":"419653","pfxpath":null,"appcode":"d5e9ffafeff64a9eafdd893b376e1bc4","overdueCharge":"30%","unitPrice":101,"loanDay":"7","rate":0.02,"limits":null}
         * expire : 604800
         * mobile : 15625226978
         * userId : 3
         * limits : 1000
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNTQzNDc3OTIyLCJleHAiOjE1NDQwODI3MjJ9.BnuNmmWut6hRuPIAA8oxl9Cy6AkddD0LXBoUKYSSTXx3oKqJKy42Gu3wm9jhQBRkIwr2vkWy05mYfVp5eHsq7A
         */

        private String msg;
        private String res;
        private UserStatusBean userStatus;
        private ParameterBean parameter;
        private int expire;
        private String mobile;
        private long userId;


        private String token;
        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public UserStatusBean getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(UserStatusBean userStatus) {
            this.userStatus = userStatus;
        }

        public ParameterBean getParameter() {
            return parameter;
        }

        public void setParameter(ParameterBean parameter) {
            this.parameter = parameter;
        }

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserStatusBean {
            /**
             * id : 3
             * tbUserId : 3
             * zhifubaoStatus : 0
             * idCardStatus : 1
             * dataStatus : 1
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
             * limits : null
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


            public float getRate() {
                return rate;
            }

            public void setRate(float rate) {
                this.rate = rate;
            }


            public int getLimits() {
                return limits;
            }

            public void setLimits(int limits) {
                this.limits = limits;
            }

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




        }
    }
}
