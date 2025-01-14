package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileDonor extends Fragment {

    private EditText inputName, inputUsername, inputEmail, inputPassword, inputGender, inputAge, inputContact, inputBirthDate;
    private TextView usernameTextView;
    private Button saveButton;
    private FirebaseFirestore db;
    private SharedPreferences prefs;

    public EditProfileDonor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile_donor, container, false);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize SharedPreferences to retrieve user session data
        prefs = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);

        // Retrieve username from SharedPreferences
        String username = prefs.getString("username", null);

        // Bind views
        usernameTextView = rootView.findViewById(R.id.username);
        inputName = rootView.findViewById(R.id.inputName);
        inputEmail = rootView.findViewById(R.id.inputEmail);
        inputPassword = rootView.findViewById(R.id.inputPassword);
        inputGender = rootView.findViewById(R.id.inputGender);
        inputUsername = rootView.findViewById(R.id.inputUsername);
        inputAge = rootView.findViewById(R.id.inputAge);
        inputContact = rootView.findViewById(R.id.inputContact);
        inputBirthDate = rootView.findViewById(R.id.inputBirthDate);
        saveButton = rootView.findViewById(R.id.saveButton);

        if (username != null) {
            // Display username in the TextView
            usernameTextView.setText("@" + username);

            // Fetch user profile data from Firestore
            fetchUserProfileData(username);
        } else {
            // Handle case where username is not available
            usernameTextView.setText("No username available");
            Toast.makeText(getActivity(), "User session not found.", Toast.LENGTH_SHORT).show();
        }

        // Set up save button to save the updated data to Firestore
        saveButton.setOnClickListener(v -> {
            if (username != null) {
                saveProfileData(username);
            } else {
                Toast.makeText(getActivity(), "Unable to save profile. User session not found.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void fetchUserProfileData(String username) {
        // Retrieve user profile data from Firestore
        db.collection("users").document(username)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve and display user profile data
                        inputUsername.setText(documentSnapshot.getString("username"));
                        inputName.setText(documentSnapshot.getString("name"));
                        inputEmail.setText(documentSnapshot.getString("email"));
                        inputPassword.setText(documentSnapshot.getString("password"));
                        inputGender.setText(documentSnapshot.getString("gender"));
                        inputAge.setText(documentSnapshot.getString("age"));
                        inputContact.setText(documentSnapshot.getString("contact"));
                        inputBirthDate.setText(documentSnapshot.getString("birthDate"));
                    } else {
                        Toast.makeText(getActivity(), "User profile not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("EditProfileDonor", "Error fetching profile: ", e);
                    Toast.makeText(getActivity(), "Failed to fetch user profile. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveProfileData(String username) {
        // Retrieve updated data from the EditTexts
        String updatedName = inputName.getText().toString();
        String updatedEmail = inputEmail.getText().toString();
        String updatedPassword = inputPassword.getText().toString();
        String updatedGender = inputGender.getText().toString();
        String updatedAge = inputAge.getText().toString();
        String updatedContact = inputContact.getText().toString();
        String updatedBirthDate = inputBirthDate.getText().toString();

        // Validate input fields
        if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedPassword.isEmpty() ||
                updatedGender.isEmpty() || updatedAge.isEmpty() || updatedContact.isEmpty() ||
                updatedBirthDate.isEmpty()) {
            Toast.makeText(getActivity(), "All fields must be filled.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare the data to be saved in Firestore
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("name", updatedName);
        userProfile.put("email", updatedEmail);
        userProfile.put("password", updatedPassword);
        userProfile.put("gender", updatedGender);
        userProfile.put("age", updatedAge);
        userProfile.put("contact", updatedContact);
        userProfile.put("birthDate", updatedBirthDate);

        // Update user profile in Firestore
        db.collection("users").document(username)
                .set(userProfile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Profile has been updated successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("EditProfileDonor", "Error saving profile: ", e);
                    Toast.makeText(getActivity(), "Failed to update profile. Please try again.", Toast.LENGTH_SHORT).show();
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
