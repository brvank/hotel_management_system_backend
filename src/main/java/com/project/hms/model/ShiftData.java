package com.project.hms.model;

public class ShiftData{

    private boolean day;
    private boolean night;

    public ShiftData() {
    }

    public ShiftData(boolean day, boolean night) {
        this.day = day;
        this.night = night;
    }

    public boolean isDay() {
        return day;
    }

    public void setDay(boolean day) {
        this.day = day;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }
}