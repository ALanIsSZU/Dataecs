package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SystemUntils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaoBaoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.yan_zhen_img)
    ImageView yanZhenImg;
    @BindView(R.id.yan_zhen)
    LinearLayout yanZhen;
    @BindView(R.id.phone_yan_zhen)
    EditText phoneYanZhen;
    @BindView(R.id.content_relate)
    RelativeLayout contentRelate;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.shen_txt)
    TextView shenTxt;
    @BindView(R.id.useName)
    EditText useName;


    //判断是用户登录还是输入手机验证码
    private boolean isLogin = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tao_bao_layout);
        ButterKnife.bind(this);
        intView();
        intData();

    }

    private void intView() {
        back.setVisibility(View.VISIBLE);
        title.setText("淘宝登录");

    }

    private void intData() {



    }

    @OnClick({R.id.back, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.login_btn:

                if (isLogin) {
                    //账号密码登录
                    if (useName.getText().toString() == null || "".equals(useName.getText().toString())) {
                        Toast.makeText(TaoBaoActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.getText().toString() == null || "".equals(password.getText().toString())) {
                        Toast.makeText(TaoBaoActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (verificationCode.getText().toString() == null || "".equals(verificationCode.getText().toString())) {
                        Toast.makeText(TaoBaoActivity.this, "请输入图片验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } else {
                    //输入手机验证码
                    if (phoneYanZhen.getText().toString() == null || "".equals(phoneYanZhen.getText().toString())) {
                        Toast.makeText(TaoBaoActivity.this, "请输入手机验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }


                }

                break;
        }
    }

    //提交用户和密码
    public void commitUserInfo(){
        if (!SystemUntils.isNetworkConnected(TaoBaoActivity.this)) {
            Toast.makeText(TaoBaoActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.PYTHON_BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
    }

    //提交验证码
    public void commitVerification(){

        if (!SystemUntils.isNetworkConnected(TaoBaoActivity.this)) {
            Toast.makeText(TaoBaoActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.PYTHON_BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);

    }

}
