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

public class EditProfileDonorFragment extends Fragment {
    private EditText inputName, inputAge, inputGender, inputContact, inputEmail, inputPassword, inputBirthDate;
    private Button saveButton;
    private ImageView donorProfilePic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile_donor, container, false);

        // Initialize UI components
        donorProfilePic = view.findViewById(R.id.donorProfilePic);
        inputName = view.findViewById(R.id.inputName);
        inputAge = view.findViewById(R.id.inputAge);
        inputGender = view.findViewById(R.id.inputGender);
        inputContact = view.findViewById(R.id.inputContact);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputBirthDate = view.findViewById(R.id.inputBirthDate);
        saveButton = view.findViewById(R.id.saveButton);

        // Pre-fill user data (Replace with actual data retrieval logic)
        preFillUserData();

        // Save button click listener
        saveButton.setOnClickListener(v -> saveProfileChanges());

        return view;
    }

    private void preFillUserData() {
        // Simulate fetching user data (Replace this with actual database/API calls)
        inputName.setText("admin");
        inputAge.setText("30 years old");
        inputGender.setText("Female");
        inputContact.setText("01X-XXXX XXX");
        inputEmail.setText("admin@gmail.com");
        inputPassword.setText("********");
        inputBirthDate.setText("31 Dec 1993");
    }

    private void saveProfileChanges() {
        String name = inputName.getText().toString().trim();
        String age = inputAge.getText().toString().trim();
        String gender = inputGender.getText().toString().trim();
        String contact = inputContact.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String birthDate = inputBirthDate.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            inputName.setError("Name is required!");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            inputAge.setError("Age is required!");
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            inputGender.setError("Gender is required!");
            return;
        }
        if (TextUtils.isEmpty(contact)) {
            inputContact.setError("Contact info is required!");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Email is required!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Password is required!");
            return;
        }
        if (TextUtils.isEmpty(birthDate)) {
            inputBirthDate.setError("Date of Birth is required!");
            return;
        }

        // Simulate saving profile changes (Replace with actual database/API update logic)
        Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
    }
}
