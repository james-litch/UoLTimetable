package com.group51.uoltimetable.utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group51.uoltimetable.R;

import org.json.JSONArray;
import org.json.JSONException;

public class AttendanceRecyclerAdapter extends RecyclerView.Adapter<AttendanceRecyclerAdapter.AttendanceViewHolder> {
    private JSONArray attendances;

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView lectureName;
        TextView attendanceAmount;

        AttendanceViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.attendance_card);
            lectureName = itemView.findViewById(R.id.attendance_lecture_name);
            attendanceAmount = itemView.findViewById(R.id.attendance_lecture_percentage);


        }
    }


    public AttendanceRecyclerAdapter(JSONArray attendances) {
        this.attendances = attendances;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendance_card, viewGroup, false);
        return new AttendanceViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceRecyclerAdapter.AttendanceViewHolder attendanceViewHolder, int i) {
        try {
            attendanceViewHolder.lectureName.setText(attendances.getJSONObject(i).getString("lectureName"));
            attendanceViewHolder.attendanceAmount.setText(attendances.getJSONObject(i).getString("attendance"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return attendances.length();
    }
}


