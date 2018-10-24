package com.example.administrator.dataecs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dataecs.R;
import com.example.administrator.dataecs.model.BankInfoModel;

import java.util.List;

public class BankListAdapter extends BaseAdapter{



    private Context context;
    private List<BankInfoModel.ListBean> list;

    public BankListAdapter(Context context,List<BankInfoModel.ListBean> list){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.bank_adapter_layout,parent,false);
            holder.bankName=convertView.findViewById(R.id.bank_name);
            holder.banktype=convertView.findViewById(R.id.bank_type);
            holder.bankNumber=convertView.findViewById(R.id.bank_number);
            convertView.setTag(holder);
        }else {
            holder= (viewHolder) convertView.getTag();
        }

        holder.bankNumber.setText(list.get(position).getCard());
        holder.banktype.setText(list.get(position).getBanktype());
        holder.bankName.setText(list.get(position).getBankname());

        return convertView;
    }

    public class viewHolder{

        TextView bankName;
        TextView banktype;
        TextView bankNumber;

    }
}
