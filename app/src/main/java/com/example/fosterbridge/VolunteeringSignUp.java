package com.example.fosterbridge;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

public class VolunteeringSignUp extends Fragment {
    private static final String ARG_EVENT_NAME = "event_name";
    private static final String ARG_ORPHANAGE_ID = "orphanage_id";
    private static final String ARG_EVENT_DESCRIPTION = "event_description";
    private static final String ARG_DATE = "date";  // Added date argument

    private String eventName;
    private String orphanageId;
    private String eventDescription;
    private String eventDate;  // Added date field

    private FirebaseFirestore db;

    public VolunteeringSignUp() {
        // Required empty constructor
    }

    public static VolunteeringSignUp newInstance(String eventName, String orphanageId, String eventDescription, String eventDate) {
        VolunteeringSignUp fragment = new VolunteeringSignUp();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_NAME, eventName);
        args.putString(ARG_ORPHANAGE_ID, orphanageId);
        args.putString(ARG_EVENT_DESCRIPTION, eventDescription);
        args.putString(ARG_DATE, eventDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventName = getArguments().getString(ARG_EVENT_NAME);
            orphanageId = getArguments().getString(ARG_ORPHANAGE_ID);
            eventDescription = getArguments().getString(ARG_EVENT_DESCRIPTION);
            eventDate = getArguments().getString(ARG_DATE);  // Fetch date from arguments
        }

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteering_sign_up, container, false);
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showUpButton(); // Show up button in HomeFragment
        }
        // Example: Display event details
        TextView eventNameTextView = view.findViewById(R.id.event_name);
        TextView orphanageNameTextView = view.findViewById(R.id.orphanage_name);
        TextView eventDescriptionTextView = view.findViewById(R.id.event_description);
        TextView eventDateTextView = view.findViewById(R.id.date);

        eventNameTextView.setText(eventName);
        eventDescriptionTextView.setText(eventDescription);
        eventDateTextView.setText(eventDate);

        // Fetch orphanage name using orphanageId
        fetchOrphanageName(orphanageId, orphanageNameTextView);

        return view;
    }

    private void fetchOrphanageName(String orphanageId, TextView orphanageNameTextView) {
        db.collection("orphanage").document(orphanageId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String orphanageName = documentSnapshot.getString("name");
                        orphanageNameTextView.setText(orphanageName);
                    } else {
                        orphanageNameTextView.setText("Orphanage not found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore Error", "Error fetching orphanage name: " + e.getMessage());
                    orphanageNameTextView.setText("Error loading orphanage");
                });
    }
}
