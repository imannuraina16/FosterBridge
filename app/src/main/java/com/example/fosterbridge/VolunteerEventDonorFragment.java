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

public class VolunteerEventOrphanageFragment extends Fragment {

    private TextView orphanageNameTextView, usernameTextView;
    private TextView event1TextView, event2TextView, event3TextView, event4TextView;
    private TextView applyEvent1TextView, applyEvent2TextView, applyEvent3TextView, applyEvent4TextView;
    private ShapeableImageView userImageView;

    // Required empty public constructor
    public VolunteerEventOrphanageFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_volunteer_event_orphanage, container, false);

        // Initialize views
        orphanageNameTextView = rootView.findViewById(R.id.textView);
        usernameTextView = rootView.findViewById(R.id.textView2);
        event1TextView = rootView.findViewById(R.id.textView5);
        event2TextView = rootView.findViewById(R.id.textView6);
        event3TextView = rootView.findViewById(R.id.textView7);  // Update with your actual IDs
        event4TextView = rootView.findViewById(R.id.textView8);  // Update with your actual IDs
        applyEvent1TextView = rootView.findViewById(R.id.clickableTextView1);
        applyEvent2TextView = rootView.findViewById(R.id.clickableTextView2);
        applyEvent3TextView = rootView.findViewById(R.id.clickableTextView3);
        applyEvent4TextView = rootView.findViewById(R.id.clickableTextView4);
        userImageView = rootView.findViewById(R.id.imageView2);

        // Set the orphanage name and username dynamically (Optional)
        orphanageNameTextView.setText("Orphanage XYZ");
        usernameTextView.setText("John Doe");

        // Set up event application listeners
        setupEventListeners();

        return rootView;
    }

    private void setupEventListeners() {
        // Event 1 - Apply button
        applyEvent1TextView.setOnClickListener(v -> {
            // Handle Apply Event 1
            String eventDetails = event1TextView.getText().toString();
            Toast.makeText(getActivity(), "Applied to: " + eventDetails, Toast.LENGTH_SHORT).show();
        });

        // Event 2 - Apply button
        applyEvent2TextView.setOnClickListener(v -> {
            // Handle Apply Event 2
            String eventDetails = event2TextView.getText().toString();
            Toast.makeText(getActivity(), "Applied to: " + eventDetails, Toast.LENGTH_SHORT).show();
        });

        // Event 3 - Apply button
        applyEvent3TextView.setOnClickListener(v -> {
            // Handle Apply Event 3 (you can add this event if needed in the XML)
            String eventDetails = event3TextView.getText().toString();
            Toast.makeText(getActivity(), "Applied to: " + eventDetails, Toast.LENGTH_SHORT).show();
        });

        // Event 4 - Apply button
        applyEvent4TextView.setOnClickListener(v -> {
            // Handle Apply Event 4 (you can add this event if needed in the XML)
            String eventDetails = event4TextView.getText().toString();
            Toast.makeText(getActivity(), "Applied to: " + eventDetails, Toast.LENGTH_SHORT).show();
        });

        // If you want to change the user image, you can set an onClickListener here for the image view
        userImageView.setOnClickListener(v -> {
            // Handle image click, for example, show a Toast or navigate to another screen
            Toast.makeText(getActivity(), "User Profile Image Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Additional setup if necessary after the view is created
    }
}
