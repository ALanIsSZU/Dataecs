package com.example.administrator.dataecs.adapter;

import android.content.Context;
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

        holder.number.setText("手机：" + list.get(i).getNumber());
        holder.name.setText("姓名：" + list.get(i).getName());


        holder.channel.setText("渠道：" + list.get(i).getSourceId());

        if (list.get(i).getRepayStatus() == null || list.get(i).getRepayStatus().equals("")||list.get(i).getRepayStatus().equals("未还款")) {
            holder.moneyType.setText("还款状态: 待还款");
        } else {

            holder.moneyType.setText("还款状态:" + list.get(i).getRepayStatus());

        }

        return view;
    }

    class ViewHolder {

        TextView name;
        TextView number;
        TextView channel;
        TextView moneyType;

    }

}
