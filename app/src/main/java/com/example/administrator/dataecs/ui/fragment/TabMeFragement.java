package com.example.administrator.dataecs.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.ui.activity.BanckCordActivity;
import com.example.administrator.dataecs.ui.activity.DataPerfectActivity;
import com.example.administrator.dataecs.ui.activity.HelpActivity;
import com.example.administrator.dataecs.ui.activity.InformationActivity;
import com.example.administrator.dataecs.ui.activity.LoginActivity;
import com.example.administrator.dataecs.ui.activity.RecordActivity;
import com.example.administrator.dataecs.ui.activity.SettingActivity;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/7/4.
 */

public class TabMeFragement extends Fragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.useHead)
    ImageView useHead;
    @BindView(R.id.useName)
    TextView useName;
    @BindView(R.id.login_tab)
    RelativeLayout loginTab;
    @BindView(R.id.record)
    RelativeLayout record;
    @BindView(R.id.setting)
    RelativeLayout setting;
    @BindView(R.id.back_information)
    RelativeLayout backInformation;
    @BindView(R.id.id_information)
    RelativeLayout idInformation;
    @BindView(R.id.information)
    RelativeLayout information;
    @BindView(R.id.back_information_type)
    TextView backInformationType;
    @BindView(R.id.id_information_type)
    TextView idInformationType;
    @BindView(R.id.attestation_type)
    ImageView attestationType;
    @BindView(R.id.help_center)
    RelativeLayout helpCenter;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.me_layout, null);
        unbinder = ButterKnife.bind(this, view);
        intview();
        return view;
    }

    private void intview() {
        title.setText("个人中心");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_tab, R.id.record, R.id.setting,
            R.id.back_information, R.id.id_information, R.id.information, R.id.help_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //登录
            case R.id.login_tab:
                if ("".equals(SPUtils.get(getActivity(), Config.TOKEN_VALUE, ""))) {
                    Intent run = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(run);
                } else {
                    return;
                }
                break;

            //我的账户
            case R.id.back_information:

                if ("".equals(SPUtils.get(getActivity(), Config.TOKEN_VALUE, ""))) {

                    Intent inten3 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(inten3);

                } else {
                    Intent backInformation = new Intent(getActivity(), BanckCordActivity.class);
                    backInformation.putExtra("type", false);
                    startActivityForResult(backInformation,Config.ME_TO_BANCK);
                }

                break;

            //资料认证
            case R.id.id_information:

                if ("".equals(SPUtils.get(getActivity(), Config.TOKEN_VALUE, ""))) {

                    Intent inten3 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(inten3);

                } else {

//                    Intent idInformation = new Intent(getActivity(), AttestationActivity.class);
//                    idInformation.putExtra("isperfect",(boolean)SPUtils.get(getActivity(),BaseServer.ID_INFORMATION,false));
//                    startActivity(idInformation);
                    Intent data = new Intent(getActivity(), DataPerfectActivity.class);
                    startActivity(data);
                }

                break;

            //借款记录
            case R.id.record:

                if (!"".equals(SPUtils.get(getActivity(), Config.TOKEN_VALUE, ""))) {
                    Intent intent = new Intent(getActivity(), RecordActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("isBackRefresh",false);
                    getActivity().startActivity(intent);
                }

                break;

            //消息中心
            case R.id.information:
//                if ("".equals(SPUtils.get(getActivity(), Config.TOKEN_VALUE, ""))) {
//
//                    Intent inten3 = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(inten3);
//
//                } else {

                    Intent information = new Intent(getActivity(), InformationActivity.class);
                    getActivity().startActivity(information);
//                }

                break;

            //帮助中心
            case R.id.help_center:
                Intent help = new Intent(getActivity(), HelpActivity.class);
                getActivity().startActivity(help);
                break;//关于我们
            case R.id.setting:
                Intent intent1 = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent1);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //认证状态
        boolean taoBao = (boolean) SPUtils.get(getActivity(), Config.TAO_BAO_PERFECT, false);
        boolean idCard = (boolean) SPUtils.get(getActivity(), Config.ID_CARD_PERFECT, false);
        boolean idInfo = (boolean) SPUtils.get(getActivity(), Config.ID_INFOMATION_PERFECT, false);
        boolean phone = (boolean) SPUtils.get(getActivity(), Config.PHONE_STORE_PERFECT, false);
        boolean face = (boolean) SPUtils.get(getActivity(), Config.FACE_FOUSE_PERFECT, false);
        boolean banck = (boolean) SPUtils.get(getActivity(), Config.BANCK_PERFECT, false);

        if ("".equals(SPUtils.get(getActivity(), Config.TOKEN_VALUE, ""))) {
            useName.setText("请登陆");
            attestationType.setVisibility(View.GONE);
            backInformationType.setVisibility(View.GONE);
            idInformationType.setVisibility(View.GONE);

        } else {

            useName.setText((String) SPUtils.get(getActivity(), Config.PHONE_VALUE, ""));
            attestationType.setVisibility(View.VISIBLE);
            backInformationType.setVisibility(View.VISIBLE);
            idInformationType.setVisibility(View.VISIBLE);

            if (taoBao && idCard && idInfo && phone && face && banck) {
                attestationType.setImageResource(R.drawable.personal_info_verification);
            } else {
                attestationType.setImageResource(R.drawable.wei_ren_zheng);
            }

            backInformationType.setText(banck ? "已完善" : "未完善");
            idInformationType.setText(taoBao && idCard && idInfo && phone && face ? "已完善" : "未完善");
        }


    }


}
