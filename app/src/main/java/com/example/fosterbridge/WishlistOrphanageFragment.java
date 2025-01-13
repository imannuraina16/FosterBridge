package com.example.fosterbridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

public class WishlistOrphanageFragment extends Fragment {

    // Declare UI components
    private ShapeableImageView profilePic;
    private TextView usernameTV, orphanageTV, orphanageNameTV, wishlistTV, wallpaintTV;
    private EditText writeWishlistML;
    private Button postButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist_orphanage, container, false);

        // Initialize UI components
        profilePic = view.findViewById(R.id.profilePic);
        usernameTV = view.findViewById(R.id.usernameTV);
        orphanageTV = view.findViewById(R.id.orphanageTV);
        orphanageNameTV = view.findViewById(R.id.orphanage_nameTV);
        wishlistTV = view.findViewById(R.id.wishlistTV);
        wallpaintTV = view.findViewById(R.id.wallpaintTV);
        writeWishlistML = view.findViewById(R.id.writeWishlistML);
        postButton = view.findViewById(R.id.postButton);

        // Set initial data for TextViews
        usernameTV.setText("@username");  // Replace with actual username
        orphanageNameTV.setText("orphanage_name");  // Replace with actual orphanage name
        wallpaintTV.setText("Wall paint");

        // Handle "Post" button click
        postButton.setOnClickListener(v -> {
            String wishlistText = writeWishlistML.getText().toString().trim();

            if (!wishlistText.isEmpty()) {
                // You can handle posting the wishlist text to a database or backend server here
                Toast.makeText(getContext(), "Wishlist posted: " + wishlistText, Toast.LENGTH_SHORT).show();
                writeWishlistML.setText("");  // Clear the EditText after posting
            } else {
                Toast.makeText(getContext(), "Please write something before posting!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}