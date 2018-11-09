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
import com.example.administrator.dataecs.model.BanckCommitModle;
import com.example.administrator.dataecs.model.JinJiRequestModle;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SPUtils;
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

public class AddBanckCardActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.id_cord)
    EditText idCord;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.yinhanka)
    EditText yinhanka;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    //登录手机号
    String loginPhone;

    boolean isPerfect=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banck_add_info_layout);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        title.setText("添加银行卡");
    }

    @OnClick({R.id.back, R.id.commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

                finish();
                break;

            case R.id.commit_btn:
                loginPhone = SharePreferencesUtil.getUserName(this);
                if ("".equals(name.getText().toString())) {
                    Toast.makeText(this, "输入正确名字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (idCord.getText().toString() == null || "".equals(idCord.getText().toString()) || !StringUtil.isTrueID(idCord.getText().toString())) {
                    Toast.makeText(this, "输入正确的身份证", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText().toString() == null || "".equals(phone.getText().toString()) || !StringUtil.isMobileNO(phone.getText().toString())) {
                    Toast.makeText(this, "输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (yinhanka.getText().toString().equals("") || yinhanka.getText().toString() == null || !StringUtil.checkBankCard(yinhanka.getText().toString())) {
                    Toast.makeText(this, "输入正确的银行卡", Toast.LENGTH_SHORT).show();
                    return;
                }

                BanckCommitModle banckInfo = new BanckCommitModle();
                banckInfo.setUserMobile(loginPhone);
                banckInfo.setName(name.getText().toString());
                banckInfo.setIdNumber(idCord.getText().toString());
                banckInfo.setCard(yinhanka.getText().toString());
                banckInfo.setPhone(phone.getText().toString());

                String json = new Gson().toJson(banckInfo);
                commitBankInfo(json);

                break;
        }
    }

    public void commitBankInfo(String json) {

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
        Call<JinJiRequestModle> call = inte.commiBanckInfo(body);

        call.enqueue(new Callback<JinJiRequestModle>() {
            @Override
            public void onResponse(Call<JinJiRequestModle> call, Response<JinJiRequestModle> response) {

                JinJiRequestModle modle = response.body();
                if (modle.getMap().getCode() == 0) {
                    //添加成功
                    ToastUntils.ToastShort(AddBanckCardActivity.this, modle.getMap().getResult());

                    SPUtils.put(AddBanckCardActivity.this,BaseServer.BANCK_INFORMATION,true);
                    finish();
                } else {
                    //添加失败
                    ToastUntils.ToastShort(AddBanckCardActivity.this, modle.getMap().getResult());
                }

            }

            @Override
            public void onFailure(Call<JinJiRequestModle> call, Throwable t) {
                ToastUntils.ToastShort(AddBanckCardActivity.this, "提交失败，请检查网络");
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
