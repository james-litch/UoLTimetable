package com.group51.uoltimetable.Model;

import android.arch.lifecycle.ViewModel;

import org.json.JSONObject;

public class LectureInfoViewModel extends ViewModel {

    private JSONObject lectureInfo;


    public void setLectureInfo(JSONObject lecture) {
        lectureInfo = lecture;
    }

    public JSONObject getLectureInfo() {
        return lectureInfo;
    }


}
