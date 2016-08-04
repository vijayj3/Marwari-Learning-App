package com.example.android.marwari;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by AV JAYARAMAN on 03-Aug-16.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private Context mContext;
    public CategoryAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case (0):
                return new NumbersFragment();
            case (1):
                return new FamilyFragment();
            case (2):
                return new ColorsFragment();
            default:
                return new PhrasesFragment();
        }

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.category_numbers);
            case 1:
                return mContext.getString(R.string.category_family);
            case 2:
                return mContext.getString(R.string.category_colors);
            default:
                return mContext.getString(R.string.category_phrases);
        }

    }
}
