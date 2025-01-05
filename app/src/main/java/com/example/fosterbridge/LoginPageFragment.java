package com.example.fosterbridge;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {
    private EditText inputUsername, inputPassword;
    private Button loginButton, registerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);

        // Initialize UI components
        inputUsername = view.findViewById(R.id.inputUsername);
        inputPassword = view.findViewById(R.id.inputPassword);
        loginButton = view.findViewById(R.id.selectLoginButton);
        registerButton = view.findViewById(R.id.selectRegister);

        // Set button click listeners
        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> navigateToRegistrationFragment());

        return view;
    }

    private void loginUser() {
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(username)) {
            inputUsername.setError("Username is required!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Password is required!");
            return;
        }

        // Simulate login check
        if (validateCredentials(username, password)) {
            Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
            // Add logic to navigate to the main/home page
        } else {
            Toast.makeText(getContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateCredentials(String username, String password) {
        // Replace this with actual validation logic (e.g., API call or database query)
        return username.equals("testuser") && password.equals("password123");
    }

    private void navigateToRegistrationFragment() {
        // Navigate to Registration Fragment
        Fragment registrationFragment = new AccRegistrationFragment(); // Replace with the actual Registration Fragment class
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, registrationFragment); // Replace 'fragment_container' with your container ID
        transaction.addToBackStack(null); // Optional: Add to back stack for navigation
        transaction.commit();
    }
}


//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link LoginPageFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class LoginPageFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public LoginPageFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment LoginPageFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static LoginPageFragment newInstance(String param1, String param2) {
//        LoginPageFragment fragment = new LoginPageFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login_page, container, false);
//    }
//}