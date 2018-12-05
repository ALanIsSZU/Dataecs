package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.BanckCardRequsetModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.StringUtil;
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

public class AddBanckCardActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.id_cord)
    EditText idCord;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.yinhanka)
    EditText yinhanka;
    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.content_lay)
    LinearLayout contentLay;
    @BindView(R.id.loading_lay)
    View loadingLay;

    //登录手机号
    String loginPhone;

    boolean isPerfect = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banck_add_info_layout);
        ButterKnife.bind(this);
        back.setVisibility(View.VISIBLE);
        title.setText("添加银行卡");
    }

    @OnClick({R.id.back, R.id.commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

                finish();
                break;

            case R.id.commit_btn:
                loginPhone = SharePreferencesUtil.getUserName(this);
                if ("".equals(name.getText().toString())) {
                    Toast.makeText(this, "输入正确名字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (idCord.getText().toString() == null || "".equals(idCord.getText().toString()) || !StringUtil.isTrueID(idCord.getText().toString())) {
                    Toast.makeText(this, "输入正确的身份证", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText().toString() == null || "".equals(phone.getText().toString()) || !StringUtil.isMobileNO(phone.getText().toString())) {
                    Toast.makeText(this, "输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (yinhanka.getText().toString().equals("") || yinhanka.getText().toString() == null || !StringUtil.checkBankCard(yinhanka.getText().toString())) {
                    Toast.makeText(this, "输入正确的银行卡", Toast.LENGTH_SHORT).show();
                    return;
                }

//                BanckCommitModle banckInfo = new BanckCommitModle();
//                banckInfo.setUserMobile(loginPhone);
//                banckInfo.setName(name.getText().toString());
//                banckInfo.setIdNumber(idCord.getText().toString());
//                banckInfo.setCard(yinhanka.getText().toString());
//                banckInfo.setPhone(phone.getText().toString());
//
//                String json = new Gson().toJson(banckInfo);
                commitBankInfo(
                        (long) SPUtils.get(this, Config.USED_ID, 1L),
                        idCord.getText().toString().trim(),
                        yinhanka.getText().toString().trim(),
                        phone.getText().toString().trim(),
                        name.getText().toString().trim()
                );

                break;
        }
    }

    public void commitBankInfo(long userid, final String idCard, String banckNumber,
                               String phone, String userName) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingLay.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<BanckCardRequsetModel> call = inte.commiBanckInfo(userid, idCard, banckNumber, phone, userName);

        call.enqueue(new Callback<BanckCardRequsetModel>() {
            @Override
            public void onResponse(Call<BanckCardRequsetModel> call, Response<BanckCardRequsetModel> response) {

                BanckCardRequsetModel modle = response.body();
                if ("success".equals(modle.getMap().getRes())) {
                    ToastUntils.ToastShort(AddBanckCardActivity.this, modle.getMap().getMsg());
                    SPUtils.put(AddBanckCardActivity.this,Config.BANCK_PERFECT,true);
                    loadingLay.setVisibility(View.GONE);
                    finish();
                } else {
                    ToastUntils.ToastShort(AddBanckCardActivity.this, modle.getMap().getMsg());
                    loadingLay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BanckCardRequsetModel> call, Throwable t) {
                ToastUntils.ToastShort(AddBanckCardActivity.this, "请求失败！" + t.toString());
                loadingLay.setVisibility(View.GONE);
            }
        });
    }


}
