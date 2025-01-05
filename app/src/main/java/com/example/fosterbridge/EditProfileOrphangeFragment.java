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

public class EditProfileOrphanageFragment extends Fragment {
    private EditText inputOrphanageName, inputContact, inputEmail, inputAddress, inputRegisterNum;
    private Button saveButton;
    private ImageView orphanageProfilePic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile_orphanage, container, false);

        // Initialize UI components
        orphanageProfilePic = view.findViewById(R.id.TVusername);
        inputOrphanageName = view.findViewById(R.id.inputOrphanageName);
        inputContact = view.findViewById(R.id.inputContact);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputAddress = view.findViewById(R.id.inputAddress);
        inputRegisterNum = view.findViewById(R.id.inputRegisterNum);
        saveButton = view.findViewById(R.id.saveButton);

        // Pre-fill orphanage data (Replace with actual data retrieval logic)
        preFillOrphanageData();

        // Save button click listener
        saveButton.setOnClickListener(v -> saveProfileChanges());

        return view;
    }

    private void preFillOrphanageData() {
        // Simulate fetching orphanage data (Replace with actual database/API calls)
        inputOrphanageName.setText("orphanage_name");
        inputContact.setText("01X-XXXX XXX");
        inputEmail.setText("orphanage@gmail.com");
        inputAddress.setText("Petaling Jaya");
        inputRegisterNum.setText("************");
    }

    private void saveProfileChanges() {
        String orphanageName = inputOrphanageName.getText().toString().trim();
        String contact = inputContact.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String address = inputAddress.getText().toString().trim();
        String registerNum = inputRegisterNum.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(orphanageName)) {
            inputOrphanageName.setError("Orphanage name is required!");
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
        if (TextUtils.isEmpty(address)) {
            inputAddress.setError("Address is required!");
            return;
        }
        if (TextUtils.isEmpty(registerNum)) {
            inputRegisterNum.setError("Registration number is required!");
            return;
        }

        // Simulate saving profile changes (Replace with actual database/API update logic)
        Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
    }
}
