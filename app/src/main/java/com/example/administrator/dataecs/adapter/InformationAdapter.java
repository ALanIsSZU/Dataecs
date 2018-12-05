package com.example.administrator.dataecs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.model.InformationContentModel;

import java.util.List;

public class InformationAdapter extends BaseAdapter {


    private Context context;
    private List<InformationContentModel.PageBean.ListBean> list;

    public InformationAdapter(Context context, List<InformationContentModel.PageBean.ListBean> list) {
        this.context = context;
        this.list = list;
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
        if (convertView == null) {
            holder = new viewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.information_list_lay, parent, false);
            holder.title = convertView.findViewById(R.id.information_title);
            holder.time = convertView.findViewById(R.id.information_time);
            holder.content = convertView.findViewById(R.id.information_content);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        if (list.get(position).getNewsTitle() != null) {

            holder.title.setText(list.get(position).getNewsTitle());
        }

        if (list.get(position).getCreationTime() != null) {

            holder.time.setText(list.get(position).getCreationTime());
        }

        if (list.get(position).getNewsContent() != null) {

            holder.content.setText(list.get(position).getNewsContent());
        }


        return convertView;
    }

    public class viewHolder {

        TextView title;
        TextView time;
        TextView content;

    }
}
