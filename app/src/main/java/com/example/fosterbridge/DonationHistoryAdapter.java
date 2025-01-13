package com.example.fosterbridge;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.DonationViewHolder> {
    private static final String TAG = "DonationHistoryAdapter";

    private List<Donation> donations;

    public DonationHistoryAdapter(List<Donation> donations) {
        this.donations = donations;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_history_item, parent, false);
        return new DonationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Donation donation = donations.get(position);

        // Parse the amount from String to double before formatting
        try {
            double amount = Double.parseDouble(donation.getAmount());
            holder.tvAmount.setText(String.format("RM %.2f", amount));
        } catch (NumberFormatException e) {
            // Handle invalid amount format
            Log.e(TAG, "Invalid amount format for donation", e);
            holder.tvAmount.setText("RM 0.00");
        }

        // Check if the time field is null and handle it
        if (donation.getTimestamp() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            holder.tvDate.setText(sdf.format(donation.getTimestamp().toDate()));
        } else {
            holder.tvDate.setText("Date not available");
            Log.w(TAG, "Donation time is null for donation ID: " + donation.getId());
        }

        // Check if orphanage_username is null and log it if necessary
        if (donation.getOrphanageUsername() != null) {
            holder.tvDescription.setText("Donated to " + donation.getOrphanageUsername());
        } else {
            holder.tvDescription.setText("Orphanage name not available");
            Log.w(TAG, "Orphanage username is null for donation ID: " + donation.getId());
        }
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    // Method to update the list of donations
    public void setDonations(List<Donation> donations) {
        this.donations = donations;
        notifyDataSetChanged(); // Notify the adapter that the data has changed and it needs to refresh the views
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount, tvDate, tvDescription;

        public DonationViewHolder(View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvDonationAmount);
            tvDate = itemView.findViewById(R.id.tvDonationDate);
            tvDescription = itemView.findViewById(R.id.tvDonationDescription);
        }
    }
}
