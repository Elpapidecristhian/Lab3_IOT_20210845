package com.example.lab3_iot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(LocationItem item);
    }

    private List<LocationItem> locationList;
    private OnItemClickListener listener;

    public LocationAdapter(List<LocationItem> locationList, OnItemClickListener listener) {
        this.locationList = locationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationItem item = locationList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvRegion.setText("Región: " + item.getRegion());
        holder.tvCountry.setText("País: " + item.getCountry());
        holder.tvId.setText("ID: " + item.getId());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("idLocation", String.valueOf(item.getId()));

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_locationFragment_to_forecasterFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRegion, tvCountry, tvId;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRegion = itemView.findViewById(R.id.tvRegion);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvId = itemView.findViewById(R.id.tvId);
        }
    }
}
