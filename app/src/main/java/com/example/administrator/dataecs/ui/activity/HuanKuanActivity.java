package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.ZhangQiModle;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HuanKuanActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ben_jin)
    TextView benJin;
    @BindView(R.id.shou_xu)
    TextView shouXu;
    @BindView(R.id.zong_jin_er)
    TextView zongJinEr;
    @BindView(R.id.banck_card)
    TextView banckCard;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.back_lay)
    RelativeLayout backLay;
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;
    @BindView(R.id.order_txt)
    TextView orderTxt;

    //还款金额(本金)
    double totleMoney;
    //订单id
    long order;
    //逾期天数
    int overDays;
    //传回来的银行卡号
    String banckNum = "";
    //订单号
    String orderCode;
    //逾期手续
    double yuQi;

    //返回是否刷新
    boolean isBackRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huan_kuan_layout);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {

        back.setVisibility(View.VISIBLE);
        title.setText("还款");
        totleMoney = getIntent().getDoubleExtra("totleMoneyForRepay", 0.0);
        order = getIntent().getLongExtra("OrderIDForRepay", -1L);
        overDays = getIntent().getIntExtra("overdayForRepay", 1);
        orderCode = getIntent().getStringExtra("overcodeForRepay");
        yuQi = getIntent().getDoubleExtra("overdueForRepay", 0);

        benJin.setText(totleMoney + "");

        double yuqiMoney = yuQi;
        shouXu.setText(yuqiMoney + "");
        zongJinEr.setText((totleMoney + yuqiMoney) + "");
        orderTxt.setText(orderCode);
    }


    @OnClick({R.id.back, R.id.confirm, R.id.back_lay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

                Intent intent1 = new Intent();
                intent1.putExtra("HK_TO_DATA_SUPER", isBackRefresh);
                setResult(Config.HK_BACK_DATA, intent1);

                finish();
                break;

            case R.id.confirm:

                if (banckNum.equals("") || !StringUtil.checkBankCard(banckNum)) {
                    Toast.makeText(this, "请选择的银行卡！", Toast.LENGTH_SHORT).show();
                    return;
                }

                commitHKInfo(order);

                break;

            case R.id.back_lay:

                Intent intent = new Intent(HuanKuanActivity.this, BanckCordActivity.class);
                intent.putExtra("type", true);
                startActivityForResult(intent, Config.HK_TO_BANCK);
                break;

        }
    }

    public void commitHKInfo(long id) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(HuanKuanActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<ZhangQiModle> call = inte.repayCommit(id);
        call.enqueue(new Callback<ZhangQiModle>() {
            @Override
            public void onResponse(Call<ZhangQiModle> call, Response<ZhangQiModle> response) {
                ZhangQiModle modle = response.body();

                if (modle.getCode() == 0) {

                    if ("success".equals(modle.getMap().getRes())) {

                        ToastUntils.ToastShort(HuanKuanActivity.this, modle.getMap().getMsg());

                        isBackRefresh = true;
                        Intent intent = new Intent();
                        intent.putExtra("HK_TO_DATA_SUPER", isBackRefresh);
                        setResult(Config.HK_BACK_DATA, intent);

                        finish();
                    } else {
                        ToastUntils.ToastShort(HuanKuanActivity.this, modle.getMap().getMsg());
                    }
                } else {
                    ToastUntils.ToastShort(HuanKuanActivity.this, modle.getMsg());
                }

            }

            @Override
            public void onFailure(Call<ZhangQiModle> call, Throwable t) {
                ToastUntils.ToastShort(HuanKuanActivity.this, "请求失败" + t.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case Config.BANCK_BACK_HK:

                if (requestCode == Config.HK_TO_BANCK) {
                    String a = data.getStringExtra("banckNum");
                    if (data.getStringExtra("banckNum") != null &&
                            !"".equals(data.getStringExtra("banckNum"))) {

                        banckNum = data.getStringExtra("banckNum");
                        banckCard.setText("**** **** **** " + banckNum.substring(banckNum.length() - 4, banckNum.length()));
                    }

                }

                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent();
            intent.putExtra("HK_TO_DATA_SUPER", isBackRefresh);
            setResult(Config.HK_BACK_DATA, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
