package com.example.administrator.dataecs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.model.JinJiInfoModel;

import java.util.List;

public class JinJiListAdapter extends BaseAdapter{

    private Context context;
    private List<JinJiInfoModel.PageBean.ListBean> list;

    public JinJiListAdapter(Context context,List<JinJiInfoModel.PageBean.ListBean> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder holder;
        if (convertView==null){
            holder=new viewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.jin_ji_list_adapter,parent,false);
            holder.name=convertView.findViewById(R.id.name);
            holder.qingShu=convertView.findViewById(R.id.qing_shu);
            holder.phone=convertView.findViewById(R.id.phone);
            convertView.setTag(holder);
        }else {
            holder= (viewHolder) convertView.getTag();
        }

        holder.name.setText("姓名："+list.get(position).getEmergentName());
        holder.phone.setText("手机："+list.get(position).getEmergentPhone());
        holder.qingShu.setText("亲属关系："+list.get(position).getRelationship());

        return convertView;
    }

    public class viewHolder{

        TextView name;
        TextView qingShu;
        TextView phone;

    }

}
