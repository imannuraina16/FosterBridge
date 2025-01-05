package com.example.fostermessages;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fostermessages.adapter.SearchUserRecyclerAdapter;
import com.example.fostermessages.model.UserModel;
import com.example.fostermessages.utils.firebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.io.LineReader;
import com.google.firebase.firestore.Query;

public class searchUserActivity extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    RecyclerView recyclerView;

    SearchUserRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_user);

        searchInput = findViewById(R.id.user_input);
        searchButton = findViewById(R.id.searchUser_btn);
        backButton = findViewById(R.id.mssg_back);
        recyclerView = findViewById(R.id.recycler_view);

        backButton.setOnClickListener(v -> {
            getOnBackPressedDispatcher();
        });

        searchButton.setOnClickListener(v -> {
            String searchTerm = searchInput.getText().toString();
            if(searchTerm.isEmpty()){
                searchInput.setError("Invalid name");
                return;
            }
            setupSearchRecyclerView(searchTerm);
        });
    }

    void setupSearchRecyclerView(String searchTerm){

        Query query = firebaseUtil.allUserCollectionReference().whereGreaterThanOrEqualTo("name",searchTerm);

        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>().setQuery(query, UserModel.class).build();

        adapter = new SearchUserRecyclerAdapter(options, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.startListening();
    }
}