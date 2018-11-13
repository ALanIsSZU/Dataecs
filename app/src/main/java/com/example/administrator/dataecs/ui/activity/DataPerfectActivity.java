package com.example.administrator.dataecs.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.example.administrator.dataecs.MyApplication;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Constants;
import com.example.administrator.dataecs.util.StartXYSDKUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

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

        //百度api的添加活体动作
        MyApplication.livenessList.add(LivenessTypeEnum.Eye);
        MyApplication.livenessList.add(LivenessTypeEnum.Mouth);
        MyApplication.livenessList.add(LivenessTypeEnum.HeadUp);
        MyApplication.livenessList.add(LivenessTypeEnum.HeadDown);
        MyApplication.livenessList.add(LivenessTypeEnum.HeadLeft);
        MyApplication.livenessList.add(LivenessTypeEnum.HeadRight);
        MyApplication.livenessList.add(LivenessTypeEnum.HeadLeftOrRight);
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

            //百度的人脸识别
            case R.id.face_fouse_relate:

                if (!isFacePerfect){

                    andPermissionTools(BaseServer.FACE_FOCUS, false,
                            Manifest.permission.CAMERA);
                }
                break;

            //身份证识别
            case R.id.id_card_relate:
                Intent idCard = new Intent(this, IDcardActivity.class);
                startActivity(idCard);
                break;
        }
    }

        //-------------------------------------------权限检测开始---------------------------------------------------------------------//

        public void andPermissionTools(int code, final boolean cancelable, String... permissions) {

            AndPermission.with(this)
                    .permission(permissions)
                    .requestCode(code)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(DataPerfectActivity.this);

                            builder.setTitle("授权界面")
                                    .setMessage("你已经取消授权，请打授权界面开启权限")
                                    .setCancelable(cancelable)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            rationale.resume();

                                        }
                                    }).show();
                        }
                    })
                    .callback(listener)
                    .start();
        }

        private void setFaceConfig() {
            FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
            // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整（百度人脸识别）

            config.setLivenessTypeList(MyApplication.livenessList);
            config.setLivenessRandom(MyApplication.isLivenessRandom);
            config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
            config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
            config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
            config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
            config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
            config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
            config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
            config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
            config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
            config.setCheckFaceQuality(true);
            config.setFaceDecodeNumberOfThreads(2);

            FaceSDKManager.getInstance().setFaceConfig(config);

        }


        private PermissionListener listener = new PermissionListener() {
            //授权成功
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                if (requestCode==BaseServer.FACE_FOCUS){
                    //调取人脸识别api
                    setFaceConfig();
                    Intent faceFouse = new Intent(DataPerfectActivity.this, FaceFouseActivity.class);
                    startActivity(faceFouse);
                }
            }
            //授权失败
            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                if (requestCode==BaseServer.FACE_FOCUS){
                    //每次拒绝都会弹出
                    AndPermission.defaultSettingDialog(DataPerfectActivity.this, BaseServer.ID_CARD_FAIL)
                            .setTitle("申请权限")
                            .setMessage("米店 需要使用相机权限，进行人脸识别。")
                            .setPositiveButton("去设置")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
            }
        };


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode== BaseServer.FACE_FOCUS_FAIL){
                if (AndPermission.hasPermission(DataPerfectActivity.this, Manifest.permission.CAMERA)) {
                    //有这个权限，处理的逻辑

                } else {
                    //没有这个权限，处理的逻辑
                    AndPermission.defaultSettingDialog(DataPerfectActivity.this, BaseServer.ID_CARD_FAIL)
                            .setTitle("申请权限")
                            .setMessage("米店 需要使用相机权限，进行人脸识别。")
                            .setPositiveButton("去设置")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
            }

        }
    }

