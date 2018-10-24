package com.example.administrator.dataecs.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/7/11.
 */

public class SharePreferencesUtil {

    /**
     * 共享参数文件名
     */
    static final String SHARED_NAME = "Dataecs";

    /**
     * 是否登录
     */
    static final String IS_LOGIN = "IS_LOGIN";

    /**
     * 用户的信息
     */
    static final String USER_INFO = "USER_INFO";
    static final String USER_ID = "USER_INFO";
    static final String USEER_Name = "USEER_Name";
    /**
     * 审核开关
     */
    static final String KAI_GUANG_SWICH="KAI_GUANG_SWICH";
    static final String HOUTAI_SWITCH = "HOUTAI_SWITCH";
    public static void saveLogin(Context context) {
        SharedPreferences shared = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        shared.edit().putBoolean(IS_LOGIN, false).commit();

    }
    public static boolean isLogin(Context context) {
        SharedPreferences shared = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        return shared.getBoolean(IS_LOGIN, true);
    }


    public static void saveUserInfo(Context context, String usedId) {
        SharedPreferences shared = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(USEER_Name, usedId);
        editor.commit();
    }

    public static String getUserId(Context context) {
        SharedPreferences shared = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return shared.getString(USER_ID, "");
    }

    public static String getUserName(Context context) {
        SharedPreferences shared = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        return shared.getString(USEER_Name, "");
    }

    public static void saveHouTaiSwich(Context context, boolean HouTaiSwitch) {
        SharedPreferences share = context.getSharedPreferences(KAI_GUANG_SWICH, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putBoolean(HOUTAI_SWITCH, HouTaiSwitch);
        editor.commit();
    }

    public static boolean getHouTaiSwich(Context context) {
        SharedPreferences shared = context.getSharedPreferences(KAI_GUANG_SWICH, Context.MODE_PRIVATE);

        return shared.getBoolean(HOUTAI_SWITCH, true);
    }

    /**
     * 贷款申请审核状态
     */
    static final String DAI_KUANG_SHENHE="DAI_KUANG_SHENHE";
    static final String DAI_KUANG_SHENHE_KEY = "DAI_KUANG_SHENHE_KEY";

    public static void saveShenQing(Context context, int isSave){
        SharedPreferences share = context.getSharedPreferences(DAI_KUANG_SHENHE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putInt(DAI_KUANG_SHENHE_KEY, isSave);

        editor.commit();
    }

    public static int getShenQing(Context context) {
        SharedPreferences shared = context.getSharedPreferences(DAI_KUANG_SHENHE, Context.MODE_PRIVATE);
        return shared.getInt(DAI_KUANG_SHENHE_KEY,4);
    }


    /**
     * 贷款还款日期
     */
    static final String DAI_KUANG_SHENHE_DAY="DAI_KUANG_SHENHE_DAY";
    static final String DAI_KUANG_SHENHE_DAY_KEY = "DAI_KUANG_SHENHE_DAY_KEY";

    public static void saveTime(Context context, String time){
        SharedPreferences share = context.getSharedPreferences(DAI_KUANG_SHENHE_DAY, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(DAI_KUANG_SHENHE_DAY_KEY, time);

        editor.commit();
    }

    public static String getTime(Context context) {
        SharedPreferences shared = context.getSharedPreferences(DAI_KUANG_SHENHE_DAY, Context.MODE_PRIVATE);
        return shared.getString(DAI_KUANG_SHENHE_DAY_KEY,"");
    }

    /**
     * 贷款还款金额
     */
    static final String DAI_KUANG_SHENHE_MONEY="DAI_KUANG_SHENHE_MONEY";
    static final String DAI_KUANG_SHENHE_MONEY_KEY = "DAI_KUANG_SHENHE_MONEY_KEY";

    public static void saveMoney(Context context, String money){
        SharedPreferences share = context.getSharedPreferences(DAI_KUANG_SHENHE_MONEY, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(DAI_KUANG_SHENHE_MONEY_KEY, money);

        editor.commit();
    }

    public static String getMoney(Context context) {
        SharedPreferences shared = context.getSharedPreferences(DAI_KUANG_SHENHE_MONEY, Context.MODE_PRIVATE);
        return shared.getString(DAI_KUANG_SHENHE_MONEY_KEY,"");
    }

    /**
     * 判断紧急联系人是否完善
     */
    static final String JIN_JI_INFO_PERFECT="JIN_JI_INFO_PERFECT";
    static final String JIN_JI_INFO_PERFECT_KEY = "JIN_JI_INFO_PERFECT_KEY";

    public static void saveJinJiPefect(Context context, boolean isPerfect){
        SharedPreferences share = context.getSharedPreferences(JIN_JI_INFO_PERFECT, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putBoolean(JIN_JI_INFO_PERFECT_KEY, isPerfect);

        editor.commit();
    }

    public static boolean getJinJiPefect(Context context) {
        SharedPreferences shared = context.getSharedPreferences(JIN_JI_INFO_PERFECT, Context.MODE_PRIVATE);
        return shared.getBoolean(JIN_JI_INFO_PERFECT_KEY,false);
    }
}
