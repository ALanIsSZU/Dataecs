package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.AgreementModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
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

public class AgreementActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.loading_txt)
    TextView loadingTxt;
    @BindView(R.id.loading_lay)
    View loadingLay;
    @BindView(R.id.content_title)
    TextView contentTitle;

    //是注册协议还是借款协议
    boolean isRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agreement_lay);
        ButterKnife.bind(this);
        intView();
        getInfo();
    }

    //获取控件的宽高
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
//            int width= Tools.px2dip(this,metrics.widthPixels);
//            int height=Tools.px2dip(this,metrics.heightPixels);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loadingLay.getLayoutParams();
            params.height = metrics.heightPixels;
            params.width = metrics.widthPixels;
            loadingLay.setLayoutParams(params);

        }

    }


    private void intView() {

        back.setVisibility(View.VISIBLE);
        loadingTxt.setText("加载中。。。");

        isRegister = getIntent().getBooleanExtra("isRegister", false);

        if (isRegister) {
            title.setText("用户协议");
        } else {

            title.setText("借款协议");
        }


    }

    @OnClick(R.id.back)
    public void onViewClicked() {

        finish();

    }

    public void getInfo() {
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
        Call<AgreementModel> call = inte.getAgreementInfo(Config.roleId);

        call.enqueue(new Callback<AgreementModel>() {
            @Override
            public void onResponse(Call<AgreementModel> call, Response<AgreementModel> response) {
                AgreementModel model = response.body();

                if (model.getCode() == 0) {

                    int nunm = 0;
                    if (model.getAgreementList() != null) {

                        if (isRegister) {

                            for (int i = 0; i < model.getAgreementList().size(); i++) {
                                if ("用户协议".equals(model.getAgreementList().get(i).getAgreementTitle())) {
                                    nunm = i;
                                }
                            }

                            content.setText(Html.fromHtml(model.getAgreementList().get(nunm).getAgreementContent()));

                        } else {
                            for (int i = 0; i < model.getAgreementList().size(); i++) {
                                if ("借款协议".equals(model.getAgreementList().get(i).getAgreementTitle())) {
                                    nunm = i;
                                }
                            }
                            content.setText(Html.fromHtml(model.getAgreementList().get(nunm).getAgreementContent()));
                        }

                    }

                } else {
                    ToastUntils.ToastShort(AgreementActivity.this, model.getMsg());
                }
                loadingLay.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<AgreementModel> call, Throwable t) {

                ToastUntils.ToastShort(AgreementActivity.this, "服务器链接失败!" + t.toString());
                loadingLay.setVisibility(View.GONE);

            }
        });
    }

}
