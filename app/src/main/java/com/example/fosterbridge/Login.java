package com.example.fosterbridge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Login extends Fragment {

    private EditText enterUsername, enterPassword;
    private Button buttonLogin;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Firestore
        FirebaseApp.initializeApp(requireContext());
        db = FirebaseFirestore.getInstance();

        // Initialize views
        enterUsername = view.findViewById(R.id.enter_username);
        enterPassword = view.findViewById(R.id.enter_password);
        buttonLogin = view.findViewById(R.id.button_login);

        // Set button click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        return view;
    }

    private void loginUser() {
        // Get user input
        String username = enterUsername.getText().toString().trim();
        String password = enterPassword.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(username)) {
            enterUsername.setError("Username is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            enterPassword.setError("Password is required");
            return;
        }

        // Start checking in 'users' collection first
        checkLoginInCollection("users", username, password, true);
    }

    private void checkLoginInCollection(String collection, String username, String password, boolean checkOtherCollection) {
        db.collection(collection)
                .document(username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String storedPassword = document.getString("password");
                                if (storedPassword != null && storedPassword.equals(password)) {
                                    // Successful login
                                    Toast.makeText(requireContext(), "Login successful as " + collection, Toast.LENGTH_SHORT).show();
                                    saveUserDataToPrefs(username, collection);
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    // Password mismatch
                                    Toast.makeText(requireContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // If user not found in current collection, optionally check the other collection
                                if (checkOtherCollection && collection.equals("users")) {
                                    checkLoginInCollection("orphanage", username, password, false);
                                } else if (!checkOtherCollection) {
                                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // Error during login
                            Toast.makeText(requireContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void saveUserDataToPrefs(String username, String collection) {
        // Use SharedPreferences to store user data during the session
        SharedPreferences prefs = requireContext().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);  // Store the username
        editor.putString("userType", collection);  // Store the collection (users or orphanage)
        editor.apply();  // Apply changes asynchronously
    }

}
