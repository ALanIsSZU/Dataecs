package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.dialog.AlertDialog;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.BanckCardRequsetModel;
import com.example.administrator.dataecs.model.MailCommitModel;
import com.example.administrator.dataecs.model.RequsetMailModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.StringUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttestationActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.name_content)
    TextView nameContent;
    @BindView(R.id.id_cord)
    EditText idCord;
    @BindView(R.id.id_cord_content)
    TextView idCordContent;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.phone_content)
    TextView phoneContent;
    @BindView(R.id.company_name)
    EditText companyName;
    @BindView(R.id.company_name_content)
    TextView companyNameContent;
    @BindView(R.id.company_location)
    EditText companyLocation;
    @BindView(R.id.company_location_content)
    TextView companyLocationContent;
    @BindView(R.id.position)
    EditText position;
    @BindView(R.id.position_content)
    TextView positionContent;
    @BindView(R.id.time)
    EditText time;
    @BindView(R.id.timecontent)
    TextView timecontent;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.money_content)
    TextView moneyContent;
    @BindView(R.id.emergency_nane)
    EditText emergencyNane;
    @BindView(R.id.emergency_nane_content)
    TextView emergencyNaneContent;
    @BindView(R.id.emergency_relation)
    EditText emergencyRelation;
    @BindView(R.id.emergency_relation_content)
    TextView emergencyRelationContent;
    @BindView(R.id.emergency_phone)
    EditText emergencyPhone;
    @BindView(R.id.emergency_phone_content)
    TextView emergencyPhoneContent;
    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.add_contacts)
    ImageView addContacts;
    @BindView(R.id.content_lay)
    LinearLayout contentLay;
    @BindView(R.id.name_relate)
    RelativeLayout nameRelate;
    @BindView(R.id.id_relate)
    RelativeLayout idRelate;
    @BindView(R.id.phone_relate)
    RelativeLayout phoneRelate;
    @BindView(R.id.company_name_relate)
    RelativeLayout companyNameRelate;
    @BindView(R.id.company_location_relate)
    RelativeLayout companyLocationRelate;
    @BindView(R.id.position_relate)
    RelativeLayout positionRelate;
    @BindView(R.id.time_relate)
    RelativeLayout timeRelate;
    @BindView(R.id.money_relate)
    RelativeLayout moneyRelate;
    @BindView(R.id.ji_ben_info)
    TextView jiBenInfo;
    @BindView(R.id.zhi_zi_info)
    TextView zhiZiInfo;
    @BindView(R.id.mail_info)
    TextView mailInfo;
    @BindView(R.id.emergency_name_relate)
    RelativeLayout emergencyNameRelate;
    @BindView(R.id.emergency_relation_relate)
    RelativeLayout emergencyRelationRelate;
    @BindView(R.id.emergency_phone_relate)
    RelativeLayout emergencyPhoneRelate;
    @BindView(R.id.loading_lay)
    View loadingLayout;
    @BindView(R.id.loading_txt)
    TextView loadingTxt;

    //添加了布局的几个Edite对象集合
    //联系人
    List<EditText> contactsName;
    //联系人关系
    List<TextView> contactRelationship;
    //联系人电话
    List<EditText> contactsPhone;
    //整个布局的集合
    List<LinearLayout> allLay;

    //判断基本信息和资质要求要不要隐藏
    boolean jiBen = false;
    boolean zhiZi = false;
    boolean mailOpen = true;

    //基本信息和资质要求的json
    String baseJson;
    //联系人的json
    String mailJson;

    //紧急联系人亲属的选项
    String oneStr = "母子/母女";
    String secondStr = "兄弟/兄妹";
    String thirdStr = "朋友";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identity_authentication);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        title.setText("身份认证");
        back.setVisibility(View.VISIBLE);

        contactsName = new ArrayList<>();
        contactRelationship = new ArrayList<>();
        contactsPhone = new ArrayList<>();
        allLay = new ArrayList<>();

        loadingTxt.setText("正在加载。。。");

        for (int i = 0; i < 3; i++) {
            View lay = lay = View.inflate(AttestationActivity.this, R.layout.add_layout, null);

            //整个布局
            LinearLayout contentLinear = lay.findViewById(R.id.content_linear);
            contentLinear.setVisibility(View.GONE);

            EditText name = lay.findViewById(R.id.other_emergency_nane);
            EditText relationship = lay.findViewById(R.id.other_emergency_relation);
            EditText phone = lay.findViewById(R.id.other_emergency_phone);


            final TextView one = lay.findViewById(R.id.one_txt);
            final TextView sencode = lay.findViewById(R.id.second_txt);
            final TextView thrid = lay.findViewById(R.id.thrid_txt);
            final TextView four = lay.findViewById(R.id.four_txt);
            final TextView five = lay.findViewById(R.id.five_txt);


            if (i == 0) {
                one.setVisibility(View.VISIBLE);
                sencode.setVisibility(View.VISIBLE);
                thrid.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.GONE);

                contactRelationship.add(one);
                contactRelationship.add(sencode);

                one.setText("母子/母女");
                one.setBackgroundResource(R.drawable.select_backgromund_touch);
                sencode.setText("父子/父女");

                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setBackgroundResource(R.drawable.select_backgromund_touch);
                        sencode.setBackgroundResource(R.drawable.select_background_noting);
                        oneStr = "母子/母女";
                    }
                });

                sencode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one.setBackgroundResource(R.drawable.select_background_noting);
                        sencode.setBackgroundResource(R.drawable.select_backgromund_touch);
                        oneStr = "父子/父女";
                    }
                });


            } else if (i == 1) {
                one.setVisibility(View.GONE);
                sencode.setVisibility(View.GONE);
                thrid.setVisibility(View.VISIBLE);
                four.setVisibility(View.VISIBLE);
                five.setVisibility(View.GONE);

                contactRelationship.add(thrid);
                contactRelationship.add(four);

                thrid.setText("兄弟/兄妹");
                thrid.setBackgroundResource(R.drawable.select_backgromund_touch);
                four.setText("姐弟/姐妹");


                thrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        thrid.setBackgroundResource(R.drawable.select_backgromund_touch);
                        four.setBackgroundResource(R.drawable.select_background_noting);
                        secondStr = "兄弟/兄妹";
                    }

                });

                four.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        thrid.setBackgroundResource(R.drawable.select_background_noting);
                        four.setBackgroundResource(R.drawable.select_backgromund_touch);
                        secondStr = "姐弟/姐妹";

                    }
                });
            } else if (i == 2) {
                one.setVisibility(View.GONE);
                sencode.setVisibility(View.GONE);
                thrid.setVisibility(View.GONE);
                four.setVisibility(View.GONE);
                five.setVisibility(View.VISIBLE);
                contactRelationship.add(five);

                five.setText("朋友");
                five.setBackgroundResource(R.drawable.select_backgromund_touch);
                thirdStr = "朋友";
            }

            contactsName.add(name);
            contactsPhone.add(phone);
            allLay.add(contentLinear);
            contentLay.addView(lay);
        }

        //获取数据
        getInfo((long) SPUtils.get(this, Config.USED_ID, 1L));

    }


    @OnClick({R.id.back, R.id.commit_btn, R.id.add_contacts,
            R.id.zhi_zi_info, R.id.ji_ben_info, R.id.mail_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.commit_btn:

//                    if (name.getText().toString() == null || "".equals(name.getText().toString())) {
//                        ToastUntils.ToastShort(AttestationActivity.this, "请输入正确姓名");
//                        return;
//                    }
//
//                    if (idCord.getText().toString() == null || "".equals(idCord.getText().toString())
//                            || !StringUtil.isTrueID(idCord.getText().toString())) {
//                        ToastUntils.ToastShort(AttestationActivity.this, "请输入正确身份证号码");
//                        return;
//                    }

                if (companyName.getText().toString() == null || "".equals(companyName.getText().toString())) {
                    ToastUntils.ToastShort(AttestationActivity.this, "请输入正确公司姓名");
                    return;
                }

                if (companyLocation.getText().toString() == null || "".equals(companyLocation.getText().toString())) {
                    ToastUntils.ToastShort(AttestationActivity.this, "请输入正确公司地址");
                    return;
                }

                if (position.getText().toString() == null || "".equals(position.getText().toString())) {
                    ToastUntils.ToastShort(AttestationActivity.this, "请输入正确职位");
                    return;
                }

                if (time.getText().toString() == null || "".equals(time.getText().toString())) {
                    ToastUntils.ToastShort(AttestationActivity.this, "请输入正确工作时间");
                    return;
                }

                if (money.getText().toString() == null || "".equals(money.getText().toString())) {
                    ToastUntils.ToastShort(AttestationActivity.this, "请输入正确工作待遇");
                    return;
                }

                if (contactsName != null && contactsName.size() > 0) {

                    for (int i = 0; i < contactsName.size(); i++) {
                        if (contactsName.get(i).getText().toString() == null || "".equals(contactsName.get(i).getText().toString())) {
                            ToastUntils.ToastShort(AttestationActivity.this, "请输入正确联系人姓名");
                            return;
                        }
                    }

                    for (int i = 0; i < contactsPhone.size(); i++) {
                        if (contactsPhone.get(i).getText().toString() == null || "".equals(contactsPhone.get(i).getText().toString()) || !StringUtil.isMobileNO(contactsPhone.get(i).getText().toString())) {
                            ToastUntils.ToastShort(AttestationActivity.this, "请输入正确联系人电话号码");
                            return;
                        }
                    }

                }
                //构造资质要求和联系人信息的json
                MailCommitModel bean = new MailCommitModel();
                bean.setTbUserId((long) SPUtils.get(this, Config.USED_ID, 1L));
                bean.setCompan(companyName.getText().toString());
                bean.setPosition(position.getText().toString());
                bean.setIncome(money.getText().toString());
                bean.setSeniority(time.getText().toString());
                bean.setUse("");
                bean.setUnitAddress("");
                bean.setAddressDetails(companyLocation.getText().toString());
                bean.setEducation("");
                bean.setResidence("");
                bean.setResidenceDetails("");

                if (contactsName != null && contactsName.size() > 0) {
                    for (int i = 0; i < contactsName.size(); i++) {

                        if (i == 0) {
                            bean.setRelation1(oneStr);
                            bean.setName1(contactsName.get(i).getText().toString());
                            bean.setNumber1(contactsPhone.get(i).getText().toString());
                        } else if (i == 1) {
                            bean.setRelation2(secondStr);
                            bean.setName2(contactsName.get(i).getText().toString());
                            bean.setNumber2(contactsPhone.get(i).getText().toString());
                        } else if (i == 2) {
                            bean.setRelation3(thirdStr);
                            bean.setName3(contactsName.get(i).getText().toString());
                            bean.setNumber3(contactsPhone.get(i).getText().toString());
                        }

                    }

                }

                final String json = new Gson().toJson(bean);


                new AlertDialog(this).builder()
                        .setMsg("确定提交")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commitMailInfo(json);
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

                break;

            //添加联系人布局
            case R.id.add_contacts:
//                View lay = lay = View.inflate(AttestationActivity.this, R.layout.add_layout, null);
//                EditText name = lay.findViewById(R.id.other_emergency_nane);
//                EditText relationship = lay.findViewById(R.id.other_emergency_relation);
//                EditText phone = lay.findViewById(R.id.other_emergency_phone);
//
//                contactsName.add(name);
//                contactRelationship.add(relationship);
//                contactsPhone.add(phone);
//                contentLay.addView(lay);
                break;
            case R.id.ji_ben_info:

                if (jiBen) {
                    jiBen = false;
                    jiBenInfo.setText("展开");
                    nameRelate.setVisibility(View.GONE);
                    idRelate.setVisibility(View.GONE);
                    phoneRelate.setVisibility(View.GONE);
                } else {
                    jiBen = true;
                    jiBenInfo.setText("收起");
                    nameRelate.setVisibility(View.VISIBLE);
                    idRelate.setVisibility(View.VISIBLE);
                    phoneRelate.setVisibility(View.GONE);
                }

                break;

            case R.id.zhi_zi_info:

                if (zhiZi) {
                    zhiZi = false;
                    zhiZiInfo.setText("展开");
                    companyNameRelate.setVisibility(View.GONE);
                    companyLocationRelate.setVisibility(View.GONE);
                    positionRelate.setVisibility(View.GONE);
                    timeRelate.setVisibility(View.GONE);
                    moneyRelate.setVisibility(View.GONE);
                } else {
                    zhiZi = true;
                    zhiZiInfo.setText("收起");
                    companyNameRelate.setVisibility(View.VISIBLE);
                    companyLocationRelate.setVisibility(View.VISIBLE);
                    positionRelate.setVisibility(View.VISIBLE);
                    timeRelate.setVisibility(View.VISIBLE);
                    moneyRelate.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.mail_info:

                if (allLay != null && allLay.size() > 0) {

                    if (mailOpen) {
                        mailInfo.setText("收起");
                        mailOpen = false;
                        for (int i = 0; i < allLay.size(); i++) {

                            allLay.get(i).setVisibility(View.VISIBLE);

                        }
                    } else {
                        mailInfo.setText("展开");
                        mailOpen = true;
                        for (int i = 0; i < allLay.size(); i++) {

                            allLay.get(i).setVisibility(View.GONE);

                        }
                    }

                }


                break;
        }
    }


    //提交数据（联系人信息）
    public void commitMailInfo(String json) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingLayout.setVisibility(View.VISIBLE);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(genericClient(body))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<BanckCardRequsetModel> call = inte.commitJinJiInfo(body);
        call.enqueue(new Callback<BanckCardRequsetModel>() {
            @Override
            public void onResponse(Call<BanckCardRequsetModel> call, Response<BanckCardRequsetModel> response) {
                BanckCardRequsetModel model = response.body();
                if (!"fail".equals(model.getMap().getRes())) {
                    ToastUntils.ToastShort(AttestationActivity.this, model.getMap().getMsg());
                    loadingLayout.setVisibility(View.GONE);
                    SPUtils.put(AttestationActivity.this, Config.ID_INFOMATION_PERFECT, true);
                    finish();
                } else {
                    ToastUntils.ToastShort(AttestationActivity.this, model.getMap().getMsg());
                    loadingLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BanckCardRequsetModel> call, Throwable t) {
                ToastUntils.ToastShort(AttestationActivity.this, "请求失败！" + t.toString());
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    public static OkHttpClient genericClient(final RequestBody body) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
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

    //请求数据（全部数据）
    public void getInfo(long userid) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingLayout.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);

        Call<RequsetMailModel> call = inte.getAllInfo(userid);
        call.enqueue(new Callback<RequsetMailModel>() {
            @Override
            public void onResponse(Call<RequsetMailModel> call, Response<RequsetMailModel> response) {
                RequsetMailModel model = response.body();

                if (model.getCode() == 0) {
                    //界面显示

                    if (model.getUserData() != null) {

                        companyName.setText(model.getUserData().getCompan());
                        companyLocation.setText(model.getUserData().getAddressDetails());
                        position.setText(model.getUserData().getPosition());
                        time.setText(model.getUserData().getSeniority());
                        money.setText(model.getUserData().getIncome());

                        contactsName.get(0).setText(model.getUserData().getName1());
                        contactsName.get(1).setText(model.getUserData().getName2());
                        contactsName.get(2).setText(model.getUserData().getName3());

                        contactsPhone.get(0).setText(model.getUserData().getNumber1());
                        contactsPhone.get(1).setText(model.getUserData().getNumber2());
                        contactsPhone.get(2).setText(model.getUserData().getNumber3());

                        if (oneStr.equals(model.getUserData().getRelation1())) {
                            contactRelationship.get(0).setBackgroundResource(R.drawable.select_backgromund_touch);
                            contactRelationship.get(1).setBackgroundResource(R.drawable.select_background_noting);
                        } else {
                            contactRelationship.get(0).setBackgroundResource(R.drawable.select_background_noting);
                            contactRelationship.get(1).setBackgroundResource(R.drawable.select_backgromund_touch);
                        }

                        if (secondStr.equals(model.getUserData().getRelation2())) {
                            contactRelationship.get(2).setBackgroundResource(R.drawable.select_backgromund_touch);
                            contactRelationship.get(3).setBackgroundResource(R.drawable.select_background_noting);
                        } else {
                            contactRelationship.get(2).setBackgroundResource(R.drawable.select_background_noting);
                            contactRelationship.get(3).setBackgroundResource(R.drawable.select_backgromund_touch);
                        }

                    }

                } else {
                    ToastUntils.ToastShort(AttestationActivity.this, "请求失败！");
                }

                loadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RequsetMailModel> call, Throwable t) {
                ToastUntils.ToastShort(AttestationActivity.this, "请求失败！" + t.toString());
                loadingLayout.setVisibility(View.GONE);
            }
        });


    }


}
