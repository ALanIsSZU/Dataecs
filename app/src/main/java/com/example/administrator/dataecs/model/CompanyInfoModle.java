package com.example.administrator.dataecs.model;

public class CompanyInfoModle {



    /**
     * enterprise : string
     * enterprisePosition : string
     * id : 0
     * personalTreatment : string
     * position : string
     * userMobile : string
     * workingHours : string
     */

    private String userMobile;
    private String enterprise;
    private String enterprisePosition;
    private String position;
    private String workingHours;
    private String personalTreatment;
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public String getPersonalTreatment() {
        return personalTreatment;
    }

    public void setPersonalTreatment(String personalTreatment) {
        this.personalTreatment = personalTreatment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }
}
