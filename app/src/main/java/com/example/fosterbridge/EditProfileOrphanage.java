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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileOrphanage extends Fragment {

    private TextView usernameTextView;
    private TextView userTypeTextView;
    private EditText inputUsername;
    private EditText inputName;
    private EditText inputContact;
    private EditText inputEmail;
    private EditText inputAddress;
    private EditText inputPassword;
    private EditText inputRegNum;
    private Button saveButton;

    private FirebaseFirestore db;

    public EditProfileOrphanage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile_orphanage, container, false);

        // Initialize TextViews and EditTexts
        usernameTextView = view.findViewById(R.id.username);
        userTypeTextView = view.findViewById(R.id.userType);
        inputUsername = view.findViewById(R.id.inputUsername);
        inputName = view.findViewById(R.id.inputName);
        inputContact = view.findViewById(R.id.inputContact);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputAddress = view.findViewById(R.id.inputAddress);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputRegNum = view.findViewById(R.id.inputRegNum);
        saveButton = view.findViewById(R.id.saveButton);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Retrieve SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);

        // Get username and userType from SharedPreferences
        String username = sharedPreferences.getString("username", "No username");
        String userType = sharedPreferences.getString("userType", "Orphanage");

        // Set username and userType
        usernameTextView.setText(username);
        userTypeTextView.setText(userType);

        // Fetch data from Firestore
        db.collection("orphanage").document(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    inputUsername.setText(username);
                    inputName.setText(document.getString("name"));
                    inputContact.setText(document.getString("contact_info"));
                    inputEmail.setText(document.getString("email"));
                    inputAddress.setText(document.getString("location"));
                    inputPassword.setText(document.getString("password"));
                    inputRegNum.setText(document.getString("registration_number"));
                }
            }
        });

        // Set save button action
        saveButton.setOnClickListener(v -> saveProfileData(username));

        return view;
    }

    private void saveProfileData(String oldUsername) {
        // Retrieve updated data from the EditTexts
        String updatedUsername = inputUsername.getText().toString().trim();  // Edited username
        String updatedName = inputName.getText().toString().trim();
        String updatedContact = inputContact.getText().toString().trim();
        String updatedEmail = inputEmail.getText().toString().trim();
        String updatedAddress = inputAddress.getText().toString().trim();
        String updatedPassword = inputPassword.getText().toString().trim();
        String updatedRegNum = inputRegNum.getText().toString().trim();

        // Validate input fields
        if (updatedUsername.isEmpty() || updatedName.isEmpty() || updatedContact.isEmpty() || updatedEmail.isEmpty() ||
                updatedAddress.isEmpty() || updatedPassword.isEmpty() || updatedRegNum.isEmpty()) {
            Toast.makeText(getActivity(), "All fields must be filled.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the updated username already exists in Firestore (for orphanages)
        db.collection("orphanage").document(updatedUsername).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Username already exists, show a Toast message and prevent the update
                        Toast.makeText(getActivity(), "Username already exists. Please choose a different one.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Prepare the data to be saved in Firestore
                        Map<String, Object> orphanageProfile = new HashMap<>();
                        orphanageProfile.put("name", updatedName);
                        orphanageProfile.put("contact_info", updatedContact);
                        orphanageProfile.put("email", updatedEmail);
                        orphanageProfile.put("location", updatedAddress);
                        orphanageProfile.put("password", updatedPassword);
                        orphanageProfile.put("registration_number", updatedRegNum);

                        // Retrieve old data and set new username
                        db.collection("orphanage").document(oldUsername).get()
                                .addOnSuccessListener(docSnapshot -> {
                                    if (docSnapshot.exists()) {
                                        // Move data to new document with updated username
                                        db.collection("orphanage").document(updatedUsername)
                                                .set(orphanageProfile)
                                                .addOnSuccessListener(aVoid -> {
                                                    // Data has been successfully copied to the new document
                                                    // Now delete the old document
                                                    deleteOldDocument(oldUsername);

                                                    // Update SharedPreferences with the new username
                                                    SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE).edit();
                                                    editor.putString("username", updatedUsername);  // Update the stored username
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
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure in checking if the username exists
                    Toast.makeText(getActivity(), "Failed to check username. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteOldDocument(String oldUsername) {
        db.collection("orphanage").document(oldUsername)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Document deleted successfully
                })
                .addOnFailureListener(e -> {
                    // Handle failure to delete old document
                    Toast.makeText(getActivity(), "Failed to delete old profile. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }


}
