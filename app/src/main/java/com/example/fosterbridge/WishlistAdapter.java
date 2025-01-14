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

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private List<WishlistItem> wishlistItems;
    private FirebaseFirestore db;

    public WishlistAdapter() {
        this.wishlistItems = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    // Set the list of wishlist items for the RecyclerView
    public void setWishlistItems(List<WishlistItem> wishlistItems) {
        this.wishlistItems = wishlistItems;
        notifyDataSetChanged();
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item (wishlist_item.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WishlistViewHolder holder, int position) {
        WishlistItem wishlistItem = wishlistItems.get(position);
        holder.descriptionTextView.setText(wishlistItem.getDescription());

        // Set the delete button click listener
        holder.deleteButton.setOnClickListener(v -> deleteWishlistItem(wishlistItem, position, holder));
    }

    // Method to delete a wishlist item
    private void deleteWishlistItem(WishlistItem wishlistItem, int position, WishlistViewHolder holder) {
        if (wishlistItem.getId() == null) {
            Toast.makeText(holder.itemView.getContext(), "Error: Wishlist item ID is null.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the Firestore document reference for the wishlist item
        DocumentReference wishlistItemDocRef = db.collection("orphanage_wishlist").document(wishlistItem.getId());

        // Delete the wishlist item from Firestore
        wishlistItemDocRef.delete()
                .addOnSuccessListener(aVoid -> {
                    // Remove the wishlist item from the local list
                    wishlistItems.remove(position);

                    // Notify the adapter about the removal
                    notifyItemRemoved(position);

                    // Check if the list is empty and show a message
                    if (wishlistItems.isEmpty()) {
                        Toast.makeText(holder.itemView.getContext(), "Wishlist is now empty.", Toast.LENGTH_SHORT).show();
                        // Optionally, show a placeholder message or update the UI
                        // For example:
                        // showEmptyState();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Wishlist item deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(holder.itemView.getContext(), "Failed to delete wishlist item.", Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public int getItemCount() {
        return wishlistItems.size();
    }

    // Method to add a new wishlist item at the end of the list (instead of the top)
    public void addWishlistItem(WishlistItem newWishlistItem) {
        wishlistItems.add(newWishlistItem);  // Insert the new item at the end of the list (bottom)
        notifyItemInserted(wishlistItems.size() - 1);  // Notify the adapter that a new item was inserted at the last position
    }

    // ViewHolder class for the Wishlist item
    public class WishlistViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;
        Button deleteButton;

        public WishlistViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.wishlistTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
