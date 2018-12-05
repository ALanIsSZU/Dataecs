package com.example.administrator.dataecs.model;

public class ZQCommitModel {


    /**
     * tbUserIndentId : 5
     * days : sdhfj
     * remark : sdfsdf
     */
    //订单
    private long tbUserIndentId;
    //展期时间
    private String days;
    //申请理由
    private String remark;

    public long getTbUserIndentId() {
        return tbUserIndentId;
    }

    public void setTbUserIndentId(long tbUserIndentId) {
        this.tbUserIndentId = tbUserIndentId;
    }


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
