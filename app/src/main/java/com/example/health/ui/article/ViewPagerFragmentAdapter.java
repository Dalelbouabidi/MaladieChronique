package com.example.health.ui.article;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private String[] titles= new String[]{"Maladie Chronique", "Conseil"};

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MaladeFragment();
            case 1:
                return new ConseilFragment();

        }
        return new MaladeFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
