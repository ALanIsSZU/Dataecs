package com.example.administrator.dataecs.model;

/**
 * Created by Administrator on 2018/7/16.
 */

public class MainlistItem {

    int sourceid;
    String source;
    String explainDetail;

    String remark1;

    String payStart;
    String payEnd;

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }


    public int getSourceid() {
        return sourceid;
    }

    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExplainDetail() {
        return explainDetail;
    }

    public void setExplainDetail(String explainDetail) {
        this.explainDetail = explainDetail;
    }

    public String getPayStart() {
        return payStart;
    }

    public void setPayStart(String payStart) {
        this.payStart = payStart;
    }

    public String getPayEnd() {
        return payEnd;
    }

    public void setPayEnd(String payEnd) {
        this.payEnd = payEnd;
    }
}
