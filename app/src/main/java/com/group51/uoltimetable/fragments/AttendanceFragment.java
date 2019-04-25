package com.group51.uoltimetable.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.AttendanceRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AttendanceFragment extends Fragment {
    View view;
    JSONObject attendance;
    JSONObject attendance2;
    JSONArray attendances;
    private static RecyclerView recyclerView;
    private AttendanceRecyclerAdapter adapter;


    public AttendanceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.attendance_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_attendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initialiseData();
        initialiseAdapter();

        return view;
    }

    private void initialiseData() {

        attendance = new JSONObject();
        attendance2 = new JSONObject();
        attendances = new JSONArray();
        try {
            attendance.put("lectureName", "AI");
            attendance.put("attendance", "0%");
            attendance2.put("lectureName", "The Rest");
            attendance2.put("attendance", "0%");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        attendances.put(attendance);
        attendances.put(attendance2);
    }

    private void initialiseAdapter() {
        adapter = new AttendanceRecyclerAdapter(attendances);
        recyclerView.setAdapter(adapter);

    }
}
