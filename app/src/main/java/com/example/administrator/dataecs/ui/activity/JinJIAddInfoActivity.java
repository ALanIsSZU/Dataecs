package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.JinJiAddModle;
import com.example.administrator.dataecs.model.JinJiRequestModle;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

public class JinJIAddInfoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.id_cord)
    EditText qinShu;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jin_ji_layout);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {
        back.setVisibility(View.VISIBLE);
        title.setText("添加紧急联系人");
    }


    @OnClick({R.id.back, R.id.commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.commit_btn:
                String loginPhone = SharePreferencesUtil.getUserName(this);

                if (name.getText().toString() == null || "".equals(name.getText().toString())) {
                    ToastUntils.ToastShort(JinJIAddInfoActivity.this, "输入正确名字");
                    return;
                }
                if (qinShu.getText().toString() == null || "".equals(qinShu.getText().toString())) {
                    ToastUntils.ToastShort(JinJIAddInfoActivity.this, "输入正确亲属关系");
                    return;
                }
                if (phone.getText().toString() == null || "".equals(phone.getText().toString()) || !StringUtil.isMobileNO(phone.getText().toString())) {
                    ToastUntils.ToastShort(JinJIAddInfoActivity.this, "输入正确的手机号码");
                    return;
                }
                if (phone.getText().toString() ==loginPhone) {
                    ToastUntils.ToastShort(JinJIAddInfoActivity.this, "无法将自己设为紧急联系人,请重新输入");
                    return;
                }

                //封装成一个json传给后台
                JinJiAddModle modle = new JinJiAddModle();
                modle.setUserMobile(loginPhone);
                modle.setEmergentName(name.getText().toString());
                modle.setEmergentPhone(phone.getText().toString());
                modle.setRelationship(qinShu.getText().toString());

                String json = new Gson().toJson(modle);
                commitInfo(json);
                break;
        }
    }

    //提交信息
    public void commitInfo(String json) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<JinJiRequestModle> call = inte.commitJinJiInfo(body);
        call.enqueue(new Callback<JinJiRequestModle>() {
            @Override
            public void onResponse(Call<JinJiRequestModle> call, Response<JinJiRequestModle> response) {
                JinJiRequestModle modle = response.body();
                if (modle.getMap().getCode() == 0) {
                    ToastUntils.ToastShort(JinJIAddInfoActivity.this, "提交成功");
                    SharePreferencesUtil.saveJinJiPefect(JinJIAddInfoActivity.this,true);
                    finish();
                } else {
                    ToastUntils.ToastShort(JinJIAddInfoActivity.this, "填写信息的手机已存在，请重新填写");
                }
            }

            @Override
            public void onFailure(Call<JinJiRequestModle> call, Throwable t) {
                ToastUntils.ToastShort(JinJIAddInfoActivity.this, "提交失败，请检查网络!");
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


}
