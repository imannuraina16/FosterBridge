package com.example.fosterbridge;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends Fragment {

    private EditText enterUsername, enterEmail, enterPassword;
    private RadioButton buttonOrphanage, buttonDonor;
    private Button buttonGetStarted;

    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize Firestore
        FirebaseApp.initializeApp(requireContext());
        db = FirebaseFirestore.getInstance();

        // Initialize views
        enterUsername = view.findViewById(R.id.enter_username);
        enterEmail = view.findViewById(R.id.enter_email);
        enterPassword = view.findViewById(R.id.enter_password);
        buttonOrphanage = view.findViewById(R.id.button_orphanage);
        buttonDonor = view.findViewById(R.id.button_donor);
        buttonGetStarted = view.findViewById(R.id.button_getstarted);

        // Set button click listener
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        return view;
    }

    private void registerUser() {
        // Get user input
        String username = enterUsername.getText().toString().trim();
        String email = enterEmail.getText().toString().trim();
        String password = enterPassword.getText().toString().trim();
        String collection; // To decide which Firestore collection to use

        // Validate inputs
        if (TextUtils.isEmpty(username)) {
            enterUsername.setError("Username is required");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            enterEmail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            enterPassword.setError("Password is required");
            return;
        }

        if (buttonOrphanage.isChecked()) {
            collection = "orphanage"; // Save to orphanages collection
        } else if (buttonDonor.isChecked()) {
            collection = "users"; // Save to users collection
        } else {
            Toast.makeText(requireContext(), "Please select a user type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a user data map
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("password", password); // In production, passwords should be hashed
        user.put("userType", buttonOrphanage.isChecked() ? "Orphanage" : "Donor");

        // Save data to the appropriate Firestore collection
        db.collection(collection)
                .document(username) // Use email as the document ID
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                            clearInputs();
                        } else {
                            Toast.makeText(requireContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void clearInputs() {
        enterUsername.setText("");
        enterEmail.setText("");
        enterPassword.setText("");
        buttonOrphanage.setChecked(false);
        buttonDonor.setChecked(false);
    }
}
