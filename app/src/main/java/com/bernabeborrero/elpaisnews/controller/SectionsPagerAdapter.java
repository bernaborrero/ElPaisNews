package com.bernabeborrero.elpaisnews.controller;

/**
 * Created by berna on 6/02/15.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.bernabeborrero.elpaisnews.R;

import java.util.Locale;

/**
 * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return NewsFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_section_headlines).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_section_spain).toUpperCase(l);
            case 2:
                return context.getString(R.string.title_section_europe).toUpperCase(l);
            case 3:
                return context.getString(R.string.title_section_world).toUpperCase(l);
        }
        return null;
    }
}