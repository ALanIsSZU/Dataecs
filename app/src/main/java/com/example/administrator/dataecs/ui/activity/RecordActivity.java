package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.RecordAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.RecordMdel;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SPUtils;
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

/**
 * Created by Administrator on 2018/7/4.
 */

public class RecordActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.record_list)
    ListView recordList;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;
    //无记录文本
    @BindView(R.id.no_record)
    TextView noRecord;
    @BindView(R.id.no_record_iocn)
    ImageView noRecordIocn;

    RecordAdapter recordAdapter;
    //数据集合
    List<RecordMdel.PageBean.ListBean> list;
    String phone;
    //刷新状态
    boolean isRefresh = false;
    //请求的页码
    private int currentPage = 1;
    //一次请求的条数
    private int pageSize = 10;

    //回来是否刷新
    boolean isBackRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_layout);
        ButterKnife.bind(this);
        intView();
        intData();
    }

    private void intView() {
        back.setVisibility(View.VISIBLE);
        title.setText("借款记录");

        isBackRefresh = getIntent().getBooleanExtra("isBackRefresh", false);

        list = new ArrayList<>();
        recordAdapter = new RecordAdapter(this, list);
        recordList.setAdapter(recordAdapter);

        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecordActivity.this, ShenQingActivity.class);
                intent.putExtra("typeCode", list.get(position).getStatusCode());
                intent.putExtra("content", list.get(position).getStatus());
                startActivity(intent);
            }
        });
    }

    private void intData() {

        phone = SharePreferencesUtil.getUserName(this);
        smartRefres.autoRefresh();

        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                isRefresh = true;
                getRecordData(phone, currentPage, pageSize);
            }
        });

        smartRefres.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                isRefresh = false;
                getRecordData(phone, currentPage, pageSize);
            }
        });

    }

    //请求记录数据
    public void getRecordData(String phone, int currentPage, int pageSize) {

        if (!SystemUntils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<RecordMdel> call = inte.getRecordInfo((Long) SPUtils.get(RecordActivity.this, Config.USED_ID, 1L),
                pageSize, currentPage);
        call.enqueue(new Callback<RecordMdel>() {
            @Override
            public void onResponse(Call<RecordMdel> call, Response<RecordMdel> response) {

                RecordMdel modle = response.body();

                if (isRefresh) {
                    list.clear();
                }

                list.addAll(modle.getPage().getList());
                recordAdapter.notifyDataSetChanged();

                if (list.size() > 0) {
                    recordList.setVisibility(View.VISIBLE);
                    noRecord.setVisibility(View.GONE);
                    noRecordIocn.setVisibility(View.GONE);
                } else {
                    recordList.setVisibility(View.GONE);
                    noRecord.setVisibility(View.VISIBLE);
                    noRecordIocn.setVisibility(View.VISIBLE);
                }

                if (modle.getPage().getList().size() < 10) {
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
            public void onFailure(Call<RecordMdel> call, Throwable t) {

                if (isRefresh) {
                    smartRefres.finishRefresh(false);
                } else {
                    smartRefres.finishLoadMore(false);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isBackRefresh) {
            smartRefres.autoRefresh();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            case Config.HK_BACK_DATA:

                if (requestCode == Config.DATA_TO_HK) {

                    isBackRefresh = data.getBooleanExtra("HK_TO_DATA_SUPER", false);

                }

                break;
            case Config.ZQ_BACK_DATA:

                if (requestCode == Config.DATA_TO_ZQ) {
                    isBackRefresh = data.getBooleanExtra("ZQ_TO_DATA_SUPER", false);
                }

                break;
        }

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
