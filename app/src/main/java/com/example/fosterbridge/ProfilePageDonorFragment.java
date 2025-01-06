package com.example.fosterbridge;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfilePageDonorFragment extends Fragment {

    private Button contributionButton, impactTrackerButton, volunteeringButton, donationButton, messageButton, editProfileButton;
    private TextView profilePic, nameTV, donorTV;
    private ShapeableImageView profileImageView;

    public ProfilePageDonorFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page_donor, container, false);

        // Initialize UI components
        contributionButton = view.findViewById(R.id.ContributionButton);
        impactTrackerButton = view.findViewById(R.id.ImpactTrackerButton);
        volunteeringButton = view.findViewById(R.id.VolunteeringButton);
        donationButton = view.findViewById(R.id.DonationButton);
        messageButton = view.findViewById(R.id.MessageButton);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        profilePic = view.findViewById(R.id.profilePic);
        nameTV = view.findViewById(R.id.nameTV);
        donorTV = view.findViewById(R.id.donorTV);
        profileImageView = view.findViewById(R.id.imageView);

        // Set default text
        preFillUserData();

        // Set up onClick listeners for buttons
        contributionButton.setOnClickListener(v -> onContributionClick());
        impactTrackerButton.setOnClickListener(v -> onImpactTrackerClick());
        volunteeringButton.setOnClickListener(v -> onVolunteeringHistoryClick());
        donationButton.setOnClickListener(v -> onDonationHistoryClick());
        messageButton.setOnClickListener(v -> onSendMessageClick());
        editProfileButton.setOnClickListener(v -> onEditProfileClick());

        // Set up click listener for profile picture
        profileImageView.setOnClickListener(v -> {
            // For now, show a message when clicking on the profile picture (can be extended later)
            Toast.makeText(getContext(), "Profile picture clicked!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void preFillUserData() {
        // Replace with real data retrieval logic, e.g., from a database or API
        nameTV.setText("John Doe");
        donorTV.setText("Donor");
        profilePic.setText("Welcome, John!");
    }

    private void onContributionClick() {
        // Handle Contribution button click
        Toast.makeText(getContext(), "Contribution page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onImpactTrackerClick() {
        // Handle Impact Tracker button click
        Toast.makeText(getContext(), "Impact Tracker page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onVolunteeringHistoryClick() {
        // Handle Volunteering History button click
        Toast.makeText(getContext(), "Volunteering History page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onDonationHistoryClick() {
        // Handle Donation History button click
        Toast.makeText(getContext(), "Donation History page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onSendMessageClick() {
        // Handle Send Message button click
        Toast.makeText(getContext(), "Send Message page coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void onEditProfileClick() {
        // Handle Edit Profile button click
        // You can navigate to another fragment or activity to edit the profile
        Toast.makeText(getContext(), "Edit Profile page coming soon!", Toast.LENGTH_SHORT).show();
    }
}
