package com.group51.uoltimetable.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group51.uoltimetable.Model.LectureInfoViewModel;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.DateHelper;

import java.util.Calendar;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int num_frags = 5;
    private LectureInfoViewModel viewModel;
    Boolean showNextWeek = false;
    DateHelper dateHelper;
    int dayOfWeek;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //this inflates out tab layout file.
        view = inflater.inflate(R.layout.tab_fragment_layout, null);
        // set up.
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);


        // create a new adapter for our pageViewer. This adapters returns child fragments as per the position of the page Viewer.
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        // this is a workaround
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                //provide the viewPager to TabLayout.
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        //preload adjacent tabs, helps reduce lag.

        viewPager.setOffscreenPageLimit(2);

        dateHelper = new DateHelper();
        dayOfWeek = dateHelper.getDayOfToday();

        //sets the opening day of the schedule to the current day, if its the weekend set to monday.
        if (dayOfWeek < Calendar.SATURDAY) {
            viewPager.setCurrentItem(dayOfWeek - 1, true);

        } else {
            showNextWeek = true;
            viewPager.setCurrentItem(0, true);
        }

        return view;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fragManager) {
            super(fragManager);
        }

        //return the fragment with respect to page position.
        @Override
        public Fragment getItem(int position) {

            return DayFragment.newInstance(dateHelper.getDateOfDay(position, showNextWeek));

        }

        @Override
        public int getCount() {

            return num_frags;

        }

        //This method returns the title of the tab according to the position.
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Monday";
                case 1:
                    return "Tuesday";
                case 2:
                    return "Wednesday";
                case 3:
                    return "Thursday";
                case 4:
                    return "Friday";
            }
            return null;
        }
    }


}