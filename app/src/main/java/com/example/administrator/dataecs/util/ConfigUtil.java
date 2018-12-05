package com.example.administrator.dataecs.util;


import com.xinyan.bigdata.bean.TitleConfig;

/**
 * Created by Creedon.dong on 2018/5/3.
 * email:0426k@sina.com
 *
 * @name Xinyan-bigdata-sdk
 * @class name：com.xinyan.bigdatademo.utils
 * @change
 * @chang time
 * @class describe  演示demo 用到的数据
 */
public class ConfigUtil {

    public static TitleConfig titleConfig = new TitleConfig();
    public static String token;
    public static String tradeNo;
    public static String memberId;
    public static String type;
    public static String environment;

    //运营商
    public static String realname;//运营商
    public static String idcard;//运营商
    public static String phoneNum;//运营商
    public static String phoneServerCode;//运营商手机号服务密码
    public static String carrierCanInput = Constants.YES;//yes运营商页面可以输入
    public static String carrierIDandNameShow = Constants.NO;//是否显示用户名  默认不显示
    public static String resultPageBackColor;//解析页背景颜色
    public static String animViewColor;//解析页动画view颜色

}
