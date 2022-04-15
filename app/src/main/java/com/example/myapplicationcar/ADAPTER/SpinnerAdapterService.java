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
import java.util.List;

public class SpinnerAdapterService extends ArrayAdapter<Service>{
    Context context;
    List<Service> list;
    public SpinnerAdapterService(@NonNull Context context, @NonNull List<Service> objects) {
        super(context, 0, objects);
        this.list = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_ct, null);
        }
        if (list == null){

        }
        Service service = list.get(position);
        if (service != null) {
            TextView tvTenSach = view.findViewById(R.id.sp_item);
            tvTenSach.setText(service.getName());
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_ct, null);
        }
        if (list == null){

        }
        Service service = list.get(position);
        if (service != null) {
            TextView tvTenSach = view.findViewById(R.id.sp_item);
            tvTenSach.setText(service.getName());
        }

        return view;
    }
}
