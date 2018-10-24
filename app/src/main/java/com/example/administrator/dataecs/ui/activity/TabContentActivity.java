package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.TabListAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.TabContentModel;
import com.example.administrator.dataecs.util.BaseServer;
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

/**
 * Created by Administrator on 2018/7/7.
 */

public class TabContentActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tabContentList)
    ListView tabContentList;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;

    private TabListAdapter adapter;
    private List<TabContentModel.ResultBean> list;
    private String type;
    private int pageSize = 10;
    private int currentPage = 1;
    private boolean isRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_content_layout);
        ButterKnife.bind(this);
        intView();
        intData();
    }

    private void intView() {

        back.setVisibility(View.VISIBLE);
        tabContentList.setVerticalScrollBarEnabled(false);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("tabData");
        String data = bundle.getString("title");
        type = bundle.getString("type");
        title.setText(data);

        list = new ArrayList<>();

        adapter = new TabListAdapter(list, this);
        tabContentList.setAdapter(adapter);
    }

    private void intData() {
//        getTabContent(currentPage, pageSize, finalType);
        smartRefres.autoRefresh();

        tabContentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TabContentActivity.this, ContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sourceId", list.get(i).getSourceid());
                intent.putExtra("itemData", bundle);
                startActivity(intent);
            }
        });

        //下拉刷新
        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage=1;
                isRefresh=true;
                getTabContent( currentPage, pageSize, type);
            }
        });
        //上拉加载
        smartRefres.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                isRefresh=false;
                getTabContent(currentPage, pageSize, type);
            }
        });
    }


    public void getTabContent(int currentPage,int pageSize,String type) {
        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<TabContentModel> call = inte.getTabData(currentPage, pageSize, type);
        call.enqueue(new Callback<TabContentModel>() {
            @Override
            public void onResponse(Call<TabContentModel> call, Response<TabContentModel> response) {
                if (isRefresh){
                    list.clear();
                }

                TabContentModel model = response.body();

                list.addAll(model.getResult());
                adapter.notifyDataSetChanged();

                if (model.getResult().size()<10){
                    // 这个方法是在最后一页，没有更多数据时调用的，会在页面底部标记没有更多数据
                    smartRefres.finishLoadMoreWithNoMoreData();
                    smartRefres.setNoMoreData(false);
                }

                if (isRefresh){

                    smartRefres.finishRefresh();
                }else {

                    smartRefres.finishLoadMore();
                }
            }

            @Override
            public void onFailure(Call<TabContentModel> call, Throwable t) {
                Log.d("123", "请求失败" + t.toString());
                if (isRefresh) {
                    smartRefres.finishRefresh(false);
                } else {
                    smartRefres.finishLoadMore(false);
                }

                Toast.makeText(TabContentActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {

        finish();

    }
}
