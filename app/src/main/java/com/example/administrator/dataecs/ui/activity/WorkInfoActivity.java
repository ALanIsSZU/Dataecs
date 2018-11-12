package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.CompanyInfoModle;
import com.example.administrator.dataecs.model.CompanyModel;
import com.example.administrator.dataecs.model.WorkInfoModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
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

public class WorkInfoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.change_info)
    TextView changeInfo;
    @BindView(R.id.company_name)
    EditText companyName;
    @BindView(R.id.company_location)
    EditText companyLocation;
    @BindView(R.id.company_work)
    EditText companyWork;
    @BindView(R.id.work_time)
    EditText workTime;
    @BindView(R.id.work_money)
    EditText workMoney;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.work_info)
    TextView workInfo;

    //工作信息字段
    String groupName;
    String groupLocation;
    String groupWork;
    String groupTime;
    String groupMoney;
    CompanyInfoModle info;
    //是否有完善信息
    boolean isperfect = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_info_layout);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {
        back.setVisibility(View.VISIBLE);
        title.setText("工作信息");
        Intent intent=getIntent();
        isperfect=intent.getBooleanExtra("work",false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isperfect) {
            workInfo.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            String loginPhone= SharePreferencesUtil.getUserName(this);
            getWorkInfo(loginPhone);

        } else {
            workInfo.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.back, R.id.change_info, R.id.company_name, R.id.company_location, R.id.company_work, R.id.work_time, R.id.work_money, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent=new Intent();
                intent.putExtra("work_go",isperfect);
                setResult(BaseServer.WORK_TO_MAIN,intent);
                finish();
                break;

            case R.id.login_btn:
                if (companyName.getText().toString() == null || "".equals(companyName.getText().toString())) {
                    ToastUntils.ToastShort(WorkInfoActivity.this, "输入正确的企业名");
                    return;
                }

                if (companyLocation.getText().toString() == null || "".equals(companyLocation.getText().toString())) {
                    ToastUntils.ToastShort(WorkInfoActivity.this, "输入正确的企业位置");
                    return;
                }

                if (companyWork.getText().toString() == null || "".equals(companyWork.getText().toString())) {
                    ToastUntils.ToastShort(WorkInfoActivity.this, "输入正确的职位");
                    return;
                }

                if (workTime.getText().toString() == null || "".equals(workTime.getText().toString())) {
                    ToastUntils.ToastShort(WorkInfoActivity.this, "输入正确的工作时长");
                    return;
                }

                if (workMoney.getText().toString() == null || "".equals(workMoney.getText().toString())) {
                    ToastUntils.ToastShort(WorkInfoActivity.this, "输入正确的个人待遇");
                    return;
                }

                groupName = companyName.getText().toString();
                groupLocation = companyLocation.getText().toString();
                groupMoney = workMoney.getText().toString();
                groupWork = companyWork.getText().toString();
                groupTime = workTime.getText().toString();
                String phone = SharePreferencesUtil.getUserName(WorkInfoActivity.this);

                info = new CompanyInfoModle();
                info.setEnterprise(groupName);
                info.setEnterprisePosition(groupLocation);
                info.setPersonalTreatment(groupMoney);
                info.setPosition(groupWork);
                info.setUserMobile(phone);
                info.setWorkingHours(groupTime);
                String json = new Gson().toJson(info);
                commitWorkInfo(json);
                break;

        }
    }


    //提交工作信息
    private void commitWorkInfo(String json) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<CompanyModel> call = inte.getCompanyInfo(body);
        call.enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                CompanyModel model = response.body();

                if (model.getCode()==0) {
                    workInfo.setText(
                            "所在企业：" + groupName + "\n"
                                    + "企业所在位置：" + groupLocation + "\n"
                                    + "职位：" + groupWork + "\n"
                                    + "工作时长：" + groupTime + "\n"
                                    + "个人待遇：" + groupMoney + "\n"
                    );

                    scrollView.setVisibility(View.GONE);
                    changeInfo.setVisibility(View.VISIBLE);
                    workInfo.setVisibility(View.VISIBLE);
                    isperfect=true;

                    ToastUntils.ToastShort(WorkInfoActivity.this, "提交成功");
                }

            }

            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
                ToastUntils.ToastShort(WorkInfoActivity.this, "提交失败,请检测网络");
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

    //获取工作信息
    public void getWorkInfo(String phone){

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<WorkInfoModel> call=inte.getWorkInfo(phone);
        call.enqueue(new Callback<WorkInfoModel>() {
            @Override
            public void onResponse(Call<WorkInfoModel> call, Response<WorkInfoModel> response) {

                WorkInfoModel model=response.body();
                if (model.getMap().getCode()==0){
                    workInfo.setText(
                            "所在企业：" + model.getMap().getResult().getEnterprise() + "\n"
                                    + "企业所在位置：" + model.getMap().getResult().getEnterprisePosition() + "\n"
                                    + "职位：" + model.getMap().getResult().getPosition() + "\n"
                                    + "工作时长：" + model.getMap().getResult().getWorkingHours() + "\n"
                                    + "个人待遇：" + model.getMap().getResult().getPersonalTreatment() + "\n"
                    );

                }

            }

            @Override
            public void onFailure(Call<WorkInfoModel> call, Throwable t) {
                ToastUntils.ToastShort(WorkInfoActivity.this,t.toString());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.putExtra("work_go",isperfect);
            setResult(BaseServer.WORK_TO_MAIN,intent);
            finish();
        }

        return true;
    }
}
