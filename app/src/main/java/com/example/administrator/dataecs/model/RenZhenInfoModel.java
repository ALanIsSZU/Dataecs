package com.example.administrator.dataecs.model;

import java.util.List;

public class RenZhenInfoModel {


    /**
     * msg : success
     * code : 0
     * map : {"emergentList":[{"id":16,"emergentName":"推广","emergentPhone":"15625226978","relationship":"方法","userMobile":"13456213212","code":null,"flag":0},{"id":17,"emergentName":"消息","emergentPhone":"15625226999","relationship":"哥哥","userMobile":"13456213212","code":null,"flag":0},{"id":18,"emergentName":"消息","emergentPhone":"15625226888","relationship":"哥哥","userMobile":"13456213212","code":null,"flag":0},{"id":19,"emergentName":"消息","emergentPhone":"15625226666","relationship":"哥哥","userMobile":"13456213212","code":null,"flag":0},{"id":20,"emergentName":"消息","emergentPhone":"15625226111","relationship":"哥哥","userMobile":"13456213212","code":null,"flag":0}],"poolClientInfo":{"id":null,"number":"13456213212","name":"任天堂","sex":null,"acquisitionTime":null,"statusId":null,"age":null,"borrowing":null,"sourceId":null,"auditId":null,"frequency":null,"qq":null,"idCard":"123456123456123","appUserId":null,"loanMonery":null,"loanTime":null,"repayTime":null,"overday":null,"expectTime":null,"repayStatus":null,"remark":null,"zmScore":null},"workInformation":{"id":6,"userMobile":"13456213212","enterprise":"方法","enterprisePosition":"方法","position":"方法","workingHours":"额度","personalTreatment":"方法","name":null,"idCard":null,"bankCard":null}}
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
         * emergentList : [{"id":16,"emergentName":"推广","emergentPhone":"15625226978","relationship":"方法","userMobile":"13456213212","code":null,"flag":0},{"id":17,"emergentName":"消息","emergentPhone":"15625226999","relationship":"哥哥","userMobile":"13456213212","code":null,"flag":0},{"id":18,"emergentName":"消息","emergentPhone":"15625226888","relationship":"哥哥","userMobile":"13456213212","code":null,"flag":0},{"id":19,"emergentName":"消息","emergentPhone":"15625226666","relationship":"哥哥","userMobile":"13456213212","code":null,"flag":0},{"id":20,"emergentName":"消息","emergentPhone":"15625226111","relationship":"哥哥","userMobile":"13456213212","code":null,"flag":0}]
         * poolClientInfo : {"id":null,"number":"13456213212","name":"任天堂","sex":null,"acquisitionTime":null,"statusId":null,"age":null,"borrowing":null,"sourceId":null,"auditId":null,"frequency":null,"qq":null,"idCard":"123456123456123","appUserId":null,"loanMonery":null,"loanTime":null,"repayTime":null,"overday":null,"expectTime":null,"repayStatus":null,"remark":null,"zmScore":null}
         * workInformation : {"id":6,"userMobile":"13456213212","enterprise":"方法","enterprisePosition":"方法","position":"方法","workingHours":"额度","personalTreatment":"方法","name":null,"idCard":null,"bankCard":null}
         */

        private PoolClientInfoBean poolClientInfo;
        private WorkInformationBean workInformation;
        private List<EmergentListBean> emergentList;

        public PoolClientInfoBean getPoolClientInfo() {
            return poolClientInfo;
        }

        public void setPoolClientInfo(PoolClientInfoBean poolClientInfo) {
            this.poolClientInfo = poolClientInfo;
        }

        public WorkInformationBean getWorkInformation() {
            return workInformation;
        }

        public void setWorkInformation(WorkInformationBean workInformation) {
            this.workInformation = workInformation;
        }

        public List<EmergentListBean> getEmergentList() {
            return emergentList;
        }

        public void setEmergentList(List<EmergentListBean> emergentList) {
            this.emergentList = emergentList;
        }

        public static class PoolClientInfoBean {
            /**
             * id : null
             * number : 13456213212
             * name : 任天堂
             * sex : null
             * acquisitionTime : null
             * statusId : null
             * age : null
             * borrowing : null
             * sourceId : null
             * auditId : null
             * frequency : null
             * qq : null
             * idCard : 123456123456123
             * appUserId : null
             * loanMonery : null
             * loanTime : null
             * repayTime : null
             * overday : null
             * expectTime : null
             * repayStatus : null
             * remark : null
             * zmScore : null
             */

            private Object id;
            private String number;
            private String name;
            private Object sex;
            private Object acquisitionTime;
            private Object statusId;
            private Object age;
            private Object borrowing;
            private Object sourceId;
            private Object auditId;
            private Object frequency;
            private Object qq;
            private String idCard;
            private Object appUserId;
            private Object loanMonery;
            private Object loanTime;
            private Object repayTime;
            private Object overday;
            private Object expectTime;
            private Object repayStatus;
            private Object remark;
            private Object zmScore;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getAcquisitionTime() {
                return acquisitionTime;
            }

            public void setAcquisitionTime(Object acquisitionTime) {
                this.acquisitionTime = acquisitionTime;
            }

            public Object getStatusId() {
                return statusId;
            }

            public void setStatusId(Object statusId) {
                this.statusId = statusId;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public Object getBorrowing() {
                return borrowing;
            }

            public void setBorrowing(Object borrowing) {
                this.borrowing = borrowing;
            }

            public Object getSourceId() {
                return sourceId;
            }

            public void setSourceId(Object sourceId) {
                this.sourceId = sourceId;
            }

            public Object getAuditId() {
                return auditId;
            }

            public void setAuditId(Object auditId) {
                this.auditId = auditId;
            }

            public Object getFrequency() {
                return frequency;
            }

            public void setFrequency(Object frequency) {
                this.frequency = frequency;
            }

            public Object getQq() {
                return qq;
            }

            public void setQq(Object qq) {
                this.qq = qq;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public Object getAppUserId() {
                return appUserId;
            }

            public void setAppUserId(Object appUserId) {
                this.appUserId = appUserId;
            }

            public Object getLoanMonery() {
                return loanMonery;
            }

            public void setLoanMonery(Object loanMonery) {
                this.loanMonery = loanMonery;
            }

            public Object getLoanTime() {
                return loanTime;
            }

            public void setLoanTime(Object loanTime) {
                this.loanTime = loanTime;
            }

            public Object getRepayTime() {
                return repayTime;
            }

            public void setRepayTime(Object repayTime) {
                this.repayTime = repayTime;
            }

            public Object getOverday() {
                return overday;
            }

            public void setOverday(Object overday) {
                this.overday = overday;
            }

            public Object getExpectTime() {
                return expectTime;
            }

            public void setExpectTime(Object expectTime) {
                this.expectTime = expectTime;
            }

            public Object getRepayStatus() {
                return repayStatus;
            }

            public void setRepayStatus(Object repayStatus) {
                this.repayStatus = repayStatus;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Object getZmScore() {
                return zmScore;
            }

            public void setZmScore(Object zmScore) {
                this.zmScore = zmScore;
            }
        }

        public static class WorkInformationBean {
            /**
             * id : 6
             * userMobile : 13456213212
             * enterprise : 方法
             * enterprisePosition : 方法
             * position : 方法
             * workingHours : 额度
             * personalTreatment : 方法
             * name : null
             * idCard : null
             * bankCard : null
             */

            private int id;
            private String userMobile;
            private String enterprise;
            private String enterprisePosition;
            private String position;
            private String workingHours;
            private String personalTreatment;
            private Object name;
            private Object idCard;
            private Object bankCard;

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

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getIdCard() {
                return idCard;
            }

            public void setIdCard(Object idCard) {
                this.idCard = idCard;
            }

            public Object getBankCard() {
                return bankCard;
            }

            public void setBankCard(Object bankCard) {
                this.bankCard = bankCard;
            }
        }

        public static class EmergentListBean {
            /**
             * id : 16
             * emergentName : 推广
             * emergentPhone : 15625226978
             * relationship : 方法
             * userMobile : 13456213212
             * code : null
             * flag : 0
             */

            private int id;
            private String emergentName;
            private String emergentPhone;
            private String relationship;
            private String userMobile;
            private Object code;
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

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
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
