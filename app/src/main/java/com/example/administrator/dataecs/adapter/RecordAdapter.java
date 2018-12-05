package com.example.administrator.dataecs.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.model.RecordMdel;
import com.example.administrator.dataecs.ui.activity.HuanKuanActivity;
import com.example.administrator.dataecs.ui.activity.ZhangQiActivity;
import com.example.administrator.dataecs.util.Config;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.record_list_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.name);
            holder.number = view.findViewById(R.id.number);
            holder.txt = view.findViewById(R.id.txt);
            holder.channel = view.findViewById(R.id.channel);
            holder.type = view.findViewById(R.id.shen_he_type);
            holder.other_lay = view.findViewById(R.id.other_lay);
            holder.yu_qi = view.findViewById(R.id.yu_qi);
            holder.huan_kuan = view.findViewById(R.id.huan_kuan);

            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();

        }

        if (list.get(i).getPostponeTime() == null) {

            holder.number.setText(Html.fromHtml("还款时间：<font color='#666666'><small>" + list.get(i).getRepaymentsTime().substring(0, 10) + "</small></font>"));
        } else {
            holder.number.setText(Html.fromHtml("展期时间：<font color='#666666'><small>" + list.get(i).getPostponeTime().substring(0, 10) + "</small></font>"));
        }

        holder.name.setText(Html.fromHtml("姓名：<font color='#666666'><small>" + list.get(i).getName() + "</small></font>"));
        holder.channel.setText(Html.fromHtml("还款状态: <font color='#666666'><small>" + list.get(i).getStatus() + "</small></font>"));
        selectType(list.get(i).getStatusCode(), holder.type);

        holder.txt.setText("订单编号：" + list.get(i).getOrderId());


        if (list.get(i).getStatusCode() == 6 || list.get(i).getStatusCode() == 7) {
            holder.other_lay.setVisibility(View.VISIBLE);
        } else {
            holder.other_lay.setVisibility(View.GONE);
        }

        holder.huan_kuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HuanKuanActivity.class);
                intent.putExtra("totleMoneyForRepay", list.get(i).getMoeny());
                intent.putExtra("OrderIDForRepay", list.get(i).getId());
                intent.putExtra("overdayForRepay", list.get(i).getOverdueDays());
                intent.putExtra("overcodeForRepay", list.get(i).getOrderId());
                intent.putExtra("overdueForRepay", list.get(i).getServiceCharge());
                ((Activity) context).startActivityForResult(intent, Config.DATA_TO_HK);
            }
        });

        holder.yu_qi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZhangQiActivity.class);
                intent.putExtra("totleMoneyForZQ", list.get(i).getMoeny());
                intent.putExtra("repayDayForZQ", list.get(i).getRepaymentsTime());
                intent.putExtra("OrderIDForZQ", list.get(i).getId());
                intent.putExtra("overcodeZQ", list.get(i).getOrderId());
                intent.putExtra("PoneTimeForZQ", list.get(i).getPostponeTime());
                ((Activity) context).startActivityForResult(intent, Config.DATA_TO_ZQ);
            }
        });


        return view;
    }

    public void selectType(int type, ImageView view) {

        switch (type) {
            case 0:
                view.setImageResource(R.drawable.wei_tong_guo);
                break;
            case 1:
                view.setImageResource(R.drawable.shen_he_zhong);
                break;
            case 2:
                view.setImageResource(R.drawable.chu_shen);
                break;
            case 3:
                view.setImageResource(R.drawable.wei_da_kuan);
                break;
            case 4:
                view.setImageResource(R.drawable.da_kuai_shi_bai);
                break;
            case 5:
                view.setImageResource(R.drawable.yi_huam_kuang);
                break;
            case 6:
                view.setImageResource(R.drawable.huan_kuan_zhong);
                break;
            case 7:
                view.setImageResource(R.drawable.yi_yu_qi);
                break;
        }

    }

    class ViewHolder {

        TextView name;
        TextView txt;
        TextView number;
        TextView channel;
        ImageView type;
        LinearLayout other_lay;
        TextView yu_qi;
        TextView huan_kuan;

    }

}
