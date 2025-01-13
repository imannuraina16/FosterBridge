package com.example.fosterbridge;

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

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.UpdateViewHolder> {
    private List<Update> updatesList;
    private FirebaseFirestore db;

    public UpdatesAdapter() {
        this.updatesList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    // Set the list of updates for the RecyclerView
    public void setUpdatesList(List<Update> updatesList) {
        this.updatesList = updatesList;
        notifyDataSetChanged();
    }

    @Override
    public UpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updates, parent, false);
        return new UpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpdateViewHolder holder, int position) {
        Update update = updatesList.get(position);
        holder.descriptionTextView.setText(update.getDescription());

        // Set the delete button click listener
        holder.deleteButton.setOnClickListener(v -> deleteUpdate(update, position, holder));
    }

    // Method to delete an update
    private void deleteUpdate(Update update, int position, UpdateViewHolder holder) {
        if (update.getId() == null) {
            Toast.makeText(holder.itemView.getContext(), "Error: Update ID is null.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the Firestore document reference for the update
        DocumentReference updateDocRef = db.collection("orphanage_updates").document(update.getId());

        // Delete the update from Firestore
        updateDocRef.delete()
                .addOnSuccessListener(aVoid -> {
                    // Remove the update from the local list at the correct position
                    updatesList.remove(position);  // Remove the item from the local list

                    // Notify the adapter about the removal
                    notifyItemRemoved(position);   // Notify the adapter that the item has been removed

                    // Check if the list is empty and show a message
                    if (updatesList.isEmpty()) {
                        Toast.makeText(holder.itemView.getContext(), "No updates available.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Update deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(holder.itemView.getContext(), "Failed to delete update.", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return updatesList.size();
    }

    // Method to add a new update at the end of the list (instead of the top)
    public void addUpdate(Update newUpdate) {
        updatesList.add(newUpdate);  // Insert the new update at the end of the list (bottom)
        notifyItemInserted(updatesList.size() - 1);  // Notify the adapter that a new item was inserted at the last position
    }

    public class UpdateViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;
        Button deleteButton;

        public UpdateViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
