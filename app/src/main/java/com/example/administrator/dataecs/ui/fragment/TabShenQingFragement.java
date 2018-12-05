package com.example.administrator.dataecs.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.dialog.AlertDialog;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.DaiKuanModel;
import com.example.administrator.dataecs.model.HuanKuanInfoModle;
import com.example.administrator.dataecs.model.RecordMdel;
import com.example.administrator.dataecs.ui.activity.AgreementActivity;
import com.example.administrator.dataecs.ui.activity.DataPerfectActivity;
import com.example.administrator.dataecs.ui.activity.LoginActivity;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabShenQingFragement extends Fragment {

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
    @BindView(R.id.borrow_money_layout)
    View borrowMoneyLayout;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.jian_fa)
    ImageView jianFa;
    @BindView(R.id.txt4)
    TextView txt4;

    Unbinder unbinder;

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
    //协议是否选中
    boolean isSelect = true;
    //利率
    float liLv;
    //是否可以进行刷新（没结束app之前）
    boolean isFinishApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.shen_qing_layout, null);
        unbinder = ButterKnife.bind(this, view);
        isFinishApp = (boolean) SPUtils.get(getActivity(), Config.IS_FINISH_APP, true);

        intData();
        //请求借款天数、利率额度
        if (!"".equals(SPUtils.get(getActivity(), Config.TOKEN_VALUE, ""))) {
            getAllInfo();
        }

        return view;
    }

    private void intData() {

        back.setVisibility(View.GONE);
        title.setText("申请认证");
//        changeInfo.setVisibility(View.VISIBLE);
//        changeInfo.setText("刷新");
        agreementTxt.setText(Html.fromHtml("同意<font color='#64B8FF'><small>《借款协议》</small></font>"));


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    seekBar.setProgress(0);
                    moneyNoew = 0;
                    moneyValue.setText("0.00");
                    giveMoney.setText("￥" + 0 + ".00元");
                    txt3.setText(moneyNoew + "");
                    txt4.setText("（利息0元+管理费：0元实际放款：");
                } else {

                    if (progress == MaxMoney) {
                        seekBar.setProgress(MaxMoney);
                        moneyValue.setText(MaxMoney + ".00");
                        moneyNoew = MaxMoney;
                        getTotleMoney();
                        txt3.setText(moneyValue.getText().toString());
                        getGuanLi();
                    }
                    moneyValue.setText((progress / 100 * 100) + ".00");
                    moneyNoew = progress / 100 * 100;
                    getTotleMoney();
                    txt3.setText(moneyValue.getText().toString());
                    getGuanLi();
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

                getSuperType();
            }
        });

        smartRefres.setEnableLoadMore(false);

    }

    @OnClick({R.id.login_btn, R.id.change_info, R.id.dai_kuan_commit,
            R.id.day_select_one, R.id.day_select_second, R.id.day_select_thirdly,
            R.id.day_select_fourth, R.id.select_agreement, R.id.agreement_txt, R.id.get_money_btn, R.id.add, R.id.jian_fa})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.login_btn:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                break;

            //刷新状态
            case R.id.change_info:


                break;

            //第二次提交贷款
            case R.id.dai_kuan_commit:

                break;
            case R.id.day_select_one:
                repayDay = (int) SPUtils.get(getActivity(), Config.ONCE_SELECT_DAY, 0);
                multiple = 1;
                repayTime = Tools.DateToString(repayDay - 1);

                getTotleMoney();
                getGuanLi();
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
                repayDay = (int) SPUtils.get(getActivity(), Config.SECOND_SELECT_DAY, 0);
                multiple = 2;
                repayTime = Tools.DateToString(repayDay - 1);

                getTotleMoney();
                getGuanLi();
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
                repayDay = (int) SPUtils.get(getActivity(), Config.THIRD_SELECT_DAY, 0);
                multiple = 3;
                repayTime = Tools.DateToString(repayDay - 1);

                getTotleMoney();
                getGuanLi();
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
                repayDay = (int) SPUtils.get(getActivity(), Config.FOURTH_SELECT_DAY, 0);
                multiple = 4;
                repayTime = Tools.DateToString(repayDay - 1);

                getTotleMoney();
                getGuanLi();
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

                if (isSelect) {
                    selectAgreement.setImageResource(R.drawable.select_no);
                    isSelect = false;

                } else {
                    selectAgreement.setImageResource(R.drawable.select);
                    isSelect = true;
                }

                break;
            case R.id.agreement_txt:

                Intent agreement = new Intent(getActivity(), AgreementActivity.class);
                agreement.putExtra("isRegister",false);
                getActivity().startActivity(agreement);

                break;
            case R.id.get_money_btn:

                if (!(boolean) SPUtils.get(getActivity(), Config.BANCK_PERFECT, false) ||
                        !(boolean) SPUtils.get(getActivity(), Config.ID_INFOMATION_PERFECT, false) ||
                        !(boolean) SPUtils.get(getActivity(), Config.PHONE_STORE_PERFECT, false) ||
                        !(boolean) SPUtils.get(getActivity(), Config.TAO_BAO_PERFECT, false) ||
                        !(boolean) SPUtils.get(getActivity(), Config.FACE_FOUSE_PERFECT, false) ||
                        !(boolean) SPUtils.get(getActivity(), Config.ID_CARD_PERFECT, false)
                        ) {
                    new AlertDialog(getActivity()).builder()
                            .setMsg("资料未完善，是否立即前往完善")
                            .setPositiveButton("立即前往", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent1 = new Intent(getActivity(), DataPerfectActivity.class);
                                    startActivity(intent1);

                                }
                            })
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                    return;
                }

                if (!isSelect) {
                    ToastUntils.ToastShort(getActivity(), "请勾选借款协议!");
                    return;
                }

                commitDaiKuan();
                break;

            case R.id.add:
                if (moneyNoew > MaxMoney - 100) {
                    seekBar.setProgress(MaxMoney);
                    moneyNoew = MaxMoney;
                    getTotleMoney();
                    txt3.setText(moneyValue.getText().toString());
                    getGuanLi();
                    return;
                } else {
                    moneyNoew = moneyNoew + 100;
                    seekBar.setProgress(moneyNoew);
                    moneyValue.setText(moneyNoew + ".00");
                    getTotleMoney();
                    getGuanLi();
                    txt3.setText(moneyValue.getText().toString());
                }
                break;
            case R.id.jian_fa:
                if (moneyNoew < 100) {
                    moneyNoew = 0;
                    txt4.setText("（利息0元+管理费：0元实际放款：");
                    seekBar.setProgress(0);
                    giveMoney.setText("￥" + 0 + ".00元");
                    txt3.setText(moneyNoew + "");
                    return;
                } else {
                    moneyNoew = moneyNoew - 100;
                    seekBar.setProgress(moneyNoew);
                    moneyValue.setText(moneyNoew + ".00");
                    txt3.setText(moneyValue.getText().toString());
                    getGuanLi();
                }
                break;
        }

    }

    //总放款金额
    public void getTotleMoney() {
        if (liLv != 0F) {
            double a = moneyNoew - Double.parseDouble(moneyValue.getText().toString()) / 1000 * 300 * repayDay / 7 - Double.parseDouble(moneyValue.getText().toString()) * liLv / 360 * repayDay;
            giveMoney.setText("￥" + new DecimalFormat("#.00").format(a) + "元");
        }
    }

    //管理费
    public void getGuanLi() {
        if (liLv != 0F) {

            txt4.setText("（利息:0" + (new DecimalFormat("#.00").format(Double.parseDouble(moneyValue.getText().toString()) * liLv / 360 * repayDay) + "+管理费：" + (new DecimalFormat("#.00").format(Double.parseDouble(moneyValue.getText().toString()) / 1000 * 300 * repayDay / 7)) + "元实际放款："));
        }
//        txt4.setText("（利息"+""+"+管理费：" + (Double.parseDouble(moneyValue.getText().toString()) / 1000 * 300 * multiple) + "元实际放款：");
    }

    //提交贷款
    public void commitDaiKuan() {
        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        double d = Double.parseDouble(moneyValue.getText().toString());

        Call<DaiKuanModel> call = inte.commitDaiKuang((Long) SPUtils.get(getActivity(), Config.USED_ID, 1L)
                , d, repayTime, repayDay);
        call.enqueue(new Callback<DaiKuanModel>() {
            @Override
            public void onResponse(Call<DaiKuanModel> call, Response<DaiKuanModel> response) {
                DaiKuanModel model = response.body();
                if (model.getCode() == 0) {
                    if (model.getMap().isRes()) {

                        isFinishApp = false;
                        SPUtils.put(getActivity(), Config.IS_FINISH_APP, false);
                        smartRefres.setEnableRefresh(true);

                        SharePreferencesUtil.saveShenQing(getActivity(), 10);
                        checkState();

                        ToastUntils.ToastShort(getActivity(), "贷款成功！");

                    } else {
                        ToastUntils.ToastShort(getActivity(), model.getMap().getMsg());
                    }
                } else {
                    ToastUntils.ToastShort(getActivity(), model.getMsg());
                }
            }

            @Override
            public void onFailure(Call<DaiKuanModel> call, Throwable t) {
                ToastUntils.ToastShort(getActivity(), "请求失败！" + t.toString());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        intReoseView();
        checkState();
    }

    public void intReoseView() {
        if (!isFinishApp) {
            smartRefres.setEnableRefresh(true);
        } else {
            smartRefres.setEnableRefresh(false);
        }

        if ((int) SPUtils.get(getActivity(), Config.ONCE_SELECT_DAY, 0) == 0) {
            daySelectOne.setVisibility(View.GONE);
        } else {
            daySelectOne.setVisibility(View.VISIBLE);
            daySelectOne.setText((int) SPUtils.get(getActivity(), Config.ONCE_SELECT_DAY, 0) + "天");
        }

        if ((int) SPUtils.get(getActivity(), Config.SECOND_SELECT_DAY, 0) == 0) {
            daySelectSecond.setVisibility(View.GONE);
        } else {
            daySelectSecond.setVisibility(View.VISIBLE);
            daySelectSecond.setText((int) SPUtils.get(getActivity(), Config.SECOND_SELECT_DAY, 0) + "天");
        }

        if ((int) SPUtils.get(getActivity(), Config.THIRD_SELECT_DAY, 0) == 0) {
            daySelectThirdly.setVisibility(View.GONE);
        } else {
            daySelectThirdly.setVisibility(View.VISIBLE);
            daySelectThirdly.setText((int) SPUtils.get(getActivity(), Config.THIRD_SELECT_DAY, 0) + "天");
        }

        if ((int) SPUtils.get(getActivity(), Config.FOURTH_SELECT_DAY, 0) == 0) {
            daySelectFourth.setVisibility(View.GONE);
        } else {
            daySelectFourth.setVisibility(View.VISIBLE);
            daySelectFourth.setText((int) SPUtils.get(getActivity(), Config.FOURTH_SELECT_DAY, 0) + "天");

        }

        MaxMoney = (int) SPUtils.get(getActivity(), Config.MAX_MONEY, 3000);
//        MaxMoney = 3000;
        moneyValue.setText(MaxMoney + ".00");
        moneyNoew = MaxMoney;
        txt3.setText(MaxMoney + ".00");
        liLv = (float) SPUtils.get(getActivity(), Config.RATE_OF_INTEREST, 0F);
        getGuanLi();
        getTotleMoney();

        seekBar.setMax(MaxMoney);
        seekBar.setProgress(MaxMoney);
    }

    //判断审核状态
    public void checkState() {
        switch (SharePreferencesUtil.getShenQing(getActivity())) {
            //未审核
            case 0:
                loginLayout.setVisibility(View.GONE);
//                otherLay.setVisibility(View.VISIBLE);
                borrowMoneyLayout.setVisibility(View.VISIBLE);
                attestationLay.setVisibility(View.GONE);
                isSecoend = false;
                SPUtils.put(getActivity(), Config.IS_FINISH_APP, true);
                smartRefres.setEnableRefresh(false);
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

                if ("".equals(SharePreferencesUtil.getTime(getActivity())) || "".equals(SharePreferencesUtil.getMoney(getActivity()))) {
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
                    fangKuanCommitContent.setText("还款日期：" + SharePreferencesUtil.getTime(getActivity()) + "\n"
                            + "还款金额：" + SharePreferencesUtil.getMoney(getActivity()));

                }

                break;
            //没登录
            case 4:
                loginLayout.setVisibility(View.VISIBLE);
                borrowMoneyLayout.setVisibility(View.GONE);
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
            //黑名单
            case 6:
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_no);
                shenHeCommitTxt.setText("审核未通过");
                shenHeCommitLogo.setVisibility(View.VISIBLE);
                shenHeCommitLogo.setImageResource(R.drawable.jin_gao_icon);
                shenHeCommitContent.setText("由于您已被放款中心纳入黑名单，无法申请贷款");

                qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_no);
                qianYueCommitTxt.setText("签约");

                fangKuanIcon.setImageResource(R.drawable.fang_kuan_no);
                fangKuanCommitTxt.setText("放款");

                break;

            case 7:
                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_yes);
                shenHeCommitTxt.setText(TypeStr);
                shenHeCommitLogo.setVisibility(View.VISIBLE);
                if ("审核不通过".equals(TypeStr)) {
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
            case 8:
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
            case 9:

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
                fangKuanCommitTxt.setText(TypeStr);
                fangKuanCommitLogo.setVisibility(View.VISIBLE);
                if ("已逾期".equals(TypeStr) || "打款失败".equals(TypeStr)) {
                    fangKuanCommitLogo.setImageResource(R.drawable.jin_gao_icon);
                } else {

                    fangKuanCommitLogo.setImageResource(R.drawable.yes);
                }

                break;

            case 10:

                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                borrowMoneyLayout.setVisibility(View.GONE);

                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
                shenQingCommitLogo.setVisibility(View.VISIBLE);
                shenQingCommitLogo.setImageResource(R.drawable.yes);

                shenHeIcon.setImageResource(R.drawable.jie_kuan_yes);
                shenHeCommitTxt.setText("审核中");
                shenHeCommitLogo.setImageResource(R.drawable.yes);

                qianYueIcon.setImageResource(R.drawable.jie_kuan_pass_no);
                qianYueCommitTxt.setText("签约");

                fangKuanIcon.setImageResource(R.drawable.fang_kuan_no);
                fangKuanCommitTxt.setText("放款");

                break;


        }
    }

    String TypeStr;

    //获取审核状态（最新的）
    public void getSuperType() {

        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<RecordMdel> call = inte.getRecordInfo((Long) SPUtils.get(getActivity(), Config.USED_ID, 1L),
                10, 1);
        call.enqueue(new Callback<RecordMdel>() {
            @Override
            public void onResponse(Call<RecordMdel> call, Response<RecordMdel> response) {
                RecordMdel model = response.body();
                if (model.getCode() == 0) {

                    TypeStr = model.getPage().getList().get(0).getStatus();
                    int code = model.getPage().getList().get(0).getStatusCode();

                    if (code == 1 || code == 2) {

                        SharePreferencesUtil.saveShenQing(getActivity(), 7);
                    } else if (code == 3) {

                        SharePreferencesUtil.saveShenQing(getActivity(), 8);

                    } else if (code == 6 || code == 7) {


                        SharePreferencesUtil.saveShenQing(getActivity(), 9);

                    } else if (code == 0 || code == 4 || code == 5) {
                        SharePreferencesUtil.saveShenQing(getActivity(), 0);
                    }
                    checkState();
                    smartRefres.finishRefresh();
                } else {
                    ToastUntils.ToastShort(getActivity(), model.getMsg());
                    smartRefres.finishRefresh();
                }
            }

            @Override
            public void onFailure(Call<RecordMdel> call, Throwable t) {
                ToastUntils.ToastShort(getActivity(), "请求失败！" + t.toString());
                smartRefres.finishRefresh(false);
            }
        });
    }

    //请求借款天数、利率额度
    public void getAllInfo() {

        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<HuanKuanInfoModle> call = inte.getDayandOther(Config.roleId, (long) SPUtils.get(getActivity(), Config.USED_ID, 1L));
        call.enqueue(new Callback<HuanKuanInfoModle>() {
            @Override
            public void onResponse(Call<HuanKuanInfoModle> call, Response<HuanKuanInfoModle> response) {
                HuanKuanInfoModle modle = response.body();
                if ("success".equals(modle.getMsg())) {

                    //利率
                    SPUtils.put(getActivity(), Config.RATE_OF_INTEREST, modle.getParameter().getRate());

                    //额度
                    SPUtils.put(getActivity(), Config.MAX_MONEY, modle.getParameter().getLimits());


                    int[] size = new int[4];

                    String days = modle.getParameter().getLoanDay();
                    String[] dayArray = days.split(",");

                    for (int i = 0; i < dayArray.length; i++) {

                        size[i] = Integer.valueOf(dayArray[i]).intValue();
                    }

                    if (dayArray.length < 4) {

                        for (int i = 0; i < size.length - dayArray.length; i++) {
                            size[dayArray.length + i] = 0;
                        }
                    }

                    Tools.saveDay(getActivity(), size[0], size[1], size[2], size[3]);

                    MaxMoney = modle.getParameter().getLimits();
                    liLv = modle.getParameter().getRate();
                    seekBar.setMax(MaxMoney);


                } else {
                    ToastUntils.ToastShort(getActivity(), "请求失败!");
                }
            }

            @Override
            public void onFailure(Call<HuanKuanInfoModle> call, Throwable t) {
                ToastUntils.ToastShort(getActivity(), "请求失败!" + t.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
