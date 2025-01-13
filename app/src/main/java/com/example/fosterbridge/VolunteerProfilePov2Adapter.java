package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VolunteerProfilePov2Adapter extends RecyclerView.Adapter<VolunteerProfilePov2Adapter.VolunteerProfileViewHolder> {

    private List<VolunteerProfile> volunteerProfileList;

    public VolunteerProfilePov2Adapter(List<VolunteerProfile> volunteerProfileList) {
        this.volunteerProfileList = volunteerProfileList;
    }

    @NonNull
    @Override
    public VolunteerProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_item_2, parent, false);
        return new VolunteerProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerProfileViewHolder holder, int position) {
        VolunteerProfile profile = volunteerProfileList.get(position);

        // Bind data to views
        holder.eventName.setText(profile.getEvent_name());
        holder.eventDate.setText(profile.getDate());
        holder.eventDescription.setText(profile.getEvent_description());
    }

    @Override
    public int getItemCount() {
        return volunteerProfileList.size();
    }

    static class VolunteerProfileViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventDate, eventDescription;

        public VolunteerProfileViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.volunteerEventName);
            eventDate = itemView.findViewById(R.id.event_date);
            eventDescription = itemView.findViewById(R.id.volunteerTextView);
        }
    }
}
