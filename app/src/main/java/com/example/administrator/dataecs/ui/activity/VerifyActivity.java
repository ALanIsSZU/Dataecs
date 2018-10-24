package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.IsPerfectModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifyActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.proson_info_txt)
    TextView prosonInfoTxt;
    @BindView(R.id.person_info)
    RelativeLayout personInfo;
    @BindView(R.id.jin_ji_txt)
    TextView jinJiTxt;
    @BindView(R.id.jin_ji)
    RelativeLayout jinJi;
    @BindView(R.id.phone_store_txt)
    TextView phoneStoreTxt;
    @BindView(R.id.phone_store)
    RelativeLayout phoneStore;
    @BindView(R.id.bank_cord_txt)
    TextView bankCordTxt;
    @BindView(R.id.bank_cord)
    RelativeLayout bankCord;
    @BindView(R.id.zhi_ma_txt)
    TextView zhiMaTxt;
    @BindView(R.id.zhi_ma)
    RelativeLayout zhiMa;
    @BindView(R.id.work_info_txt)
    TextView workInfoTxt;
    @BindView(R.id.work_info)
    RelativeLayout workInfo;
    @BindView(R.id.zhi_fu_bao_txt)
    TextView zhiFuBaoTxt;
    @BindView(R.id.zhi_fu_bao)
    RelativeLayout zhiFuBao;
    @BindView(R.id.tao_bao_txt)
    TextView taoBaoTxt;
    @BindView(R.id.tao_bao)
    RelativeLayout taoBao;
    @BindView(R.id.gif_rela)
    RelativeLayout gifRela;

    //每个tab的资料完善状态
    boolean personPerferct = false;
    boolean jinJiPerferct = false;
    boolean phoneStorePerferct = false;
    boolean bankPerferct = false;
    boolean zhiMaPerferct = false;
    boolean workPerferct = false;
    boolean zhiFuPerferct = false;
    boolean TaoBaoPerferct = false;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    //登陆手机号
    String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_layout);
        ButterKnife.bind(this);
        intView();
        intData();
    }

    private void intView() {
        back.setVisibility(View.VISIBLE);
        gifRela.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        title.setText("验证中心");
    }

    private void intData() {

        phone = SharePreferencesUtil.getUserName(this);
        getInfoPerfect(phone);

    }

    //请求是否完善数据
    public void getInfoPerfect(String phone) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<IsPerfectModel> call = inte.getInfoPerferct(phone);
        call.enqueue(new Callback<IsPerfectModel>() {
            @Override
            public void onResponse(Call<IsPerfectModel> call, Response<IsPerfectModel> response) {
                IsPerfectModel model = response.body();

                workPerferct = model.getMap().isIsPerfectWorkInfomation();
                jinJiPerferct = model.getMap().isIsPerfectEmergent();
                bankPerferct = model.getMap().isIsPerfectMaterial();
                personPerferct = model.getMap().isPerfectPooclientInfo();

                SharePreferencesUtil.saveJinJiPefect(VerifyActivity.this,jinJiPerferct);

                prosonInfoTxt.setText(personPerferct ? "已完善" : "未完善");
                jinJiTxt.setText(jinJiPerferct ? "已完善" : "未完善");
                phoneStoreTxt.setText(phoneStorePerferct ? "已完善" : "未完善");
                bankCordTxt.setText(bankPerferct ? "已完善" : "未完善");
                zhiMaTxt.setText(zhiFuPerferct ? "已认证" : "未认证");
                workInfoTxt.setText(workPerferct ? "已完善" : "未完善");
                zhiFuBaoTxt.setText(zhiFuPerferct ? "已认证" : "未认证");
                taoBaoTxt.setText(TaoBaoPerferct ? "已认证" : "未认证");

                gifRela.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<IsPerfectModel> call, Throwable t) {
                ToastUntils.ToastShort(VerifyActivity.this, t.toString());
            }
        });
    }

    @OnClick({R.id.back, R.id.person_info, R.id.jin_ji, R.id.phone_store,
            R.id.bank_cord, R.id.zhi_ma, R.id.work_info, R.id.zhi_fu_bao, R.id.tao_bao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            //个人信息
            case R.id.person_info:
                Intent personInfo = new Intent(VerifyActivity.this, PersonActivity.class);
                personInfo.putExtra("person", personPerferct);
                startActivityForResult(personInfo, BaseServer.PERSON_CODE);
                break;

            //紧急联系人
            case R.id.jin_ji:

                Intent jinJi = new Intent(VerifyActivity.this, JinJIPersonActivity.class);
//                jinJi.putExtra("jinJi", jinJiPerferct);
                startActivity(jinJi);
                break;

            //手机运营商
            case R.id.phone_store:

                Intent phoneStore = new Intent(VerifyActivity.this, PhoneStoreActivity.class);
                startActivity(phoneStore);
                break;

            //收款银行卡
            case R.id.bank_cord:

                Intent banckCord = new Intent(VerifyActivity.this, BanckCordActivity.class);
                banckCord.putExtra("bank", bankPerferct);
                startActivityForResult(banckCord,BaseServer.BANK_CODE);
                break;

            //芝麻授权
            case R.id.zhi_ma:
                break;

            //工作信息
            case R.id.work_info:

                Intent workInfo = new Intent(VerifyActivity.this, WorkInfoActivity.class);
                workInfo.putExtra("work", workPerferct);
                startActivityForResult(workInfo,BaseServer.WORK_CODE);

                break;

            //支付宝认证
            case R.id.zhi_fu_bao:
                if (zhiFuPerferct) {
                    return;
                } else {

                    Intent zhiFuBao = new Intent(VerifyActivity.this, ZhiFuBaoActivity.class);
                    startActivity(zhiFuBao);
                }

                break;

            //淘宝认证
            case R.id.tao_bao:

                if (TaoBaoPerferct) {
                    return;
                } else {

                    Intent taoBao = new Intent(VerifyActivity.this, TaoBaoActivity.class);
                    startActivity(taoBao);
                }

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        jinJiPerferct=SharePreferencesUtil.getJinJiPefect(this);

        prosonInfoTxt.setText(personPerferct ? "已完善" : "未完善");
        jinJiTxt.setText(jinJiPerferct ? "已完善" : "未完善");
        phoneStoreTxt.setText(phoneStorePerferct ? "已完善" : "未完善");
        bankCordTxt.setText(bankPerferct ? "已完善" : "未完善");
        zhiMaTxt.setText(zhiFuPerferct ? "已认证" : "未认证");
        workInfoTxt.setText(workPerferct ? "已完善" : "未完善");
        zhiFuBaoTxt.setText(zhiFuPerferct ? "已认证" : "未认证");
        taoBaoTxt.setText(TaoBaoPerferct ? "已认证" : "未认证");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            case BaseServer.PERSON_TO_MAIN:

                if (requestCode==BaseServer.PERSON_CODE ){
                    personPerferct=data.getBooleanExtra("person_go",false);
                    prosonInfoTxt.setText(personPerferct ? "已完善" : "未完善");
                }

                break;

            case BaseServer.JIN_JI_CODE:

                break;

            case BaseServer.BANK_MAIN:

                if (requestCode==BaseServer.BANK_CODE){
                    bankPerferct=data.getBooleanExtra("banck_go",false);
                    bankCordTxt.setText(bankPerferct ? "已完善" : "未完善");
                }

                break;

            case BaseServer.WORK_TO_MAIN:

                if (requestCode==BaseServer.WORK_CODE){
                    workPerferct=data.getBooleanExtra("work_go",false);
                    workInfoTxt.setText(workPerferct ? "已完善" : "未完善");
                }

                break;


        }

    }
}
