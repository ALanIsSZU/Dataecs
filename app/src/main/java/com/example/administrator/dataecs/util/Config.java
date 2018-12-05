package com.example.administrator.dataecs.util;


public class Config {

    //短信验证 isDury ：true模拟发送 false真实发送
    public static boolean isDury = false;
//    public static boolean isDury = true;

    //用户的角色Id
    public static long roleId = 1L;
    //渠道ID
    public static final long sourceid = 3L;

    //新颜订环境  测试环境：test 正式环境：product 默认是生产
    public static String orderEn = "product";

    //新颜的apiUser和apiKey
    public static final String XyApiUser = "8150719872";
    public static final String XyApiKey = "6613d600d19941a094753830bd6fc0af";

    //    // 为了apiKey,secretKey为您调用百度人脸在线接口的，如注册，比对等。
//    // 为了的安全，建议放在您的服务端，端把人脸传给服务器，在服务端端
//    // license为调用sdk的人脸检测功能使用，人脸识别 = 人脸检测（sdk功能）  + 人脸比对（服务端api）
    public static String apiKey = "替换为你的apiKey(ak)";
    public static String secretKey = "替换为你的secretKey(sk)";
    public static String licenseID = "LyFri7JDAumZWVUt-face-android";
    public static String licenseFileName = "idl-license .face-android";
    /*
     * <p>
     * 每个开发者账号只能创建一个人脸库；groupID用于标识人脸库
     * <p>
     * 人脸识别 接口 https://aip.baidubce.com/rest/2.0/face/v3/search
     * 人脸注册 接口 https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add
     */
    public static String groupID = "替换为你的人脸组groupID";

    //SharedPreferences的key值
    public static final String PHONE_VALUE = "PHONE_VALUE";
    public static final String TOKEN_TIME = "TOKEN_TIME";
    public static final String USED_ID = "USED_ID";
    public static final String TOKEN_VALUE = "TOKEN_VALUE";
    public static final String MAX_MONEY = "MAX_MONEY";

    //资料认真是否完善
    public static final String ID_INFOMATION_PERFECT = "ID_INFOMATION_PERFECT";
    public static final String PHONE_STORE_PERFECT = "PHONE_STORE_PERFECT";
    public static final String TAO_BAO_PERFECT = "TAO_BAO_PERFECT";
    public static final String FACE_FOUSE_PERFECT = "FACE_FOUSE_PERFECT";
    public static final String ID_CARD_PERFECT = "ID_CARD_PERFECT";
    public static final String BANCK_PERFECT = "BANCK_PERFECT";

    public static final int ME_TO_BANCK = 1030;
    public static final int BANCK_BACK_ME = 1031;
    public static final int HK_TO_BANCK = 1032;
    public static final int BANCK_BACK_HK = 1033;
    public static final int DATA_TO_HK = 1034;
    public static final int HK_BACK_DATA = 1035;
    public static final int DATA_TO_ZQ = 1036;
    public static final int ZQ_BACK_DATA = 1037;

    //后台传过来的天数
    public static final String ONCE_SELECT_DAY = "ONCE_SELECT_DAY";
    public static final String SECOND_SELECT_DAY = "SECOND_SELECT_DAY";
    public static final String THIRD_SELECT_DAY = "THIRD_SELECT_DAY";
    public static final String FOURTH_SELECT_DAY = "FOURTH_SELECT_DAY";
    //年利率
    public static final String RATE_OF_INTEREST = "RATE_OF_INTEREST";
    //判断申请界面的是否可以刷新
    public static final String IS_FINISH_APP = "IS_FINISH_APP";

}