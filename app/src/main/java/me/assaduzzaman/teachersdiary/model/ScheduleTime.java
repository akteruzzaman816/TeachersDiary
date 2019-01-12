package me.assaduzzaman.teachersdiary.model;

public class ScheduleTime {
    private String routineDay;
    private String routineTime;
    private String courseName;

    public ScheduleTime(String routineDay, String routineTime, String courseName) {
        this.routineDay = routineDay;
        this.routineTime = routineTime;
        this.courseName = courseName;
    }


    public String getRoutineDay() {
        return routineDay;
    }

    public void setRoutineDay(String routineDay) {
        this.routineDay = routineDay;
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
