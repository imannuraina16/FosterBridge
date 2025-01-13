package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.imageview.ShapeableImageView;

public class DonorUpdateFragment extends Fragment {

    private TextView orphanageNameTextView, usernameTextView;
    private TextView update1TextView, update2TextView, update3TextView, update4TextView, update5TextView;
    private ShapeableImageView userImageView;

    // Required empty public constructor
    public DonorUpdateFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_donor_update, container, false);

        // Initialize views
        orphanageNameTextView = rootView.findViewById(R.id.textView);
        usernameTextView = rootView.findViewById(R.id.textView2);
        update1TextView = rootView.findViewById(R.id.textView5);
        update2TextView = rootView.findViewById(R.id.textView6);
        update3TextView = rootView.findViewById(R.id.textView7);  // Update with actual ID if added
        update4TextView = rootView.findViewById(R.id.textView8);  // Update with actual ID if added
        update5TextView = rootView.findViewById(R.id.textView9);  // Update with actual ID if added
        userImageView = rootView.findViewById(R.id.imageView4);

        // Set dynamic content (Optional)
        orphanageNameTextView.setText("Orphanage XYZ");
        usernameTextView.setText("John Doe");

        // Set up event listeners for each update section
        setupUpdateListeners();

        return rootView;
    }

    private void setupUpdateListeners() {
        // Update 1 - clickable action
        update1TextView.setOnClickListener(v -> {
            // Handle Update 1 click
            String updateDetails = update1TextView.getText().toString();
            Toast.makeText(getActivity(), "Clicked on: " + updateDetails, Toast.LENGTH_SHORT).show();
        });

        // Update 2 - clickable action
        update2TextView.setOnClickListener(v -> {
            // Handle Update 2 click
            String updateDetails = update2TextView.getText().toString();
            Toast.makeText(getActivity(), "Clicked on: " + updateDetails, Toast.LENGTH_SHORT).show();
        });

        // Update 3 - clickable action
        update3TextView.setOnClickListener(v -> {
            // Handle Update 3 click (you can add the actual event or update ID here)
            String updateDetails = update3TextView.getText().toString();
            Toast.makeText(getActivity(), "Clicked on: " + updateDetails, Toast.LENGTH_SHORT).show();
        });

        // Update 4 - clickable action
        update4TextView.setOnClickListener(v -> {
            // Handle Update 4 click (if you have a 4th update section in your XML)
            String updateDetails = update4TextView.getText().toString();
            Toast.makeText(getActivity(), "Clicked on: " + updateDetails, Toast.LENGTH_SHORT).show();
        });

        // Update 5 - clickable action
        update5TextView.setOnClickListener(v -> {
            // Handle Update 5 click (if you have a 5th update section in your XML)
            String updateDetails = update5TextView.getText().toString();
            Toast.makeText(getActivity(), "Clicked on: " + updateDetails, Toast.LENGTH_SHORT).show();
        });

        // Handle user profile image click
        userImageView.setOnClickListener(v -> {
            // You can open the profile page or show a toast here
            Toast.makeText(getActivity(), "User Profile Image Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Additional setup if necessary after the view is created
    }
}
