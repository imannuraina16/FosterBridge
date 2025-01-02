package com.example.fosterbridge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.text.Layout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import com.example.fosterbridge.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends Fragment{

    TextView title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button button_contribute = view.findViewById(R.id.button_contribute);
        button_contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new Contribute());
                fragmentTransaction.commit();

                if (getActivity() != null) {
                    BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView2);
                    bottomNavigationView.setSelectedItemId(R.id.contribute);
                }
            }
        });
        // Inflate the layout for this fragment

        title = getActivity().findViewById(R.id.title);

        Button button_seeAll = view.findViewById(R.id.button_seeAll);
        button_seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new AboutUs());
                title.setText("About Us");
                fragmentTransaction.commit();

            }
        });
        return view;
    }



}