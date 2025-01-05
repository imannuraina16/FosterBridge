package com.example.fosterbridge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DonateMonetary extends Fragment {
    private static final String ARG_NAME = "name";
    private static final String ARG_LOCATION = "location";

    private String orphanageName;
    private String orphanageLocation;

    public static DonateMonetary newInstance(String name, String location) {
        DonateMonetary fragment = new DonateMonetary();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orphanageName = getArguments().getString(ARG_NAME);
            orphanageLocation = getArguments().getString(ARG_LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate_monetary, container, false);
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showUpButton(); // Show up button in HomeFragment
        }
        TextView nameTextView = view.findViewById(R.id.orphanage_name);
        TextView locationTextView = view.findViewById(R.id.orphanage_address);

        // Display data
        nameTextView.setText(orphanageName);
        locationTextView.setText(orphanageLocation);

        EditText donation_amount = view.findViewById(R.id.donate_amount);
        Button button_donate_now = view.findViewById(R.id.button_donate_now);
        Button btnRm10 = view.findViewById(R.id.btnRm10);
        Button btnRm30 = view.findViewById(R.id.btnRm30);
        Button btnRm50 = view.findViewById(R.id.btnRm50);
        Button btnRm100 = view.findViewById(R.id.btnRm100);
        Button btnRm200 = view.findViewById(R.id.btnRm200);
        Button btnRm300 = view.findViewById(R.id.btnRm300);
        Button btnRm500 = view.findViewById(R.id.btnRm500);
        Button btnRm1000 = view.findViewById(R.id.btnRm1000);

        btnRm10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donation_amount.setText("10");
            }
        });
        btnRm30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donation_amount.setText("30");
            }
        });
        btnRm50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donation_amount.setText("50");
            }
        });
        btnRm100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donation_amount.setText("100");
            }
        });
        btnRm200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donation_amount.setText("200");
            }
        });
        btnRm300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donation_amount.setText("300");
            }
        });
        btnRm500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donation_amount.setText("500");
            }
        });
        btnRm1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donation_amount.setText("1000");
            }
        });

        button_donate_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}