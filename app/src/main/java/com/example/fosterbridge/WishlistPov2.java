package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class WishlistPov2 extends Fragment {

    private RecyclerView recyclerView;
    private WishlistAdapter2 adapter;
    private List<WishlistItem> wishlistItems;
    private FirebaseFirestore firestore;
    private TextView usernameTextView, nameTextView;

    private String orphanageUsername;
    private String orphanageName;

    public WishlistPov2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist_pov2, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView_wishlist);
        usernameTextView = view.findViewById(R.id.username_wishlist);
        nameTextView = view.findViewById(R.id.name_wishlist);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Retrieve arguments passed from the previous fragment
        if (getArguments() != null) {
            orphanageUsername = getArguments().getString("orphanage_username", "Default Username");
            orphanageName = getArguments().getString("name", "Default Name");
        }

        // Set username and name
        usernameTextView.setText("@" + orphanageUsername);
        nameTextView.setText(orphanageName);

        // Initialize RecyclerView and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wishlistItems = new ArrayList<>();
        adapter = new WishlistAdapter2();
        recyclerView.setAdapter(adapter);

        // Fetch wishlist items from Firestore
        fetchWishlistFromFirestore();

        return view;
    }

    private void fetchWishlistFromFirestore() {
        firestore.collection("orphanage_wishlist")
                .whereEqualTo("username", orphanageUsername) // Query for wishlist by orphanage username
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    wishlistItems.clear();
                    if (querySnapshot.isEmpty()) {
                        // Show a toast if no wishlist items are found
                        Toast.makeText(getContext(), "No wishlist items found for this orphanage.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Add wishlist items to the list
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            WishlistItem item = document.toObject(WishlistItem.class);
                            wishlistItems.add(item);
                        }
                        adapter.setWishlistItems(wishlistItems);  // Update the adapter with new data
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    Toast.makeText(getContext(), "Error fetching wishlist items.", Toast.LENGTH_SHORT).show();
                });
    }
}