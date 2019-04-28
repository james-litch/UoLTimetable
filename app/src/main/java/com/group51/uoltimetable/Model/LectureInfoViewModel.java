package com.group51.uoltimetable.Model;

import android.arch.lifecycle.ViewModel;

import org.json.JSONObject;

public class LectureInfoViewModel extends ViewModel {

    private JSONObject lectureInfo;
    private String date;


    public void setLectureInfo(JSONObject lecture) {
        lectureInfo = lecture;
    }

    public JSONObject getLectureInfo() {
        return lectureInfo;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
