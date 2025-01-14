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
        View view = inflater.inflate(R.layout.fragment_edit_profile_orphanage, container, false);

        // Initialize views
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

        // Get username and userType from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "No username");
        String userType = sharedPreferences.getString("userType", "Orphanage");

        // Set username and userType
        usernameTextView.setText(username);
        userTypeTextView.setText(userType);
        inputUsername.setText(username); // Set username but leave it non-editable

        // Fetch data from Firestore
        db.collection("orphanage").document(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
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


    private void saveProfileData(String username) {
        // Retrieve updated data from the EditTexts
        String updatedName = inputName.getText().toString().trim();
        String updatedContact = inputContact.getText().toString().trim();
        String updatedEmail = inputEmail.getText().toString().trim();
        String updatedAddress = inputAddress.getText().toString().trim();
        String updatedPassword = inputPassword.getText().toString().trim();
        String updatedRegNum = inputRegNum.getText().toString().trim();

        // Validate input fields
        if (updatedName.isEmpty() || updatedContact.isEmpty() || updatedEmail.isEmpty() ||
                updatedAddress.isEmpty() || updatedPassword.isEmpty() || updatedRegNum.isEmpty()) {
            Toast.makeText(getActivity(), "All fields must be filled.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare the data to be saved in Firestore
        Map<String, Object> orphanageProfile = new HashMap<>();
        orphanageProfile.put("name", updatedName);
        orphanageProfile.put("contact_info", updatedContact);
        orphanageProfile.put("email", updatedEmail);
        orphanageProfile.put("location", updatedAddress);
        orphanageProfile.put("password", updatedPassword);
        orphanageProfile.put("registration_number", updatedRegNum);

        // Update the existing document in Firestore
        db.collection("orphanage").document(username)
                .set(orphanageProfile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to update profile. Please try again.", Toast.LENGTH_SHORT).show();
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
