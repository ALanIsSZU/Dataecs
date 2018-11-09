package com.example.administrator.dataecs.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.LoginNewModle;
import com.example.administrator.dataecs.model.PhoneListModel;
import com.example.administrator.dataecs.model.VerificationCodeModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.weight.CountDownTextView;
import com.google.gson.Gson;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

import static com.example.administrator.dataecs.R.id.phone_num;

/**
 * Created by Administrator on 2018/7/4.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(phone_num)
    EditText phoneNum;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.agreemen)
    TextView agreemen;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.countDownTextView)
    CountDownTextView countDownTextView;
    @BindView(R.id.select_agreement)
    ImageView selectAgreement;
    @BindView(R.id.pass_word_new)
    EditText passWordNew;
    @BindView(R.id.pass_word_line)
    View passWordLine;

    //用户条款的选择
    private boolean isSlect = true;

    //验证码按钮是否点击
    private boolean isYanSlect = false;

    //短信验证 isDury ：true模拟发送 false真实发送
    private boolean isDury = false;

    //通讯录
    PhoneListModel ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);

        intView();
    }

    private void intView() {
        ss = new PhoneListModel();
        back.setVisibility(View.VISIBLE);
        passWordNew.setVisibility(View.GONE);
        passWordLine.setVisibility(View.GONE);
        title.setText("登录");
        //设置EditText的属性
        SpannableString phoneTips = new SpannableString("输入手机号");
        AbsoluteSizeSpan tipSise = new AbsoluteSizeSpan(15, true);
        phoneTips.setSpan(tipSise, 0, phoneTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        phoneNum.setHint(phoneTips);

        SpannableString passwordTips = new SpannableString("输入验证码");
        AbsoluteSizeSpan passwordSize = new AbsoluteSizeSpan(15, true);
        passwordTips.setSpan(passwordSize, 0, passwordTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        password.setHint(passwordTips);


    }


    @OnClick({R.id.back, R.id.agreemen, R.id.login_btn, R.id.countDownTextView, R.id.select_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.agreemen:
                Intent intent = new Intent(this, WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "用户协议");
                bundle.putString("url", BaseServer.YONGHU);
                intent.putExtra("data", bundle);
                startActivity(intent);
                break;
            case R.id.login_btn:
                if (!StringUtil.isMobileNO(phoneNum.getText().toString())) {
                    Toast.makeText(this, "输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isYanSlect) {
                    Toast.makeText(this, "请点击获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString() == null || password.getText().toString().length() < 6) {
                    Toast.makeText(this, "输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isSlect) {
                    Toast.makeText(this, "请勾选《用户协议》", Toast.LENGTH_SHORT).show();
                    return;
                }

                andPermissionTools(BaseServer.REQUEST_Mail, false,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS);

//                Login(phoneNum.getText().toString(), password.getText().toString());
                break;
            case R.id.countDownTextView:
                isYanSlect = true;
                if (!StringUtil.isMobileNO(phoneNum.getText().toString())) {
                    Toast.makeText(this, "输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    //重置验证码时间
                    countDownTextView.reset();
                } else {
                    //验证码倒计时
                    countDownTextView.setCountDownMillis(60 * 1000);
                    countDownTextView.start();
                    getVerificationCode();
                }
                break;

            case R.id.select_agreement:

                if (isSlect) {
                    selectAgreement.setImageResource(R.drawable.select_no);
                    isSlect = false;
                } else {
                    selectAgreement.setImageResource(R.drawable.select);
                    isSlect = true;
                }

                break;
        }
    }


    //获取手机通讯录信息
    public String getMailData(String mobile, String smscode) {

        List<PhoneListModel.BcBean> list = new ArrayList<>();
        Cursor cursor = getContentResolver().query(CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        if (cursor != null) {

            while (cursor.moveToNext()) {

                String name = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                PhoneListModel.BcBean bcBean = new PhoneListModel.BcBean();
                bcBean.setName(name);
                bcBean.setNumber(number);
                list.add(bcBean);
            }
        }
        cursor.close();
        ss.setBc(list);


        PhoneListModel.AbBean abBean = new PhoneListModel.AbBean();
        abBean.setSmscode(smscode);
        abBean.setMobile(mobile);
        ss.setAb(abBean);
        String s = new Gson().toJson(ss);
        return s;
    }

    //登录请求
    public void Login(String json) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(LoginActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);

        Call<LoginNewModle> call = inte.getLoginData(body);

        call.enqueue(new Callback<LoginNewModle>() {
            @Override
            public void onResponse(Call<LoginNewModle> call, Response<LoginNewModle> response) {
                LoginNewModle modle = response.body();

                if (modle.getResult().getRes().equals("sucess")) {
                    Toast.makeText(LoginActivity.this, modle.getResult().getMsg(), Toast.LENGTH_SHORT).show();

                    String userName = modle.getResult().getUsrname();
                    SharePreferencesUtil.saveUserInfo(LoginActivity.this, userName);
                    SharePreferencesUtil.saveShenQing(LoginActivity.this, modle.getResult().getStatus());
                    //账户和身份认证
                    SPUtils.put(LoginActivity.this,BaseServer.ID_INFORMATION,modle.getResult().isPerfectIdentity());
                    SPUtils.put(LoginActivity.this,BaseServer.BANCK_INFORMATION,modle.getResult().isPerfectMaterial());
                    //用户是否全部认证
                    if (modle.getResult().isPerfectIdentity() && modle.getResult().isPerfectMaterial()){
                        SPUtils.put(LoginActivity.this,BaseServer.ALL_ATTESTATION,true);
                    }else {
                        SPUtils.put(LoginActivity.this,BaseServer.ALL_ATTESTATION,false);
                    }

                    if (modle.getResult().getStatus() == 3) {

                        if (modle.getResult().getRepayTime() == null || "".equals(modle.getResult().getRepayTime())) {
                            SharePreferencesUtil.saveTime(LoginActivity.this, "");
                        } else {

                            SharePreferencesUtil.saveTime(LoginActivity.this, modle.getResult().getRepayTime());

                        }

                        if (modle.getResult().getLoanMonery() == null) {
                            SharePreferencesUtil.saveMoney(LoginActivity.this, "");
                        } else {

                            SharePreferencesUtil.saveMoney(LoginActivity.this, modle.getResult().getLoanMonery() + "");
                        }
                    }
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, modle.getResult().getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<LoginNewModle> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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


    //发送验证码
    public void getVerificationCode() {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(LoginActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<VerificationCodeModel> call = inte.getVerification(phoneNum.getText().toString(), isDury);
        call.enqueue(new Callback<VerificationCodeModel>() {
            @Override
            public void onResponse(Call<VerificationCodeModel> call, Response<VerificationCodeModel> response) {
                VerificationCodeModel model = response.body();
                if (model.getSendhRes().getResult().equals("0")) {
                    Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerificationCodeModel> call, Throwable t) {
                Log.d("123", t.toString());
//                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //-------------------------------------------权限检测开始---------------------------------------------------------------------//
    public void andPermissionTools(int code, final boolean cancelable, String... permissions) {

        AndPermission.with(this)
                .permission(permissions)
                .requestCode(code)
//                .rationale(new RationaleListener() {
//                    @Override
//                    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//
//                        builder.setTitle("授权界面")
//                                .setMessage("你已经取消授权，请打授权界面开启权限")
//                                .setCancelable(cancelable)
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        rationale.resume();
//
//                                    }
//                                }).show();
//                    }
//                })
                .callback(listener)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {

            if (requestCode == BaseServer.REQUEST_Mail) {
                String s = getMailData(phoneNum.getText().toString(), password.getText().toString());
                Login(s);
            }

        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (requestCode == BaseServer.REQUEST_Mail) {

                PhoneListModel.AbBean abBean = new PhoneListModel.AbBean();
                abBean.setSmscode(password.getText().toString());
                abBean.setMobile(phoneNum.getText().toString());
                ss.setAb(abBean);
                String s = new Gson().toJson(ss);
                Login(s);

            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (AndPermission.hasPermission(LoginActivity.this, Manifest.permission.READ_CONTACTS,
//                Manifest.permission.WRITE_CONTACTS)) {
//            String s = getMailData(phoneNum.getText().toString(), password.getText().toString());
//            Login(s);
//
//        } else {
//
//            PhoneListModel.AbBean abBean = new PhoneListModel.AbBean();
//            abBean.setSmscode(password.getText().toString());
//            abBean.setMobile(phoneNum.getText().toString());
//            ss.setAb(abBean);
//            String s = new Gson().toJson(ss);
//            Login(s);
//
//        }

    }
}
