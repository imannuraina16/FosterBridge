package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ProfileDonor extends Fragment {

    TextView title;
    private TextView usernameTextView;
    private Button editProfileButton;

    public ProfileDonor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_donor, container, false);
        title = getActivity().findViewById(R.id.title);
        // Initialize the TextView for username
        usernameTextView = view.findViewById(R.id.username);

        // Retrieve SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);

        // Get the username from SharedPreferences
        String username = sharedPreferences.getString("username", "No username");

        // Set the username into the TextView
        usernameTextView.setText("@" + username);

        // Initialize editProfileButton and set the click listener
        editProfileButton = view.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(v -> navigateToEditProfile());

        return view;
    }

    private void navigateToEditProfile() {
        // Create an instance of the EditProfileDonor fragment
        EditProfileDonor editProfileDonor = new EditProfileDonor();

        // Begin the fragment transaction to replace the current fragment with EditProfileDonor
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, editProfileDonor); // Replace with the container ID where fragments are displayed
        title.setText("Edit Profile");
        transaction.addToBackStack(null); // Add to back stack if you want the user to be able to press back to return to Profile fragment
        transaction.commit();
    }
}
