package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.administrator.dataecs.adapter.JinJiListAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.JinJiInfoModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

public class JinJIPersonToActivity extends AppCompatActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.list)
    ListView listView;

    JinJiListAdapter adapter;
    List<JinJiInfoModel.PageBean.ListBean> list;
    //ListView脚部布局
    View bootView;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;

    //刷新
    boolean isRefresh = false;
    //请求的页码
    private int currentPage = 1;
    //一次请求的条数
    private int pageSize = 10;
    //登录的手机号
    String loginPhone;

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
        title.setText("紧急联系人");
        list = new ArrayList<>();
        adapter = new JinJiListAdapter(this, list);
        //脚部布局
        bootView = LayoutInflater.from(this).inflate(R.layout.jin_ji_list_foot, null);
        listView.addFooterView(bootView);
        listView.setAdapter(adapter);
    }

    private void intData() {
        loginPhone = SharePreferencesUtil.getUserName(this);
        smartRefres.autoRefresh();
        //下拉刷新，上拉加载
        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                isRefresh = true;
                getPersonInfo(loginPhone, currentPage, pageSize);
            }
        });

        smartRefres.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                isRefresh = false;
                getPersonInfo(loginPhone, currentPage, pageSize);

            }
        });

        //添加紧急联系人
        LinearLayout footLay = bootView.findViewById(R.id.foot_lay);
        footLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JinJIPersonToActivity.this, JinJIAddInfoActivity.class);
                startActivity(intent);
            }
        });

    }

    //请求数据
    public void getPersonInfo(String phone, int currentPage, int pageSize) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<JinJiInfoModel> call = inte.getJinJiInfo(phone, currentPage, pageSize);
        call.enqueue(new Callback<JinJiInfoModel>() {
            @Override
            public void onResponse(Call<JinJiInfoModel> call, Response<JinJiInfoModel> response) {
                JinJiInfoModel model = response.body();
                if (isRefresh) {
                    list.clear();
                }

                list.addAll(model.getPage().getList());
                adapter.notifyDataSetChanged();

                if (model.getPage().getList().size() < 10) {
                    // 这个方法是在最后一页，没有更多数据时调用的，会在页面底部标记没有更多数据
                    smartRefres.finishLoadMoreWithNoMoreData();
                    smartRefres.setNoMoreData(false);
                }

                if (isRefresh) {

                    smartRefres.finishRefresh();
                } else {

                    smartRefres.finishLoadMore();
                }

            }

            @Override
            public void onFailure(Call<JinJiInfoModel> call, Throwable t) {
                if (isRefresh) {
                    smartRefres.finishRefresh(false);
                } else {
                    smartRefres.finishLoadMore(false);
                }
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {

        finish();

    }

}
