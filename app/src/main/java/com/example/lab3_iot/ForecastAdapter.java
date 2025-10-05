package com.example.lab3_iot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.lab3_iot.R;
import com.example.lab3_iot.ForecastItem;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<ForecastItem> forecastList;

    public ForecastAdapter(List<ForecastItem> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastItem item = forecastList.get(position);
        holder.tvDate.setText("📅 " + item.getDate());
        holder.tvCondition.setText(item.getCondition());
        holder.tvTemps.setText("Máx: " + item.getMaxTemp() + "°C  |  Mín: " + item.getMinTemp() + "°C");

        Glide.with(holder.itemView.getContext())
                .load("https:" + item.getIconUrl())
                .into(holder.imgIcon);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvCondition, tvTemps;
        ImageView imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCondition = itemView.findViewById(R.id.tvCondition);
            tvTemps = itemView.findViewById(R.id.tvTemps);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
