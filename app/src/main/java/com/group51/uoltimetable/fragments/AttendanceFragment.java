package com.group51.uoltimetable.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.utilities.AttendanceRecyclerAdapter;
import com.group51.uoltimetable.utilities.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceFragment extends Fragment {
    View view;
    JSONArray attendances;
    private RecyclerView recyclerView;
    private AttendanceRecyclerAdapter adapter;
    SessionManager sessionManager;
    String url = "https://student.csc.liv.ac.uk/~sgmbray/AttendanceCheck.php";

    public AttendanceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.attendance_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_attendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sessionManager = new SessionManager(getContext());

        initialiseData();
        initialiseAdapter();

        return view;
    }

    private void initialiseData() {
        //TODO get attendance data
        attendances = new JSONArray();
        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    attendances = new JSONArray(response);
                    adapter.updateItems(attendances);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("dead" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", sessionManager.getUsername());
                return params;
            }
        };

        queue.add(request);

    }

    private void initialiseAdapter() {
        adapter = new AttendanceRecyclerAdapter(attendances);
        recyclerView.setAdapter(adapter);

    }
}
