package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.FindPasswordModel;
import com.example.administrator.dataecs.model.LoginNewModle;
import com.example.administrator.dataecs.model.PhoneListModel;
import com.example.administrator.dataecs.model.RegisterCommitModel;
import com.example.administrator.dataecs.model.VerificationCodeModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.example.administrator.dataecs.weight.CountDownTextView;
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

import static com.example.administrator.dataecs.R.id.phone_num;

public class RegisterActivity extends AppCompatActivity {

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
    @BindView(R.id.pass_code)
    EditText passCode;
    @BindView(R.id.second_pass_code)
    EditText secondPassCode;
    @BindView(R.id.register_linear)
    LinearLayout registerLinear;
    @BindView(R.id.loading_txt)
    TextView loadingTxt;
    @BindView(R.id.content_lay)
    LinearLayout contentLay;
    @BindView(R.id.loading_lay)
    View loadingLay;


    //用户条款的选择
    private boolean isSlect = true;

    //验证码按钮是否点击
    private boolean isYanSlect = false;

    //短信验证 isDury ：true模拟发送 false真实发送
    private boolean isDury = Config.isDury;

    //通讯录
    PhoneListModel ss;

    //判断是注册页面还是找回密码界面
    boolean isRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ButterKnife.bind(this);
        intView();

    }

    private void intView() {
        ss = new PhoneListModel();
        back.setVisibility(View.VISIBLE);
        passWordNew.setVisibility(View.GONE);
        passWordLine.setVisibility(View.GONE);
        countDownTextView.setCountDownColor(R.color.new_title, R.color.new_title);
        loadingTxt.setText("加载中。。。");

        Intent intent = getIntent();
        isRegister = intent.getBooleanExtra("isRegister", true);
        agreemen.setText(Html.fromHtml("注册即表示您已阅读，并同意<font color='#64B8FF'><small>《用户协议》</small></font>"));
        if (isRegister) {
            title.setText("注册");
            loginBtn.setText("注册");
            registerLinear.setVisibility(View.VISIBLE);
        } else {

            title.setText("找回密码");
            loginBtn.setText("完成");
            registerLinear.setVisibility(View.GONE);
        }

        //设置EditText的属性
        SpannableString phoneTips = new SpannableString("请输入手机号");
        AbsoluteSizeSpan tipSise = new AbsoluteSizeSpan(15, true);
        phoneTips.setSpan(tipSise, 0, phoneTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        phoneNum.setHint(phoneTips);

        SpannableString passwordTips = new SpannableString("请输入验证码");
        AbsoluteSizeSpan passwordSize = new AbsoluteSizeSpan(15, true);
        passwordTips.setSpan(passwordSize, 0, passwordTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        password.setHint(passwordTips);

        SpannableString passCodeTips = new SpannableString("请设置6-16位密码");
        AbsoluteSizeSpan passCodedSize = new AbsoluteSizeSpan(15, true);
        passCodeTips.setSpan(passCodedSize, 0, passCodeTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        passCode.setHint(passCodeTips);

        SpannableString secondPassCodeTips = new SpannableString("请再次输入设置6-16位密码");
        AbsoluteSizeSpan secondPassCodeSize = new AbsoluteSizeSpan(15, true);
        secondPassCodeTips.setSpan(secondPassCodeSize, 0, secondPassCodeTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        secondPassCode.setHint(secondPassCodeTips);


    }


    @OnClick({R.id.back, R.id.agreemen, R.id.login_btn, R.id.countDownTextView, R.id.select_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.agreemen:
//                Intent intent = new Intent(this, WebActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("title", "用户协议");
//                bundle.putString("url", BaseServer.YONGHU);
//                intent.putExtra("data", bundle);
//                startActivity(intent);
                Intent agreement = new Intent(RegisterActivity.this, AgreementActivity.class);
                agreement.putExtra("isRegister", true);
                startActivity(agreement);

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

                if (isRegister) {

                    if (!isSlect) {
                        Toast.makeText(this, "请勾选《用户协议》", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (passCode.getText().toString() == null || passCode.getText().toString().trim().length() < 6
                        || passCode.getText().toString().length() > 16) {
                    Toast.makeText(this, "输入正确6-16位密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (secondPassCode.getText().toString() == null || secondPassCode.getText().toString().trim().length() < 6
                        || secondPassCode.getText().toString().length() > 16) {
                    Toast.makeText(this, "输入正确6-16位密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!secondPassCode.getText().toString().trim().equals(passCode.getText().toString().trim())) {
                    Toast.makeText(this, "两次输入的密码不相同", Toast.LENGTH_SHORT).show();
                    return;
                }
                //获取通讯录
                /*andPermissionTools(BaseServer.REQUEST_Mail, false,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS);*/

                if (isRegister) {
                    //注册页面逻辑
                    RegisterCommitModel commitModel = new RegisterCommitModel();
                    commitModel.setMobile(phoneNum.getText().toString().trim());
                    commitModel.setSmscode(password.getText().toString().trim());
                    commitModel.setPassword(passCode.getText().toString().trim());
                    commitModel.setRoleid(Config.roleId);
                    commitModel.setSourceid(Config.sourceid);
                    String json = new Gson().toJson(commitModel);
                    registerInfo(json);

                } else {
                    //找回密码逻辑
                    FindPasswordModel findModel = new FindPasswordModel();
                    findModel.setMobile(phoneNum.getText().toString().trim());
                    findModel.setSmscode(password.getText().toString().trim());
                    findModel.setNewPassword(passCode.getText().toString().trim());
                    findModel.setRoleid(Config.roleId);
                    String findJson = new Gson().toJson(findModel);
                    findPassword(findJson);
                }

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

    //找回密码
    public void findPassword(String json) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(RegisterActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<LoginNewModle> call = inte.commitFindPassword(body);
        call.enqueue(new Callback<LoginNewModle>() {
            @Override
            public void onResponse(Call<LoginNewModle> call, Response<LoginNewModle> response) {

                LoginNewModle modle = response.body();

                if ("success".equals(modle.getMap().getRes())) {
                    ToastUntils.ToastShort(RegisterActivity.this, modle.getMap().getMsg());
                    finish();
                } else {
                    ToastUntils.ToastShort(RegisterActivity.this, modle.getMap().getMsg());
                }
            }

            @Override
            public void onFailure(Call<LoginNewModle> call, Throwable t) {
                ToastUntils.ToastShort(RegisterActivity.this, "请求失败！" + t.toString());
            }
        });

    }

    //注册
    public void registerInfo(String json) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(RegisterActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<LoginNewModle> call = inte.registerInfo(body);
        call.enqueue(new Callback<LoginNewModle>() {
            @Override
            public void onResponse(Call<LoginNewModle> call, Response<LoginNewModle> response) {
                LoginNewModle modle = response.body();

                if ("success".equals(modle.getMap().getRes())) {
                    ToastUntils.ToastShort(RegisterActivity.this, modle.getMap().getMsg());
                    finish();
                } else {
                    ToastUntils.ToastShort(RegisterActivity.this, modle.getMap().getMsg());
                }

            }

            @Override
            public void onFailure(Call<LoginNewModle> call, Throwable t) {
                ToastUntils.ToastShort(RegisterActivity.this, "请求失败！" + t.toString());
            }
        });
    }

    //发送验证码
    public void getVerificationCode() {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(RegisterActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
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
                    Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerificationCodeModel> call, Throwable t) {
                Log.d("123", t.toString());
//                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* //登录请求

    public void Login(String json) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(RegisterActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
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
                    Toast.makeText(RegisterActivity.this, modle.getResult().getMsg(), Toast.LENGTH_SHORT).show();

                    String userName = modle.getResult().getUsrname();
                    SharePreferencesUtil.saveUserInfo(RegisterActivity.this, userName);
                    SharePreferencesUtil.saveShenQing(RegisterActivity.this, modle.getResult().getStatus());
                    //账户和身份认证
                    SPUtils.put(RegisterActivity.this, BaseServer.ID_INFORMATION, modle.getResult().isPerfectIdentity());
                    SPUtils.put(RegisterActivity.this, BaseServer.BANCK_INFORMATION, modle.getResult().isPerfectMaterial());
                    //用户是否全部认证
                    if (modle.getResult().isPerfectIdentity() && modle.getResult().isPerfectMaterial()) {
                        SPUtils.put(RegisterActivity.this, BaseServer.ALL_ATTESTATION, true);
                    } else {
                        SPUtils.put(RegisterActivity.this, BaseServer.ALL_ATTESTATION, false);
                    }

                    if (modle.getResult().getStatus() == 3) {

                        if (modle.getResult().getRepayTime() == null || "".equals(modle.getResult().getRepayTime())) {
                            SharePreferencesUtil.saveTime(RegisterActivity.this, "");
                        } else {

                            SharePreferencesUtil.saveTime(RegisterActivity.this, modle.getResult().getRepayTime());

                        }

                        if (modle.getResult().getLoanMonery() == null) {
                            SharePreferencesUtil.saveMoney(RegisterActivity.this, "");
                        } else {

                            SharePreferencesUtil.saveMoney(RegisterActivity.this, modle.getResult().getLoanMonery() + "");
                        }
                    }
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, modle.getResult().getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            @Override
            public void onFailure(Call<LoginNewModle> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
*/
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


    //获取手机通讯录信息
    /*public String getMailData(String mobile, String smscode) {

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
    }*/

    //-------------------------------------------权限检测开始---------------------------------------------------------------------//
   /* public void andPermissionTools(int code, final boolean cancelable, String... permissions) {

        AndPermission.with(this)
                .permission(permissions)
                .requestCode(code)
                .callback(listener)
                .start();

    }*/

   /* private PermissionListener listener = new PermissionListener() {
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
    };*/


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
