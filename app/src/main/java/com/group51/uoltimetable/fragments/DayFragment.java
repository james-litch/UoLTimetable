package com.group51.uoltimetable.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.group51.uoltimetable.Model.LectureViewModel;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.activities.MainActivity;
import com.group51.uoltimetable.utilities.LectureRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Objects;

public class DayFragment extends Fragment {
    private static RecyclerView recyclerView;
    private LectureRecyclerAdapter adapter;
    JSONArray lectures;
    JSONObject lecture;
    private LectureViewModel viewModel;


    public DayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lectures_fragment, container, false);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(LectureViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_view_lectures);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        initialiseData(viewModel.getDate());
        initialiseAdapter();
        return view;

    }

    private void initialiseData(String date) {

        //viewModel.getDate
        Toast.makeText(getContext(), "date is : " + date, Toast.LENGTH_LONG).show();
        lecture = new JSONObject();
        JSONObject otherLecture = new JSONObject();
        lectures = new JSONArray();
        try {
            lecture.put("lectureName", "Programming");
            lecture.put("lecturerName", "bill gates");
            lecture.put("location", "gaffistan");
            lecture.put("startTime", "12:00");
            lecture.put("endTime", "12:00");

            otherLecture.put("lectureName", "maths");
            otherLecture.put("lecturerName", "someone");
            otherLecture.put("location", "home");
            otherLecture.put("startTime", "15:00");
            otherLecture.put("endTime", "16:00");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        lectures.put(lecture);
        lectures.put(otherLecture);


    }

    private void initialiseAdapter() {
        adapter = new LectureRecyclerAdapter(lectures);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new LectureRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),
                        "item clicked" + position + " works?", Toast.LENGTH_LONG).show();
                try {
                    viewModel.setLectureInfo(lectures.getJSONObject(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((MainActivity) getActivity()).replaceFragment();

            }
        });

    }


}
