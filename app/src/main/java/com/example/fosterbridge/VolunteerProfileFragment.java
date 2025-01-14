package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class VolunteerProfileFragment extends Fragment {

    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences;
    private String username;
    private EditText eventNameEditText, eventDateEditText, volunteerDescriptionEditText;
    private Button postButton;
    private RecyclerView recyclerView;
    private VolunteerProfileAdapter profileAdapter;
    private List<VolunteerProfile> profileList;
    private TextView usernameTextView;
    private TextView nameTextView;

    public VolunteerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_volunteer_profile, container, false);

        // Initialize Firestore and SharedPreferences
        db = FirebaseFirestore.getInstance();
        sharedPreferences = getActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);

        // Retrieve the username from SharedPreferences
        username = sharedPreferences.getString("username", "Anonymous");

        // Initialize UI elements
        eventNameEditText = rootView.findViewById(R.id.event_name);
        eventDateEditText = rootView.findViewById(R.id.event_date);
        volunteerDescriptionEditText = rootView.findViewById(R.id.volunteer_description);
        postButton = rootView.findViewById(R.id.button_post_wishlist);
        recyclerView = rootView.findViewById(R.id.recyclerView_volunteer);
        usernameTextView = rootView.findViewById(R.id.username_volunteer);
        nameTextView = rootView.findViewById(R.id.name_volunteer);

        // Set up RecyclerView with your existing adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profileAdapter = new VolunteerProfileAdapter(getContext());
        recyclerView.setAdapter(profileAdapter);

        // Initialize the profile list
        profileList = new ArrayList<>();

        // Load events from Firestore
        loadEvents();
        loadUserData();

        // Set up post button click listener
        postButton.setOnClickListener(v -> postEventData());

        return rootView;
    }

    private void postEventData() {
        String eventName = eventNameEditText.getText().toString().trim();
        String eventDate = eventDateEditText.getText().toString().trim();
        String volunteerDescription = volunteerDescriptionEditText.getText().toString().trim();

        if (eventName.isEmpty() || eventDate.isEmpty() || volunteerDescription.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        VolunteerProfile volunteerProfile = new VolunteerProfile(eventName, eventDate, volunteerDescription, username);

        db.collection("events")
                .add(volunteerProfile)
                .addOnSuccessListener(documentReference -> {
                    volunteerProfile.setId(documentReference.getId());  // Set the document ID
                    profileAdapter.addVolunteerProfile(volunteerProfile);
                    clearInputs();
                    Toast.makeText(getContext(), "Event posted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to post event", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearInputs() {
        eventNameEditText.setText("");
        eventDateEditText.setText("");
        volunteerDescriptionEditText.setText("");
    }

    private void loadEvents() {
        db.collection("events")
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        profileList.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            VolunteerProfile profile = document.toObject(VolunteerProfile.class);
                            if (profile != null) {
                                profile.setId(document.getId());  // Set the document ID
                                profileList.add(profile);
                            }
                        }
                        profileAdapter.setVolunteerProfiles(profileList);
                    } else {
                        Toast.makeText(getContext(), "No events found for this user.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to load events.", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadUserData() {
        usernameTextView.setText("@" + username);

        FirebaseFirestore.getInstance().collection("orphanage").document(username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            String name = document.getString("name");
                            nameTextView.setText(name != null ? name : "No name available");
                        } else {
                            nameTextView.setText("Document not found");
                        }
                    } else {
                        nameTextView.setText("Error fetching name");
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.showUpButton();
        }
    }
}
