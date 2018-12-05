package com.example.administrator.dataecs.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.dataecs.ui.activity.TaoBaoActivity;
import com.xinyan.bigdata.XinYanSDK;
import com.xinyan.bigdata.bean.StartParams;
import com.xinyan.bigdata.bean.XinyanCallBackData;
import com.xinyan.bigdata.callback.XYBDResultCallback;


public class StartXYSDKUtil {

    private static String mTxnType;//任务类型
    private static Activity mActivity;

    /**
     * new service 调起sdk
     */

    /**
     * @param activity
     * @param txnType  业务类型 carrier，jinjiedao、、、、
     */
    public static void startSDK(Activity activity, String txnType) {
        mActivity = activity;
        mTxnType = txnType;
        getTask(mActivity, mTxnType, "");
    }

    public static void startSDK(Activity activity, String txnType, String orderInfo) {
        mActivity = activity;
        mTxnType = txnType;

        getTask(mActivity, mTxnType, orderInfo);
    }

    public static StartParams getTask(final Activity mActivity, String txnType, String tradeNo) {
        //转对象传参数
        StartParams startParams = new StartParams();
        startParams.setType(txnType);
        startParams.setTradeNo(tradeNo);
        startParams.setUser_id(tradeNo);//TODO  自己提供
        startParams.setRealname(ConfigUtil.realname);
        startParams.setIdcard(ConfigUtil.idcard);
        startParams.setPhoneNum(ConfigUtil.phoneNum);
        startParams.setPhoneServerCode(ConfigUtil.phoneServerCode);
        startParams.setCarrierCanInput(ConfigUtil.carrierCanInput);
        startParams.setCarrierIDandNameShow(ConfigUtil.carrierIDandNameShow);
        ConfigUtil.titleConfig.setToolbarbgcolor("#64B8FF");
        ConfigUtil.titleConfig.setThemecolor("#64B8FF");
        startParams.setTitleConfig(ConfigUtil.titleConfig);
        startParams.setNewService(Tools.isNewService(txnType));

        startParams.setTaskId((long) SPUtils.get(mActivity, Config.USED_ID, 1L) + "");//TODO    小于32  唯一字符串

        ConfigUtil.type = txnType;//缓存

        execuSDK(mActivity, startParams);
        return startParams;
    }

    private static void execuSDK(final Activity mActivity, StartParams startParams) {
        XinYanSDK.getInstance().start(mActivity, startParams, new XYBDResultCallback() {
            @Override
            public void onCallBack(XinyanCallBackData xinyanCallBackData) {
                openResultActivity(mActivity, xinyanCallBackData);
                ConfigUtil.titleConfig.setmTitle("");//这里是为了下次做任务不带之前设置的title，走默认的title
                ConfigUtil.token = xinyanCallBackData.getToken();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("aaa", "=======--==========" + throwable.getMessage());
                ConfigUtil.titleConfig.setmTitle("");
            }
        });
    }

    /**
     * 打开结果展示页
     */
    public static void openResultActivity(Context context, XinyanCallBackData data) {

        Bundle resultBundle = new Bundle();
        resultBundle.putParcelable("data", data);
        Intent intent = new Intent(context, TaoBaoActivity.class);
        intent.putExtras(resultBundle);
        context.startActivity(intent);
    }


}
