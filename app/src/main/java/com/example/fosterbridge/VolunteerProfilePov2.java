package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class VolunteerProfilePov2 extends Fragment {

    private RecyclerView recyclerView;
    private VolunteerProfilePov2Adapter adapter;
    private List<VolunteerProfile> volunteerProfileList;
    private FirebaseFirestore firestore;
    private TextView usernameTextView, nameTextView;

    private String orphanageUsername;
    private String orphanageName;


    public VolunteerProfilePov2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_profile_pov2, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView_volunteer);
        usernameTextView = view.findViewById(R.id.username_volunteer);
        nameTextView = view.findViewById(R.id.name_volunteer);


        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Retrieve arguments passed from previous fragment
        if (getArguments() != null) {
            orphanageUsername = getArguments().getString("orphanage_username", "Default Username");
            orphanageName = getArguments().getString("name", "Default Name");
        }

        // Set username and name
        usernameTextView.setText("@" + orphanageUsername);
        nameTextView.setText(orphanageName);

        // Initialize RecyclerView and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        volunteerProfileList = new ArrayList<>();
        adapter = new VolunteerProfilePov2Adapter(volunteerProfileList);
        recyclerView.setAdapter(adapter);

        // Fetch events from Firestore
        fetchEventsFromFirestore();

        return view;
    }

    private void fetchEventsFromFirestore() {
        firestore.collection("events")
                .whereEqualTo("username", orphanageUsername) // Query for events by orphanage username
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    volunteerProfileList.clear();
                    if (querySnapshot.isEmpty()) {
                        // Show a toast if no events are found
                        Toast.makeText(getContext(), "No events found for this orphanage.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Add profiles to the list
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            VolunteerProfile profile = document.toObject(VolunteerProfile.class);
                            volunteerProfileList.add(profile);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    Toast.makeText(getContext(), "Error fetching events.", Toast.LENGTH_SHORT).show();
                });
    }

}
