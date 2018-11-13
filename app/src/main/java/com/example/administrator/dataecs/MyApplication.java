package com.example.administrator.dataecs;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.xinyan.bigdata.XinYanSDK;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class MyApplication extends Application{

    public static List<LivenessTypeEnum> livenessList = new ArrayList<LivenessTypeEnum>();
    public static boolean isLivenessRandom = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        initAccessTokenLicenseFile();
    }

    public void initAccessTokenLicenseFile(){

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        //初始化百度的身份证识别API
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                ToastUntils.ToastShort(MyApplication.this,"百度Api获取token失败,"+error.toString());
            }
        }, getApplicationContext());

        //初始化新颜的sdk
        XinYanSDK.getInstance().init(this);

        // 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
        // 应用上下文
        // 申请License取得的APPID
        // assets目录下License文件名
        FaceSDKManager.getInstance().initialize(this, Config.licenseID, Config.licenseFileName);
    }


}
