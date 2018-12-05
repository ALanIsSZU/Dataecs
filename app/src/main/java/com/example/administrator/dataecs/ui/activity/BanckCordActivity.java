package com.example.administrator.dataecs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.administrator.dataecs.util.Config;
import com.example.administrator.dataecs.util.SPUtils;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.util.ToastUntils;
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

public class BanckCordActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.change_info)
    TextView changeInfo;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;
    @BindView(R.id.loading_txt)
    TextView loadingTxt;
    @BindView(R.id.loading_lay)
    View loadingLay;

    //登录的手机号
    String loginPhone;
    //ListView脚部布局
    View bootView;
    LinearLayout footLay;

    BankListAdapter adapter;
    List<BankInfoModel.PageBean.ListBean> list;

    //标识符
    boolean type;

    //请求的页码
    private int currentPage = 1;
    //一次请求的条数
    private int pageSize = 10;
    //刷新
    boolean isRefresh = true;
    //选择的银行卡
    String banckNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jin_ji_contenet);
        ButterKnife.bind(this);
        intView();
        intData();
    }

    private void intView() {
        type = getIntent().getBooleanExtra("type", false);

        loadingTxt.setText("加载中。。。");
        back.setVisibility(View.VISIBLE);
        title.setText("银行卡管理");
        bootView = LayoutInflater.from(this).inflate(R.layout.banck_foot, null);

        list = new ArrayList<>();
        adapter = new BankListAdapter(this, list);

        listView.addFooterView(bootView);
        listView.setAdapter(adapter);
        listView.setFooterDividersEnabled(false);

        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                isRefresh = true;
                getCordInfo(pageSize, currentPage, (long) SPUtils.get(BanckCordActivity.this, Config.USED_ID, 1L));
            }
        });

        smartRefres.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                isRefresh = false;
                getCordInfo(pageSize, currentPage, (long) SPUtils.get(BanckCordActivity.this, Config.USED_ID, 1L));
            }
        });

        smartRefres.setEnableRefresh(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type) {
                    banckNum = list.get(position).getBankNumber();
                    Intent intent = new Intent();
                    intent.putExtra("banckNum", banckNum);
                    setResult(Config.BANCK_BACK_HK, intent);
                    finish();
                }
            }
        });

    }

    private void intData() {
        loginPhone = SharePreferencesUtil.getUserName(this);
        //添加银行卡
        footLay = bootView.findViewById(R.id.foot_lay);
        footLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BanckCordActivity.this, AddBanckCardActivity.class);
                startActivityForResult(intent, BaseServer.BANCK_TO_ADDBANCK);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

//        smartRefres.autoRefresh();
        getCordInfo(pageSize, currentPage, (long) SPUtils.get(BanckCordActivity.this, Config.USED_ID, 1L));
    }

    public void getCordInfo(int pageSize, int currPage, long userId) {
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
        Call<BankInfoModel> call = inte.getBanckInfo(userId, currPage, pageSize);
        call.enqueue(new Callback<BankInfoModel>() {
            @Override
            public void onResponse(Call<BankInfoModel> call, Response<BankInfoModel> response) {
                BankInfoModel model = response.body();

                if (model.getMsg().equals("success")) {

                    if (isRefresh) {
                        if (list.size() > 0) {

                            list.clear();
                        }
                    }

                    if (model.getPage().getList().size() > 0) {
                        footLay.setVisibility(View.GONE);
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
                        isRefresh = true;
                    }
                } else {
                    ToastUntils.ToastShort(BanckCordActivity.this, "请求失败!");
                }
                loadingLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<BankInfoModel> call, Throwable t) {
                ToastUntils.ToastShort(BanckCordActivity.this, "请求失败!" + t.toString());
                loadingLay.setVisibility(View.GONE);
                if (isRefresh) {
                    smartRefres.finishRefresh(false);
                } else {
                    smartRefres.finishLoadMore(false);
                    isRefresh = true;
                }
            }
        });

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


}
