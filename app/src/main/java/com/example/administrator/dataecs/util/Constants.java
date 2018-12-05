package com.example.administrator.dataecs.util;

public class Constants {

    //---------------默认key--------------
    // 生产
    public static final String APIUSER_PRODUCT = "8150716192";
    public static final String APIKEY_PRODUCT = "f275fde017e44bb29f79f186dcfe3422";
    public static final String TERMINAL_PRODUCT = "8150716192";
    public static final String MEMBERID_PRODUCT = "8150716192";

    /**
     * 对应的相关渠道；
     */
    public static class Function {
        public static final String FUNCTION_CARRIER = "carrier";//运营商
        public static final String FUNCTION_QQ = "qq";//QQ验证
        public static final String FUNCTION_ALIPAY = "alipay";//支付宝；
        public static final String FUNCTION_TAOBAO = "taobao";//淘宝
        public static final String FUNCTION_JINGDONG = "jingdong";//京东
        public static final String FUNCTION_CHSI = "chsi";//学信网
        public static final String FUNCTION_FUND = "fund";//公积金
        public static final String FUNCTION_DIDI = "didi";//滴滴；

        public static final String FUNCTION_SECURITY = "security";//社保
        public static final String FUNCTION_ONLINE_BANK = "bank";//网银账单
        public static final String FUNCTION_TAOBAOPAY = "taobaopay";//淘宝支付宝认证
        public static final String FUNCTION_YOUPINGZHENG = "youpingzheng";
        public static final String FUNCTION_JINJIEDAO = "jinjiedao";

    }
    public static class UrlManager {
        //测试预订单
        public static final String TEST_PREORDER_URL = "https://test.xinyan.com/gateway-data/data/v2/preOrder";
        //生产预订单
        public static final String PRODUCT_PREORDER_URL = "https://api.xinyan.com/gateway-data/data/v2/preOrder";
        public static final String TEST_NOTIFY_URL = "http://test.xinyan.com/data/test/member/notify";
        public static final String PRODUCT_NOTIFY_URL = "https://api.xinyan.com/data/test/member/notify";
        //获取结果url
        public static final String BASE_TEST_RESULTURL = "https://test.xinyan.com/data";
        public static final String BASE_PRODUCT_RESULTURL = "https://api.xinyan.com/data";

        public static final String TAOBAO_RESULT_URL = "/taobao/v3/userdata/{order}";
        public static final String JINGDONG_RESULT_URL = "/jingdong/v3/userdata/{order}";
        public static final String ALIPAY_RESULT_URL = "/alipay/v3/data/{order}";
        public static final String CARRIER_RESULT_URL = "/carrier/v2/mobile/{order}?mobile=";
        public static final String QQ_RESULT_URL = "/qq/v1/alldata/{order}";
        public static final String FUND_RESULT_URL = "/fund/v2/result/{order}";
        public static final String CHIS_RESULT_URL = "/chsi/v1/all/{order}";
        public static final String DIDI_RESULT_URL = "/didi/v1/alldata/{order}";
        public static final String MAIL_RESULT_URL = "/email/v3/bills/{orderNo}";
        public static final String SECURITY_RESULT_URL = "/security/v1/info/{orderNo}";
        public static final String BANK_RESULT_URL = "/bank/v2/cards/all/{orderNo}";
        public static final String TAOBAOPAY_RESULT_URL = "/taobaopay/v1/info/{orderNo}";

        //----------------------new  service ---------------------------
        public static final String NEW_SERVICE_RESULT_URL = "/api/user/data?apiUser=%s&apiEnc=%s&token=%s";// 任务数据查询接口

    }


    public static final String NO = "NO";
    public static final String YES = "YES";

}
