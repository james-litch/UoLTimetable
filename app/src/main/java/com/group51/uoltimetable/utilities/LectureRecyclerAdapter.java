package com.group51.uoltimetable.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group51.uoltimetable.R;

import org.json.JSONArray;
import org.json.JSONException;



public class LectureRecyclerAdapter extends RecyclerView.Adapter<LectureRecyclerAdapter.LectureViewHolder> {
    private OnItemClickListener clickListener;
    private JSONArray lectures;
    DateTimeHelper dateTimeHelper;

    public void updateItems(JSONArray lectures) {
        this.lectures = lectures;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }


    public static class LectureViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView lectureName;
        TextView lecturerName;
        TextView location;
        TextView startTime;
        TextView endTime;

        LectureViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            lectureName = itemView.findViewById(R.id.lecture_name);
            lecturerName = itemView.findViewById(R.id.lecturer_name);
            location = itemView.findViewById(R.id.location);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    public LectureRecyclerAdapter(JSONArray lectures) {
        this.lectures = lectures;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public LectureViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lecture_card, viewGroup, false);
        return new LectureViewHolder(v, clickListener);

    }

    @Override
    public void onBindViewHolder(LectureViewHolder lectureViewHolder, int i) {
        try {
            dateTimeHelper = new DateTimeHelper();

            String startTime = dateTimeHelper.getStringFromDate(lectures.getJSONObject(i).getString("dateTime"), false);
            String endTime = dateTimeHelper.getStringFromDate(lectures.getJSONObject(i).getString("dateTime"), true);


            lectureViewHolder.lectureName.setText(lectures.getJSONObject(i).getString("lectureName"));
            lectureViewHolder.lecturerName.setText(lectures.getJSONObject(i).getString("lecturerName"));
            lectureViewHolder.location.setText(lectures.getJSONObject(i).getString("location"));
            lectureViewHolder.startTime.setText(startTime);
            lectureViewHolder.endTime.setText(endTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return lectures.length();
    }
}