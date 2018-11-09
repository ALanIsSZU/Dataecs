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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.CheckInfoModel;
import com.example.administrator.dataecs.model.SecondCommitModle;
import com.example.administrator.dataecs.ui.activity.LoginActivity;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
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

    Unbinder unbinder;
    String LoginPhone;

    //判断是未审核还是二次贷款状态
    boolean isSecoend = false;

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
        title.setText("申请认证");
//        changeInfo.setVisibility(View.VISIBLE);
//        changeInfo.setText("刷新");

        //下拉刷新，刷新审核状态
        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                LoginPhone = SharePreferencesUtil.getUserName(getActivity());

                if ("".equals(LoginPhone)) {
                    smartRefres.finishRefresh();
                    return;
                }
                oldStateType = SharePreferencesUtil.getShenQing(getActivity());
                getSHType(LoginPhone);
            }
        });
        smartRefres.setEnableLoadMore(false);

    }

    @OnClick({R.id.login_btn, R.id.change_info, R.id.dai_kuan_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.login_btn:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
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

                //第二次提交贷款
            case R.id.dai_kuan_commit:
                if (isSecoend) {
                    String number = SharePreferencesUtil.getUserName(getActivity());
                    SecondCommit(number);
                } else {

                    if ((Boolean) SPUtils.get(getActivity(),BaseServer.BANCK_INFORMATION,false)&&
                            (boolean) SPUtils.get(getActivity(), BaseServer.ID_INFORMATION, false)){
                        ToastUntils.ToastShort(getActivity(), "信息待审核！");
                    }else {
                        ToastUntils.ToastShort(getActivity(), "请完善身份认证和我的账户!");
                    }

                }
                break;
        }

    }

    //立即申请贷款(二次贷款)
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
                //审核中

                loginLayout.setVisibility(View.GONE);
                otherLay.setVisibility(View.GONE);
                attestationLay.setVisibility(View.VISIBLE);
                shenQingIcon.setImageResource(R.drawable.shen_qing_yes);
                shenQingCommitTxt.setText("借款已申请");
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


                SharePreferencesUtil.saveShenQing(getActivity(), 1);

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
                shenQingCommitLogo.setVisibility(View.INVISIBLE);
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
                    fangKuanCommitContent.setText("还款日期：" + SharePreferencesUtil.getTime(getActivity())+"\n"
                            +"还款金额：" + SharePreferencesUtil.getMoney(getActivity()));

                }

                break;
            //没登录
            case 4:
                loginLayout.setVisibility(View.VISIBLE);
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
                Log.d("123","后台传过来的状态"+model.getMap().getStatus());
                SharePreferencesUtil.saveShenQing(getActivity(), model.getMap().getStatus());

                //这个操作不让数据每次刷新都进行修改UI操作
                if (model.getMap().getStatus() == oldStateType && model.getMap().getStatus() != 3) {
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
