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
import android.widget.Toast;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.example.administrator.dataecs.MyApplication;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.PerfectTypeModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.ConfigUtil;
import com.example.administrator.dataecs.util.Constants;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.StartXYSDKUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.xinyan.bigdata.XinYanSDK;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    @BindView(R.id.id_card_type)
    TextView idCardType;


    //资料和认证是否完善
    boolean isMyelfInfoPerfect;
    boolean isPhonePerfect;
    boolean isTaoBaoPerfect;
    boolean isFacePerfect;
    boolean isIDCardPerfect;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_attantin_lay);
        ButterKnife.bind(this);
        intView();
//        getType();
    }

    private void intView() {
        title.setText("资料认证");

        back.setVisibility(View.VISIBLE);

        //百度api的添加活体动作
        MyApplication.livenessList.clear();
        MyApplication.livenessList.add(LivenessTypeEnum.Eye);
        MyApplication.livenessList.add(LivenessTypeEnum.Mouth);
//        MyApplication.livenessList.add(LivenessTypeEnum.HeadUp);
//        MyApplication.livenessList.add(LivenessTypeEnum.HeadDown);
        MyApplication.livenessList.add(LivenessTypeEnum.HeadLeft);
        MyApplication.livenessList.add(LivenessTypeEnum.HeadRight);
        MyApplication.livenessList.add(LivenessTypeEnum.HeadLeftOrRight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        getType();
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

                if (!isPhonePerfect) {

//                    Intent intent = new Intent(DataPerfectActivity.this, PhoneStoreActivity.class);
//                    startActivity(intent);
                    ConfigUtil.idcard = "";
                    ConfigUtil.realname = "";
                    ConfigUtil.phoneNum = "";
                    ConfigUtil.phoneServerCode = "";

//                ConfigUtil.idcard = "330325197802264617";
//                ConfigUtil.realname = "王克昌";
//                ConfigUtil.phoneNum = "13682676171";
//                ConfigUtil.phoneServerCode = "139222";
                    ConfigUtil.carrierCanInput = Constants.YES;
                    ConfigUtil.carrierIDandNameShow = Constants.YES;


                    XinYanSDK.getInstance().setMerchantAttribute(Config.XyApiUser,
                            Config.XyApiKey);
                    StartXYSDKUtil.startSDK(DataPerfectActivity.this, Constants.Function.FUNCTION_CARRIER);
//                XinYanSDK.getInstance().setMerchantAttribute(apiUser, apiKey);
//                StartXYSDKUtil.startSDK(PhoneStoreActivity.this, Constants.Function.FUNCTION_CARRIER, Config.orderEn, false);

                }

                break;

            //淘宝
            case R.id.tao_bao_relate:
                if (!isTaoBaoPerfect) {
                    //启动新颜SDK
                    loadingTxt.setText("跳转中...");
                    loadingLay.setVisibility(View.VISIBLE);
                    XinYanSDK.getInstance().setMerchantAttribute(Config.XyApiUser,
                            Config.XyApiKey);
                    StartXYSDKUtil.startSDK(DataPerfectActivity.this,
                            Constants.Function.FUNCTION_TAOBAOPAY);
                }
                break;

            //百度的人脸识别
            case R.id.face_fouse_relate:

                if (!isFacePerfect) {

                    andPermissionTools(BaseServer.FACE_FOCUS, false,
                            Manifest.permission.CAMERA);
                }
                break;

            //身份证识别
            case R.id.id_card_relate:

                if (!isIDCardPerfect) {

                    Intent idCard = new Intent(this, IDcardActivity.class);
                    startActivity(idCard);

                }
                break;
        }
    }

    //获取认证状态
    public void getType() {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(DataPerfectActivity.this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingTxt.setText("加载中...");
        loadingLay.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<PerfectTypeModel> call = inte.getPerfectType((long) SPUtils.get(DataPerfectActivity.this, Config.USED_ID, 1L));
        call.enqueue(new Callback<PerfectTypeModel>() {
            @Override
            public void onResponse(Call<PerfectTypeModel> call, Response<PerfectTypeModel> response) {
                PerfectTypeModel model = response.body();
                if ("success".equals(model.getMsg())) {

                    SPUtils.put(DataPerfectActivity.this, Config.ID_INFOMATION_PERFECT,
                            model.getUserStatus().getDataStatus() == 0 ? false : true);
                    SPUtils.put(DataPerfectActivity.this, Config.PHONE_STORE_PERFECT,
                            model.getUserStatus().getOperatorStatus() == 0 ? false : true);
                    SPUtils.put(DataPerfectActivity.this, Config.TAO_BAO_PERFECT,
                            model.getUserStatus().getZhifubaoStatus() == 0 ? false : true);
                    SPUtils.put(DataPerfectActivity.this, Config.FACE_FOUSE_PERFECT,
                            model.getUserStatus().getFaceStatus() == 0 ? false : true);
                    SPUtils.put(DataPerfectActivity.this, Config.ID_CARD_PERFECT,
                            model.getUserStatus().getIdCardStatus() == 0 ? false : true);

                    isMyelfInfoPerfect = (boolean) SPUtils.get(DataPerfectActivity.this, Config.ID_INFOMATION_PERFECT, false);
                    isPhonePerfect = (boolean) SPUtils.get(DataPerfectActivity.this, Config.PHONE_STORE_PERFECT, false);
                    isTaoBaoPerfect = (boolean) SPUtils.get(DataPerfectActivity.this, Config.TAO_BAO_PERFECT, false);
                    isFacePerfect = (boolean) SPUtils.get(DataPerfectActivity.this, Config.FACE_FOUSE_PERFECT, false);
                    isIDCardPerfect = (boolean) SPUtils.get(DataPerfectActivity.this, Config.ID_CARD_PERFECT, false);

                    idInformationType.setText(isMyelfInfoPerfect ? "已完善" : "未完善");
                    phoneStoreType.setText(isPhonePerfect ? "已完善" : "未完善");
                    taoBaoType.setText(isTaoBaoPerfect ? "已完善" : "未完善");
                    faceFouseType.setText(isFacePerfect ? "已完善" : "未完善");
                    idCardType.setText(isIDCardPerfect ? "已完善" : "未完善");

                    loadingLay.setVisibility(View.GONE);
                } else {
                    ToastUntils.ToastShort(DataPerfectActivity.this, "请求失败！");
                    loadingLay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PerfectTypeModel> call, Throwable t) {
                ToastUntils.ToastShort(DataPerfectActivity.this, "请求失败！" + t.toString());
                loadingLay.setVisibility(View.GONE);
            }
        });

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
            if (requestCode == BaseServer.FACE_FOCUS) {
                //调取人脸识别api
                setFaceConfig();
                Intent faceFouse = new Intent(DataPerfectActivity.this, FaceFouseActivity.class);
                startActivity(faceFouse);
            }
        }

        //授权失败
        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (requestCode == BaseServer.FACE_FOCUS) {
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

        if (requestCode == BaseServer.FACE_FOCUS_FAIL) {
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

