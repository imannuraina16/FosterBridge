package com.example.fosterbridge;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class DonateFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    MyAdapter myAdapter;
    ArrayList<Orphanage> orphanageArrayList;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        db = FirebaseFirestore.getInstance();
        orphanageArrayList = new ArrayList<>();

        // Initialize adapter with listener
        myAdapter = new MyAdapter(requireContext(), orphanageArrayList, orphanage -> {
            // Handle button click here
            goToDonateMonetary(orphanage);
        });

        recyclerView.setAdapter(myAdapter);

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
                    orphanageArrayList.add(dc.getDocument().toObject(Orphanage.class));
                }
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private void goToDonateMonetary(Orphanage orphanage) {
        // Create a new instance of the target fragment
        title = getActivity().findViewById(R.id.title);
        DonateMonetary donateMonetary = DonateMonetary.newInstance(orphanage.getName(), orphanage.getLocation());
        // Replace the current fragment with the detail fragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, donateMonetary);
        transaction.addToBackStack(null);
        title.setText("Donate");
        transaction.commit();
    }
}




