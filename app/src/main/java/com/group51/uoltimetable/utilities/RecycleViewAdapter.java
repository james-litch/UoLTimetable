package com.group51.uoltimetable.utilities;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group51.uoltimetable.R;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.LectureViewHolder> {

    public static class LectureViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView lectureName;
        TextView lecturerName;
        TextView location;
        TextView startTime;
        TextView endTime;

        LectureViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            lectureName = itemView.findViewById(R.id.lecture_name);
            lecturerName = itemView.findViewById(R.id.lecturer_name);
            location = itemView.findViewById(R.id.location);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
        }
    }

    List<Lecture> lectures;

    public RecycleViewAdapter(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public LectureViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lecture_layout, viewGroup, false);
        LectureViewHolder lvh = new LectureViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(LectureViewHolder lectureViewHolder, int i) {
        lectureViewHolder.lectureName.setText(lectures.get(i).lectureName);
        lectureViewHolder.lecturerName.setText(lectures.get(i).lecturerName);
        lectureViewHolder.location.setText(lectures.get(i).location);
        lectureViewHolder.startTime.setText(lectures.get(i).startTime);
        lectureViewHolder.endTime.setText(lectures.get(i).endTime);

    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }
}