package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.ZhiFuAutoInfoModel;
import com.example.administrator.dataecs.util.AuthResult;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SystemUntils;

import java.util.Map;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ZhiFuBaoActivity extends AppCompatActivity {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhi_fu_layout);
        ButterKnife.bind(this);
        intview();
    }

    private void intview() {

        getZhiFuInfo();


    }

    //获取后台传过来的支付宝的加密字段
    public void getZhiFuInfo() {

        if (!SystemUntils.isNetworkConnected(ZhiFuBaoActivity.this)) {
            Toast.makeText(ZhiFuBaoActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<ZhiFuAutoInfoModel> call = inte.getZhiFuAutoInfo();
        call.enqueue(new Callback<ZhiFuAutoInfoModel>() {
            @Override
            public void onResponse(Call<ZhiFuAutoInfoModel> call, Response<ZhiFuAutoInfoModel> response) {


                final ZhiFuAutoInfoModel model=response.body();

                Runnable authRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造AuthTask 对象
                        AuthTask authTask = new AuthTask(ZhiFuBaoActivity.this);
                        // 调用授权接口，获取授权结果
                        Map<String, String> result = authTask.authV2(model.getMap().getOrderInfo(), true);

                        Message msg = new Message();
                        msg.what = SDK_AUTH_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread authThread = new Thread(authRunnable);
                authThread.start();
            }

            @Override
            public void onFailure(Call<ZhiFuAutoInfoModel> call, Throwable t) {

            }
        });



    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(ZhiFuBaoActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(ZhiFuBaoActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


}
