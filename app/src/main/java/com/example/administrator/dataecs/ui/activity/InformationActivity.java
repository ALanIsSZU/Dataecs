package com.example.administrator.dataecs.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.InformationAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.InformationContentModel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
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

public class InformationActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;
    @BindView(R.id.no_message_logo)
    ImageView noMessageLogo;
    @BindView(R.id.tip_txt)
    TextView tipTxt;


    //请求的页码
    private int currentPage = 1;
    //一次请求的条数
    private int pageSize = 10;
    //刷新
    boolean isRefresh = true;

    List<InformationContentModel.PageBean.ListBean> list;
    InformationAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {

        title.setText("消息中心");
        back.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        adapter = new InformationAdapter(this, list);
        listView.setAdapter(adapter);

        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                isRefresh = true;
                getInformationInfo();
            }
        });

        smartRefres.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                isRefresh = false;
                getInformationInfo();
            }
        });

        smartRefres.autoRefresh();

    }

    @OnClick({R.id.back, R.id.title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

                finish();

                break;

        }
    }

    public void getInformationInfo() {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final AllInte inte = retrofit.create(AllInte.class);
        Call<InformationContentModel> call = inte.getInfomationContent(Config.roleId, currentPage, pageSize);
        call.enqueue(new Callback<InformationContentModel>() {
            @Override
            public void onResponse(Call<InformationContentModel> call, Response<InformationContentModel> response) {
                InformationContentModel model = response.body();
                if ("success".equals(model.getMsg())) {

                    if (model.getPage().getList() == null ) {
                        smartRefres.setVisibility(View.GONE);
                        tipTxt.setVisibility(View.VISIBLE);
                        noMessageLogo.setVisibility(View.VISIBLE);
//                        return;
                    } else {
                        tipTxt.setVisibility(View.GONE);
                        noMessageLogo.setVisibility(View.GONE);
                        smartRefres.setVisibility(View.VISIBLE);

                        if (isRefresh) {
                            list.clear();
                        }



                        if (isRefresh){

                            if (model.getPage().getList().size()==0){
                                smartRefres.setVisibility(View.GONE);
                                tipTxt.setVisibility(View.VISIBLE);
                                noMessageLogo.setVisibility(View.VISIBLE);

                            }else {
                                list.addAll(model.getPage().getList());
                                adapter.notifyDataSetChanged();
                            }

                        }else {
                            list.addAll(model.getPage().getList());
                            adapter.notifyDataSetChanged();
                        }

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

                } else {
                    Toast.makeText(InformationActivity.this, "请求失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InformationContentModel> call, Throwable t) {
                Toast.makeText(InformationActivity.this, "请求失败！" + t.toString(), Toast.LENGTH_SHORT).show();

                if (isRefresh) {
                    smartRefres.finishRefresh(false);
                } else {
                    smartRefres.finishLoadMore(false);
                }
            }
        });
    }

}
