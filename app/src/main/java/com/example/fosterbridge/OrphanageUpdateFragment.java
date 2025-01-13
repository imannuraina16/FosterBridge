package com.example.fosterbridge;
package com.example.orphanageupdates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class OrphanageUpdateFragment extends Fragment {

    // Declare all the necessary UI components
    private ShapeableImageView profileImageView;
    private TextInputLayout textInputLayout;
    private TextInputEditText updateInputEditText;
    private Button postButton;
    private TextView orphanageNameTextView, usernameTextView, editProfileTextView;
    private LinearLayout updateContainer1, updateContainer2, updateContainer3, updateContainer4;

    // Required empty public constructor
    public OrphanageUpdateFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_orphanage_update, container, false);

        // Initialize views
        profileImageView = rootView.findViewById(R.id.imageView5);
        textInputLayout = rootView.findViewById(R.id.textInputFrame);
        updateInputEditText = rootView.findViewById(R.id.textInputEditText);
        postButton = rootView.findViewById(R.id.button);
        orphanageNameTextView = rootView.findViewById(R.id.textView);
        usernameTextView = rootView.findViewById(R.id.textView2);
        editProfileTextView = rootView.findViewById(R.id.textView8);
        updateContainer1 = rootView.findViewById(R.id.grey_box_1);
        updateContainer2 = rootView.findViewById(R.id.grey_box_2);
        updateContainer3 = rootView.findViewById(R.id.grey_box_3);
        updateContainer4 = rootView.findViewById(R.id.grey_box_4);

        // Set dynamic content (Optional)
        orphanageNameTextView.setText("Orphanage XYZ");
        usernameTextView.setText("John Doe");

        // Set up listeners for various actions
        setUpListeners();

        return rootView;
    }

    private void setUpListeners() {
        // Handle profile image click
        profileImageView.setOnClickListener(v -> {
            // Handle profile image click, e.g., open profile screen or show Toast
            Toast.makeText(getActivity(), "Profile Image Clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle "Post" button click
        postButton.setOnClickListener(v -> {
            String updateText = updateInputEditText.getText().toString();
            if (!updateText.isEmpty()) {
                // Handle posting of update (e.g., save to database or server)
                Toast.makeText(getActivity(), "Update Posted: " + updateText, Toast.LENGTH_SHORT).show();
                updateInputEditText.setText(""); // Clear the input field after posting
            } else {
                Toast.makeText(getActivity(), "Please write an update before posting.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle "Edit Profile" click
        editProfileTextView.setOnClickListener(v -> {
            // Handle edit profile click (navigate to profile edit screen or show a dialog)
            Toast.makeText(getActivity(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle "Delete" link click in the update container
        TextView deleteLink = rootView.findViewById(R.id.clickableTextView);
        deleteLink.setOnClickListener(v -> {
            // Handle update deletion
            Toast.makeText(getActivity(), "Delete Update Clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle clicks in the update containers (for future updates)
        updateContainer1.setOnClickListener(v -> {
            // Handle Update 1 click
            Toast.makeText(getActivity(), "Clicked on Update 1", Toast.LENGTH_SHORT).show();
        });

        updateContainer2.setOnClickListener(v -> {
            // Handle Update 2 click
            Toast.makeText(getActivity(), "Clicked on Update 2", Toast.LENGTH_SHORT).show();
        });

        updateContainer3.setOnClickListener(v -> {
            // Handle Update 3 click
            Toast.makeText(getActivity(), "Clicked on Update 3", Toast.LENGTH_SHORT).show();
        });

        updateContainer4.setOnClickListener(v -> {
            // Handle Update 4 click
            Toast.makeText(getActivity(), "Clicked on Update 4", Toast.LENGTH_SHORT).show();
        });
    }
}
