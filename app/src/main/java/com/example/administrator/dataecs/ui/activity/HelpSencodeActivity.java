package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.HelpSencodeAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.HelpModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;

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

public class HelpSencodeActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.loading_txt)
    TextView loadingTxt;
    @BindView(R.id.loading_lay)
    View loadinglay;


    HelpSencodeAdapter adapter;
    List<HelpModel.HelpListBean.SecondTitleListBean> list;

    //帮助中心的第几项
    int itemNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_sencide_lay);
        ButterKnife.bind(this);
        intView();
        getTitleInfo();
    }

    private void intView() {
        back.setVisibility(View.VISIBLE);
        title.setText("借款说明");
        loadingTxt.setText("加载中。。。");
        loadinglay.setVisibility(View.VISIBLE);
        itemNum=getIntent().getIntExtra("itemNum",1);
        list=new ArrayList<>();
        adapter=new HelpSencodeAdapter(list,this);
        listView.setAdapter(adapter);
    }

    public void getTitleInfo(){

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<HelpModel> call=inte.getHelpInfo(Config.roleId);
        call.enqueue(new Callback<HelpModel>() {
            @Override
            public void onResponse(Call<HelpModel> call, Response<HelpModel> response) {
                HelpModel model=response.body();
                if (model.getCode()==0){
                    list.addAll(model.getHelpList().get(itemNum).getSecondTitleList());
                    adapter.notifyDataSetChanged();
                }
                loadinglay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<HelpModel> call, Throwable t) {
                ToastUntils.ToastShort(HelpSencodeActivity.this,"请求失败"+t.toString());
                loadinglay.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
