package com.example.administrator.dataecs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.model.HelpModel;

import java.util.List;

public class HelpSencodeAdapter  extends BaseAdapter {

    private List<HelpModel.HelpListBean.SecondTitleListBean> list;
    private Context context;
    boolean isVisibley =true;

    public HelpSencodeAdapter(List<HelpModel.HelpListBean.SecondTitleListBean> list, Context context) {

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

        final ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.sencode_help_list_lay, viewGroup, false);
            holder = new ViewHolder();
            holder.title=convertView.findViewById(R.id.title);
            holder.answer=convertView.findViewById(R.id.answer);
            holder.jianTou=convertView.findViewById(R.id.jian_tou);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.title.setText(list.get(i).getSecondTitle());
        holder.answer.setText(list.get(i).getSecondTitleContent());


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisibley){

                    holder.answer.setVisibility(View.VISIBLE);
                    isVisibley=false;
                    holder.jianTou.setImageResource(R.drawable.upnew);
                }else {
                    holder.answer.setVisibility(View.GONE);
                    isVisibley=true;
                    holder.jianTou.setImageResource(R.drawable.down_new);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView answer;
        ImageView jianTou;
    }

}