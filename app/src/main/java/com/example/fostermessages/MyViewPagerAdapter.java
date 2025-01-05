package com.example.fostermessages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fostermessages.Fragments.ChatsFrag;
import com.example.fostermessages.Fragments.NotiFrag;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return new ChatsFrag();
            case 1 :
                return new NotiFrag();
            default :
                return new ChatsFrag();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
