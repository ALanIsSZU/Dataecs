package com.example.administrator.dataecs.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.CheckInfoModel;
import com.example.administrator.dataecs.model.SecondCommitModle;
import com.example.administrator.dataecs.model.ShenQinCommitModel;
import com.example.administrator.dataecs.ui.activity.LoginActivity;
import com.example.administrator.dataecs.ui.activity.VerifyActivity;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    @BindView(R.id.name)
    EditText nameEidit;
    @BindView(R.id.id_cord)
    EditText idCord;
    @BindView(R.id.yinhanka)
    EditText yinhanka;
    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.shen_txt)
    TextView shenTxt;
    @BindView(R.id.content_layout)
    RelativeLayout contentLayout;
    @BindView(R.id.other_layout)
    View otherLayout;
    @BindView(R.id.pay_layout)
    View payLayout;
    @BindView(R.id.pay_time)
    TextView payTime;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_layout)
    RelativeLayout loginLayout;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.second_commit_btn)
    TextView secondCommitBtn;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.go_to_yan)
    TextView goToYan;
    @BindView(R.id.perfect_layout)
    View perfectLayout;
    @BindView(R.id.change_info)
    TextView changeInfo;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.logintxt)
    TextView logintxt;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;
    @BindView(R.id.tip_txt)
    TextView tipTxt;

    Unbinder unbinder;
    String LoginPhone;

    //审核状态
    int oldStateType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.shen_qing_layout, null);

        unbinder = ButterKnife.bind(this, view);
        intData();
        return view;
    }

    private void intData() {

        back.setVisibility(View.GONE);
        title.setText("申请");
//        changeInfo.setVisibility(View.VISIBLE);
//        changeInfo.setText("刷新");

        //下拉刷新，刷新审核状态
        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                LoginPhone = SharePreferencesUtil.getUserName(getActivity());

                if ("".equals(LoginPhone)) {
                    smartRefres.finishRefresh();
                    Log.d("123", "没有登录");
                    return;
                }
                oldStateType = SharePreferencesUtil.getShenQing(getActivity());
                getSHType(LoginPhone);
            }
        });
        smartRefres.setEnableLoadMore(false);

    }

    @OnClick({R.id.name, R.id.id_cord, R.id.commit_btn, R.id.login_btn, R.id.second_commit_btn, R.id.go_to_yan, R.id.change_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commit_btn:
                LoginPhone = SharePreferencesUtil.getUserName(getActivity());
                if (nameEidit.equals("")) {
                    Toast.makeText(getActivity(), "输入正确名字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (idCord.getText().toString() == null || "".equals(idCord.getText().toString()) || !StringUtil.isTrueID(idCord.getText().toString())) {
                    Toast.makeText(getActivity(), "输入正确的身份证", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (yinhanka.getText().toString().equals("") || yinhanka.getText().toString() == null || !StringUtil.checkBankCard(yinhanka.getText().toString())) {
                    Toast.makeText(getActivity(), "输入正确的银行卡", Toast.LENGTH_SHORT).show();
                    return;
                }

                String idCard = idCord.getText().toString();
                String name = nameEidit.getText().toString();
                String bankCard = yinhanka.getText().toString();

                commitInfo(LoginPhone, idCard, name, bankCard);
                break;

            case R.id.login_btn:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.second_commit_btn:
                String number = SharePreferencesUtil.getUserName(getActivity());
                SecondCommit(number);
                break;

            //资料未完善跳转验证中心
            case R.id.go_to_yan:
                Intent yanZheng = new Intent(getActivity(), VerifyActivity.class);
                startActivity(yanZheng);
                break;

            //刷新状态
            case R.id.change_info:
                LoginPhone = SharePreferencesUtil.getUserName(getActivity());
                if ("".equals(LoginPhone)) {
                    return;
                }
                oldStateType = SharePreferencesUtil.getShenQing(getActivity());
                getSHType(LoginPhone);
                break;
        }

    }

    //提交审核数据
    public void commitInfo(String phone, String idCard, String name, String bankCard) {

        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<ShenQinCommitModel> call = inte.getCommitInfo(phone, idCard, name, bankCard);
        call.enqueue(new Callback<ShenQinCommitModel>() {
            @Override
            public void onResponse(Call<ShenQinCommitModel> call, Response<ShenQinCommitModel> response) {
                Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                loginLayout.setVisibility(View.GONE);
                perfectLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.GONE);
                secondCommitBtn.setVisibility(View.GONE);
                SharePreferencesUtil.saveShenQing(getActivity(), 1);
                shenTxt.setText("审核中");

            }

            @Override
            public void onFailure(Call<ShenQinCommitModel> call, Throwable t) {
                Toast.makeText(getActivity(), "网络异常，提交失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //立即申请贷款
    public void SecondCommit(String phone) {
        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                loginLayout.setVisibility(View.GONE);
                perfectLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.GONE);
                secondCommitBtn.setVisibility(View.GONE);
                SharePreferencesUtil.saveShenQing(getActivity(), 1);
                shenTxt.setText("审核中");
            }

            @Override
            public void onFailure(Call<SecondCommitModle> call, Throwable t) {
                Toast.makeText(getActivity(), "网络异常，提交失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkState();
    }

    //判断审核状态
    public void checkState() {
        switch (SharePreferencesUtil.getShenQing(getActivity())) {
            //未审核
            case 0:
                loginLayout.setVisibility(View.GONE);
                perfectLayout.setVisibility(View.VISIBLE);
                otherLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.GONE);
                secondCommitBtn.setVisibility(View.GONE);
                tipTxt.setVisibility(View.GONE);
                break;
            //审核中
            case 1:
                loginLayout.setVisibility(View.GONE);
                perfectLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.GONE);
                secondCommitBtn.setVisibility(View.GONE);
                tipTxt.setVisibility(View.GONE);
                shenTxt.setText("审核中");
                break;
            //已审核，已还款
            case 2:
                loginLayout.setVisibility(View.GONE);
                perfectLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.GONE);
                secondCommitBtn.setVisibility(View.VISIBLE);
                tipTxt.setVisibility(View.GONE);
                break;
            //已审核，未还款
            case 3:

                if ("".equals(SharePreferencesUtil.getTime(getActivity())) || "".equals(SharePreferencesUtil.getMoney(getActivity()))) {
                    //审核通过，还没放款
                    loginLayout.setVisibility(View.GONE);
                    perfectLayout.setVisibility(View.GONE);
                    otherLayout.setVisibility(View.VISIBLE);
                    payLayout.setVisibility(View.GONE);
                    secondCommitBtn.setVisibility(View.GONE);
                    tipTxt.setVisibility(View.GONE);
                    shenTxt.setText("放款中");
                } else {
                    //审核通过，已经放款
                    loginLayout.setVisibility(View.GONE);
                    perfectLayout.setVisibility(View.GONE);
                    otherLayout.setVisibility(View.GONE);
                    payLayout.setVisibility(View.VISIBLE);
                    secondCommitBtn.setVisibility(View.GONE);
                    tipTxt.setVisibility(View.GONE);
                    payTime.setText("还款日期：" + SharePreferencesUtil.getTime(getActivity()));
                    payMoney.setText("还款金额：" + SharePreferencesUtil.getMoney(getActivity()));

                }

                break;
            //没登录
            case 4:
                loginLayout.setVisibility(View.VISIBLE);
                perfectLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.GONE);
                secondCommitBtn.setVisibility(View.GONE);
                tipTxt.setVisibility(View.GONE);
                break;
            //放弃
            case 5:
                loginLayout.setVisibility(View.GONE);
                perfectLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.GONE);
                secondCommitBtn.setVisibility(View.GONE);
                tipTxt.setVisibility(View.VISIBLE);

                tipTxt.setText("你的资料不符合放款条件");

                break;
            //黑名单
            case 6:
                loginLayout.setVisibility(View.GONE);
                perfectLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.GONE);
                secondCommitBtn.setVisibility(View.GONE);
                tipTxt.setVisibility(View.VISIBLE);

                tipTxt.setText("你已被放款中心纳入黑名单，无法申请放款");

                break;
        }
    }

    //获取审核状态,来刷新界面
    public void getSHType(String phone) {

        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
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
                SharePreferencesUtil.saveShenQing(getActivity(), model.getMap().getStatus());

                //这个操作不让数据每次刷新都进行修改UI操作
                if (model.getMap().getStatus() == oldStateType && model.getMap().getStatus() != 3) {
                    Log.d("123", "刷新状态相同!!!!!");
                    smartRefres.finishRefresh();
                    return;
                }

                if (model.getMap().getStatus() == 3) {

                    if (model.getMap().getLoanMonery() == null) {

                        SharePreferencesUtil.saveMoney(getActivity(), "");

                    } else {

                        SharePreferencesUtil.saveMoney(getActivity(), model.getMap().getLoanMonery() + "");
                    }

                    if (model.getMap().getRepayTime() == null || "".equals(model.getMap().getRepayTime())) {

                        SharePreferencesUtil.saveTime(getActivity(), "");

                    } else {

                        SharePreferencesUtil.saveTime(getActivity(), model.getMap().getRepayTime());
                    }
                }
                smartRefres.finishRefresh();
                checkState();

            }

            @Override
            public void onFailure(Call<CheckInfoModel> call, Throwable t) {
                ToastUntils.ToastShort(getActivity(), t.toString());
                smartRefres.finishRefresh(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
