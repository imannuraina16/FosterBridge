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
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AccRegistrationFragment extends Fragment {
    private EditText inputUsername, inputEmail, inputPassword;
    private RadioButton donorButton, orphanageButton;
    private Button getStartedButton;
    private TabLayout tabLayout;

    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acc_registration, container, false);

        // Initialize UI components
        inputUsername = view.findViewById(R.id.inputUsername);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        donorButton = view.findViewById(R.id.donorButton);
        orphanageButton = view.findViewById(R.id.orphanageButton);
        getStartedButton = view.findViewById(R.id.getStartedButton);
        tabLayout = view.findViewById(R.id.tabLayout);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Set TabLayout Listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) { // "Login" tab position
                    navigateToLoginFragment();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Optional: Handle if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Handle if needed
            }
        });

        // Set button click listener
        getStartedButton.setOnClickListener(v -> registerUser());

        return view;
    }

    private void registerUser() {
        String username = inputUsername.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String accountType = donorButton.isChecked() ? "Donor" : orphanageButton.isChecked() ? "Orphanage" : "";

        // Validate inputs
        if (TextUtils.isEmpty(username)) {
            inputUsername.setError("Username is required!");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Email is required!");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            inputPassword.setError("Password must be at least 8 characters!");
            return;
        }
        if (TextUtils.isEmpty(accountType)) {
            Toast.makeText(getContext(), "Please select an account type!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create user data map
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("password", password);
        userData.put("accountType", accountType);

        // Save to Firestore
        db.collection("users")
                .add(userData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Registration successful! You can now log in.", Toast.LENGTH_SHORT).show();
                    navigateToLoginFragment();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateToLoginFragment() {
        // Navigate to Login Fragment
        Fragment loginFragment = new LoginFragment(); // Replace with the actual Login Fragment class
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, loginFragment); // Ensure 'fragment_container' matches your container ID
        transaction.commit();
    }
}

//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;

//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.*;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//public class AccRegistrationFragment extends Fragment {
//    private EditText inputUsername, inputEmail, inputPassword;
//    private RadioButton donorButton, orphanageButton;
//    private Button registerButton, loginButton;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_acc_registration, container, false);
//
//        // Initialize UI components
//        inputUsername = view.findViewById(R.id.inputUsername);
//        inputEmail = view.findViewById(R.id.inputEmail);
//        inputPassword = view.findViewById(R.id.inputPassword);
//        donorButton = view.findViewById(R.id.donorButton);
//        orphanageButton = view.findViewById(R.id.orphanageButton);
//        registerButton = view.findViewById(R.id.getStartedButton);
//        loginButton = view.findViewById(R.id.selectLoginButton);
//
//        // Set button click listener
//        registerButton.setOnClickListener(v -> registerUser());
//        loginButton.setOnClickListener(v -> navigateToLoginFragment());
//        return view;
//    }
//
//    private void registerUser() {
//        String username = inputUsername.getText().toString().trim();
//        String email = inputEmail.getText().toString().trim();
//        String password = inputPassword.getText().toString().trim();
//        String accountType = donorButton.isChecked() ? "Donor" : orphanageButton.isChecked() ? "Orphanage" : "";
//
//        // Validate inputs
//        if (TextUtils.isEmpty(username)) {
//            inputUsername.setError("Username is required!");
//            return;
//        }
//        if (TextUtils.isEmpty(email)) {
//            inputEmail.setError("Email is required!");
//            return;
//        }
//        if (TextUtils.isEmpty(password) || password.length() < 8) {
//            inputPassword.setError("Password must be at least 8 characters!");
//            return;
//        }
//        if (TextUtils.isEmpty(accountType)) {
//            Toast.makeText(getContext(), "Please select an account type!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Simulate backend logic (e.g., API call)
//        saveToDatabase(username, email, password, accountType);
//        Toast.makeText(getContext(), "Registration successful! You can now log in.", Toast.LENGTH_SHORT).show();
//    }
//
//    private void navigateToLoginFragment() {
//        // Navigate to Login Fragment
//        Fragment loginFragment = new LoginFragment(); // Replace with the actual Login Fragment class
//        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, loginFragment); // Replace 'fragment_container' with your container ID
//        transaction.addToBackStack(null); // Optional: Add to back stack for navigation
//        transaction.commit();
//    }
//
//    private void saveToDatabase(String username, String email, String password, String accountType) {
//        // Replace this with actual database logic (e.g., Firebase or SQLite)
//        // Example:
//        System.out.println("Saving to database:");
//        System.out.println("Username: " + username);
//        System.out.println("Email: " + email);
//        System.out.println("Password: " + password);
//        System.out.println("Account Type: " + accountType);
//    }
//}


