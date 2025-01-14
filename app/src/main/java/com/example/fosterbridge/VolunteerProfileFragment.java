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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VolunteerProfileFragment extends Fragment {

    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences;
    private String username;
    private EditText eventNameEditText, eventDateEditText, volunteerDescriptionEditText;
    private Button postButton;
    private RecyclerView recyclerView;
    private VolunteerProfileAdapter profileAdapter;  // Using your existing adapter
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
        username = sharedPreferences.getString("username", "Anonymous"); // Default to "Anonymous" if no username is stored

        // Initialize UI elements
        eventNameEditText = rootView.findViewById(R.id.event_name);
        eventDateEditText = rootView.findViewById(R.id.event_date);
        volunteerDescriptionEditText = rootView.findViewById(R.id.volunteer_description);
        postButton = rootView.findViewById(R.id.button_post_wishlist);
        recyclerView = rootView.findViewById(R.id.recyclerView_volunteer);
        usernameTextView = rootView.findViewById(R.id.username_volunteer);  // Add username display
        nameTextView = rootView.findViewById(R.id.name_volunteer);

        // Set up RecyclerView with your existing adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profileAdapter = new VolunteerProfileAdapter(getContext());  // Initialize the adapter
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

    // Method to post event data to Firestore
    private void postEventData() {
        // Get the input values from the EditTexts
        String eventName = eventNameEditText.getText().toString().trim();
        String eventDate = eventDateEditText.getText().toString().trim();
        String volunteerDescription = volunteerDescriptionEditText.getText().toString().trim();

        // Validate inputs
        if (eventName.isEmpty() || eventDate.isEmpty() || volunteerDescription.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a volunteer profile object
        VolunteerProfile volunteerProfile = new VolunteerProfile(eventName, eventDate, volunteerDescription, username);

        // Store the event data in Firestore under the "events" collection
        db.collection("events")
                .add(volunteerProfile) // Automatically generate a unique document ID
                .addOnSuccessListener(documentReference -> {
                    // Show success message
                    Toast.makeText(getContext(), "Event posted successfully", Toast.LENGTH_SHORT).show();
                    loadEvents(); // Reload the events list
                    clearInputs(); // Clear the input fields after posting
                })
                .addOnFailureListener(e -> {
                    // Show error message
                    Toast.makeText(getContext(), "Failed to post event", Toast.LENGTH_SHORT).show();
                });
    }

    // Helper method to clear input fields after posting
    private void clearInputs() {
        eventNameEditText.setText("");
        eventDateEditText.setText("");
        volunteerDescriptionEditText.setText("");
    }

    // Method to load events from Firestore
    private void loadEvents() {
        db.collection("events")
                .whereEqualTo("username", username) // Fetch events only for the current user
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        profileList.clear(); // Clear the existing list
                        profileList.addAll(queryDocumentSnapshots.toObjects(VolunteerProfile.class));
                        profileAdapter.setVolunteerProfiles(profileList);  // Update the adapter's data
                    } else {
                        Toast.makeText(getContext(), "No events found for this user.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to load events.", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadUserData() {
        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Anonymous");

        // Set the username in the TextView
        usernameTextView.setText("@" + username);

        // Fetch name from Firestore
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
            mainActivity.showUpButton();  // Ensure up button is shown
        }
    }
}
