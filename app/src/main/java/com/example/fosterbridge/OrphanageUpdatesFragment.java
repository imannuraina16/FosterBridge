package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class OrphanageUpdatesFragment extends Fragment {
    private FirebaseFirestore db;
    private CollectionReference updatesCollection;
    private EditText updateDescriptionEditText;
    private Button postButton;
    private RecyclerView recyclerView;
    private UpdatesAdapter updatesAdapter;
    private List<Update> updatesList;
    private TextView usernameTextView;
    private TextView nameTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_orphanage_updates, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        updatesCollection = db.collection("orphanage_updates");

        // Set up UI components
        updateDescriptionEditText = view.findViewById(R.id.update_description);
        postButton = view.findViewById(R.id.button_post);
        recyclerView = view.findViewById(R.id.recyclerView4);
        usernameTextView = view.findViewById(R.id.username2);  // Add username display
        nameTextView = view.findViewById(R.id.nameTV2);  // Add name display
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the adapter for RecyclerView
        updatesAdapter = new UpdatesAdapter();
        recyclerView.setAdapter(updatesAdapter);

        // Initialize the updates list
        updatesList = new ArrayList<>();

        // Post button click listener
        postButton.setOnClickListener(v -> postUpdate());

        // Retrieve the username and name from Firestore and SharedPreferences
        loadUserData();

        // Return the view
        return view;
    }

    // Method to load user data (username and name) from SharedPreferences and Firestore
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

    // Method to post the update
    private void postUpdate() {
        String description = updateDescriptionEditText.getText().toString().trim();

        if (description.isEmpty()) {
            Toast.makeText(getContext(), "Please enter an update description.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Anonymous");  // Default to "Anonymous" if no username is found

        // Create a new update with the description and username
        Map<String, Object> update = new HashMap<>();
        update.put("description", description);
        update.put("username", username);  // Store the username along with the update

        // Save the update to Firestore
        updatesCollection.add(update)
                .addOnSuccessListener(documentReference -> {
                    // Create a new Update object for the posted update
                    Update newUpdate = new Update(description);
                    newUpdate.setId(documentReference.getId());  // Set the document ID manually
                    newUpdate.setUsername(username);  // Set the username for the update

                    // Add the new update to the list at the end
                    updatesAdapter.addUpdate(newUpdate);

                    // Show success message and clear the input field
                    Toast.makeText(getContext(), "Update posted successfully.", Toast.LENGTH_SHORT).show();
                    updateDescriptionEditText.setText(""); // Clear the input field
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to post update. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadUpdates();
    }

    private void loadUpdates() {
        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("username", "Anonymous");

        // Filter the query to only get updates that belong to the logged-in user
        updatesCollection.whereEqualTo("username", loggedInUsername)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<Update> updatesList = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            // Create an Update object and set the document ID
                            Update update = document.toObject(Update.class);
                            if (update != null) {
                                update.setId(document.getId());  // Set the document ID manually
                            }
                            updatesList.add(update);
                        }
                        updatesAdapter.setUpdatesList(updatesList);
                    } else {
                        Toast.makeText(getContext(), "No updates found for this user.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to load updates. Please try again.", Toast.LENGTH_SHORT).show();
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
