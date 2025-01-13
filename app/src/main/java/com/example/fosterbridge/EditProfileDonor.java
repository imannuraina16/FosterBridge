package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditProfileDonor extends Fragment {

    private EditText inputUsername, inputName, inputEmail, inputPassword, inputGender, inputAge, inputContact, inputBirthDate;
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
        prefs = getActivity().getSharedPreferences("UserSessionPrefs", getActivity().MODE_PRIVATE);

        // Retrieve username from SharedPreferences (email and password will be fetched from Firestore)
        String username = prefs.getString("username", "No username");

        // Bind views
        usernameTextView = rootView.findViewById(R.id.username);
        inputUsername = rootView.findViewById(R.id.inputUsername);
        inputName = rootView.findViewById(R.id.inputName);
        inputEmail = rootView.findViewById(R.id.inputEmail);
        inputPassword = rootView.findViewById(R.id.inputPassword);
        inputGender = rootView.findViewById(R.id.inputGender);
        inputAge = rootView.findViewById(R.id.inputAge);
        inputContact = rootView.findViewById(R.id.inputContact);
        inputBirthDate = rootView.findViewById(R.id.inputBirthDate);
        saveButton = rootView.findViewById(R.id.saveButton);

        // Display username in the textView and input field
        usernameTextView.setText("@"+username);
        inputUsername.setText(username);

        // Fetch user profile data from Firestore
        fetchUserProfileData(username);

        // Set up save button to save the updated data to Firestore
        saveButton.setOnClickListener(v -> {
            saveProfileData();
        });

        return rootView;
    }

    private void fetchUserProfileData(String username) {
        // Retrieve user profile data from Firestore
        db.collection("users").document(username)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve email, password, and other profile data from Firestore
                        String userEmail = documentSnapshot.getString("email");
                        String userPassword = documentSnapshot.getString("password");
                        String userName = documentSnapshot.getString("name");
                        String userGender = documentSnapshot.getString("gender");
                        String userAge = documentSnapshot.getString("age");
                        String userContact = documentSnapshot.getString("contact");
                        String userBirthDate = documentSnapshot.getString("birthDate");

                        // Display the retrieved data in the input fields
                        inputName.setText(userName);
                        inputEmail.setText(userEmail);
                        inputPassword.setText(userPassword);
                        inputGender.setText(userGender);
                        inputAge.setText(userAge);
                        inputContact.setText(userContact);
                        inputBirthDate.setText(userBirthDate);
                    } else {
                        // Handle case where the document doesn't exist
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure (e.g., show an error message)
                });
    }

    private void saveProfileData() {
        // Retrieve updated data from the EditTexts
        String updatedUsername = inputUsername.getText().toString();  // Edited username
        String updatedName = inputName.getText().toString();
        String updatedEmail = inputEmail.getText().toString();
        String updatedPassword = inputPassword.getText().toString();
        String updatedGender = inputGender.getText().toString();
        String updatedAge = inputAge.getText().toString();
        String updatedContact = inputContact.getText().toString();
        String updatedBirthDate = inputBirthDate.getText().toString();

        // Prepare the data to be saved in Firestore
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("name", updatedName);
        userProfile.put("email", updatedEmail);
        userProfile.put("password", updatedPassword);
        userProfile.put("gender", updatedGender);
        userProfile.put("age", updatedAge);
        userProfile.put("contact", updatedContact);
        userProfile.put("birthDate", updatedBirthDate);

        // Retrieve the old username from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String oldUsername = sharedPreferences.getString("username", "No username");

        // Check if the updated username already exists in the database
        db.collection("users").document(updatedUsername).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Username already exists, show a Toast message and don't allow the profile update
                        Toast.makeText(getActivity(), "Username already exists. Please choose a different one.", Toast.LENGTH_SHORT).show();
                    } else {
                        // If the username has changed, perform the rename operation
                        if (!updatedUsername.equals(oldUsername)) {
                            // Fetch data from the old document and then save it under the new username
                            db.collection("users").document(oldUsername).get()
                                    .addOnSuccessListener(doc -> {
                                        if (doc.exists()) {
                                            // Move the data to the new username (create a new document)
                                            db.collection("users").document(updatedUsername)
                                                    .set(userProfile)
                                                    .addOnSuccessListener(aVoid -> {
                                                        // Data saved successfully, now delete the old document
                                                        deleteOldDocument(oldUsername);

                                                        // Update SharedPreferences with the new username
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("username", updatedUsername);
                                                        editor.apply();

                                                        Toast.makeText(getActivity(), "Profile has been updated successfully.", Toast.LENGTH_SHORT).show();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        // Handle failure in saving data to new document
                                                        Toast.makeText(getActivity(), "Failed to update profile. Please try again.", Toast.LENGTH_SHORT).show();
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle failure in retrieving the old document
                                        Toast.makeText(getActivity(), "Failed to fetch old data. Please try again.", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            // If the username hasn't changed, just update the document with the current username
                            db.collection("users").document(updatedUsername)
                                    .set(userProfile)
                                    .addOnSuccessListener(aVoid -> {
                                        // Data saved successfully
                                        Toast.makeText(getActivity(), "Profile has been updated successfully.", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle failure
                                        Toast.makeText(getActivity(), "Failed to update profile. Please try again.", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure to check for existing username
                    Toast.makeText(getActivity(), "Failed to check username. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteOldDocument(String oldUsername) {
        db.collection("users").document(oldUsername)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Old document deleted successfully
                })
                .addOnFailureListener(e -> {
                    // Handle failure to delete old document
                    Toast.makeText(getActivity(), "Failed to delete old profile. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }


}
