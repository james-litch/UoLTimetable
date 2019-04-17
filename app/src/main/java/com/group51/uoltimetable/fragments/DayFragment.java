package com.group51.uoltimetable.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.Lecture;
import com.group51.uoltimetable.utilities.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class DayFragment extends Fragment {
    private List<Lecture> lectures;
    private static RecyclerView recyclerView;

    public DayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lectures_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        initialiseData();
        initialiseAdapter();
        return view;

    }

    private void initialiseData() {
        lectures = new ArrayList<>();
        lectures.add(new Lecture("programming", "bill gates", "gaffistan", "12:00", "13:00"));
        lectures.add(new Lecture("dirty beats", "tenner sweets", "hbgbs", "23:00", "03:00"));
        lectures.add(new Lecture("End", "end", "end", "23:00", "03:00"));
        //TODO pass in day and add data from database to array.


    }

    private void initialiseAdapter() {
        RecycleViewAdapter adapter = new RecycleViewAdapter(lectures);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


}
