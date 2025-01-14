package com.example.fosterbridge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Home extends Fragment{

    TextView title;
    RecyclerView recyclerView3;
    FirebaseFirestore db;
    HomeAdapter myAdapter;
    ArrayList<Orphanage> orphanageArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (getActivity() != null) {
            ((MainActivity) getActivity()).hideUpButton();
        }
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
                fragmentTransaction.addToBackStack(null);
                title.setText("About Us");
                fragmentTransaction.commit();

            }
        });
        recyclerView3 = view.findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false));

        db = FirebaseFirestore.getInstance();
        orphanageArrayList = new ArrayList<>();

        // Initialize adapter with listener
        myAdapter = new HomeAdapter(requireContext(), orphanageArrayList, orphanage -> {
            // Handle button click here
            goToDonateMonetary(orphanage);
        });

        recyclerView3.setAdapter(myAdapter);

        dataInitialize();
        return view;
    }

    private void dataInitialize() {
        db.collection("orphanage").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore error", error.getMessage());
                return;
            }

            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    Orphanage orphanage = dc.getDocument().toObject(Orphanage.class);

                    // Check if the name or location is null
                    if (orphanage.getName() != null && orphanage.getLocation() != null) {
                        orphanageArrayList.add(orphanage);
                    }
                }
            }
            myAdapter.notifyDataSetChanged();
        });
    }


    private void goToDonateMonetary(Orphanage orphanage) {
        // Create a new instance of the target fragment
        title = getActivity().findViewById(R.id.title);
        DonateMonetary donateMonetary = DonateMonetary.newInstance(orphanage.getName(), orphanage.getLocation(), orphanage.getUsername());
        // Replace the current fragment with the detail fragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, donateMonetary);
        transaction.addToBackStack(null);
        if (getActivity() != null) {
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView2);
            bottomNavigationView.setSelectedItemId(R.id.contribute);
        }
        title.setText("Donate");
        transaction.commit();
    }



}