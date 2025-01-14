package com.example.fosterbridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileOrphanage extends Fragment {

    private TextView usernameTextView;
    private TextView userTypeTextView;
    private TextView nameTextView;
    private Button editProfileButton;
    private Button updateButton;
    private Button wishlistButton;
    private Button volunteerButton;
    TextView title;

    private FirebaseFirestore firestore;

    public ProfileOrphanage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_orphanage, container, false);

        // Initialize TextViews and Buttons
        usernameTextView = view.findViewById(R.id.username);
        nameTextView = view.findViewById(R.id.nameTV);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        updateButton = view.findViewById(R.id.UpdateButton);
        wishlistButton = view.findViewById(R.id.WishlistButton);
        volunteerButton = view.findViewById(R.id.VolunteerButton);
        title = getActivity().findViewById(R.id.title);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Retrieve SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserSessionPrefs", Context.MODE_PRIVATE);

        // Get username and userType from SharedPreferences
        String username = sharedPreferences.getString("username", "No username");
        String userType = sharedPreferences.getString("userType", "Orphanage");

        // Set the username and userType into the respective TextViews
        usernameTextView.setText("@" + username);

        // Fetch "name" field from Firestore and set it to nameTextView
        firestore.collection("orphanage").document(username).get().addOnCompleteListener(task -> {
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

        // Set onClickListeners for buttons
        editProfileButton.setOnClickListener(v -> navigateToEditProfile());
        updateButton.setOnClickListener(v -> navigateToOrphanageUpdates());
        wishlistButton.setOnClickListener(v -> navigateToWishlist());
        volunteerButton.setOnClickListener(v -> navigateToVolunteer());

        return view;
    }

    private void navigateToEditProfile() {
        EditProfileOrphanage editProfileOrphanage = new EditProfileOrphanage();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, editProfileOrphanage);
        transaction.addToBackStack(null);
        title.setText("Edit Profile");
        transaction.commit();
    }

    private void navigateToOrphanageUpdates() {
        OrphanageUpdatesFragment orphanageUpdatesFragment = new OrphanageUpdatesFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, orphanageUpdatesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToWishlist() {
        WishlistFragment wishlistFragment = new WishlistFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, wishlistFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToVolunteer() {
        VolunteerProfileFragment volunteerFragment = new VolunteerProfileFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, volunteerFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.hideUpButton();  // Ensure up button is shown
        }
    }
}
