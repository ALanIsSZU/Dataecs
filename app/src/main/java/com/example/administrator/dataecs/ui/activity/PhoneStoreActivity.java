package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.weight.CountDownTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneStoreActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.phone_num)
    EditText phoneNum;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.countDownTextView)
    CountDownTextView countDownTextView;
    @BindView(R.id.select_agreement)
    ImageView selectAgreement;
    @BindView(R.id.agreemen)
    TextView agreemen;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.pass_word_new)
    EditText passWordNew;
    @BindView(R.id.pass_word_line)
    View passWordLine;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {

        logo.setVisibility(View.GONE);
        selectAgreement.setVisibility(View.GONE);
        agreemen.setVisibility(View.GONE);
        passWordNew.setVisibility(View.VISIBLE);
        passWordLine.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        title.setText("手机运营商");

        //设置EditText的属性
        SpannableString phoneTips = new SpannableString("输入手机号");
        AbsoluteSizeSpan tipSise = new AbsoluteSizeSpan(15, true);
        phoneTips.setSpan(tipSise, 0, phoneTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        phoneNum.setHint(phoneTips);

        SpannableString passWordNewTips = new SpannableString("输入密码");
        AbsoluteSizeSpan passWordNewSize = new AbsoluteSizeSpan(15, true);
        passWordNewTips.setSpan(passWordNewSize, 0, passWordNewTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        passWordNew.setHint(passWordNewTips);

        SpannableString passwordTips = new SpannableString("输入验证码");
        AbsoluteSizeSpan passwordSize = new AbsoluteSizeSpan(15, true);
        passwordTips.setSpan(passwordSize, 0, passwordTips.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        password.setHint(passwordTips);

    }
}
