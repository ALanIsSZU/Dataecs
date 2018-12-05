package com.example.administrator.dataecs.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.CommitPersionInfoModle;
import com.example.administrator.dataecs.model.PersonRequestModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.FileUtil;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.example.administrator.dataecs.util.Tools;
import com.google.gson.Gson;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.IOException;
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
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IDcardActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.id_card_front)
    TextView idCardFront;
    @BindView(R.id.id_front_content)
    ImageView idFrontContent;
    @BindView(R.id.id_card_contrary)
    TextView idCardContrary;
    @BindView(R.id.id_contrary_content)
    ImageView idContraryContent;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.id_card)
    EditText idCard;
    @BindView(R.id.nation)
    EditText nation;
    @BindView(R.id.birthday)
    EditText birthday;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.location)
    EditText location;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.store)
    EditText store;
    @BindView(R.id.time_of_validity)
    EditText timeOfValidity;
    @BindView(R.id.id_card_front_logo)
    ImageView idCardFrontLogo;
    @BindView(R.id.id_card_contrary_logo)
    ImageView idCardContraryLogo;
    @BindView(R.id.loading_txt)
    TextView loadingTxt;
    @BindView(R.id.loading_lay)
    View loadingLay;


    //身份证的正反面是否拍摄成功
    boolean isFrontSuccess = false;
    boolean isBackSuccess = false;
    //身份证正面和反面的图片路径
    String idCardFrontPath;
    String idCardBackPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_card_lay);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {

        back.setVisibility(View.VISIBLE);
        title.setText("身份证认证");
        loadingTxt.setText("正在识别");
    }

    @OnClick({R.id.back, R.id.id_card_front, R.id.id_card_contrary, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

//              身份证正面
            case R.id.id_card_front:
                andPermissionTools(BaseServer.ID_CARD_FRONT, false,
                        Manifest.permission.CAMERA);
                break;

//              身份证反面
            case R.id.id_card_contrary:
                andPermissionTools(BaseServer.ID_CARD_BACK, false,
                        Manifest.permission.CAMERA);
                break;

            case R.id.commit:

                if (!isFrontSuccess) {
                    ToastUntils.ToastShort(this, "请拍摄正确的身份证正面！");
                    return;
                }

                if (!isBackSuccess) {
                    ToastUntils.ToastShort(this, "请拍摄正确的身份证反面！！");
                    return;
                }

                if (name.getText().toString() == null || "".equals(name.getText().toString())) {
                    ToastUntils.ToastShort(this, "名字不能为空！");
                    return;
                }
                if (idCard.getText().toString() == null || "".equals(idCard.getText().toString())) {
                    ToastUntils.ToastShort(this, "身份证号不能为空！");
                    return;
                }
                if (nation.getText().toString() == null || "".equals(nation.getText().toString())) {
                    ToastUntils.ToastShort(this, "民族不能为空！");
                    return;
                }
                if (birthday.getText().toString() == null || "".equals(birthday.getText().toString())) {
                    ToastUntils.ToastShort(this, "生日不能为空！");
                    return;
                }
                if (sex.getText().toString() == null || "".equals(sex.getText().toString())) {
                    ToastUntils.ToastShort(this, "性别不能为空！");
                    return;
                }
                if (location.getText().toString() == null || "".equals(location.getText().toString())) {
                    ToastUntils.ToastShort(this, "地址不能为空！");
                    return;
                }
                if (store.getText().toString() == null || "".equals(store.getText().toString())) {
                    ToastUntils.ToastShort(this, "签发机关不能为空！");
                    return;
                }
                if (timeOfValidity.getText().toString() == null || "".equals(timeOfValidity.getText().toString())) {
                    ToastUntils.ToastShort(this, "有效期限不能为空！");
                    return;
                }
                loadingTxt.setText("提交中。。。");
                loadingLay.setVisibility(View.VISIBLE);

                Message msg = Message.obtain();
                msg.what = 1;
                handler.sendMessage(msg);

        }
    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    CommitPersionInfoModle infoModel = new CommitPersionInfoModle();
                    infoModel.setTbUserId((Long) SPUtils.get(IDcardActivity.this, Config.USED_ID, 1L));
                    infoModel.setName(name.getText().toString());
                    infoModel.setIdCard(idCard.getText().toString());
                    infoModel.setNation(nation.getText().toString());
                    infoModel.setBirthday(birthday.getText().toString());
                    infoModel.setSex(sex.getText().toString());
                    infoModel.setLocation(location.getText().toString());
                    infoModel.setStore(store.getText().toString());
                    infoModel.setTimeOfValidity(timeOfValidity.getText().toString());
                    infoModel.setIdCardFront(idCardFrontPath);
                    infoModel.setIdCardBack(idCardBackPath);

                    String json = new Gson().toJson(infoModel);
                    commitInfo(json);

                    break;
            }
        }
    };

    //提交身份证数据
    public void commitInfo(String json) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);

        Call<PersonRequestModel> call = inte.commitIdCardInfo(body);
        call.enqueue(new Callback<PersonRequestModel>() {
            @Override
            public void onResponse(Call<PersonRequestModel> call, retrofit2.Response<PersonRequestModel> response) {
                PersonRequestModel model = response.body();

                if (model.getCode() == 0) {
                    loadingLay.setVisibility(View.GONE);
                    ToastUntils.ToastShort(IDcardActivity.this, "提交成功");
                    SPUtils.put(IDcardActivity.this, Config.ID_CARD_PERFECT, true);
                    finish();
                } else {
                    loadingLay.setVisibility(View.GONE);
                    ToastUntils.ToastShort(IDcardActivity.this, "提交失败");
                }
            }

            @Override
            public void onFailure(Call<PersonRequestModel> call, Throwable t) {
                loadingLay.setVisibility(View.GONE);
                ToastUntils.ToastShort(IDcardActivity.this, "请求失败" + t.toString());
            }
        });

    }


    public static OkHttpClient genericClient(final RequestBody body) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
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

    //百度Api识别身份证
    private void recIDCard(final String idCardSide, final String filePath) {

        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        if (IDCardParams.ID_CARD_SIDE_FRONT.equals(idCardSide)) {

            //身份证正面
//            idCardFrontPath = filePath;
            new Thread(){

                @Override
                public void run() {
                    super.run();
                    idCardFrontPath=Tools.imageToBase64(filePath);
                }
            }.start();

            idCardFrontLogo.setVisibility(View.GONE);
            //压缩图片

            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, idFrontContent.getWidth(),
                    idFrontContent.getHeight(), true);
            idFrontContent.setImageBitmap(scaleBitmap);
        } else if (IDCardParams.ID_CARD_SIDE_BACK.equals(idCardSide)) {

            //身份证反面
//            idCardBackPath = filePath;
            new Thread(){

                @Override
                public void run() {
                    super.run();
                    idCardBackPath=Tools.imageToBase64(filePath);
                }
            }.start();

            idCardContraryLogo.setVisibility(View.GONE);
            //压缩图片
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, idContraryContent.getWidth(),
                    idContraryContent.getHeight(), true);
            idContraryContent.setImageBitmap(scaleBitmap);
        }

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {

                    if (IDCardParams.ID_CARD_SIDE_FRONT.equals(idCardSide)) {

                        if (result.getName() == null || result.getGender() == null || result.getBirthday() == null) {
                            loadingLay.setVisibility(View.GONE);
                            ToastUntils.ToastShort(IDcardActivity.this, "识别失败，请重新拍摄身份证正面");
                            isFrontSuccess = false;
                            loadingLay.setVisibility(View.GONE);
                        } else {
                            loadingLay.setVisibility(View.GONE);
                            isFrontSuccess = true;
                            name.setText(result.getName().getWords());
                            idCard.setText(result.getIdNumber().getWords());
                            nation.setText(result.getEthnic().getWords());
                            birthday.setText(result.getBirthday().getWords());
                            sex.setText(result.getGender().getWords());
                            location.setText(result.getAddress().getWords());
                        }
                    } else if (IDCardParams.ID_CARD_SIDE_BACK.equals(idCardSide)) {

                        if (result.getSignDate() == null || result.getIssueAuthority() == null) {
                            loadingLay.setVisibility(View.GONE);
                            ToastUntils.ToastShort(IDcardActivity.this, "识别失败，请重新拍摄身份证反面");
                            isBackSuccess = false;
                        } else {
                            loadingLay.setVisibility(View.GONE);
                            isBackSuccess = true;
                            timeOfValidity.setText(result.getSignDate().getWords() + "-" + result.getExpiryDate().getWords());
                            store.setText(result.getIssueAuthority().getWords());
                        }
                    }

                }
            }

            @Override
            public void onError(OCRError error) {
                ToastUntils.ToastShort(IDcardActivity.this, "识别失败," + error.toString());
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
                        final AlertDialog.Builder builder = new AlertDialog.Builder(IDcardActivity.this);

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

    private PermissionListener listener = new PermissionListener() {

        //授权成功
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            //身份证正面
            if (requestCode == BaseServer.ID_CARD_FRONT) {

                Intent intent = new Intent(IDcardActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, BaseServer.REQUEST_CODE_CAMERA);
            }

            //身份证反面
            if (requestCode == BaseServer.ID_CARD_BACK) {

                Intent intent1 = new Intent(IDcardActivity.this, CameraActivity.class);
                intent1.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent1.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                startActivityForResult(intent1, BaseServer.REQUEST_CODE_CAMERA);
            }
        }

        //授权失败
        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (requestCode == BaseServer.ID_CARD_FRONT) {
                //每次拒绝都会弹出
                AndPermission.defaultSettingDialog(IDcardActivity.this, BaseServer.ID_CARD_FAIL)
                        .setTitle("申请权限")
                        .setMessage("米店 需要使用相机权限，获取身份证。")
                        .setPositiveButton("去设置")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
            if (requestCode == BaseServer.ID_CARD_BACK) {
                //每次拒绝都会弹出
                AndPermission.defaultSettingDialog(IDcardActivity.this, BaseServer.ID_CARD_FAIL)
                        .setTitle("申请权限")
                        .setMessage("米店 需要使用相机权限，获取身份证。")
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

        //百度的UI库返回的结果
        if (requestCode == BaseServer.REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                loadingLay.setVisibility(View.VISIBLE);
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }

        if (requestCode == BaseServer.ID_CARD_FAIL) {
            if (AndPermission.hasPermission(IDcardActivity.this, Manifest.permission.CAMERA)) {
                //有这个权限，处理的逻辑

            } else {
                //没有这个权限，处理的逻辑
                AndPermission.defaultSettingDialog(IDcardActivity.this, BaseServer.ID_CARD_FAIL)
                        .setTitle("申请权限")
                        .setMessage("米店 需要使用相机权限，获取身份证。")
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
