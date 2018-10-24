package com.example.administrator.dataecs.util;

/**
 * Created by Administrator on 2018/7/16.
 */

public class BaseServer {

    //权限返回的Code
    public static final int REQUEST_SATRT = 10001;
    public static final int START_FAIL = 10002;
    public static final int REQUEST_Mail = 10003;
    public static final int MAIL_FAIL = 10004;

    //Activity返回的code
    public static final int PERSON_CODE=10005;
    public static final int JIN_JI_CODE=10006;
    public static final int BANK_CODE=10007;
    public static final int WORK_CODE=10008;

    public static final int PERSON_TO_MAIN=10009;
    public static final int JIN_JI_MAIN=10010;
    public static final int BANK_MAIN=10011;
    public static final int WORK_TO_MAIN=10012;
    public static final int BANCK_TO_ADDBANCK=10013;
    public static final int ADDBANCK_TO_BANCK=10014;

    //正服
    public static final String BASE_URL = "http://120.79.85.227:8085/renren-fast/";
    //测服
//    public static final String BASE_URL = "http://192.168.0.168:8085/renren-fast/";

    //爬虫正服
//    public static final String  PYTHON_BASE_URL = "";
    //爬虫测服
    public static final String PYTHON_BASE_URL = "";

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
    public static final String YONGHU = BASE_URL + "swagger/agreement.html";

    /**
     * 获取用户记录
     */
    public static final String RECORD = BASE_URL + "app/getByPhone";
    /**
     * 提交资料申请
     */
    public static final String COMMIT_INFO = BASE_URL + "app/updatePoolCilentInfo";
    /**
     * 查询审核状态
     */
    public static final String CHECK_TYPE = BASE_URL + "app/auditStatus";
    /**
     * 立即申请
     */
    public static final String SECOND_COMMIT = BASE_URL + "app/applyImmediately";
    /**
     * 提交工作信息
     */
    public static final String COMPANY_COMMIT = BASE_URL + "app/addWorkInformation";
    /**
     * 获取紧急联系人列表
     */
    public static final String JIN_JI_INFO = BASE_URL + "app/getEmergentInfo";
    /**
     * 提交紧急联系人信息
     */
    public static final String COMMIT_JIN_JI_NIFO = BASE_URL + "app/addEmergent";
    /**
     * 提交银行卡信息
     */
    public static final String COMMIT_BANCK_INFO = BASE_URL + "api/setCard";
    /**
     * 获取银行卡信息
     */
    public static final String GET_BANCK_INFO = BASE_URL + "api/getCardInfo";

    /**
     * 获取验证中心全部信息完成状态
     */
    public static final String GET_INFO_PERFECT = BASE_URL + "app/isPerfect";

    /**
     * 获取个人信息
     */
    public static final String GET_PERSON_INFO = BASE_URL + "app/showPoolCilentInfo";

    /**
     * 获取工作信息
     */
    public static final String GET_WORK_INFO = BASE_URL + "app/getWorkInformation";

    /**
     * 获取支付宝的privatekey等信息
     */
    public static final String GET_ZHI_FU_BAO_INFO = BASE_URL + "AppAlipay/getApiInfo";

    /**
     * 获取跳转支付宝授权的URL
     */
    public static final String GO_ZHI_FU_URL = BASE_URL + "AppAlipay/zhifubao";

    /**
     * 获取跳转支付宝授权的auth_info
     */
    public static final String GO_ZHI_FU_AUTH_INFO = BASE_URL + "AppAlipay/getOrderInfo";
}
