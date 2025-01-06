package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfilePageOrphanageFragment extends Fragment {

    private Button volunteerButton, updateButton, donateButton, editProfileButton, sendMessageButton, wishlistButton;
    private TextView orphanageNameTV, usernameTV, TVorphanage;
    private ShapeableImageView profilePic;

    public ProfilePageOrphanageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page_orphanage, container, false);

        // Initialize UI components
        volunteerButton = view.findViewById(R.id.volunteerButton);
        updateButton = view.findViewById(R.id.updateButton);
        donateButton = view.findViewById(R.id.donateButton);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        sendMessageButton = view.findViewById(R.id.sendMessageButton);
        wishlistButton = view.findViewById(R.id.wishlistButton);
        orphanageNameTV = view.findViewById(R.id.orphanage_nameTV);
        usernameTV = view.findViewById(R.id.usernameTV);
        TVorphanage = view.findViewById(R.id.TVorphanage);
        profilePic = view.findViewById(R.id.profilePic);

        // Set default data
        preFillOrphanageData();

        // Set up onClick listeners for buttons
        volunteerButton.setOnClickListener(v -> onVolunteerClick());
        updateButton.setOnClickListener(v -> onUpdateClick());
        donateButton.setOnClickListener(v -> onDonateClick());
        editProfileButton.setOnClickListener(v -> onEditProfileClick());
        sendMessageButton.setOnClickListener(v -> onSendMessageClick());
        wishlistButton.setOnClickListener(v -> onWishlistClick());

        // Set up click listener for profile picture
        profilePic.setOnClickListener(v -> {
            // For now, show a message when clicking on the profile picture (can be extended later)
            Toast.makeText(getContext(), "Profile picture clicked!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void preFillOrphanageData() {
        // Replace with real data retrieval logic, e.g., from a database or API
        orphanageNameTV.setText("Happy Orphanage");
        usernameTV.setText("Orphanage Admin");
        TVorphanage.setText("Orphanage Profile");
    }

    private void onVolunteerClick() {
        // Handle Volunteer button click
        Toast.makeText(getContext(), "Volunteer page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onUpdateClick() {
        // Handle Orphanage Updates button click
        Toast.makeText(getContext(), "Orphanage Updates page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onDonateClick() {
        // Handle Donate button click
        Toast.makeText(getContext(), "Donate page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onEditProfileClick() {
        // Handle Edit Profile button click
        // You can navigate to another fragment or activity to edit the orphanage profile
        Toast.makeText(getContext(), "Edit Profile page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onSendMessageClick() {
        // Handle Send Message button click
        Toast.makeText(getContext(), "Send Message page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onWishlistClick() {
        // Handle Wishlist button click
        Toast.makeText(getContext(), "Wishlist page coming soon!", Toast.LENGTH_SHORT).show();
    }
}
