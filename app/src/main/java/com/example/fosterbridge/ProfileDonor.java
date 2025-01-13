package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileDonor extends Fragment {

    private TextView usernameTextView;
    private TextView nameTextView;
    private Button editProfileButton;
    private Button donationHistoryButton;
    private FirebaseFirestore firestore;
    TextView title;

    public ProfileDonor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_donor, container, false);

        // Initialize TextViews and Button
        usernameTextView = view.findViewById(R.id.username);
        nameTextView = view.findViewById(R.id.nameTV);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        donationHistoryButton = view.findViewById(R.id.DonationHistoryButton);
        title = getActivity().findViewById(R.id.title);


        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Retrieve SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);

        // Get the username from SharedPreferences
        String username = sharedPreferences.getString("username", "No username");

        // Set the username into the TextView
        usernameTextView.setText("@" + username);

        // Fetch the "name" field from Firestore and set it to nameTextView
        firestore.collection("users").document(username).get().addOnCompleteListener(task -> {
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

        // Set the click listener for the Edit Profile button
        editProfileButton.setOnClickListener(v -> navigateToEditProfile());
        donationHistoryButton.setOnClickListener(v -> navigateToDonationHistory());

        return view;
    }

    private void navigateToEditProfile() {
        EditProfileDonor editProfileDonor = new EditProfileDonor();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, editProfileDonor);
        transaction.addToBackStack(null);
        title.setText("Edit Profile");
        transaction.commit();
    }

    private void navigateToDonationHistory() {
        DonationHistory donationHistory = new DonationHistory();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, donationHistory);
        transaction.addToBackStack(null);
        title.setText("Donation History");
        transaction.commit();
    }

}
