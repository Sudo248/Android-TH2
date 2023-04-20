package com.sudo248.th2.tablayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 14:38 - 15/03/2023
 */
public class AdapterViewPager extends FragmentPagerAdapter {

    private int numberPage = 0;

    public AdapterViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        numberPage = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                return new FragmentA();
            case 1:
//                return new FragmentB();
            case 2:
//                return new FragmentC();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Info";
            case 2:
                return "City";
        }
        return super.getPageTitle(position);
    }
}
