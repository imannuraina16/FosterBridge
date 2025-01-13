package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fosterbridge.OrphanageUpdatePov2;
import com.example.fosterbridge.VolunteerProfilePov2;
import com.example.fosterbridge.WishlistPov2;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileOrphanagePov2 extends Fragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_LOCATION = "location";
    private static final String ARG_ORPHANAGE_USERNAME = "orphanage_username";

    private TextView usernameTextView;
    private TextView userTypeTextView;
    private TextView nameTextView;
    private TextView contactEmailTextView;
    private TextView contactPhoneTextView;
    private Button updateButton;
    private Button wishlistButton;
    private Button volunteerButton;
    TextView title;

    private FirebaseFirestore firestore;

    private String orphanageName;
    private String orphanageLocation;
    private String orphanageUsername;

    public ProfileOrphanagePov2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_orphanage_pov2, container, false);

        // Initialize TextViews and Buttons
        usernameTextView = view.findViewById(R.id.username);
        nameTextView = view.findViewById(R.id.nameTV);
        contactEmailTextView = view.findViewById(R.id.contactEmail);
        contactPhoneTextView = view.findViewById(R.id.contactPhone);
        updateButton = view.findViewById(R.id.UpdateButton);
        wishlistButton = view.findViewById(R.id.WishlistButton);
        volunteerButton = view.findViewById(R.id.VolunteerButton);
        title = getActivity().findViewById(R.id.title);

        // Retrieve arguments passed from previous fragment
        if (getArguments() != null) {
            orphanageName = getArguments().getString(ARG_NAME, "Default Name");
            orphanageLocation = getArguments().getString(ARG_LOCATION, "Default Location");
            orphanageUsername = getArguments().getString(ARG_ORPHANAGE_USERNAME, "Default Username");
        }

        // Set the username and orphanageName into the respective TextViews
        usernameTextView.setText("@" + orphanageUsername);  // Use orphanageUsername if needed
        nameTextView.setText(orphanageName);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Fetch contact details from Firestore based on the orphanage username field
        fetchContactDetailsFromFirestore();

        // Set onClickListeners for buttons
        updateButton.setOnClickListener(v -> navigateToOrphanageUpdates());
        wishlistButton.setOnClickListener(v -> navigateToWishlist());
        volunteerButton.setOnClickListener(v -> navigateToVolunteer());

        return view;
    }

    private void fetchContactDetailsFromFirestore() {
        // Query Firestore for orphanage based on username field
        firestore.collection("orphanage")
                .whereEqualTo("username", orphanageUsername)  // Searching for orphanage by username field
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Assuming the query result contains only one document
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        String email = documentSnapshot.getString("email");
                        String phoneNumber = documentSnapshot.getString("contact_info");

                        // Set the contact info to TextViews
                        contactEmailTextView.setText("Email: " + email);
                        contactPhoneTextView.setText("Tel: " + phoneNumber);
                    } else {
                        // Handle case where the orphanage is not found
                        contactEmailTextView.setText("Email: Not available");
                        contactPhoneTextView.setText("Tel: Not available");
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure (e.g., show a message or log the error)
                    contactEmailTextView.setText("Email: Not available");
                    contactPhoneTextView.setText("Tel: Not available");
                });
    }

    private void navigateToOrphanageUpdates() {
        OrphanageUpdatePov2 ou = new OrphanageUpdatePov2();

        // Create a Bundle to pass data
        Bundle bundle = new Bundle();
        bundle.putString("name", orphanageName);  // Pass orphanage name
        bundle.putString("location", orphanageLocation);  // Pass orphanage location
        bundle.putString("orphanage_username", orphanageUsername);  // Pass orphanage username

        // Set the arguments for the fragment
        ou.setArguments(bundle);

        // Replace the fragment and add it to the back stack
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ou);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToWishlist() {
        WishlistPov2 wishlistPov2 = new WishlistPov2();

        // Create a Bundle to pass data
        Bundle bundle = new Bundle();
        bundle.putString("name", orphanageName);  // Pass orphanage name
        bundle.putString("location", orphanageLocation);  // Pass orphanage location
        bundle.putString("orphanage_username", orphanageUsername);  // Pass orphanage username

        // Set the arguments for the fragment
        wishlistPov2.setArguments(bundle);

        // Replace the fragment and add it to the back stack
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, wishlistPov2);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToVolunteer() {
        // Create a new instance of the VolunteerProfilePov2 fragment
        VolunteerProfilePov2 volunteerProfilePov2 = new VolunteerProfilePov2();

        // Create a Bundle to pass data
        Bundle bundle = new Bundle();
        bundle.putString("name", orphanageName);  // Pass orphanage name
        bundle.putString("location", orphanageLocation);  // Pass orphanage location
        bundle.putString("orphanage_username", orphanageUsername);  // Pass orphanage username

        // Set the arguments for the fragment
        volunteerProfilePov2.setArguments(bundle);

        // Replace the fragment and add it to the back stack
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, volunteerProfilePov2);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
