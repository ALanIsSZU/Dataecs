package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dataecs.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaoBaoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;

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

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


}
