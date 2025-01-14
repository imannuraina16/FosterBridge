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
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.model.PaymentMethodCreateParams;

import java.util.HashMap;
import java.util.Map;

public class Payment extends Fragment {
    private TextView donation_amount;
    private CardInputWidget cardInputWidget;
    private Button confirm_button;
    private FirebaseFirestore db;  // Firebase Firestore reference
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        donation_amount = view.findViewById(R.id.donation_amount);
        cardInputWidget = view.findViewById(R.id.card_input_widget);
        confirm_button = view.findViewById(R.id.button_confirm_donate);
        title = getActivity().findViewById(R.id.title);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get donation amount passed from the previous fragment
        Bundle args = getArguments();
        String orphanageUsername = null;
        if (args != null) {
            String donationAmount = args.getString("donation_amount");
            orphanageUsername = args.getString("orphanage_username");
            if (donationAmount != null) {
                donation_amount.setText("RM" + donationAmount);
            }
        }

        String finalOrphanageUsername = orphanageUsername; // For use in inner classes
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentMethodCreateParams cardParams = cardInputWidget.getPaymentMethodCreateParams();

                if (cardParams != null) {
                    Toast.makeText(getContext(), "Payment Success", Toast.LENGTH_SHORT).show();
                    updateDonationInFirestore(finalOrphanageUsername);
                } else {
                    Toast.makeText(getContext(), "Invalid card details", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return view;
    }

    private void updateDonationInFirestore(String orphanageUsername) {
        // Get current user ID (You can retrieve it from FirebaseAuth or your session data)
        SharedPreferences prefs = getActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "No username");

        // You would also have the orphanage ID (this can come from the arguments or wherever it's available)
        String orphanageId = "sample_orphanage_id"; // Replace with the actual orphanage ID

        // Get the donation amount (assuming the amount is a string; if you are using integers or floats, you may need to adjust this)
        String donationAmount = donation_amount.getText().toString().replace("RM", "");

        // Create a donation data object
        Map<String, Object> donation = new HashMap<>();
        donation.put("amount", donationAmount);
        donation.put("orphanage_username", orphanageUsername);
        donation.put("username", username);
        donation.put("timestamp", com.google.firebase.Timestamp.now()); // Store the timestamp of the donation

        // Get the current donation count (using a query)
        db.collection("donations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get the number of documents in the "donations" collection
                        int donationCount = task.getResult().size();

                        // Generate the new document name (donations1, donations2, ...)
                        String newDonationDocName = "donations" + (donationCount + 1);

                        // Add the donation data to the Firestore "donations" collection with the new name
                        db.collection("donations")
                                .document(newDonationDocName)
                                .set(donation)
                                .addOnSuccessListener(aVoid -> {
                                    // Success: Donation added to Firestore
                                    Toast.makeText(getContext(), "Donation saved successfully", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    // Failure: Error while saving donation
                                    Toast.makeText(getContext(), "Error saving donation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // Handle failure in fetching the donation count
                        Toast.makeText(getContext(), "Error fetching donation count: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    navigateToDonationHistory();
                });

    }

    private void navigateToDonationHistory(){
        DonationHistory donationHistory = new DonationHistory();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, donationHistory);
        transaction.addToBackStack(null);
        title.setText("Donation History");
        transaction.commit();
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
