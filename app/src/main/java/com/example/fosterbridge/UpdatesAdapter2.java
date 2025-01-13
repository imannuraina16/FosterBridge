package com.example.fosterbridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UpdatesAdapter2 extends RecyclerView.Adapter<UpdatesAdapter2.UpdateViewHolder> {

    private List<Update> updatesList;

    // Constructor that accepts a list of updates
    public UpdatesAdapter2(List<Update> updatesList) {
        if (updatesList != null) {
            this.updatesList = updatesList;
        } else {
            this.updatesList = new ArrayList<>();  // Initialize with an empty list if null
        }
    }

    // Set the list of updates for the RecyclerView
    public void setUpdatesList(List<Update> updatesList) {
        if (updatesList != null) {
            this.updatesList = updatesList;
            notifyDataSetChanged();
        }
    }

    @Override
    public UpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updates_item_2, parent, false);
        return new UpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpdateViewHolder holder, int position) {
        if (updatesList != null && !updatesList.isEmpty()) {
            Update update = updatesList.get(position);

            // Bind data to views (Check if update object is not null)
            if (update != null) {
                holder.descriptionTextView.setText(update.getDescription());
            }
        }
    }

    @Override
    public int getItemCount() {
        return (updatesList != null) ? updatesList.size() : 0;
    }

    public static class UpdateViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;

        public UpdateViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
