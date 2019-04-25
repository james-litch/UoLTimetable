package com.group51.uoltimetable.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;


import com.group51.uoltimetable.Model.LectureViewModel;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.DateHelper;

import java.util.Objects;

public class CalendarFragment extends Fragment {

    CalendarView calendarView;
    View view;
    private LectureViewModel viewModel;
    DateHelper dateHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_fragment, container, false);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(LectureViewModel.class);
        dateHelper = new DateHelper();
        viewModel.setDate(dateHelper.getDateToday());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        addDayFragment();
        initialiseCalendar();


    }

    private void initialiseCalendar() {
        calendarView = view.findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                viewModel.setDate(dayOfMonth + "-" + month + "-" + year);
                addDayFragment();
            }
        });
    }

    private void addDayFragment() {
        DayFragment dayFragment = new DayFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.day_fragment_container, dayFragment).commit();

        //TODO on click of calendar pass in the day to add day fragment method
    }


}


