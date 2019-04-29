package com.group51.uoltimetable.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group51.uoltimetable.Model.LectureInfoViewModel;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.activities.MainActivity;
import com.group51.uoltimetable.utilities.LectureRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

public class DayFragment extends Fragment {
    private RecyclerView recyclerView;
    private LectureRecyclerAdapter adapter;
    JSONArray lectures;
    JSONObject lecture;
    private LectureInfoViewModel viewModel;
    private String date;


    public DayFragment() {
    }

    public static DayFragment newInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString("date", date);

        DayFragment fragment = new DayFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            date = bundle.getString("date");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lectures_fragment, container, false);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(LectureInfoViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_view_lectures);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readBundle(getArguments());

        initialiseData(date);
        initialiseAdapter();
        return view;

    }

    private void initialiseData(String date) {

        viewModel.setDate(date);
        //TODO set JSONArray lectures = to the query and remove everything else.
        //Toast.makeText(getContext(), "date is : " + date, Toast.LENGTH_LONG).show();
        lecture = new JSONObject();
        JSONObject otherLecture = new JSONObject();
        lectures = new JSONArray();

        try {
            lecture.put("lectureName", "Computer Based Trading in financial markets");
            lecture.put("lecturerName", "bill gates");
            lecture.put("location", "the guild");
            lecture.put("latitude", 53.405936);
            lecture.put("longitude", -2.965572);
            lecture.put("dateTime", "2019-04-29 10:00:00");

            otherLecture.put("lectureName", "maths");
            otherLecture.put("lecturerName", "someone");
            otherLecture.put("location", "69 smithdown lane");
            otherLecture.put("latitude", 53.404041);
            otherLecture.put("longitude", -2.959008);
            otherLecture.put("dateTime", "2019-04-29 01:00:00");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (date.equals("2019-04-29")) {
            lectures.put(otherLecture);

        }

        if (date.equals("2019-04-30")) {
            lectures.put(lecture);
        }


    }

    private void initialiseAdapter() {
        adapter = new LectureRecyclerAdapter(lectures);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new LectureRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    viewModel.setLectureInfo(lectures.getJSONObject(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceWithInfoFragment();

            }
        });

    }


}
