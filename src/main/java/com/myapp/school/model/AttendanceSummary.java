package com.myapp.school.model;

public class AttendanceSummary {
    private final int totalDays;
    private final int presentDays;
    private final int absentDays;

    public AttendanceSummary(int totalDays, int presentDays , int absentDays){
        this.totalDays = totalDays;
        this.presentDays = presentDays;
        this.absentDays = absentDays;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getPresentDays() {
        return presentDays;
    }

    public int getAbsentDays() {
        return absentDays;
    }
    public double getPercentage(){
        if (totalDays==0) return 0;
        return (presentDays*100)/totalDays;
    }
}
