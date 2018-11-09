package com.example.administrator.dataecs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.CheckInfoModel;
import com.example.administrator.dataecs.model.UpdateModel;
import com.example.administrator.dataecs.ui.fragment.TabClassiFicationFragement;
import com.example.administrator.dataecs.ui.fragment.TabMainFragement;
import com.example.administrator.dataecs.ui.fragment.TabMeFragement;
import com.example.administrator.dataecs.ui.fragment.TabShenQingFragement;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.DownLoadUntils;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.Tools;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.tab_1)
    RelativeLayout tab1;
    @BindView(R.id.tab_2)
    RelativeLayout tab2;
    @BindView(R.id.tab_3)
    RelativeLayout tab3;
    @BindView(R.id.tab_all)
    LinearLayout tabAll;
    @BindView(R.id.tab_img_1)
    ImageView tabImg1;
    @BindView(R.id.tab_tx_1)
    TextView tabTx1;
    @BindView(R.id.tab_img_2)
    ImageView tabImg2;
    @BindView(R.id.tab_tx_2)
    TextView tabTx2;
    @BindView(R.id.tab_img_3)
    ImageView tabImg3;
    @BindView(R.id.tab_tx_3)
    TextView tabTx3;
    @BindView(R.id.tab_img_4)
    ImageView tabImg4;
    @BindView(R.id.tab_tx_4)
    TextView tabTx4;
    @BindView(R.id.tab_4)
    RelativeLayout tab4;

    // 首页、分类、我的、申请
    private TabMainFragement mainFragement;
    private TabClassiFicationFragement cfFragement;
    private TabMeFragement meFragement;
    private TabShenQingFragement sQFragement;
    private FragmentManager fragmentManager;


    //获取后台返回的版本号
    private String visionNumber;
    //更新app的URL
    private String downloadUrl;
    //本地的版本号
    private int vision;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //获取app的版本号
        getSysmtemNumber();
        //获取审核状态
        if (!SharePreferencesUtil.getUserName(this).equals("")) {
            getSHType(SharePreferencesUtil.getUserName(this));
        }
        intView();
    }

    private void intView() {
        fragmentManager = getSupportFragmentManager();
        showFragement(3);

    }

    @OnClick({R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_1:
                tabImg1.setImageResource(R.drawable.home_touch);
                tabImg2.setImageResource(R.drawable.classfation_no);
                tabImg3.setImageResource(R.drawable.me_no);
                tabImg4.setImageResource(R.drawable.shenqing_no);

                tabTx1.setTextColor(getResources().getColor(R.color.tab_touch));
                tabTx2.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx3.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx4.setTextColor(getResources().getColor(R.color.tab_no));
                showFragement(0);
                break;
            case R.id.tab_2:
                tabImg1.setImageResource(R.drawable.home_no);
                tabImg2.setImageResource(R.drawable.classfatioin_touch);
                tabImg3.setImageResource(R.drawable.me_no);
                tabImg4.setImageResource(R.drawable.shenqing_no);

                tabTx1.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx2.setTextColor(getResources().getColor(R.color.tab_touch));
                tabTx3.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx4.setTextColor(getResources().getColor(R.color.tab_no));
                showFragement(1);
                break;
            case R.id.tab_3:
                tabImg1.setImageResource(R.drawable.home_no);
                tabImg2.setImageResource(R.drawable.classfation_no);
                tabImg3.setImageResource(R.drawable.me_youch);
                tabImg4.setImageResource(R.drawable.shenqing_no);

                tabTx1.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx2.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx3.setTextColor(getResources().getColor(R.color.tab_touch));
                tabTx4.setTextColor(getResources().getColor(R.color.tab_no));

                showFragement(2);
                break;
            case R.id.tab_4:
                tabImg1.setImageResource(R.drawable.home_no);
                tabImg2.setImageResource(R.drawable.classfation_no);
                tabImg3.setImageResource(R.drawable.me_no);
                tabImg4.setImageResource(R.drawable.shenqing_tap);

                tabTx1.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx2.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx3.setTextColor(getResources().getColor(R.color.tab_no));
                tabTx4.setTextColor(getResources().getColor(R.color.tab_touch));
                showFragement(3);
                break;
        }
    }

    private void showFragement(int page) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideFragement(ft);
        switch (page) {

            case 0:
                if (mainFragement != null) {
                    ft.show(mainFragement);
                } else {
                    mainFragement = new TabMainFragement();
                    ft.add(R.id.content, mainFragement);
                }

                break;
            case 1:
                if (cfFragement != null) {
                    ft.show(cfFragement);
                } else {
                    cfFragement = new TabClassiFicationFragement();
                    ft.add(R.id.content, cfFragement);
                }

                break;
            case 2:
                if (meFragement != null) {
                    ft.show(meFragement);
                } else {
                    meFragement = new TabMeFragement();
                    ft.add(R.id.content, meFragement);
                }

                break;
            case 3:
                if (sQFragement != null) {
                    ft.show(sQFragement);
                } else {
                    sQFragement = new TabShenQingFragement();
                    ft.add(R.id.content, sQFragement);
                }

                break;

        }
        ft.commit();
    }


    private void hideFragement(FragmentTransaction ft) {

        if (mainFragement != null) {
            ft.hide(mainFragement);
        }
        if (cfFragement != null) {
            ft.hide(cfFragement);
        }
        if (meFragement != null) {
            ft.hide(meFragement);
        }
        if (sQFragement != null) {
            ft.hide(sQFragement);
        }

    }

    //请求版本号
    private void getSysmtemNumber() {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<UpdateModel> call = inte.getUpdateNumber();
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {

                UpdateModel model = response.body();
                visionNumber = model.getResult().getAndroidvesion();
//                downloadUrl=model.getResult().getAndroidurl();
                downloadUrl = BaseServer.BASE_URL + BaseServer.DOWNLOAD_URL;
                vision = Tools.getVersion(MainActivity.this);
                Boolean houTaiSwitch = model.getResult().getAndroidscroll().equals("false") ? false : true;
                SharePreferencesUtil.saveHouTaiSwich(MainActivity.this, houTaiSwitch);
                new DownLoadUntils(MainActivity.this, visionNumber, downloadUrl, vision);
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {

            }
        });

    }

    //获取审核状态
    public void getSHType(String phone) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<CheckInfoModel> call = inte.getCheckInfo(phone);
        call.enqueue(new Callback<CheckInfoModel>() {
            @Override
            public void onResponse(Call<CheckInfoModel> call, Response<CheckInfoModel> response) {
                CheckInfoModel model = response.body();
                SharePreferencesUtil.saveShenQing(MainActivity.this, model.getMap().getStatus());
                if (model.getMap().getStatus() == 3) {

                    if (model.getMap().getLoanMonery() == null) {

                        SharePreferencesUtil.saveMoney(MainActivity.this, "");

                    } else {

                        SharePreferencesUtil.saveMoney(MainActivity.this, model.getMap().getLoanMonery() + "");
                    }

                    if (model.getMap().getRepayTime() == null || "".equals(model.getMap().getRepayTime())) {

                        SharePreferencesUtil.saveTime(MainActivity.this, "");

                    } else {

                        SharePreferencesUtil.saveTime(MainActivity.this, model.getMap().getRepayTime());
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckInfoModel> call, Throwable t) {

            }
        });
    }

    //------------------------------------------按两下退出应用------------------------------------------//
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
