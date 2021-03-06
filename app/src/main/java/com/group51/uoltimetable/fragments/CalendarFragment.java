package com.group51.uoltimetable.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;


import com.group51.uoltimetable.Model.LectureInfoViewModel;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.DateTimeHelper;

import java.util.Objects;

public class CalendarFragment extends Fragment {

    CalendarView calendarView;
    View view;
    private LectureInfoViewModel viewModel;
    DateTimeHelper dateTimeHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_fragment, container, false);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(LectureInfoViewModel.class);
        dateTimeHelper = new DateTimeHelper();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        addDayFragment(dateTimeHelper.getDateToday());
        initialiseCalendar();


    }


    private void initialiseCalendar() {
        calendarView = view.findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = dateTimeHelper.dateStringFromInts(dayOfMonth, month, year);
                addDayFragment(date);
            }
        });
    }

    private void addDayFragment(String date) {
        DayFragment dayFragment = DayFragment.newInstance(date);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.day_fragment_container, dayFragment).commit();

    }


}


