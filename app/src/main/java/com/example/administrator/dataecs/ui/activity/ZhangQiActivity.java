package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.DayListAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.ZQCommitModel;
import com.example.administrator.dataecs.model.ZhangQiModle;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.example.administrator.dataecs.util.Tools;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class ZhangQiActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ben_jin)
    TextView benJin;
    @BindView(R.id.huan_kuan_day)
    TextView huanKuanDay;
    @BindView(R.id.zhang_qi_huan_kuan)
    TextView zhangQiHuanKuan;
    @BindView(R.id.zhang_qi_money)
    TextView zhangQiMoney;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.zhang_qi_day)
    Spinner zhangQiDay;
    @BindView(R.id.order_txt)
    TextView orderTxt;
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.content_lay)
    LinearLayout contentLay;
    @BindView(R.id.other_layout)
    View otherLayout;
    @BindView(R.id.reason_content)
    EditText reasonContent;

    ///展期天数
    int days = 7;
    //展期倍数
    int multiple = 1;
    //还款金额
    double totleMoney;
    //还款日期
    String repsyTime;
    //展期id
    long OrderId;
    //订单号
    String orderCode;

    //展期的数据集合
    List<String> list;
    DayListAdapter adapter;

    //返回是否刷新
    boolean isBackRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhang_qi_layout);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {

        totleMoney = getIntent().getDoubleExtra("totleMoneyForZQ", 0.0);
        repsyTime = getIntent().getStringExtra("repayDayForZQ");
        OrderId = getIntent().getLongExtra("OrderIDForZQ", -1L);
        orderCode = getIntent().getStringExtra("overcodeZQ");

        if (getIntent().getStringExtra("PoneTimeForZQ") == null) {

            huanKuanDay.setText(repsyTime.substring(0, 11));
        } else {
            String day = getIntent().getStringExtra("PoneTimeForZQ");
            huanKuanDay.setText(day.substring(0, 11));
        }

        title.setText("申请展期");
        back.setVisibility(View.VISIBLE);
        tip.setText("申请展期7天需支付300元展期费用" + "\n" + "申请展期14天需支付600元展期费用");

        zhangQiHuanKuan.setText(Tools.DateToString(6));
        zhangQiMoney.setText(totleMoney * 0.3 * multiple + "");
        benJin.setText(totleMoney + "");
        orderTxt.setText(orderCode);

        list = new ArrayList<>();
        list.add("7天");
        list.add("14天");

        adapter = new DayListAdapter(list, this);
        zhangQiDay.setAdapter(adapter);
        zhangQiDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    days = 7;
                    zhangQiHuanKuan.setText(Tools.DateToString(6));
                    zhangQiMoney.setText(totleMoney * 0.3 * multiple + "");
                } else if (position == 1) {
                    days = 14;
                    zhangQiHuanKuan.setText(Tools.DateToString(13));
                    zhangQiMoney.setText(totleMoney * 0.3 * multiple + "");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.back, R.id.confirm, R.id.close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent1 = new Intent();
                intent1.putExtra("ZQ_TO_DATA_SUPER", isBackRefresh);
                setResult(Config.ZQ_BACK_DATA, intent1);

                finish();
                break;

            case R.id.confirm:
                ZQCommitModel model = new ZQCommitModel();
                model.setDays(days + "");
                if (reasonContent.getText().toString() == null) {

                    model.setRemark("");
                } else {
                    model.setRemark(reasonContent.getText().toString());
                }
                model.setTbUserIndentId(OrderId);
                String json = new Gson().toJson(model);
                commitZQinfo(json);
                break;

            case R.id.close:
                Intent intent = new Intent();
                intent.putExtra("ZQ_TO_DATA_SUPER", isBackRefresh);
                setResult(Config.ZQ_BACK_DATA, intent);
                finish();
                break;
        }
    }

    public void commitZQinfo(String json) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(ZhangQiActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<ZhangQiModle> call = inte.ZQCommit(body);
        call.enqueue(new Callback<ZhangQiModle>() {
            @Override
            public void onResponse(Call<ZhangQiModle> call, Response<ZhangQiModle> response) {
                ZhangQiModle modle = response.body();

                if (modle.getCode() == 0) {

                    if (!("".equals(modle.getMap().getRes()))) {

                        if ("success".equals(modle.getMap().getMsg())) {

                            isBackRefresh = true;

                            ToastUntils.ToastShort(ZhangQiActivity.this, modle.getMap().getMsg());
                            contentLay.setVisibility(View.GONE);
                            otherLayout.setVisibility(View.VISIBLE);
                        } else {
                            ToastUntils.ToastShort(ZhangQiActivity.this, modle.getMap().getMsg());
                        }

                    } else {
                        ToastUntils.ToastShort(ZhangQiActivity.this, modle.getMsg());
                    }
                } else {
                    ToastUntils.ToastShort(ZhangQiActivity.this, modle.getMsg());
                }

            }

            @Override
            public void onFailure(Call<ZhangQiModle> call, Throwable t) {
                ToastUntils.ToastShort(ZhangQiActivity.this, "请求失败" + t.toString());
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            Intent intent = new Intent();
            intent.putExtra("ZQ_TO_DATA_SUPER", isBackRefresh);
            setResult(Config.ZQ_BACK_DATA, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
