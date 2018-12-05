package com.example.administrator.dataecs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dataecs.R;

import java.util.List;

public class DayListAdapter extends BaseAdapter {

    List<String> list;
    Context context;

    public DayListAdapter(List<String> list, Context context) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.day_adapter_lay, viewGroup, false);
            holder = new viewHolder();
            holder.txt=view.findViewById(R.id.txt);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();

        }

        holder.txt.setText(list.get(i));

        return view;
    }

    public class viewHolder {

        TextView txt;
    }
}