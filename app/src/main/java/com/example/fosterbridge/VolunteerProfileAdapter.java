package com.example.fosterbridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class VolunteerProfileAdapter extends RecyclerView.Adapter<VolunteerProfileAdapter.VolunteerProfileViewHolder> {
    private List<VolunteerProfile> volunteerProfileList;
    private Context context;  // Added context to make toast messages more flexible

    public VolunteerProfileAdapter(Context context) {
        this.volunteerProfileList = new ArrayList<>();
        this.context = context;
    }

    // Set the list of volunteer profiles for the RecyclerView
    public void setVolunteerProfiles(List<VolunteerProfile> volunteerProfileList) {
        this.volunteerProfileList = volunteerProfileList;
        notifyDataSetChanged();  // Notify adapter that data has changed
    }

    @Override
    public VolunteerProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item (volunteer_item.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.volunteer_item, parent, false);
        return new VolunteerProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VolunteerProfileViewHolder holder, int position) {
        VolunteerProfile volunteerProfile = volunteerProfileList.get(position);

        // Bind event name, event description, and event date to TextViews
        holder.eventNameTextView.setText(volunteerProfile.getEvent_name());
        holder.eventDateTextView.setText(volunteerProfile.getDate());
        holder.descriptionTextView.setText(volunteerProfile.getEvent_description());

        // Set the delete button click listener
        holder.deleteButton.setOnClickListener(v -> deleteVolunteerProfile(volunteerProfile, position, holder));
    }

    // Method to delete a volunteer profile
    private void deleteVolunteerProfile(VolunteerProfile volunteerProfile, int position, VolunteerProfileViewHolder holder) {
        if (volunteerProfile.getId() == null) {
            Toast.makeText(context, "Error: Volunteer profile ID is null.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the Firestore document reference for the volunteer profile
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference volunteerProfileDocRef = db.collection("events").document(volunteerProfile.getId());

        // Delete the volunteer profile from Firestore
        volunteerProfileDocRef.delete()
                .addOnSuccessListener(aVoid -> {
                    // Remove the profile from the local list at the correct position
                    volunteerProfileList.remove(position);

                    // Notify the adapter about the removal
                    notifyItemRemoved(position);

                    // Check if the list is empty and show a message
                    if (volunteerProfileList.isEmpty()) {
                        Toast.makeText(context, "No volunteer profiles available.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Volunteer profile deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete volunteer profile.", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return volunteerProfileList.size();
    }

    // Method to add a new volunteer profile at the end of the list
    public void addVolunteerProfile(VolunteerProfile newVolunteerProfile) {
        volunteerProfileList.add(newVolunteerProfile);  // Insert the new profile at the end of the list
        notifyItemInserted(volunteerProfileList.size() - 1);  // Notify the adapter that a new item was inserted at the last position
    }

    // ViewHolder class for the Volunteer profile item
    public class VolunteerProfileViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameTextView, eventDateTextView, descriptionTextView;
        Button deleteButton;

        public VolunteerProfileViewHolder(View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.volunteerEventName);  // Event Name TextView
            eventDateTextView = itemView.findViewById(R.id.event_date);  // Event Date TextView
            descriptionTextView = itemView.findViewById(R.id.volunteerTextView);  // Event Description TextView
            deleteButton = itemView.findViewById(R.id.deleteButton);  // Delete Button
        }
    }
}
