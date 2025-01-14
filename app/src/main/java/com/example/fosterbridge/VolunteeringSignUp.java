package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class VolunteeringSignUp extends Fragment {
    private static final String ARG_EVENT_NAME = "event_name";
    private static final String ARG_EVENT_DESCRIPTION = "event_description";
    private static final String ARG_DATE = "date";
    private static final String ARG_EVENT_ID = "event_id";

    private String eventId;
    private String eventName;
    private String eventDescription;
    private String eventDate;

    private FirebaseFirestore db;

    public VolunteeringSignUp() {
        // Required empty constructor
    }

    public static VolunteeringSignUp newInstance(String eventName, String eventDescription, String eventDate, String eventId) {
        VolunteeringSignUp fragment = new VolunteeringSignUp();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_NAME, eventName);
        args.putString(ARG_EVENT_DESCRIPTION, eventDescription);
        args.putString(ARG_EVENT_ID, eventId);
        args.putString(ARG_DATE, eventDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventName = getArguments().getString(ARG_EVENT_NAME);
            eventDescription = getArguments().getString(ARG_EVENT_DESCRIPTION);
            eventDate = getArguments().getString(ARG_DATE);
            eventId = getArguments().getString(ARG_EVENT_ID);
        }

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteering_sign_up, container, false);

        TextView eventNameTextView = view.findViewById(R.id.event_name);
        TextView eventDescriptionTextView = view.findViewById(R.id.event_description);
        TextView eventDateTextView = view.findViewById(R.id.date);
        Button volunteerSignUpButton = view.findViewById(R.id.button_signup);

        eventNameTextView.setText(eventName);
        eventDescriptionTextView.setText(eventDescription);
        eventDateTextView.setText(eventDate);

        // Handle volunteer sign-up button click
        volunteerSignUpButton.setOnClickListener(v -> storeVolunteerData());

        return view;
    }

    private void storeVolunteerData() {
        // Retrieve username from shared preferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "No username");

        if ("No username".equals(username)) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the user has already signed up for the event
        db.collection("volunteers")
                .whereEqualTo("username", username)
                .whereEqualTo("event_id", eventId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // User has already signed up for the event
                        Toast.makeText(getContext(), "You have signed up for this event!", Toast.LENGTH_SHORT).show();
                    } else {
                        // User has not signed up for the event, proceed with storing the data
                        // Get the current volunteer count
                        db.collection("volunteers")
                                .get()
                                .addOnCompleteListener(volunteerCountTask -> {
                                    if (volunteerCountTask.isSuccessful()) {
                                        // Get the number of documents in the "volunteers" collection
                                        int volunteerCount = volunteerCountTask.getResult().size();

                                        // Generate a new volunteer document name based on the count (e.g., "volunteer1", "volunteer2", ...)
                                        String newVolunteerDocName = "volunteer" + (volunteerCount + 1);

                                        // Create volunteer data and include event_date
                                        Map<String, Object> volunteerData = new HashMap<>();
                                        volunteerData.put("event_id", eventId);
                                        volunteerData.put("username", username);
                                        volunteerData.put("event_date", eventDate);  // Store event_date in the volunteer data

                                        // Add volunteer data to the Firestore "volunteers" collection with the new document name
                                        db.collection("volunteers")
                                                .document(newVolunteerDocName)
                                                .set(volunteerData)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(getContext(), "Successfully signed up for volunteering!", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(getContext(), "Error signing up. Please try again.", Toast.LENGTH_SHORT).show();
                                                });
                                    } else {
                                        // Handle failure in fetching volunteer count
                                        Toast.makeText(getContext(), "Error fetching volunteer count: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.showUpButton();  // Ensure up button is shown
        }
    }
}
