package com.example.administrator.dataecs.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.adapter.ClassFationAdapter;
import com.example.administrator.dataecs.inte.AllInte;
import com.example.administrator.dataecs.model.ClassiFicationModel;
import com.example.administrator.dataecs.model.MainListHearModel;
import com.example.administrator.dataecs.ui.activity.ContentActivity;
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
import butterknife.Unbinder;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/7/4.
 */

public class TabClassiFicationFragement extends Fragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.select_type)
    RelativeLayout selectType;
    @BindView(R.id.range)
    TextView range;
    @BindView(R.id.select_range)
    RelativeLayout selectRange;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.class_fication_list)
    ListView cFList;
    Unbinder unbinder;
    @BindView(R.id.smartRefres)
    SmartRefreshLayout smartRefres;
    @BindView(R.id.gif_img)
    GifImageView gifImg;
    @BindView(R.id.gif_rela)
    RelativeLayout gifRela;

    private ClassFationAdapter cfAdapter;
    private List<ClassiFicationModel.ResultBean> list;
    //类型
    private String[] type_arr = new String[5];
    List<String> type_code;

    private String[] range_arr = new String[]{
            "范围不限", "2000以下", "2000-5000", "5000-10000", "10000以上"
    };
    private boolean istypeTouch = false;
    private boolean isTypeSencode = false;
    private boolean isRangSencode = false;

    //弹窗的type
    private String typeValue = "all";
    private String rangValue = "all";
    //请求的页码
    private int currentPage = 1;
    //一次请求的条数
    private int pageSize = 10;
    private boolean isRefresh = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.classfation_layout, null);
        unbinder = ButterKnife.bind(this, view);
        intView();

        return view;
    }

    private void intView() {
        title.setText("分类");
        list = new ArrayList<>();
        type_code = new ArrayList<>();
        cfAdapter = new ClassFationAdapter(list, getContext());
        cFList.setAdapter(cfAdapter);

//        smartRefres.autoRefresh();
        getLoanType();
        getData(currentPage, typeValue, rangValue, pageSize);

        gifRela.setVisibility(View.VISIBLE);

        smartRefres.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                isRefresh = true;
                getData(currentPage, typeValue, rangValue, pageSize);
            }
        });

        smartRefres.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                isRefresh = false;
                getData(currentPage, typeValue, rangValue, pageSize);
            }
        });

        cFList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sourceId", list.get(i).getSource_id());
                intent.putExtra("itemData", bundle);
                getActivity().startActivity(intent);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.select_type, R.id.select_range, R.id.top_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_type:
                if (isTypeSencode) {
                    isTypeSencode = false;
                    popup.dismiss();
                } else {
                    istypeTouch = true;
                    isTypeSencode = true;
                    isRangSencode = false;
                    showDownPopuWindows(type_arr);

                }

                break;
            case R.id.select_range:
                if (isRangSencode) {
                    isRangSencode = false;
                } else {
                    istypeTouch = false;
                    isRangSencode = true;
                    isTypeSencode = false;
                    showDownPopuWindows(range_arr);

                }

                break;
            case R.id.top_layout:
                break;
        }
    }


    private PopupWindow popup = null;
    private View view = null;
    private View line;
    TextView[] views;

    TextView type_1;
    TextView type_2;
    TextView type_3;
    TextView type_4;
    TextView type_5;
    TextView type_6;

    //弹窗
    private void showDownPopuWindows(String[] array) {

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.popuwindows_layout, null);
            type_1 = view.findViewById(R.id.type_1);
            type_2 = view.findViewById(R.id.type_2);
            type_3 = view.findViewById(R.id.type_3);
            type_4 = view.findViewById(R.id.type_4);
            type_5 = view.findViewById(R.id.type_5);
            type_6 = view.findViewById(R.id.type_6);
            line = view.findViewById(R.id.line);
        }

        type_1.setOnClickListener(onClickListener);
        type_2.setOnClickListener(onClickListener);
        type_3.setOnClickListener(onClickListener);
        type_4.setOnClickListener(onClickListener);
        type_5.setOnClickListener(onClickListener);
        type_6.setOnClickListener(onClickListener);

        if (popup == null) {
            popup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popup.setBackgroundDrawable(new BitmapDrawable());
            popup.setOutsideTouchable(true);
            popup.setTouchable(true);
            popup.setFocusable(false);
        }

        views = new TextView[]{
                type_1,
                type_2,
                type_3,
                type_4,
                type_5,
                type_6
        };

        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.VISIBLE);
            views[i].setBackgroundResource(R.drawable.popuwindows_no_back);
            views[i].setText("");
            if (i < array.length) {
                views[i].setText(array[i]);
            } else {
                views[i].setVisibility(View.INVISIBLE);
            }

            if (istypeTouch) {
                if (views[i].getText().toString().equals(type.getText().toString())) {
                    views[i].setBackgroundResource(R.drawable.popuwindows_back);
                }
            } else {
                if (views[i].getText().toString().equals(range.getText().toString())) {
                    views[i].setBackgroundResource(R.drawable.popuwindows_back);
                }
            }
        }

        //设置popuwindows在某个控件下面
        if (!popup.isAboveAnchor()) {
            popup.showAsDropDown(topLayout, 0, 0);
        }
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.type_1:
                    if (istypeTouch) {
                        type.setText(type_arr[0]);
                        typeValue = type_code.get(0);
                    } else {
                        range.setText(range_arr[0]);
                        rangValue = "all";
                    }
                    type_1.setBackgroundResource(R.drawable.popuwindows_back);
                    popup.dismiss();
                    smartRefres.autoRefresh();
                    break;
                case R.id.type_2:
                    if (istypeTouch) {
                        type.setText(type_arr[1]);
                        typeValue = type_code.get(1);
                    } else {
                        range.setText(range_arr[1]);
                        rangValue = "2000";
                    }
                    type_2.setBackgroundResource(R.drawable.popuwindows_back);
                    popup.dismiss();
                    smartRefres.autoRefresh();
                    break;
                case R.id.type_3:
                    if (istypeTouch) {
                        type.setText(type_arr[2]);
                        typeValue = type_code.get(2);
                    } else {
                        range.setText(range_arr[2]);
                        rangValue = "2000-5000";
                    }
                    type_3.setBackgroundResource(R.drawable.popuwindows_back);
                    popup.dismiss();
                    smartRefres.autoRefresh();
                    break;
                case R.id.type_4:
                    if (istypeTouch) {
                        type.setText(type_arr[3]);
                        typeValue = type_code.get(3);
                    } else {
                        range.setText(range_arr[3]);
                        rangValue = "5000-10000";
                    }
                    type_4.setBackgroundResource(R.drawable.popuwindows_back);
                    popup.dismiss();
                    smartRefres.autoRefresh();
                    break;
                case R.id.type_5:
                    if (istypeTouch) {
                        type.setText(type_arr[4]);
                        typeValue = "04";
                    } else {
                        range.setText(range_arr[4]);
                        rangValue = "10000";
                    }
                    type_5.setBackgroundResource(R.drawable.popuwindows_back);
                    popup.dismiss();
                    smartRefres.autoRefresh();
                    break;
                case R.id.type_6:

                    break;
            }
        }
    };

    //筛选条件
    public void getData(int currentPage, String typeValue, String rangValue, int pageSize) {

        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getContext(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<ClassiFicationModel> call = inte.getClassFiationData(currentPage, typeValue, rangValue, pageSize);
        call.enqueue(new Callback<ClassiFicationModel>() {
            @Override
            public void onResponse(Call<ClassiFicationModel> call, Response<ClassiFicationModel> response) {

                gifRela.setVisibility(View.GONE);
                if (isRefresh) {
                    list.clear();
                }
                ClassiFicationModel model = response.body();
                list.addAll(model.getResult());
                cfAdapter.notifyDataSetChanged();

                if (model.getResult().size() < 10) {
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
            public void onFailure(Call<ClassiFicationModel> call, Throwable t) {
                gifImg.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();

                if (isRefresh) {
                    smartRefres.finishRefresh(false);
                } else {
                    smartRefres.finishLoadMore(false);
                }
            }
        });
    }

    //请求类型type
    public void getLoanType() {

        if (!SystemUntils.isNetworkConnected(getActivity())) {
            Toast.makeText(getContext(), "网络已断开,请检查你的网络!", Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseServer.BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllInte inte = retrofit.create(AllInte.class);
        Call<MainListHearModel> call = inte.getAllData(1, 10);
        call.enqueue(new Callback<MainListHearModel>() {
            @Override
            public void onResponse(Call<MainListHearModel> call, Response<MainListHearModel> response) {
                MainListHearModel model = response.body();
                type_arr[0] = "类型不限";
                type_code.add("all");
                for (int i = 0; i < model.getResult().getLoantype().size(); i++) {
                    type_arr[i + 1] = model.getResult().getLoantype().get(i).getLoan_type_name();
                    type_code.add(model.getResult().getLoantype().get(i).getPrimary_id()+"");

                }

            }

            @Override
            public void onFailure(Call<MainListHearModel> call, Throwable t) {
                Log.d("123", t.toString());
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
