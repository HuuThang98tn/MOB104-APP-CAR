package com.example.myapplicationcar.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;

import java.util.List;

public class SpinnerAdapterService extends BaseAdapter {
    private Context context;
    private List<Service> list;

    public SpinnerAdapterService(Context context, List<Service> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(list.get(i).getId());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_spinner_ct,viewGroup, false);
        }
        TextView tvName = view.findViewById(R.id.sp_item);

        Service service = list.get(i);
        tvName.setText(service.getName());
        return view;
    }
}
