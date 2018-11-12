package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.util.Constants;
import com.example.administrator.dataecs.util.StartXYSDKUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataPerfectActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.phone_store_type)
    TextView phoneStoreType;
    @BindView(R.id.phone_store_relate)
    RelativeLayout phoneStoreRelate;
    @BindView(R.id.tao_bao_type)
    TextView taoBaoType;
    @BindView(R.id.tao_bao_relate)
    RelativeLayout taoBaoRelate;
    @BindView(R.id.face_fouse_type)
    TextView faceFouseType;
    @BindView(R.id.face_fouse_relate)
    RelativeLayout faceFouseRelate;
    @BindView(R.id.id_information_relate)
    RelativeLayout idInformationRelate;
    @BindView(R.id.id_information_type)
    TextView idInformationType;
    @BindView(R.id.id_card_relate)
    RelativeLayout idCardRelate;
    @BindView(R.id.loading_txt)
    TextView loadingTxt;
    @BindView(R.id.loading_lay)
    View loadingLay;


    //资料和认证是否完善
    boolean isMyelfInfoPerfect = false;
    boolean isPhonePerfect = false;
    boolean isTaoBaoPerfect = false;
    boolean isFacePerfect = false;
    boolean isIDCardPerfect = false;

    //新颜商户号
    String memberId = "234";
    //新颜终端号
    String terminalId = "234";
    //新颜订单编号
    String orderInfo = "234";
    //新颜订环境  测试环境：test 正式环境：product 默认是生产
    String orderEn = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_attantin_lay);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {
        title.setText("资料认证");
        loadingTxt.setText("跳转中...");
        back.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.back, R.id.phone_store_relate, R.id.tao_bao_relate,
            R.id.face_fouse_relate, R.id.id_information_relate, R.id.id_card_relate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            //身份认证
            case R.id.id_information_relate:
                Intent idInformation = new Intent(this, AttestationActivity.class);
                startActivity(idInformation);
                break;

            //运营商
            case R.id.phone_store_relate:
                Intent phone_Store = new Intent(this, PhoneStoreActivity.class);
                startActivity(phone_Store);
                break;

            //淘宝
            case R.id.tao_bao_relate:
                if (!isTaoBaoPerfect) {
                    //启动新颜SDK
                    StartXYSDKUtil.startSDK(this, memberId, terminalId, Constants.Function.FUNCTION_TAOBAO, orderInfo, orderEn);
                }
                break;

            //人脸识别
            case R.id.face_fouse_relate:

                Intent faceFouse = new Intent(this, FaceFouseActivity.class);
                startActivity(faceFouse);
                break;

            //身份证识别
            case R.id.id_card_relate:
                Intent idCard = new Intent(this, IDcardActivity.class);
                startActivity(idCard);
                break;
        }
    }
}

