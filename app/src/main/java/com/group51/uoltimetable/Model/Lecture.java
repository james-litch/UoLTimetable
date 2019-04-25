package com.group51.uoltimetable.Model;

public class Lecture {
    private String lectureName;
    private String lecturerName;
    private String location;
    private String startTime;
    private String endTime;

    public Lecture(String lectureName, String lecturerName, String location, String startTime, String endTime) {
        this.lectureName = lectureName;
        this.lecturerName = lecturerName;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getLocation() {
        return location;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
