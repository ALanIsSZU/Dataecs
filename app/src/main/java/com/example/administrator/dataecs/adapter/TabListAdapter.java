package com.example.administrator.dataecs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.model.TabContentModel;
import com.example.administrator.dataecs.util.BaseServer;

import java.util.List;

/**
 * Created by Administrator on 2018/7/7.
 */

public class TabListAdapter extends BaseAdapter{

    List<TabContentModel.ResultBean> list;
    Context context;

    public TabListAdapter(List<TabContentModel.ResultBean> list,Context context){
        this.list=list;
        this.context=context;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;

        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.tab_content_list_adapter,viewGroup,false);
            holder=new viewHolder();
            holder.tabIcon=view.findViewById(R.id.tab_icon);
            holder.tabName=view.findViewById(R.id.tab_name);
            holder.tabContent=view.findViewById(R.id.tab_content);
            holder.tabPay=view.findViewById(R.id.tab_pay);
            view.setTag(holder);
        }else {

            holder= (viewHolder) view.getTag();

        }

        holder.tabName.setText(list.get(i).getSource());
        holder.tabContent.setText(list.get(i).getExplainDetail());
        Glide.with(context).load(BaseServer.BASE_PIC+list.get(i).getRemark1()).into(holder.tabIcon);
        holder.tabPay.setText("放款范围："+list.get(i).getBorrowBalances()+"~"+list.get(i).getBorrowBalance());

        return view;
    }

    public class viewHolder{

        ImageView tabIcon;
        TextView tabName;
        TextView tabContent;
        TextView tabPay;
    }

}
