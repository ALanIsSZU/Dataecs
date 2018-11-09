package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.BankListAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.BankInfoModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.SystemUntils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BanckCordActivity extends AppCompatActivity {


    //刷新
    boolean isRefresh = false;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.change_info)
    TextView changeInfo;
    @BindView(R.id.list)
    ListView listView;

    //登录的手机号
    String loginPhone;
    //ListView脚部布局
    View bootView;
    LinearLayout footLay;

    BankListAdapter adapter;
    List<BankInfoModel.ListBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jin_ji_contenet);
        ButterKnife.bind(this);
        intView();
        intData();
    }

    private void intView() {

        back.setVisibility(View.VISIBLE);
        title.setText("银行卡管理");
        bootView = LayoutInflater.from(this).inflate(R.layout.banck_foot, null);

        list=new ArrayList<>();
        adapter=new BankListAdapter(this,list);

        listView.addFooterView(bootView);
        listView.setAdapter(adapter);
        listView.setFooterDividersEnabled(false);
    }

    private void intData() {
        loginPhone = SharePreferencesUtil.getUserName(this);
        //添加紧急联系人
        footLay = bootView.findViewById(R.id.foot_lay);
        footLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BanckCordActivity.this, AddBanckCardActivity.class);
                startActivityForResult(intent,BaseServer.BANCK_TO_ADDBANCK);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCordInfo(loginPhone);
    }

    public void getCordInfo(String phone){
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<BankInfoModel> call=inte.getBanckInfo(phone);

        call.enqueue(new Callback<BankInfoModel>() {
            @Override
            public void onResponse(Call<BankInfoModel> call, Response<BankInfoModel> response) {

                BankInfoModel model=response.body();
                list.clear();
                list.addAll(model.getList());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<BankInfoModel> call, Throwable t) {
                Toast.makeText(BanckCordActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


}
