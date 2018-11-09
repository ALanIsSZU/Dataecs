package com.example.administrator.dataecs;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.example.administrator.dataecs.util.ToastUntils;

/**
 * Created by Administrator on 2018/7/18.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        initAccessTokenLicenseFile();
    }

    //初始化百度的身份证识别API

    public void initAccessTokenLicenseFile(){
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
    }

}
