package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.dialog.AlertDialog;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
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

        verSonName = Tools.getVersionName(this);
        codeTxt.setText("版本号 " + verSonName);
        banBenCode.setText(verSonName);

        if ("".equals(SPUtils.get(this, Config.TOKEN_VALUE, ""))) {
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

                new AlertDialog(this).builder()
                        .setMsg("是否退出")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharePreferencesUtil.saveUserInfo(SettingActivity.this, "");
                                SPUtils.put(SettingActivity.this, BaseServer.BANCK_INFORMATION, false);
                                SPUtils.put(SettingActivity.this, BaseServer.ID_INFORMATION, false);
                                SPUtils.put(SettingActivity.this, BaseServer.ALL_ATTESTATION, false);

                                SharePreferencesUtil.saveShenQing(SettingActivity.this, 4);
                                SPUtils.put(SettingActivity.this, Config.TOKEN_VALUE, "");
                                SPUtils.put(SettingActivity.this, Config.ID_INFOMATION_PERFECT, false);
                                SPUtils.put(SettingActivity.this, Config.TAO_BAO_PERFECT, false);
                                SPUtils.put(SettingActivity.this, Config.FACE_FOUSE_PERFECT, false);
                                SPUtils.put(SettingActivity.this, Config.ID_CARD_PERFECT, false);
                                SPUtils.put(SettingActivity.this, Config.PHONE_STORE_PERFECT, false);
                                SPUtils.put(SettingActivity.this, Config.BANCK_PERFECT, false);

                                SPUtils.put(SettingActivity.this, Config.ONCE_SELECT_DAY, 0);
                                SPUtils.put(SettingActivity.this, Config.SECOND_SELECT_DAY, 0);
                                SPUtils.put(SettingActivity.this, Config.THIRD_SELECT_DAY, 0);
                                SPUtils.put(SettingActivity.this, Config.FOURTH_SELECT_DAY, 0);

                                finish();
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

                break;
        }
    }

}
