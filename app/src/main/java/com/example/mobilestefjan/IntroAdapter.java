package com.example.mobilestefjan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class IntroAdapter extends FragmentPagerAdapter {
    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Intro1Fragment();
            case 1:
                return new Intro2Fragment();
            case 2:
                return new Intro3Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
