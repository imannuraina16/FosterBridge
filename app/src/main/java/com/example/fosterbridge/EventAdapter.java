package com.example.fosterbridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{
    Context context;
    ArrayList<Event> eventArrayList;
    private EventAdapter.OnItemClickListener listener;

    // Constructor with listener
    public EventAdapter(Context context, ArrayList<Event> eventArrayList, EventAdapter.OnItemClickListener listener) {
        this.context = context;
        this.eventArrayList = eventArrayList;
        this.listener = listener;
    }

    @NonNull
    public EventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new EventAdapter.MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull EventAdapter.MyViewHolder holder, int position) {
        Event event = eventArrayList.get(position);
        holder.event_name.setText(event.getEvent_name());
        holder.event_desc.setText(event.getEvent_description());
        holder.orphanage_name.setText(event.getOrphanage_name());
        holder.date.setText(event.getDate());

        // Handle button click
        holder.button_apply.setOnClickListener(v -> {
            if (listener != null) {
                listener.onButtonClick(event); // Pass data to listener
            }
        });
    }


    public int getItemCount() {
        return eventArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView event_name;
        TextView orphanage_name;
        TextView event_desc;
        TextView date;
        Button button_apply;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orphanage_name = itemView.findViewById(R.id.orphanage_name);
            event_name = itemView.findViewById(R.id.event_name);
            event_desc = itemView.findViewById(R.id.event_description);
            date = itemView.findViewById(R.id.date);
            button_apply = itemView.findViewById(R.id.button_apply);
        }
    }

    // Define interface for handling button clicks
    public interface OnItemClickListener {
        void onButtonClick(Event event);
    }
}
