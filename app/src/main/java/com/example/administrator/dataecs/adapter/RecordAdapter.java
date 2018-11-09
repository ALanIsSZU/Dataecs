package com.example.administrator.dataecs.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.model.RecordMdel;

import java.util.List;

public class RecordAdapter extends BaseAdapter {

    Context context;
    List<RecordMdel.PageBean.ListBean> list;

    public RecordAdapter(Context context, List<RecordMdel.PageBean.ListBean> list) {

        this.context = context;
        this.list = list;

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

        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.record_list_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.name);
            holder.number = view.findViewById(R.id.number);
            holder.channel = view.findViewById(R.id.channel);
            holder.moneyType = view.findViewById(R.id.money_type);

            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();

        }



        holder.number.setText(Html.fromHtml("手机：<font color='#666666'><small>"+list.get(i).getUserMobile()+"</small></font>"));
        holder.name.setText(Html.fromHtml("姓名：<font color='#666666'><small>"+list.get(i).getName()+"</small></font>"));


        holder.channel.setText(Html.fromHtml("渠道：<font color='#666666'><small>"+list.get(i).getSource()+"</small></font>"));

            holder.moneyType.setText(Html.fromHtml("还款状态: <font color='#666666'><small>"+"已还款"+"</small></font>"));


        return view;
    }

    class ViewHolder {

        TextView name;
        TextView number;
        TextView channel;
        TextView moneyType;

    }

}
