package com.example.administrator.dataecs.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.MainListAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.MainListHearModel;
import com.example.administrator.dataecs.model.MainlistItem;
import com.example.administrator.dataecs.ui.activity.ContentActivity;
import com.example.administrator.dataecs.ui.activity.TabContentActivity;
import com.example.administrator.dataecs.util.BaseServer;
import com.example.administrator.dataecs.util.GlideImageLoader;
import com.example.administrator.dataecs.util.SharePreferencesUtil;
import com.example.administrator.dataecs.util.SystemUntils;
import com.example.administrator.dataecs.weight.MarqueeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/7/4.
 */

public class TabMainFragement extends Fragment implements View.OnClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content_list)
    ListView contentList;
    Unbinder unbinder;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;
    //跑马灯
    MarqueeView marqueeView;
    LinearLayout bell_lin;

    private Banner banner;
    //首页的四个tab
    private RelativeLayout tab_1;
    private RelativeLayout tab_2;
    private RelativeLayout tab_3;
    private RelativeLayout tab_4;

    private ImageView img_1;
    private ImageView img_2;
    private ImageView img_3;

    private TextView tab_1_name;
    private TextView tab_2_name;
    private TextView tab_3_name;
    private TextView tab_4_name;
    TextView[] views;

    private MainListAdapter adapter;
    private List<MainlistItem> list;
    private View headView;
    private List<String> imgUrl;

    //跳转首页的四个tab详情的参数
    private String[] headTitle = new String[4];
    //首页的四个tab的内容
    private String[] LoanTypeArr = new String[4];
    //跳转首页的四个tab详情的ID
    private List<String> headId;
    //首页四个type的logo
    private List<String> imgTopUrl = new ArrayList<>();
    //每次请求的条数
    private int pageSize = 10;
    //请求的页码
    private int currentPage = 1;
    private boolean isRefresh = false;

    //跑马灯的内容
    List<CharSequence> marqueeContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.main_layout, null);
        unbinder = ButterKnife.bind(this, view);

        intView();
        intData();

        return view;
    }

    private void intView() {

        title.setText("首页");
        list = new ArrayList<>();
        headId=new ArrayList<>();
        marqueeContent = new ArrayList<>();

        adapter = new MainListAdapter(list, getActivity());

        //list加头部
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_view_head, null);

        //设置广告的高度
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();

        //屏幕高度
        int height = metrics.heightPixels;
        //跑马灯
        bell_lin = headView.findViewById(R.id.bell_lin);

        bell_lin.setVisibility(SharePreferencesUtil.getHouTaiSwich(getContext()) ? View.VISIBLE : View.GONE);

        marqueeView = headView.findViewById(R.id.marqueeView);
        banner = headView.findViewById(R.id.banner);

        tab_1 = headView.findViewById(R.id.tab_1);
        tab_1.setOnClickListener(this);
        tab_2 = headView.findViewById(R.id.tab_2);
        tab_2.setOnClickListener(this);
        tab_3 = headView.findViewById(R.id.tab_3);
        tab_3.setOnClickListener(this);
        tab_4 = headView.findViewById(R.id.tab_4);
        tab_4.setOnClickListener(this);

        img_1 = headView.findViewById(R.id.img_1);
        img_2 = headView.findViewById(R.id.img_2);
        img_3 = headView.findViewById(R.id.img_3);

        tab_1_name = headView.findViewById(R.id.tab_1_name);
        tab_2_name = headView.findViewById(R.id.tab_2_name);
        tab_3_name = headView.findViewById(R.id.tab_3_name);
        tab_4_name = headView.findViewById(R.id.tab_4_name);

        views = new TextView[]{
                tab_1_name,
                tab_2_name,
                tab_3_name,
                tab_4_name
        };

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) banner.getLayoutParams();
        layoutParams.height = height / 4;
        banner.setLayoutParams(layoutParams);


        contentList.setVerticalScrollBarEnabled(true);
        contentList.addHeaderView(headView);
        contentList.setAdapter(adapter);
    }

    private void intData() {
        smartRefres.autoRefresh();

        //首页跑马灯的轮播信息
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content, "","137*****336", "成功申福到了获得2000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "186*****895", "成功申请金库获得5000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "134*****327", "成功申请钱袋获得7000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "185*****089", "成功申请55红包获得8000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "135*****236", "成功申请金库获得3000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "137*****681", "成功申请速借获得4000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "134*****532", "成功申请金库获得6000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "137*****892", "成功申请速借获得2000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "186*****203", "成功申请福到了获得5000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "134*****568", "成功申请不差钱获得7000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "135*****254", "成功申请金库获得3000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "185*****938", "成功申请钱袋包获得8000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "137*****894", "成功申请55红包获得4000元")));
        marqueeContent.add(Html.fromHtml(getResources().getString(R.string.content1, "134*****898", "成功申请不差钱获得6000元")));

        marqueeView.startWithList(marqueeContent);
        //下拉刷新
        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                isRefresh = true;
                getData(currentPage, pageSize);
            }
        });

        //上拉加载
        smartRefres.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                isRefresh = false;
                getData(currentPage, pageSize);
            }
        });

        imgUrl = new ArrayList<>();
        //广告的图片
        if (!SharePreferencesUtil.getHouTaiSwich(getContext())) {
            //审核图片
            imgUrl.add("http://img.hb.aicdn.com/eb7bf73fbc0456d0ddc80996d8d5e25feb7a52b5501a2-CITWFZ_fw658");
            imgUrl.add("http://img.hb.aicdn.com/8b7aa170dd87de849c719638635f3ad4b6bb171a49a61-0vchnL_fw658");
        } else {
            //正服图片
            imgUrl.add("http://img.hb.aicdn.com/dc84655344068d3411db362745677955af34b8d5108f5-54BLuo_fw658");
            imgUrl.add("http://img.hb.aicdn.com/4fe5666da4b3bf0fb991170b351fe5dafcd70ccc24c92-VmTuKg_fw658");
        }


        contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sourceId", list.get(i - 1).getSourceid());
                intent.putExtra("itemData", bundle);
                getActivity().startActivity(intent);
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.isAutoPlay(true);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imgUrl);
        //广告点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
        //点击事件放到start方法后面
        banner.start();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tab_1:
                goToContent(headTitle[0], headId.get(0));
//                Intent intent=new Intent(getActivity(), ContentActivity.class);
//                getActivity().startActivity(intent);
                break;
            case R.id.tab_2:
                goToContent(headTitle[1], headId.get(1));
                break;
            case R.id.tab_3:
                goToContent(headTitle[2], headId.get(2));
                break;
//            case R.id.tab_4:
//                goToContent("每日更新", LoanTypeArr[3]);
//                break;

        }
    }

    public void goToContent(String title, String type) {
        Intent intent = new Intent(getActivity(), TabContentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("type", type);
        intent.putExtra("tabData", bundle);
        getActivity().startActivity(intent);
    }

    //数据请求
    public void getData(int currentPage, int pageSize) {

        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getContext(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
            return;
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
        Call<MainListHearModel> call = inte.getAllData(currentPage, pageSize);
        call.enqueue(new Callback<MainListHearModel>() {
            @Override
            public void onResponse(Call<MainListHearModel> call, Response<MainListHearModel> response) {
                MainListHearModel model = response.body();

                if (isRefresh) {
                    list.clear();
                }

                for (int i = 0; i < model.getResult().getLoantype().size(); i++) {

                    headTitle[i] = model.getResult().getLoantype().get(i).getLoan_type_name();
                    LoanTypeArr[i] = model.getResult().getLoantype().get(i).getLoan_type_code();
                    imgTopUrl.add(BaseServer.BASE_PIC + model.getResult().getLoantype().get(i).getIcon());
                    headId.add(model.getResult().getLoantype().get(i).getPrimary_id()+"");
                }
                for (int i = 0; i < model.getResult().getPoolSource().size(); i++) {
                    MainlistItem item = new MainlistItem();
                    item.setSourceid(model.getResult().getPoolSource().get(i).getSourceid());
                    item.setSource(model.getResult().getPoolSource().get(i).getSource());
                    item.setExplainDetail(model.getResult().getPoolSource().get(i).getExplainDetail());
                    item.setRemark1(model.getResult().getPoolSource().get(i).getRemark1());
                    item.setPayStart(model.getResult().getPoolSource().get(i).getBorrowBalances());
                    item.setPayEnd(model.getResult().getPoolSource().get(i).getBorrowBalance());
                    list.add(item);
                }

                adapter.notifyDataSetChanged();

                if (model.getResult().getPoolSource().size() < 10) {
                    // 这个方法是在最后一页，没有更多数据时调用的，会在页面底部标记没有更多数据
                    smartRefres.finishLoadMoreWithNoMoreData();
                    smartRefres.setNoMoreData(false);
                }

                if (isRefresh) {

                    smartRefres.finishRefresh();
                } else {

                    smartRefres.finishLoadMore();
                }

                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }

            @Override
            public void onFailure(Call<MainListHearModel> call, Throwable t) {
                Log.d("123", t.toString());
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();

                if (isRefresh) {
                    smartRefres.finishRefresh(false);
                } else {
                    smartRefres.finishLoadMore(false);
                }
            }
        });
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 1:

                    for (int i = 0; i < headTitle.length; i++) {
                        views[i].setText(headTitle[i]);
                    }

                    Glide.with(getActivity()).load(imgTopUrl.get(0)).into(img_1);
                    Glide.with(getActivity()).load(imgTopUrl.get(1)).into(img_2);
                    Glide.with(getActivity()).load(imgTopUrl.get(2)).into(img_3);

                    break;

            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        marqueeView.stopFlipping();
    }

}
