package com.hjf.admin.socket.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hjf.admin.socket.R;
import com.hjf.admin.socket.entity.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HJianFei on 2016-6-16.
 */
public class MyAdapter extends BaseAdapter {

    private List<Info> mLists = new ArrayList<>();
    private Context mContext;

    public MyAdapter(Context context, List<Info> lists) {
        mContext = context;
        mLists = lists;
    }

    @Override
    public int getCount() {
        if (mLists != null) {
            return mLists.size();
        }
        return 0;
    }

    @Override
    public Info getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.list_item, null);
            holder.tv_lenght = (TextView) view.findViewById(R.id.tv_lenght);
            holder.tv_adress = (TextView) view.findViewById(R.id.tv_adress);
            holder.tv_actual_time = (TextView) view.findViewById(R.id.tv_actual_time);
            holder.tv_status = (TextView) view.findViewById(R.id.tv_status);
            holder.tv_epc_code = (TextView) view.findViewById(R.id.tv_epc_code);
            holder.tv_tempt = (TextView) view.findViewById(R.id.tv_tempt);
            holder.tv_well = (TextView) view.findViewById(R.id.tv_well);
            holder.tv_crc = (TextView) view.findViewById(R.id.tv_crc);
            holder.tv_all_info = (TextView) view.findViewById(R.id.tv_all_info);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_lenght.setText(mLists.get(i).getDataLength());
        holder.tv_adress.setText(mLists.get(i).getReaderAddress());
        holder.tv_actual_time.setText(mLists.get(i).getActualTime());
        holder.tv_status.setText(mLists.get(i).getStatus());
        holder.tv_epc_code.setText(mLists.get(i).getEpcCode());
        holder.tv_tempt.setText(mLists.get(i).getTemperature());
        holder.tv_well.setText(mLists.get(i).getHumidity());
        holder.tv_crc.setText(mLists.get(i).getCrc());
        holder.tv_all_info.setText(mLists.get(i).getAllInfo());
        return view;
    }

    class ViewHolder {
        public TextView tv_lenght, tv_adress, tv_actual_time, tv_status, tv_epc_code, tv_tempt, tv_well, tv_crc, tv_all_info;
    }
}
