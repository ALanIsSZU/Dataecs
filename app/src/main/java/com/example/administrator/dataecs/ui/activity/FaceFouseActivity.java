package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.CommitFaceInfoModel;
import com.example.administrator.dataecs.model.PersonRequestModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaceFouseActivity extends FaceLivenessActivity {

    PopupWindow popupWindow;
    //提交的图片
    String facePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindows_lay, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);

    }

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            ToastUntils.ToastShort(FaceFouseActivity.this, "活体检测,检测成功");
            popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

            facePath = base64ImageMap.get("Mouth");

            Message msg = Message.obtain();
            msg.what = 1;
            handler.sendMessage(msg);

        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            ToastUntils.ToastShort(FaceFouseActivity.this, "活体检测,采集超时");
            finish();
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 1:

                    CommitFaceInfoModel model = new CommitFaceInfoModel();
                    model.setTbUserId((Long) SPUtils.get(FaceFouseActivity.this, Config.USED_ID, 1L));
                    model.setFace(facePath);
                    String json = new Gson().toJson(model);
                    commitImgInfo(json);
                    break;

            }
        }
    };

    public void commitImgInfo(String json) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(FaceFouseActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<PersonRequestModel> call = inte.commitFaceFouseInfo(body);
        call.enqueue(new Callback<PersonRequestModel>() {
            @Override
            public void onResponse(Call<PersonRequestModel> call, Response<PersonRequestModel> response) {
                PersonRequestModel model = response.body();
                if (model.getCode() == 0) {

                    ToastUntils.ToastShort(FaceFouseActivity.this, "提交成功");
                    SPUtils.put(FaceFouseActivity.this, Config.FACE_FOUSE_PERFECT, true);
                    dismissWindows();
                    finish();
                } else {
                    ToastUntils.ToastShort(FaceFouseActivity.this, "提交失败");
                    dismissWindows();
                }
            }

            @Override
            public void onFailure(Call<PersonRequestModel> call, Throwable t) {
                ToastUntils.ToastShort(FaceFouseActivity.this, "请求失败" + t.toString());
                dismissWindows();
            }
        });
    }

    public static OkHttpClient genericClient(final RequestBody body) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        request = requestBuilder
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .post(body)//关键部分，设置requestBody的编码格式为json
                                .build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        return httpClient;
    }

    private void dismissWindows() {
        if (popupWindow == null) {

        } else {
            if (popupWindow.isShowing()) {

                popupWindow.dismiss();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
