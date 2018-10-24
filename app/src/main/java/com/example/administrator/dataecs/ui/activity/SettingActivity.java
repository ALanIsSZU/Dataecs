package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.util.SharePreferencesUtil;

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
    LinearLayout aboutUs;
    @BindView(R.id.commonProblem)
    LinearLayout commonProblem;
    @BindView(R.id.exit_login)
    TextView exitLogin;

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
        if (SharePreferencesUtil.getUserName(this).equals("")){
            exitLogin.setVisibility(View.GONE);
        }else {
            exitLogin.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.aboutUs, R.id.commonProblem, R.id.back, R.id.exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aboutUs:
                Intent intent = new Intent(this, AboutuActivity.class);
                startActivity(intent);
                break;
            case R.id.commonProblem:
                Intent intent1 = new Intent(this, WebActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", "常见问题");
                bundle1.putString("url", "https://www.baidu.com/");
                intent1.putExtra("data", bundle1);
                startActivity(intent1);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.exit_login:
                SharePreferencesUtil.saveUserInfo(this,"");
                SharePreferencesUtil.saveShenQing(this,4);
                finish();
                break;
        }
    }

}
