package com.example.fosterbridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context context;
    ArrayList<Orphanage> orphanageArrayList;
    private OnItemClickListener listener;

    // Constructor with listener
    public HomeAdapter(Context context, ArrayList<Orphanage> orphanageArrayList, OnItemClickListener listener) {
        this.context = context;
        this.orphanageArrayList = orphanageArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item3, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orphanage orphanage = orphanageArrayList.get(position);
        holder.orphanage_name.setText(orphanage.getName());
        holder.orphanage_address.setText(orphanage.getLocation());

        // Handle button click
        holder.button_donate.setOnClickListener(v -> {
            if (listener != null) {
                listener.onButtonClick(orphanage); // Pass data to listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return orphanageArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orphanage_name;
        TextView orphanage_address;
        Button button_donate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orphanage_name = itemView.findViewById(R.id.orphanage_name);
            orphanage_address = itemView.findViewById(R.id.orphanage_address);
            button_donate = itemView.findViewById(R.id.button_donate);
        }
    }

    // Define interface for handling button clicks
    public interface OnItemClickListener {
        void onButtonClick(Orphanage orphanage);
    }
}
