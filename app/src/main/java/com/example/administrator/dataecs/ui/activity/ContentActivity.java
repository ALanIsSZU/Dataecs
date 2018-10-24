package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.XiangQingModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.SystemUntils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/7/10.
 */

public class ContentActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.app_content)
    TextView appContent;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.can_kao)
    TextView canKao;
    @BindView(R.id.apply)
    TextView apply;
    @BindView(R.id.explain)
    TextView explain;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.passPower)
    TextView passPower;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.gif_rela)
    RelativeLayout gifRela;

    //详情页面的ID
    private int sourceId;
    private XiangQingModel model;
    //详情页面的URL
    private String Url;
    private String titleName;

    //图片的URL
    private String imgUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content__ac_layout);
        ButterKnife.bind(this);

        intView();

        getData(sourceId);

    }

    private void intView() {
        back.setVisibility(View.VISIBLE);
        gifRela.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("itemData");
        sourceId = bundle.getInt("sourceId");
        model = new XiangQingModel();
    }

    private void getData(int sourceId) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<XiangQingModel> call = inte.getXiangQingData(sourceId + "");
        call.enqueue(new Callback<XiangQingModel>() {
            @Override
            public void onResponse(Call<XiangQingModel> call, Response<XiangQingModel> response) {

                gifRela.setVisibility(View.GONE);

                model = response.body();
                Url = model.getResult().getSourceurl();
                titleName = model.getResult().getSource();
                imgUrl = BaseServer.BASE_PIC + model.getResult().getRemark();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<XiangQingModel> call, Throwable t) {
                Toast.makeText(ContentActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    money.setText(model.getResult().getBorrowbalances()
                            + "~" + model.getResult().getBorrowbalance());
//                    time.setText();
                    appName.setText(model.getResult().getSource());
                    canKao.setText(model.getResult().getInterest());
                    apply.setText(model.getResult().getCondition());
                    explain.setText(model.getResult().getDetail());
                    introduce.setText(model.getResult().getIntroduction());
                    title.setText(model.getResult().getSource());
                    Glide.with(ContentActivity.this).load(imgUrl).into(appIcon);
                    break;
            }

        }
    };

    @OnClick({R.id.back, R.id.passPower})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.passPower:

                if (!SharePreferencesUtil.getUserName(this).equals("")) {
                    if (Url == null || Url.equals("")) {
                        return;
                    }
                    Intent intent = new Intent(this, WebActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", Url);
                    bundle.putString("title", titleName);
                    intent.putExtra("data", bundle);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(ContentActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }
}
