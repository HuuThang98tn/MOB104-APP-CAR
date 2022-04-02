package com.example.myapplicationcar.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.MODEL.Slider;
import com.example.myapplicationcar.R;

import java.text.DecimalFormat;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
    private List<Service> listService;
    private List<Slider> listSlider;
    private Context context;

    public SliderAdapter(List<Service> listService, List<Slider> listSlider, Context context) {
        this.listService = listService;
        this.listSlider = listSlider;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.question_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = listService.get(position);
        Slider slider = listSlider.get(position);

        if (position == 0) {
            holder.imgRate.setImageResource(R.drawable.rating_0);
        } else if (position == 1) {
            holder.imgRate.setImageResource(R.drawable.rating_1);
        } else {
            holder.imgRate.setImageResource(R.drawable.rating_2);
        }

        Glide.with(context).load(slider.getImageUrl()).error(R.drawable.img_err).into(holder.imgContent);

        holder.tvName.setText(service.getName());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(formatter.format(service.getPrice().get(0)) + " vnđ");
        holder.tvEndPrice.setText(formatter.format(service.getPrice().get(1)) + " vnđ");
    }

    @Override
    public int getItemCount() {
        return listService == null ? 0 : listService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgContent, imgRate;
        TextView tvName, tvPrice, tvEndPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgContent = itemView.findViewById(R.id.sl_img_service);
            imgRate = itemView.findViewById(R.id.sl_ratting);
            tvName = itemView.findViewById(R.id.sl_name);
            tvPrice = itemView.findViewById(R.id.sl_price);
            tvEndPrice = itemView.findViewById(R.id.sl_price_end);
        }
    }
}
