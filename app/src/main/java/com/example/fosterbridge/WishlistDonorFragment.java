package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

public class WishlistDonorFragment extends Fragment {

    // Declare UI components
    private ShapeableImageView profilePic;
    private TextView usernameTV, orphanageTV, orphanageNameTV, wishlistTV, wallpaintTV;
    private Button sendMessageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist_donor, container, false);

        // Initialize UI components
        profilePic = view.findViewById(R.id.profilePic);
        usernameTV = view.findViewById(R.id.usernameTV);
        orphanageTV = view.findViewById(R.id.orphanageTV);
        orphanageNameTV = view.findViewById(R.id.orphanage_nameTV);
        wishlistTV = view.findViewById(R.id.wishlistTV);
        wallpaintTV = view.findViewById(R.id.TVwallpaint);
        sendMessageButton = view.findViewById(R.id.sendMessageButton);

        // Set initial data for TextViews
        usernameTV.setText("@username");  // Replace with actual username
        orphanageNameTV.setText("orphanage_name");  // Replace with actual orphanage name
        wallpaintTV.setText("Wall paint");

        // Handle "Send Message" button click
        sendMessageButton.setOnClickListener(v -> {
            // You can handle navigating to a messaging screen or sending a message here
            Toast.makeText(getContext(), "Send Message clicked!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}