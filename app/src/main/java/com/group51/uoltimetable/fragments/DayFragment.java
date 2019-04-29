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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.group51.uoltimetable.Model.LectureInfoViewModel;
import com.group51.uoltimetable.R;
import com.group51.uoltimetable.activities.MainActivity;
import com.group51.uoltimetable.utilities.LectureRecyclerAdapter;
import com.group51.uoltimetable.utilities.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DayFragment extends Fragment {
    private RecyclerView recyclerView;
    private LectureRecyclerAdapter adapter;
    JSONArray lectures;
    private LectureInfoViewModel viewModel;
    private String date;
    RequestQueue queue;
    SessionManager sessionManager;
    String url = "https://student.csc.liv.ac.uk/~sgmbray/DBFetch.php";

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

        sessionManager = new SessionManager(Objects.requireNonNull(getContext()));
        initialiseData(date);
        initialiseAdapter();
        return view;

    }

    private void initialiseData(final String date) {
        lectures = new JSONArray();
        queue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    lectures = new JSONArray(response);
                    adapter.updateItems(lectures);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();

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
                params.put("date", date);
                return params;
            }
        };

        queue.add(request);

        viewModel.setDate(date);

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
