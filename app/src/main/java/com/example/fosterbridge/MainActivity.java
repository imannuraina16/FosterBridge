package com.example.fosterbridge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import com.example.fosterbridge.R;
import com.example.fosterbridge.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TextView title;
    ImageView up_button;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);
        replaceFragment(new Home());
        title = findViewById(R.id.title);
        up_button = findViewById(R.id.up_button);
        up_button.setOnClickListener(v -> {
            getSupportFragmentManager().popBackStack();
        });


        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                replaceFragment(new Home());
                title.setText("Home");
            } else if (item.getItemId() == R.id.contribute) {
                replaceFragment(new Contribute());
                title.setText("Contribute");
            } else if (item.getItemId() == R.id.message) {
                replaceFragment(new Message());
                title.setText("Message");
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(new Profile());
                title.setText("Profile");
            }


            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    public void hideUpButton() {
        // Hide up button in specific fragments/screens
        if (up_button != null) {
            up_button.setVisibility(View.GONE);  // or View.INVISIBLE
        }
    }

    public void showUpButton() {
        // Show up button in other fragments/screens
        if (up_button != null) {
            up_button.setVisibility(View.VISIBLE);
        }
    }
}