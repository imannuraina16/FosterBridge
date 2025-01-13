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

public class LoginPageFragment extends Fragment {
    private EditText inputUsername, inputPassword;
    private Button selectRegister;
    private TextView usernameTV, passwordTV;

    public LoginPageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);

        // Initialize UI components
        inputUsername = view.findViewById(R.id.inputUsername);
        inputPassword = view.findViewById(R.id.inputPassword);
        selectRegister = view.findViewById(R.id.selectRegister);
        usernameTV = view.findViewById(R.id.profilePic);
        passwordTV = view.findViewById(R.id.passwordTV);

        // Set up click listener for Register button
        selectRegister.setOnClickListener(v -> onRegisterClick());

        return view;
    }

    private void loginUser() {
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(username)) {
            inputUsername.setError("Username is required!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Password is required!");
            return;
        }

        // Simulate login check
        if (validateCredentials(username, password)) {
            Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
            // Add logic to navigate to the main/home page
        } else {
            Toast.makeText(getContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onRegisterClick() {
        // Get user input
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Basic validation for empty fields
        if (TextUtils.isEmpty(username)) {
            inputUsername.setError("Username is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Password is required");
            return;
        }

        // You can perform login validation here (e.g., compare with hardcoded credentials or make an API call)
        if (isLoginValid(username, password)) {
            // Handle successful login (e.g., navigate to the next screen)
            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            // You can navigate to a different fragment or activity here, for example:
            // navigateToHomePage();
        } else {
            // Show error message for invalid login
            Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    // Dummy login validation method - replace this with real login logic
    private boolean isLoginValid(String username, String password) {
        // Example: Replace with actual authentication logic (API call, database check, etc.)
        return username.equals("admin") && password.equals("password123");
    }

    private boolean validateCredentials(String username, String password) {
        // Replace this with actual validation logic (e.g., API call or database query)
        return username.equals("testuser") && password.equals("password123");
    }

    private void navigateToRegistrationFragment() {
        // Navigate to Registration Fragment
        Fragment registrationFragment = new SignUpFragment(); // Replace with the actual Registration Fragment class
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, registrationFragment); // Replace 'fragment_container' with your container ID
        transaction.addToBackStack(null); // Optional: Add to back stack for navigation
        transaction.commit();
    }
}