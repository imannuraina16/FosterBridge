package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DonationHistory extends Fragment {
    private static final String TAG = "DonationHistoryFragment";

    private RecyclerView rvDonateHistory;
    private DonationHistoryAdapter adapter;
    private TextView tvTotalDonation;
    private FirebaseFirestore db;
    private String loggedInUsername;

    public DonationHistory() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_donation_history, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get logged-in username from SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);

        if (loggedInUsername == null) {
            Toast.makeText(requireContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
            return rootView;
        }

        // Initialize UI components
        rvDonateHistory = rootView.findViewById(R.id.rvDonateHistory);
        tvTotalDonation = rootView.findViewById(R.id.tvTotalDonation);

        // Set up RecyclerView
        rvDonateHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DonationHistoryAdapter(new ArrayList<>());
        rvDonateHistory.setAdapter(adapter);

        // Load donation history
        loadDonationHistory();

        return rootView;
    }

    private void loadDonationHistory() {
        db.collection("donations")
                .whereEqualTo("username", loggedInUsername)  // Ensure the username matches the logged-in user
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        List<Donation> donationList = new ArrayList<>();
                        double totalDonation = 0;

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Donation donation = new Donation();

                            // Manually map fields from Firestore document to Donation object
                            donation.setId(document.getId()); // Set the Firestore document ID
                            donation.setUsername(document.getString("username"));
                            donation.setAmount(document.getString("amount"));
                            donation.setTimestamp(document.getTimestamp("timestamp"));

                            // Manually set orphanage_username field
                            donation.setOrphanageUsername(document.getString("orphanage_username"));

                            // Safely parse the amount (String) to double for calculations
                            String amountString = donation.getAmount();
                            if (amountString != null && !amountString.isEmpty()) {
                                try {
                                    double amount = Double.parseDouble(amountString);  // Parse the String to double
                                    totalDonation += amount;
                                } catch (NumberFormatException e) {
                                    Log.e(TAG, "Invalid amount format for donation", e);
                                    // Handle the error: You may choose to skip the donation or set it to 0
                                }
                            } else {
                                Log.w(TAG, "Amount is null or empty for donation ID: " + donation.getId());
                            }

                            // Add donation to the list
                            donationList.add(donation);
                        }

                        // Update the RecyclerView and Total Donation TextView
                        adapter.setDonations(donationList);
                        tvTotalDonation.setText(String.format("RM %.2f", totalDonation));
                    } else {
                        Toast.makeText(requireContext(), "No donation history found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching donation history", e);
                    Toast.makeText(requireContext(), "Failed to load donation history.", Toast.LENGTH_SHORT).show();
                });
    }

}
