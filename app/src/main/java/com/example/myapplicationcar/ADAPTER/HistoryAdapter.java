package com.example.myapplicationcar.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationcar.MODEL.Oder;
import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;

import java.text.DecimalFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Oder> list;
    private List<Service> listService;
    private Context context;
    private ItemHistory itemHistory;

    public HistoryAdapter(List<Oder> list, Context context, List<Service> listService, ItemHistory itemHistory) {
        this.list = list;
        this.listService = listService;
        this.context = context;
        this.itemHistory = itemHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Oder oder = list.get(position);

        if (oder != null) {
            holder.bienXe.setText("Biển số: " + oder.getNumber_car());
            holder.ngay.setText("Ngày: " + oder.getDate());

            for (Service item : listService) {
                if (item.getId().equals(oder.getService_id())) {
                    if (item.getType().equals("bao_duong_dinh_ky")){
                        holder.dichVu.setText("Dịch vụ: Bảo dưỡng xe ĐK " + item.getName());
                    }else {
                        holder.dichVu.setText("Dịch vụ: " + item.getName());
                    }

                    DecimalFormat formatter = new DecimalFormat("###,###,###");
                    int tb = 0;
                    tb = ((Integer.parseInt(item.getPrice().get(0) + "") + Integer.parseInt(item.getPrice().get(1) + "")) / 2) + 100000;
                    holder.tien.setText(formatter.format(tb) + "đ");
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemHistory.clickItem(oder);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bienXe, ngay, dichVu, tien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bienXe = itemView.findViewById(R.id.hs_bienXe);
            ngay = itemView.findViewById(R.id.hs_date);
            dichVu = itemView.findViewById(R.id.hs_service);
            tien = itemView.findViewById(R.id.hs_monney);
        }
    }
}
