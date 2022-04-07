package com.example.myapplicationcar.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;

import java.util.ArrayList;

public class SpinnerAdapterService extends ArrayAdapter {
    Context context;
    ArrayList<Service> list;

    public SpinnerAdapterService(@NonNull Context context, ArrayList<Service> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_spinner_ct, null);
        }

        if (list != null) {
            TextView tvName = view.findViewById(R.id.sp_item);
            tvName.setText(list.get(position).getName());
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_spinner_ct, null);

        if (list != null) {
            TextView tvName = view.findViewById(R.id.sp_item);
            tvName.setText(list.get(position).getName());
        }

        return view;
    }
}
