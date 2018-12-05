package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.HelpAdapter;
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

public class HelpActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content_list)
    ListView contentList;
    @BindView(R.id.loading_txt)
    TextView loadingTxt;
    @BindView(R.id.loading_lay)
    View loadinglay;

    HelpAdapter adapter;
    List<HelpModel.HelpListBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_lay);
        ButterKnife.bind(this);
        intView();
        getTitleInfo();
    }

    private void intView() {
        back.setVisibility(View.VISIBLE);
        title.setText("帮助中心");
        loadingTxt.setText("加载中。。。");
        loadinglay.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new HelpAdapter(list, this);
        contentList.setAdapter(adapter);

        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HelpActivity.this, HelpSencodeActivity.class);
                intent.putExtra("itemNum", position);
                startActivity(intent);
            }
        });

    }

    public void getTitleInfo() {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<HelpModel> call = inte.getHelpInfo(Config.roleId);
        call.enqueue(new Callback<HelpModel>() {
            @Override
            public void onResponse(Call<HelpModel> call, Response<HelpModel> response) {
                HelpModel model = response.body();
                if (model.getCode() == 0) {
                    list.addAll(model.getHelpList());
                    adapter.notifyDataSetChanged();
                }
                loadinglay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<HelpModel> call, Throwable t) {
                ToastUntils.ToastShort(HelpActivity.this, "请求失败" + t.toString());
                loadinglay.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {

        finish();
    }
}
