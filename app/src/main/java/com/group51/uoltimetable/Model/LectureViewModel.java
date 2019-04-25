package com.group51.uoltimetable.Model;

import android.arch.lifecycle.ViewModel;

import org.json.JSONObject;

public class LectureViewModel extends ViewModel {

    private String date;
    private JSONObject lectureInfo;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        System.out.println("date set to : " + date);
    }

    public void setLectureInfo(JSONObject lecture) {
        lectureInfo = lecture;
    }

    public JSONObject getLectureInfo() {
        return lectureInfo;
    }


}
