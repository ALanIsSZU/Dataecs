package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.CheckInfoModel;
import com.example.administrator.dataecs.model.DaiKuanModel;
import com.example.administrator.dataecs.model.SecondCommitModle;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.example.administrator.dataecs.util.Tools;
import com.example.administrator.dataecs.weight.MarqueeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShenQingActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_layout)
    RelativeLayout loginLayout;
    @BindView(R.id.change_info)
    TextView changeInfo;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;
    //审核状态
    int oldStateType;
    @BindView(R.id.shen_qing_icon)
    ImageView shenQingIcon;
    @BindView(R.id.shen_qing_commit_txt)
    TextView shenQingCommitTxt;
    @BindView(R.id.shen_qing_commit_logo)
    ImageView shenQingCommitLogo;
    @BindView(R.id.shen_he_icon)
    ImageView shenHeIcon;
    @BindView(R.id.shen_he_commit_txt)
    TextView shenHeCommitTxt;
    @BindView(R.id.shen_he_commit_logo)
    ImageView shenHeCommitLogo;
    @BindView(R.id.shen_he_commit_content)
    TextView shenHeCommitContent;
    @BindView(R.id.qian_yue_icon)
    ImageView qianYueIcon;
    @BindView(R.id.qian_yue_commit_txt)
    TextView qianYueCommitTxt;
    @BindView(R.id.qian_yue_commit_logo)
    ImageView qianYueCommitLogo;
    @BindView(R.id.fang_kuan_icon)
    ImageView fangKuanIcon;
    @BindView(R.id.fang_kuan_commit_txt)
    TextView fangKuanCommitTxt;
    @BindView(R.id.fang_kuan_commit_logo)
    ImageView fangKuanCommitLogo;
    @BindView(R.id.fang_kuan_commit_content)
    TextView fangKuanCommitContent;
    @BindView(R.id.horn_txt)
    TextView hornTxt;
    @BindView(R.id.dai_kuan_commit)
    TextView daiKuanCommit;
    @BindView(R.id.attestation_lay)
    View attestationLay;
    @BindView(R.id.other_lay)
    View otherLay;
    @BindView(R.id.money_value)
    TextView moneyValue;
    @BindView(R.id.day_select_one)
    TextView daySelectOne;
    @BindView(R.id.day_select_second)
    TextView daySelectSecond;
    @BindView(R.id.day_select_thirdly)
    TextView daySelectThirdly;
    @BindView(R.id.day_select_fourth)
    TextView daySelectFourth;
    @BindView(R.id.give_money)
    TextView giveMoney;
    @BindView(R.id.select_agreement)
    ImageView selectAgreement;
    @BindView(R.id.agreement_txt)
    TextView agreementTxt;
    @BindView(R.id.get_money_btn)
    TextView getMoneyBtn;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.txt3)
    TextView txt3;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;



    String LoginPhone;
    //判断是未审核还是二次贷款状态
    boolean isSecoend = false;
    //跑马灯的内容
    List<CharSequence> marqueeContent;
    //借款的最大金额
    int MaxMoney;
    //管理费的倍数
    int multiple = 1;
    //借款天数
    int repayDay = 7;
    //还款时间
    String repayTime = Tools.DateToString(6);
    //当前时间
    String data;
    //当前金额
    int moneyNoew;
    //审核状态
    int typeCode;
    //审核信息
    String ShenQinContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shen_qing_layout);
        ButterKnife.bind(this);

    }

    private void intData() {
        hornTxt.setVisibility(View.INVISIBLE);
        typeCode = getIntent().getIntExtra("typeCode", 18);
        ShenQinContent = getIntent().getStringExtra("content");

        back.setVisibility(View.VISIBLE);
        title.setText("申请认证");
//        changeInfo.setVisibility(View.VISIBLE);
//        changeInfo.setText("刷新");
        agreementTxt.setText(Html.fromHtml("同意<font color='#64B8FF'><small>《借款协议》</small></font>"));

        MaxMoney = (int) SPUtils.get(this, Config.MAX_MONEY, 3000);
//        MaxMoney = 3000;
        moneyValue.setText(MaxMoney + "");
        seekBar.setMax(MaxMoney);
        seekBar.setProgress(MaxMoney);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    seekBar.setProgress(0);
                    moneyNoew = 0;
                    moneyValue.setText("0.00");
                } else {

                    if (progress == MaxMoney) {
                        seekBar.setProgress(MaxMoney);
                        moneyValue.setText(MaxMoney + ".00");
                        moneyNoew = MaxMoney;
                    }
                    moneyValue.setText((progress / 100 * 100) + ".00");
                    MaxMoney = progress / 100 * 100;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //当前时间
        Calendar calendar = Calendar.getInstance();
        data = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        marqueeContent = new ArrayList<>();
        //首页跑马灯的轮播信息
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 137*****336 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 186*****895 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 134*****327 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 185*****089 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 135*****236 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 137*****681 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 134*****532 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 137*****892 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 186*****203 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 134*****568 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 135*****254 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 185*****938 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 137*****894 ", "成功借款")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, data, " 134*****898 ", "成功借款")));
        marqueeView.startWithList(marqueeContent);

        //下拉刷新，刷新审核状态
        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                LoginPhone = SharePreferencesUtil.getUserName(ShenQingActivity.this);

                if ("".equals(LoginPhone)) {
                    smartRefres.finishRefresh();
                    return;
                }
                oldStateType = SharePreferencesUtil.getShenQing(ShenQingActivity.this);
                getSHType(LoginPhone);
            }
        });
        smartRefres.setEnableLoadMore(false);
        smartRefres.setEnableRefresh(false);
    }

    @OnClick({R.id.login_btn, R.id.change_info, R.id.dai_kuan_commit,
            R.id.day_select_one, R.id.day_select_second, R.id.day_select_thirdly,
            R.id.day_select_fourth, R.id.select_agreement, R.id.agreement_txt, R.id.get_money_btn, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.login_btn:
                Intent intent = new Intent(ShenQingActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            //刷新状态
            case R.id.change_info:
                LoginPhone = SharePreferencesUtil.getUserName(ShenQingActivity.this);
                if ("".equals(LoginPhone)) {
                    return;
                }
                oldStateType = SharePreferencesUtil.getShenQing(ShenQingActivity.this);
                getSHType(LoginPhone);
                break;

            //第二次提交贷款
            case R.id.dai_kuan_commit:
                if (isSecoend) {
                    String number = SharePreferencesUtil.getUserName(ShenQingActivity.this);
                    SecondCommit(number);
                } else {

                    if ((Boolean) SPUtils.get(ShenQingActivity.this, BaseServer.BANCK_INFORMATION, false) &&
                            (boolean) SPUtils.get(ShenQingActivity.this, BaseServer.ID_INFORMATION, false)) {
                        ToastUntils.ToastShort(ShenQingActivity.this, "信息待审核！");
                    } else {
                        ToastUntils.ToastShort(ShenQingActivity.this, "请完善身份认证和我的账户!");
                    }

                }
                break;
            case R.id.day_select_one:
                multiple = 1;
                repayDay = 7;
                repayTime = Tools.DateToString(6);

                daySelectOne.setTextColor(getResources().getColor(R.color.white));
                daySelectOne.setBackgroundResource(R.drawable.day_touch);
                daySelectSecond.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectSecond.setBackgroundResource(R.drawable.day_no_touch);
                daySelectThirdly.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectThirdly.setBackgroundResource(R.drawable.day_no_touch);
                daySelectFourth.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectFourth.setBackgroundResource(R.drawable.day_no_touch);
                break;
            case R.id.day_select_second:
                multiple = 2;
                repayDay = 14;
                repayTime = Tools.DateToString(13);

                ToastUntils.ToastShort(ShenQingActivity.this, Tools.DateToString(14));
                daySelectOne.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectOne.setBackgroundResource(R.drawable.day_no_touch);
                daySelectSecond.setTextColor(getResources().getColor(R.color.white));
                daySelectSecond.setBackgroundResource(R.drawable.day_touch);
                daySelectThirdly.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectThirdly.setBackgroundResource(R.drawable.day_no_touch);
                daySelectFourth.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectFourth.setBackgroundResource(R.drawable.day_no_touch);
                break;
            case R.id.day_select_thirdly:
                multiple = 3;
                repayDay = 21;
                repayTime = Tools.DateToString(20);

                ToastUntils.ToastShort(ShenQingActivity.this, Tools.DateToString(21));
                daySelectOne.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectOne.setBackgroundResource(R.drawable.day_no_touch);
                daySelectSecond.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectSecond.setBackgroundResource(R.drawable.day_no_touch);
                daySelectThirdly.setTextColor(getResources().getColor(R.color.white));
                daySelectThirdly.setBackgroundResource(R.drawable.day_touch);
                daySelectFourth.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectFourth.setBackgroundResource(R.drawable.day_no_touch);
                break;
            case R.id.day_select_fourth:
                multiple = 4;
                repayDay = 28;
                repayTime = Tools.DateToString(27);

                ToastUntils.ToastShort(ShenQingActivity.this, Tools.DateToString(28));
                daySelectOne.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectOne.setBackgroundResource(R.drawable.day_no_touch);
                daySelectSecond.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectSecond.setBackgroundResource(R.drawable.day_no_touch);
                daySelectThirdly.setTextColor(getResources().getColor(R.color.tip_n));
                daySelectThirdly.setBackgroundResource(R.drawable.day_no_touch);
                daySelectFourth.setTextColor(getResources().getColor(R.color.white));
                daySelectFourth.setBackgroundResource(R.drawable.day_touch);
                break;
            case R.id.select_agreement:
                break;

            case R.id.agreement_txt:
                break;

            case R.id.get_money_btn:
                commitDaiKuan();
                break;

            case R.id.back:
                finish();
                break;
        }

    }

    //提交贷款
    public void commitDaiKuan() {
        if (!SystemUntils.isNetworkConnected(ShenQingActivity.this)) {
            Toast.makeText(ShenQingActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        double d = Double.parseDouble(moneyValue.getText().toString());

        Call<DaiKuanModel> call = inte.commitDaiKuang((Long) SPUtils.get(ShenQingActivity.this, Config.USED_ID, 1L)
                , d, repayTime, repayDay);
        call.enqueue(new Callback<DaiKuanModel>() {
            @Override
            public void onResponse(Call<DaiKuanModel> call, Response<DaiKuanModel> response) {
                DaiKuanModel model = response.body();
                if (model.getCode() == 0) {
                    if (model.getMap().isRes()) {
                        ToastUntils.ToastShort(ShenQingActivity.this, "贷款成功！");
                        SharePreferencesUtil.saveShenQing(ShenQingActivity.this, 1);
                    } else {
                        ToastUntils.ToastShort(ShenQingActivity.this, model.getMap().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<DaiKuanModel> call, Throwable t) {
                ToastUntils.ToastShort(ShenQingActivity.this, "请求失败！" + t.toString());
            }
        });
    }

    //立即申请贷款(二次贷款)
    public void SecondCommit(String phone) {
        if (!SystemUntils.isNetworkConnected(ShenQingActivity.this)) {
            Toast.makeText(ShenQingActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<SecondCommitModle> call = inte.getSecodeCommitInfo(phone);
        call.enqueue(new Callback<SecondCommitModle>() {
            @Override
            public void onResponse(Call<SecondCommitModle> call, Response<SecondCommitModle> response) {
                Toast.makeText(ShenQingActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                //审核中

                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);

                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_no);
                shenHeCommitTxt.setText("审核");
                shenHeCommitLogo.setVisibility(View.INVISIBLE);

                qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_no);
                qianYueCommitTxt.setText("签约");
                qianYueCommitLogo.setVisibility(View.INVISIBLE);

                fangKuanIcon.setImageResource(R.drawable.fang_kuan_no);
                fangKuanCommitTxt.setText("放款");
                fangKuanCommitLogo.setVisibility(View.INVISIBLE);

                fangKuanCommitContent.setVisibility(View.INVISIBLE);


                SharePreferencesUtil.saveShenQing(ShenQingActivity.this, 1);

            }

            @Override
            public void onFailure(Call<SecondCommitModle> call, Throwable t) {
                Toast.makeText(ShenQingActivity.this, "网络异常，提交失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        intData();
        if (typeCode == 1 || typeCode == 2 || typeCode == 0) {

            checkState(6);
        } else if (typeCode == 3) {
            checkState(7);
        } else if (typeCode == 5 || typeCode == 6 || typeCode == 7 || typeCode == 4) {
            checkState(8);
        }
    }

    //判断审核状态
    public void checkState(int type) {
        switch (type) {
            //未审核
            case 0:
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.VISIBLE);
                attestationLay.setVisibility(View.GONE);
                isSecoend = false;
                break;
            //审核中
            case 1:
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_no);
                shenHeCommitTxt.setText("审核");

                qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_no);
                qianYueCommitTxt.setText("签约");

                fangKuanIcon.setImageResource(R.drawable.fang_kuan_no);
                fangKuanCommitTxt.setText("放款");
                break;
            //已审核，已还款(二次贷款)
            case 2:
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.VISIBLE);
                attestationLay.setVisibility(View.GONE);
                isSecoend = true;

                break;
            //已审核，未还款
            case 3:

                if ("".equals(SharePreferencesUtil.getTime(ShenQingActivity.this)) || "".equals(SharePreferencesUtil.getMoney(ShenQingActivity.this))) {
                    //审核通过，还没放款(放款中)
                    loginLayout.setVisibility(View.GONE);
                    otherLay.setVisibility(View.GONE);
                    attestationLay.setVisibility(View.VISIBLE);
                    shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                    shenQingCommitTxt.setText("借款已申请");
                    shenQingCommitLogo.setVisibility(View.VISIBLE);
                    shenQingCommitLogo.setImageResource(R.drawable.yes);

                    shenHeIcon.setImageResource(R.drawable.jie_kuan_yes);
                    shenHeCommitTxt.setText("借款已通过");
                    shenHeCommitLogo.setVisibility(View.VISIBLE);
                    shenHeCommitLogo.setImageResource(R.drawable.yes);
                    shenHeCommitContent.setText("提交资料审核通过。");

                    qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_yes);
                    qianYueCommitTxt.setText("借款已签约");
                    qianYueCommitLogo.setVisibility(View.VISIBLE);
                    qianYueCommitLogo.setImageResource(R.drawable.yes);

                    fangKuanIcon.setImageResource(R.drawable.fang_kuan_no);
                    fangKuanCommitTxt.setText("放款");

                } else {
                    //审核通过，已经放款（已放款）
                    loginLayout.setVisibility(View.GONE);
                    otherLay.setVisibility(View.GONE);
                    attestationLay.setVisibility(View.VISIBLE);
                    shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                    shenQingCommitTxt.setText("借款已申请");
                    shenQingCommitLogo.setVisibility(View.VISIBLE);
                    shenQingCommitLogo.setImageResource(R.drawable.yes);

                    shenHeIcon.setImageResource(R.drawable.jie_kuan_yes);
                    shenHeCommitTxt.setText("借款以通过");
                    shenHeCommitLogo.setVisibility(View.VISIBLE);
                    shenHeCommitLogo.setImageResource(R.drawable.yes);
                    shenHeCommitContent.setText("提交资料审核通过。");

                    qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_yes);
                    qianYueCommitTxt.setText("借款已签约");
                    qianYueCommitLogo.setVisibility(View.VISIBLE);
                    qianYueCommitLogo.setImageResource(R.drawable.yes);

                    fangKuanIcon.setImageResource(R.drawable.fang_kuan_yes);
                    fangKuanCommitContent.setVisibility(View.VISIBLE);
                    fangKuanCommitTxt.setText("已放款");
                    fangKuanCommitLogo.setVisibility(View.VISIBLE);
                    fangKuanCommitLogo.setImageResource(R.drawable.yes);
                    fangKuanCommitContent.setText("还款日期：" + SharePreferencesUtil.getTime(ShenQingActivity.this) + "\n"
                            + "还款金额：" + SharePreferencesUtil.getMoney(ShenQingActivity.this));

                }

                break;
            //没登录
            case 4:
//                loginLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
                attestationLay.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                break;
            //放弃
            case 5:
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_no);
                shenHeCommitTxt.setText("审核未通过");
                shenHeCommitLogo.setImageResource(R.drawable.no_pass);
                shenHeCommitLogo.setVisibility(View.VISIBLE);
                shenHeCommitContent.setText("由于您的信用资格不良好，无法申请借款。");

                qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_no);
                qianYueCommitTxt.setText("签约");

                fangKuanIcon.setImageResource(R.drawable.fang_kuan_no);
                fangKuanCommitTxt.setText("放款");

                break;

            case 6:
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_yes);
                shenHeCommitTxt.setText(ShenQinContent);
                shenHeCommitLogo.setVisibility(View.VISIBLE);
                if ("审核不通过".equals(ShenQinContent)) {
                    shenHeCommitLogo.setImageResource(R.drawable.no_pass);
                } else {

                    shenHeCommitLogo.setImageResource(R.drawable.yes);
                }
//                shenHeCommitContent.setText("由于您已被放款中心纳入黑名单，无法申请贷款");

                qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_no);
                qianYueCommitTxt.setText("签约");

                fangKuanIcon.setImageResource(R.drawable.fang_kuan_no);
                fangKuanCommitTxt.setText("放款");

                break;
            case 7:
                //审核通过，还没放款(放款中)
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_yes);
                shenHeCommitTxt.setText("借款已通过");
                shenHeCommitLogo.setVisibility(View.VISIBLE);
                shenHeCommitLogo.setImageResource(R.drawable.yes);
                shenHeCommitContent.setText("提交资料审核通过。");

                qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_yes);
                qianYueCommitTxt.setText("借款已签约");
                qianYueCommitLogo.setVisibility(View.VISIBLE);
                qianYueCommitLogo.setImageResource(R.drawable.yes);

                fangKuanIcon.setImageResource(R.drawable.fang_kuan_no);
                fangKuanCommitTxt.setText("放款");
                break;
            case 8:

                //审核通过，已经放款（已放款）
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_yes);
                shenHeCommitTxt.setText("借款已通过");
                shenHeCommitLogo.setVisibility(View.VISIBLE);
                shenHeCommitLogo.setImageResource(R.drawable.yes);
                shenHeCommitContent.setText("提交资料审核通过。");

                qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_yes);
                qianYueCommitTxt.setText("借款已签约");
                qianYueCommitLogo.setVisibility(View.VISIBLE);
                qianYueCommitLogo.setImageResource(R.drawable.yes);

                fangKuanIcon.setImageResource(R.drawable.fang_kuan_yes);
                fangKuanCommitContent.setVisibility(View.VISIBLE);
                fangKuanCommitTxt.setText(ShenQinContent);
                fangKuanCommitLogo.setVisibility(View.VISIBLE);
                if ("已逾期".equals(ShenQinContent) || "打款失败".equals(ShenQinContent)) {
                    fangKuanCommitLogo.setImageResource(R.drawable.jin_gao_icon);
                } else {

                    fangKuanCommitLogo.setImageResource(R.drawable.yes);
                }
//                fangKuanCommitContent.setText("还款日期：" + SharePreferencesUtil.getTime(ShenQingActivity.this) + "\n"
//                        + "还款金额：" + SharePreferencesUtil.getMoney(ShenQingActivity.this));


                break;
        }

    }

    //获取审核状态,来刷新界面
    public void getSHType(String phone) {

        if (!SystemUntils.isNetworkConnected(ShenQingActivity.this)) {
            Toast.makeText(ShenQingActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<CheckInfoModel> call = inte.getCheckInfo(phone);
        call.enqueue(new Callback<CheckInfoModel>() {
            @Override
            public void onResponse(Call<CheckInfoModel> call, Response<CheckInfoModel> response) {
                CheckInfoModel model = response.body();
                Log.d("123", "后台传过来的状态" + model.getMap().getStatus());
                SharePreferencesUtil.saveShenQing(ShenQingActivity.this, model.getMap().getStatus());

                //这个操作不让数据每次刷新都进行修改UI操作
                if (model.getMap().getStatus() == oldStateType && model.getMap().getStatus() != 3) {
                    smartRefres.finishRefresh();
                    return;
                }

                if (model.getMap().getStatus() == 3) {

                    if (model.getMap().getLoanMonery() == null) {

                        SharePreferencesUtil.saveMoney(ShenQingActivity.this, "");

                    } else {

                        SharePreferencesUtil.saveMoney(ShenQingActivity.this, model.getMap().getLoanMonery() + "");
                    }

                    if (model.getMap().getRepayTime() == null || "".equals(model.getMap().getRepayTime())) {

                        SharePreferencesUtil.saveTime(ShenQingActivity.this, "");

                    } else {

                        SharePreferencesUtil.saveTime(ShenQingActivity.this, model.getMap().getRepayTime());
                    }
                }
                smartRefres.finishRefresh();
//                checkState();

            }

            @Override
            public void onFailure(Call<CheckInfoModel> call, Throwable t) {
                ToastUntils.ToastShort(ShenQingActivity.this, t.toString());
                smartRefres.finishRefresh(false);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

}
