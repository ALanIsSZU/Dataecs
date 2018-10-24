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
import com.example.administrator.dataecs.model.MainlistItem;
import com.example.administrator.dataecs.util.BaseServer;

import java.util.List;


/**
 * Created by Administrator on 2018/7/7.
 */

public class MainListAdapter extends BaseAdapter {

    private List<MainlistItem> list;
    private Context context;

    public MainListAdapter(List<MainlistItem> list, Context context) {

        this.list = list;
        this.context = context;

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
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.main_list_adater_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.p_iocn);
            holder.p_name = convertView.findViewById(R.id.p_name);
            holder.p_introduce = convertView.findViewById(R.id.p_introduce);
            holder.p_pay = convertView.findViewById(R.id.p_pay);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(BaseServer.BASE_PIC+list.get(i).getRemark1()).into(holder.img);
        holder.p_name.setText(list.get(i).getSource());
        holder.p_introduce.setText(list.get(i).getExplainDetail());
        holder.p_pay.setText("放款范围："+list.get(i).getPayStart()+"~"+list.get(i).getPayEnd());
        return convertView;
    }

    public class ViewHolder {
        ImageView img;
        TextView p_name;
        TextView p_introduce;
        TextView p_pay;
    }

}
