package com.example.fosterbridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter2 extends RecyclerView.Adapter<WishlistAdapter2.WishlistViewHolder> {

    private List<WishlistItem> wishlistItems;
    private FirebaseFirestore db;

    // Constructor to initialize the list and Firestore instance
    public WishlistAdapter2() {
        this.wishlistItems = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    // Set the list of wishlist items for the RecyclerView
    public void setWishlistItems(List<WishlistItem> wishlistItems) {
        if (wishlistItems != null) {
            this.wishlistItems = wishlistItems;
            notifyDataSetChanged();
        }
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item (wishlist_item.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item2, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WishlistViewHolder holder, int position) {
        WishlistItem wishlistItem = wishlistItems.get(position);

        // Bind data to views
        holder.descriptionTextView.setText(wishlistItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return (wishlistItems != null) ? wishlistItems.size() : 0; // Safety check for null list
    }

    // ViewHolder class for the Wishlist item
    public class WishlistViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;

        public WishlistViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.wishlistTextView);
        }
    }
}
