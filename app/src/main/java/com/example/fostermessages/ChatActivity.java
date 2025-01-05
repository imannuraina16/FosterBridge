package com.example.fostermessages;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fostermessages.model.UserModel;
import com.example.fostermessages.utils.androidUtil;
import com.example.fostermessages.utils.firebaseUtil;

import org.w3c.dom.Text;

public class ChatActivity extends AppCompatActivity {

    UserModel otherUser;
    EditText messageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;
    String chatroomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        otherUser = androidUtil.getUserModelFromIntent(getIntent());
        chatroomID = firebaseUtil.getChatroomID(firebaseUtil.currentUserID(),otherUser.getUserID);

        messageInput = findViewById(R.id.mssg_input);
        sendMessageBtn = findViewById(R.id.mssg_send_btn);
        backBtn = findViewById(R.id.mssg_back);
        otherUsername = findViewById(R.id.other_username);
        recyclerView = findViewById(R.id.chat_recylcer_view);

        backBtn.setOnClickListener((v) -> {
            getOnBackPressedDispatcher();
        });

        otherUsername.setText(otherUser.getUsername());

        getOrCreateChatroomModel();

    }


}