package com.example.fosterbridge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Contribute extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contribute, container, false);

        if (savedInstanceState == null) {
            DonateFragment donateFragment = new DonateFragment();
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.child_fragment_container, donateFragment)
                    .commit();
        }
        Button button_donate = view.findViewById(R.id.button_donate);
        button_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    DonateFragment donateFragment = new DonateFragment();
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.child_fragment_container, donateFragment)
                            .commit();
                }
            }
        });

        Button button_volunteer = view.findViewById(R.id.button_volunteer);
        button_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    VolunteerFragment volunteerFragment = new VolunteerFragment();
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.child_fragment_container, volunteerFragment)
                            .commit();
                }
            }
        });



        return view;
    }
}