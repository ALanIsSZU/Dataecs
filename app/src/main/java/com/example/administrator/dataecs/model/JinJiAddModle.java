package com.example.administrator.dataecs.model;

import java.util.List;

public class JinJiAddModle {


    private List<BcBean> el;

    public List<BcBean> getBc() {
        return el;
    }

    public void setBc(List<BcBean> bc) {
        this.el = bc;
    }

    public static class BcBean {

        /**
         * emergentName : hjfhsj
         * emergentPhone : sadasd
         * relationship : relationship
         * userMobile : userMobile
         */

        private String emergentName;
        private String emergentPhone;
        private String relationship;
        private String userMobile;

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
    }


}
