package com.example.administrator.dataecs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.model.HelpModel;

import java.util.ArrayList;
import java.util.List;

public class HelpAdapter extends BaseAdapter {

    private List<HelpModel.HelpListBean> list;
    private Context context;
    private List<TextView> viewList;

    public HelpAdapter(List<HelpModel.HelpListBean> list, Context context) {

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

            convertView = LayoutInflater.from(context).inflate(R.layout.help_list_one_lay, viewGroup, false);
            holder = new ViewHolder();
            holder.one_title = convertView.findViewById(R.id.one_title);
            holder.sencod_title_one = convertView.findViewById(R.id.sencod_title_one);
            holder.sencod_title_two = convertView.findViewById(R.id.sencod_title_two);
            holder.sencod_title_there = convertView.findViewById(R.id.sencod_title_there);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        viewList = new ArrayList<>();

        holder.one_title.setText(list.get(i).getFirstTitle());
        viewList.add(holder.sencod_title_one);
        viewList.add(holder.sencod_title_two);
        viewList.add(holder.sencod_title_there);

        if (list.get(i).getSecondTitleList() != null && list.get(i).getSecondTitleList().size() > 0) {

            if (list.get(i).getSecondTitleList().size() > 3) {

                for (int i1 = 0; i1 < 3; i1++) {

                    viewList.get(i1).setText(list.get(i).getSecondTitleList().get(i1).getSecondTitle());

                }
            } else {
                for (int i1 = 0; i1 < list.get(i).getSecondTitleList().size(); i1++) {

                    viewList.get(i1).setText(list.get(i).getSecondTitleList().get(i1).getSecondTitle());

                }
            }

        }


        return convertView;
    }

    public class ViewHolder {
        TextView one_title;
        TextView sencod_title_one;
        TextView sencod_title_two;
        TextView sencod_title_there;
    }

}
