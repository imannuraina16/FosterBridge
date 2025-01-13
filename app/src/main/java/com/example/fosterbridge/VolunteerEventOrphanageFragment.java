package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class VolunteerOrphanageFragment extends Fragment {

    private TextInputEditText eventDetailsEditText;
    private Button postButton;
    private TextView orphanageNameTextView, usernameTextView;
    private TextView textViewDeleteEvent;
    private TextView volunteerTextView;

    // Required empty public constructor
    public VolunteerOrphanageFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_volunteer_event_orphanage, container, false);

        // Initialize the views
        eventDetailsEditText = rootView.findViewById(R.id.textInputEditText);
        postButton = rootView.findViewById(R.id.button);
        orphanageNameTextView = rootView.findViewById(R.id.textView);
        usernameTextView = rootView.findViewById(R.id.textView2);
        textViewDeleteEvent = rootView.findViewById(R.id.clickableTextView);
        volunteerTextView = rootView.findViewById(R.id.textView4);

        // Setting default text (Optional)
        orphanageNameTextView.setText("Orphanage XYZ");
        usernameTextView.setText("John Doe");

        // Set up a listener for the "Post" button
        postButton.setOnClickListener(v -> {
            String eventDetails = eventDetailsEditText.getText().toString();
            if (!eventDetails.isEmpty()) {
                // In this example, just show a Toast message
                Toast.makeText(getActivity(), "Event Posted: " + eventDetails, Toast.LENGTH_SHORT).show();
            } else {
                // Show an error if the input is empty
                Toast.makeText(getActivity(), "Please enter event details.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up listener for "Delete" action on the event
        textViewDeleteEvent.setOnClickListener(v -> {
            // Here you can implement the action to delete the event
            Toast.makeText(getActivity(), "Event Deleted", Toast.LENGTH_SHORT).show();
            // Optionally, hide the event layout or remove it
            // e.g., greyBox1.setVisibility(View.GONE);
        });

        // Set up listener for the volunteer text
        volunteerTextView.setOnClickListener(v -> {
            // Handle the volunteer section (if necessary)
            Toast.makeText(getActivity(), "Volunteering Section", Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Additional setup if necessary after the view is created
    }
}
