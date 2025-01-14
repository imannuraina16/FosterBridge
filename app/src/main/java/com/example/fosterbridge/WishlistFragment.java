package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class WishlistFragment extends Fragment {
    private FirebaseFirestore db;
    private CollectionReference wishlistCollection;
    private EditText wishlistDescriptionEditText;
    private Button postButton;
    private RecyclerView recyclerView;
    private WishlistAdapter wishlistAdapter;
    private List<WishlistItem> wishlistItems;
    private TextView usernameTextView;
    private TextView nameTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        wishlistCollection = db.collection("orphanage_wishlist");

        // Set up UI components
        wishlistDescriptionEditText = view.findViewById(R.id.wishlist_description);
        postButton = view.findViewById(R.id.button_post_wishlist);
        recyclerView = view.findViewById(R.id.recyclerView_wishlist);
        usernameTextView = view.findViewById(R.id.username_wishlist);  // Add username display
        nameTextView = view.findViewById(R.id.name_wishlist);  // Add name display
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the adapter for RecyclerView
        wishlistAdapter = new WishlistAdapter();
        recyclerView.setAdapter(wishlistAdapter);

        // Initialize the wishlist items list
        wishlistItems = new ArrayList<>();

        // Post button click listener
        postButton.setOnClickListener(v -> postWishlistItem());

        // Retrieve the username and name from Firestore and SharedPreferences
        loadUserData();

        // Return the view
        return view;
    }

    // Method to load user data (username and name) from SharedPreferences and Firestore
    private void loadUserData() {
        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Anonymous");

        // Set the username in the TextView
        usernameTextView.setText("@" + username);

        // Fetch name from Firestore
        FirebaseFirestore.getInstance().collection("orphanage").document(username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            String name = document.getString("name");
                            nameTextView.setText(name != null ? name : "No name available");
                        } else {
                            nameTextView.setText("Document not found");
                        }
                    } else {
                        nameTextView.setText("Error fetching name");
                    }
                });
    }

    // Method to post the wishlist item
    private void postWishlistItem() {
        String description = wishlistDescriptionEditText.getText().toString().trim();

        if (description.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a wishlist description.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Anonymous");

        // Create a new wishlist item with the description and username
        Map<String, Object> wishlistItem = new HashMap<>();
        wishlistItem.put("description", description);
        wishlistItem.put("username", username);  // Store the username along with the wishlist item

        // Save the wishlist item to Firestore
        wishlistCollection.add(wishlistItem)
                .addOnSuccessListener(documentReference -> {
                    // Create a new WishlistItem object for the posted wishlist item
                    WishlistItem newWishlistItem = new WishlistItem(description);
                    newWishlistItem.setId(documentReference.getId());  // Set the document ID manually
                    newWishlistItem.setUsername(username);  // Set the username for the wishlist item

                    // Add the new wishlist item to the list at the end
                    wishlistAdapter.addWishlistItem(newWishlistItem);

                    // Show success message and clear the input field
                    Toast.makeText(getContext(), "Wishlist item posted successfully.", Toast.LENGTH_SHORT).show();
                    wishlistDescriptionEditText.setText(""); // Clear the input field
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to post wishlist item. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadWishlistItems();
    }

    private void loadWishlistItems() {
        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "Anonymous");

        // Filter the query to only get items that belong to the logged-in user
        wishlistCollection.whereEqualTo("username", loggedInUsername)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<WishlistItem> wishlistItems = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            // Create a WishlistItem object and set the document ID
                            WishlistItem wishlistItem = document.toObject(WishlistItem.class);
                            if (wishlistItem != null) {
                                wishlistItem.setId(document.getId());  // Set the document ID manually
                            }
                            wishlistItems.add(wishlistItem);
                        }
                        wishlistAdapter.setWishlistItems(wishlistItems);
                    } else {
                        Toast.makeText(getContext(), "No wishlist items found for this user.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to load wishlist items. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.showUpButton();  // Ensure up button is shown
        }
    }
}
