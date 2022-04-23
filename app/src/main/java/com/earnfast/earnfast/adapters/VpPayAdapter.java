package com.earnfast.earnfast.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.earnfast.earnfast.fragments.PayRequest;
import com.earnfast.earnfast.fragments.Transaction;

public class VpPayAdapter extends FragmentStateAdapter {
    public VpPayAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0) return new PayRequest();
        return  new Transaction();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
