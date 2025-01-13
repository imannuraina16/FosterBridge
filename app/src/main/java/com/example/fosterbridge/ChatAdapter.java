package com.example.fosterbridge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    // List of chat messages
    private List<Message> chatMessages;

    // Constructor to initialize messages
    public ChatAdapter(List<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the `item_chat_message.xml` layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // Get the message at the current position
        Message message = chatMessages.get(position);

        // Check if the message is sent or received
        if (message.isSent()) {
            holder.sentMessage.setVisibility(View.VISIBLE);
            holder.sentMessage.setText(message.getText());

            holder.receivedMessage.setVisibility(View.GONE);
        } else {
            holder.receivedMessage.setVisibility(View.VISIBLE);
            holder.receivedMessage.setText(message.getText());

            holder.sentMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        // Return the number of messages
        return chatMessages.size();
    }

    // ViewHolder class to bind the views
    static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView sentMessage, receivedMessage;

        ChatViewHolder(View itemView) {
            super(itemView);
            sentMessage = itemView.findViewById(R.id.message_text_sent); // TextView for sent messages
            receivedMessage = itemView.findViewById(R.id.message_text); // TextView for received messages
        }
    }
}
