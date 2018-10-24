package com.example.administrator.dataecs.model;

/**
 * Created by Administrator on 2018/7/17.
 */

public class VerificationCodeModel {


    /**
     * content : 500112
     * sendhRes : {"msgid":"a523bc8b302a463e97a31bb3c23a8571","result":"0","desc":"提交成功","failPhones":""}
     */

    private int content;
    private SendhResBean sendhRes;

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public SendhResBean getSendhRes() {
        return sendhRes;
    }

    public void setSendhRes(SendhResBean sendhRes) {
        this.sendhRes = sendhRes;
    }

    public static class SendhResBean {
        /**
         * msgid : a523bc8b302a463e97a31bb3c23a8571
         * result : 0
         * desc : 提交成功
         * failPhones :
         */

        private String msgid;
        private String result;
        private String desc;
        private String failPhones;

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getFailPhones() {
            return failPhones;
        }

        public void setFailPhones(String failPhones) {
            this.failPhones = failPhones;
        }
    }
}
