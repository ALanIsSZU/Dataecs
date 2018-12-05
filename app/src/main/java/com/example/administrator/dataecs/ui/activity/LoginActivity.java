package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.LoginCommitModel;
import com.example.administrator.dataecs.model.PhoneListModel;
import com.example.administrator.dataecs.model.SuperLoginModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.example.administrator.dataecs.util.Tools;
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

/**
 * Created by Administrator on 2018/7/4.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.phone_num)
    EditText phoneNum;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.find_password)
    TextView findPassword;
    @BindView(R.id.register)
    TextView register;

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

        title.setText("登录");
        //设置EditText的属性
        SpannableString phoneTips = new SpannableString("输入手机号");
        AbsoluteSizeSpan tipSise = new AbsoluteSizeSpan(15, true);
        phoneTips.setSpan(tipSise, 0, phoneTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        phoneNum.setHint(phoneTips);

        SpannableString passwordTips = new SpannableString("输入6-16位密码");
        AbsoluteSizeSpan passwordSize = new AbsoluteSizeSpan(15, true);
        passwordTips.setSpan(passwordSize, 0, passwordTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        password.setHint(passwordTips);

        findPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        findPassword.getPaint().setAntiAlias(true);

        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        register.getPaint().setAntiAlias(true);
    }


    @OnClick({R.id.back, R.id.login_btn, R.id.find_password, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.login_btn:
                if (!StringUtil.isMobileNO(phoneNum.getText().toString())) {
                    Toast.makeText(this, "输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString() == null || password.getText().toString().trim().length() < 6
                        || password.getText().toString().trim().length() > 16) {
                    Toast.makeText(this, "输入正确6-16位密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                //获取通讯录
               /* andPermissionTools(BaseServer.REQUEST_Mail, false,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS);*/

//                Login(phoneNum.getText().toString(), password.getText().toString());

                LoginCommitModel model = new LoginCommitModel();
                model.setMobile(phoneNum.getText().toString().trim());
                model.setPassword(password.getText().toString().trim());
                model.setRoleid(Config.roleId);
                String json = new Gson().toJson(model);
                LoginNew(json);
                break;
            case R.id.find_password:

                Intent findPassword = new Intent(this, RegisterActivity.class);
                findPassword.putExtra("isRegister", false);
                startActivity(findPassword);
                break;
            case R.id.register:

                Intent registerIntent = new Intent(this, RegisterActivity.class);
                registerIntent.putExtra("isRegister", true);
                startActivity(registerIntent);
                break;
        }
    }

    //新的登录接口
    public void LoginNew(String json) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(LoginActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<SuperLoginModel> call = inte.getLoginData(body);
        call.enqueue(new Callback<SuperLoginModel>() {
            @Override
            public void onResponse(Call<SuperLoginModel> call, Response<SuperLoginModel> response) {

                SuperLoginModel model = response.body();
                if ("success".equals(model.getMap().getRes())) {

                    ToastUntils.ToastShort(LoginActivity.this, model.getMap().getMsg());
                    //存信息
                    SPUtils.put(LoginActivity.this, Config.TOKEN_TIME, model.getMap().getExpire());
                    SPUtils.put(LoginActivity.this, Config.PHONE_VALUE, model.getMap().getMobile());
                    SPUtils.put(LoginActivity.this, Config.USED_ID, model.getMap().getUserId());
                    SPUtils.put(LoginActivity.this, Config.TOKEN_VALUE, model.getMap().getToken());
                    SPUtils.put(LoginActivity.this, Config.MAX_MONEY, model.getMap().getParameter().getLimits());
                    SharePreferencesUtil.saveShenQing(LoginActivity.this, 0);

                    //认证状态

                    SPUtils.put(LoginActivity.this, Config.TAO_BAO_PERFECT, model.getMap().getUserStatus().getZhifubaoStatus() == 0 ? false : true);

                    SPUtils.put(LoginActivity.this, Config.ID_CARD_PERFECT, model.getMap().getUserStatus().getIdCardStatus() == 0 ? false : true);

                    SPUtils.put(LoginActivity.this, Config.ID_INFOMATION_PERFECT, model.getMap().getUserStatus().getDataStatus() == 0 ? false : true);

                    SPUtils.put(LoginActivity.this, Config.PHONE_STORE_PERFECT, model.getMap().getUserStatus().getOperatorStatus() == 0 ? false : true);

                    SPUtils.put(LoginActivity.this, Config.FACE_FOUSE_PERFECT, model.getMap().getUserStatus().getFaceStatus() == 0 ? false : true);

                    SPUtils.put(LoginActivity.this, Config.BANCK_PERFECT, model.getMap().getUserStatus().getBankStatus() == 0 ? false : true);

                    SPUtils.put(LoginActivity.this, Config.RATE_OF_INTEREST, model.getMap().getParameter().getRate());

                    //利率
                    SPUtils.put(LoginActivity.this, Config.RATE_OF_INTEREST,model.getMap().getParameter().getRate());

                    int[] size = new int[4];

                    String days = model.getMap().getParameter().getLoanDay();
                    String[] dayArray = days.split(",");

                    for (int i = 0; i < dayArray.length; i++) {

                        size[i] = Integer.valueOf(dayArray[i]).intValue();
                    }

                    if (dayArray.length < 4 ) {

                        for (int i = 0; i < size.length - dayArray.length; i++) {
                            size[dayArray.length + i ] = 0;
                        }
                    }

                    Tools.saveDay(LoginActivity.this, size[0], size[1], size[2], size[3]);

                    finish();

                } else {
                    ToastUntils.ToastShort(LoginActivity.this, model.getMap().getMsg());
                }

            }

            @Override
            public void onFailure(Call<SuperLoginModel> call, Throwable t) {
                ToastUntils.ToastShort(LoginActivity.this, "请求失败！" + t.toString());
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

    /*//登录请求(有传通讯录)
    public void Login(String json) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(LoginActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this, modle.getResult().getMsg(), Toast.LENGTH_SHORT).show();

                    String userName = modle.getResult().getUsrname();
                    SharePreferencesUtil.saveUserInfo(LoginActivity.this, userName);
                    SharePreferencesUtil.saveShenQing(LoginActivity.this, modle.getResult().getStatus());
                    //账户和身份认证
                    SPUtils.put(LoginActivity.this, BaseServer.ID_INFORMATION, modle.getResult().isPerfectIdentity());
                    SPUtils.put(LoginActivity.this, BaseServer.BANCK_INFORMATION, modle.getResult().isPerfectMaterial());
                    //用户是否全部认证
                    if (modle.getResult().isPerfectIdentity() && modle.getResult().isPerfectMaterial()) {
                        SPUtils.put(LoginActivity.this, BaseServer.ALL_ATTESTATION, true);
                    } else {
                        SPUtils.put(LoginActivity.this, BaseServer.ALL_ATTESTATION, false);
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

    }*/

    /*  //获取手机通讯录信息
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
    }*/

    //-------------------------------------------权限检测开始---------------------------------------------------------------------//
/*    public void andPermissionTools(int code, final boolean cancelable, String... permissions) {

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

    }*/
}
