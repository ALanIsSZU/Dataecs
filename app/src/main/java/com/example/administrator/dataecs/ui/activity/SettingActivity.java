package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/4.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.aboutUs)
    RelativeLayout aboutUs;
    @BindView(R.id.exit_login)
    TextView exitLogin;
    @BindView(R.id.code_txt)
    TextView codeTxt;
    @BindView(R.id.ban_ben_code)
    TextView banBenCode;

    String verSonName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {

        back.setVisibility(View.VISIBLE);
        title.setText("设置");

        verSonName= Tools.getVersionName(this);
        codeTxt.setText("版本号 "+verSonName);
        banBenCode.setText(verSonName);

        if (SharePreferencesUtil.getUserName(this).equals("")) {
            exitLogin.setVisibility(View.GONE);
        } else {
            exitLogin.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.aboutUs, R.id.back, R.id.exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aboutUs:
                Intent intent = new Intent(this, AboutuActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.exit_login:
                SharePreferencesUtil.saveUserInfo(this, "");
                SharePreferencesUtil.saveShenQing(this, 4);
                SPUtils.put(this, BaseServer.BANCK_INFORMATION,false);
                SPUtils.put(this, BaseServer.ID_INFORMATION,false);
                SPUtils.put(this, BaseServer.ALL_ATTESTATION,false);
                finish();
                break;
        }
    }

}
