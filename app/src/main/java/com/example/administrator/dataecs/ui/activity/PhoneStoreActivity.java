package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dataecs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneStoreActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_store_lay);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {

        title.setText("手机运营商");


    }
}
