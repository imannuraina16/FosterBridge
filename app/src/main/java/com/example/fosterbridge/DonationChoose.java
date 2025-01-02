package com.example.fosterbridge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DonationChoose extends Fragment {
    TextView title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_choose, container, false);

        title = getActivity().findViewById(R.id.title);
        ImageView button_monetary = view.findViewById(R.id.button_monetary);
        ImageView button_education = view.findViewById(R.id.button_education);
        ImageView button_meals = view.findViewById(R.id.button_meals);

        button_monetary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new DonateMonetary());
                title.setText("Monetary");
                fragmentTransaction.commit();
            }
        });
        button_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new DonateEducation());
                title.setText("Education");
                fragmentTransaction.commit();
            }
        });
        button_meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new DonateMeals());
                title.setText("Meals");
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}