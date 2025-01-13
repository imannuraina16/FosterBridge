package com.example.fosterbridge;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class VolunteerFragment extends Fragment {
    private RecyclerView recyclerView2;
    private FirebaseFirestore db;
    private EventAdapter eventAdapter;
    private ArrayList<Event> eventArrayList;
    private TextView title;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);

        // Initialize RecyclerView
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize Firestore and ArrayList
        db = FirebaseFirestore.getInstance();
        eventArrayList = new ArrayList<>();

        // Initialize Adapter with Button Click Listener
        eventAdapter = new EventAdapter(requireContext(), eventArrayList, this::goToVolunteeringSignUp);
        recyclerView2.setAdapter(eventAdapter);

        // Load data from Firestore
        dataInitialize();

        return view;
    }

    private void dataInitialize() {
        db.collection("events").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore error", error.getMessage());
                return;
            }

            if (value != null) {
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        Event event = dc.getDocument().toObject(Event.class);

                        // Fetch orphanage name using orphanage_id
                        fetchOrphanageDetails(event);
                    }
                }
            }
        });
    }

    private void fetchOrphanageDetails(Event event) {
        db.collection("orphanage").document(event.getUsername()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Set orphanage name in the event object
                        event.setOrphanage_name(documentSnapshot.getString("name"));

                        // Add to the list and notify adapter
                        eventArrayList.add(event);
                        eventAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("Firestore", "Orphanage not found for ID: " + event.getUsername());
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Failed to fetch orphanage details: " + e.getMessage()));
    }

    private void goToVolunteeringSignUp(Event event) {
        // Update title (if it exists)
        if (getActivity() != null) {
            title = getActivity().findViewById(R.id.title);
            if (title != null) {
                title.setText("Volunteer");
            }
        }

        // Navigate to VolunteeringSignUp Fragment
        VolunteeringSignUp volunteeringSignUp = VolunteeringSignUp.newInstance(
                event.getEvent_name(),
                event.getEvent_description(),
                event.getDate(),
                event.getEvent_id()// Pass the event_id
        );

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, volunteeringSignUp);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}