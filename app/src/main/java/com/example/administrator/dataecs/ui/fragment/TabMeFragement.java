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
import com.example.administrator.dataecs.ui.activity.LoginActivity;
import com.example.administrator.dataecs.ui.activity.RecordActivity;
import com.example.administrator.dataecs.ui.activity.SettingActivity;
import com.example.administrator.dataecs.ui.activity.VerifyActivity;
import com.example.administrator.dataecs.util.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/7/4.
 *
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
    @BindView(R.id.verify)
    RelativeLayout verify;
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
        title.setText("我的");
        if (SharePreferencesUtil.getUserName(getActivity()).equals("")) {
            useName.setText("请登陆");
        } else {
            useName.setText(SharePreferencesUtil.getUserName(getActivity()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_tab, R.id.record, R.id.setting, R.id.verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_tab:
                if (SharePreferencesUtil.getUserName(getActivity()).equals("")) {
                    Intent run = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(run);
                } else {
                    return;
                }
                break;
            case R.id.record:

                if (!SharePreferencesUtil.getUserName(getActivity()).equals("")) {
                    Intent intent = new Intent(getActivity(), RecordActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }

                break;
            case R.id.setting:
                Intent intent1 = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.verify:

                if ("".equals(SharePreferencesUtil.getUserName(getActivity()))){

                    Intent inten3=new Intent(getActivity(),LoginActivity.class);
                    startActivity(inten3);

                }else {

                    Intent intent2=new Intent(getActivity(), VerifyActivity.class);
                    getActivity().startActivity(intent2);
                }

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (SharePreferencesUtil.getUserName(getActivity()).equals("")) {
            useName.setText("请登陆");
        } else {
            useName.setText(SharePreferencesUtil.getUserName(getActivity()));
        }
    }


}
