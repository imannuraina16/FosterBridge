package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrphanageUpdatePov2 extends Fragment {

    private RecyclerView recyclerView;
    private UpdatesAdapter2 updatesAdapter;
    private List<Update> updatesList;
    private FirebaseFirestore firestore;
    private TextView usernameTextView, nameTextView;

    private String orphanageUsername;
    private String orphanageName;

    public OrphanageUpdatePov2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_orphanage_update_pov2, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView4);
        usernameTextView = view.findViewById(R.id.username2);
        nameTextView = view.findViewById(R.id.nameTV2);

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
        updatesList = new ArrayList<>();
        updatesAdapter = new UpdatesAdapter2(updatesList);
        recyclerView.setAdapter(updatesAdapter);

        // Fetch orphanage updates from Firestore
        fetchUpdatesFromFirestore();

        return view;
    }

    private void fetchUpdatesFromFirestore() {
        firestore.collection("orphanage_updates")
                .whereEqualTo("username", orphanageUsername) // Query for updates by orphanage username
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    updatesList.clear();
                    if (querySnapshot.isEmpty()) {
                        // Show a toast if no updates are found
                        Toast.makeText(getContext(), "No updates found for this orphanage.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Add updates to the list
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            Update update = document.toObject(Update.class);
                            updatesList.add(update);
                        }
                        updatesAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    Toast.makeText(getContext(), "Error fetching updates.", Toast.LENGTH_SHORT).show();
                });
    }
}
