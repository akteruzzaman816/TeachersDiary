package me.assaduzzaman.teachersdiary.model;

public class ScheduleTime {
    private String routineTime;
    private String courseName;

    public ScheduleTime(String routineTime, String courseName) {
        this.routineTime = routineTime;
        this.courseName = courseName;
    }

    public String getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(String routineTime) {
        this.routineTime = routineTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
