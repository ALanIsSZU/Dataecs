package com.example.administrator.dataecs.util;

/**
 * Created by Administrator on 2018/7/16.
 */

public class BaseServer {

    //Code
    public static final int REQUEST_SATRT = 1001;
    public static final int START_FAIL = 1002;
    public static final int REQUEST_Mail = 1003;
    public static final int MAIL_FAIL = 1004;
    public static final int ID_CARD_FRONT=1015;
    public static final int ID_CARD_BACK=1017;
    public static final int ID_CARD_FAIL=1016;

    public static final int PERSON_CODE = 1005;
    public static final int JIN_JI_CODE = 1006;
    public static final int BANK_CODE = 1007;
    public static final int WORK_CODE = 1008;

    public static final int PERSON_TO_MAIN = 1009;
    public static final int JIN_JI_MAIN = 1010;
    public static final int BANK_MAIN = 1011;
    public static final int WORK_TO_MAIN = 1012;
    public static final int BANCK_TO_ADDBANCK = 1013;
    public static final int ADDBANCK_TO_BANCK = 1014;
    public static final int REQUEST_CODE_CAMERA = 1020;
    public static final int FACE_FOCUS = 1021;
    public static final int FACE_FOCUS_FAIL = 1022;



    //SharePreferences的存储的关键字
    public static final String ID_INFORMATION = "ID_INFORMATION";
    public static final String BANCK_INFORMATION = "BANCK_INFORMATION";
    public static final String ALL_ATTESTATION = "ALL_ATTESTATION";

    //正服
//    public static final String BASE_URL = "http://120.79.85.227:80/renren-fast/";
    //测服
    public static final String BASE_URL = "http://192.168.0.168:8080/renren-fast/";

    //图片地址拼接
    public static final String BASE_PIC = "http://120.79.85.227";

    /**
     * 首页内容
     */
    public static final String MAIN_HEAD = "app/loanType";

    /**
     * 首页四个tab
     */
    public static final String MAIN_HEAD_TAB = "app/selectTypeSource";

    /**
     * 详情页面
     */
    public static final String SELECT_ITME_CONTENT = "app/selectSourceDetail";
    /**
     * 分类界面
     */
    public static final String CLASSI_FICATION = "app/classify";

    /**
     * 验证码
     */
    public static final String VERIFICATION_CODE = "app/getnote";
    /**
     * 登录
     */
    public static final String LOGIN = "app/login";

    /**
     * 获取版本号
     */
    public static final String UPDATE = "app/version";

    /**
     * 更新应用的URL
     */
    public static final String DOWNLOAD_URL = "file/downLoad";

    /**
     * 用户协议
     */
    public static final String YONGHU = "swagger/agreement.html";

    /**
     * 获取用户记录
     */
//    public static final String RECORD =  "app/getByPhone";
    public static final String RECORD = "api/getLoanRecord";
    /**
     * 提交资料申请
     */
    public static final String COMMIT_INFO = "app/updatePoolCilentInfo";
    /**
     * 查询审核状态
     */
    public static final String CHECK_TYPE =  "app/auditStatus";
    /**
     * 立即申请(第二次贷款)
     */
    public static final String SECOND_COMMIT = "app/applyImmediately";
    /**
     * 提交工作信息
     */
    public static final String COMPANY_COMMIT =  "app/addWorkInformation";
    /**
     * 获取紧急联系人列表
     */
    public static final String JIN_JI_INFO = "app/getEmergentInfo";
    /**
     * 提交紧急联系人信息
     */
    public static final String COMMIT_JIN_JI_NIFO =  "app/addEmergent";
    /**
     * 提交银行卡信息
     */
    public static final String COMMIT_BANCK_INFO =  "api/setCard";
    /**
     * 获取银行卡信息
     */
    public static final String GET_BANCK_INFO = "api/getCardInfo";

    /**
     * 获取验证中心全部信息完成状态
     */
    public static final String GET_INFO_PERFECT = "app/isPerfect";

    /**
     * 获取个人信息
     */
    public static final String GET_PERSON_INFO =  "app/showPoolCilentInfo";

    /**
     * 获取工作信息
     */
    public static final String GET_WORK_INFO = "app/getWorkInformation";

    /**
     * 获取支付宝的privatekey等信息
     */
    public static final String GET_ZHI_FU_BAO_INFO =  "AppAlipay/getApiInfo";

    /**
     * 获取跳转支付宝授权的URL
     */
    public static final String GO_ZHI_FU_URL = "AppAlipay/zhifubao";

    /**
     * 获取跳转支付宝授权的auth_info
     */
    public static final String GO_ZHI_FU_AUTH_INFO =  "AppAlipay/getOrderInfo";

    /**
     * 提交基本信息和资质要求
     */
    public static final String BASE_ZHI_INFO = "app/updatePersonInfo";

    /**
     * 获取身份认证的信息
     */
    public static final String REN_ZHEN_INFO =  "app/showPersonInfo";

    /**
     * 注册
     */
    public static final String REGISTER_INFO =  "app/register";
    /**
     * 找回密码
     */
    public static final String FIND_PASSWORD = "app/forgotPassword";
    /**
     * 身份证识别
     */
    public static final String ID_CARD_INFO = "app/useridcard/save";
    /**
     * 人脸识别
     */
    public static final String FACE_FOUSE_INFO = "app/userface/save";
    /**
     * 淘宝认证
     */
    public static final String TAO_BAO_ATTESTATION = "app/XYencrypt";
}
