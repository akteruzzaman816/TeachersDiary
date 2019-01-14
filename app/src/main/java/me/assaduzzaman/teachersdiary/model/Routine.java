package me.assaduzzaman.teachersdiary.model;

public class Routine {
    private int routineID;
    private String teacherCode;
    private String courseName;
    private String routineDay;
    private String routineFaculty;
    private String routineSemester;
    private String routineTime;
    private String routineSection;
    private String routineRoom;
    private String routineBatch;
    private String courseCode;
    private int routineStatus;

    public Routine() {
    }

    public Routine(int routineID, String routineDay, String routineSemester, String routineTime) {
        this.routineID = routineID;
        this.routineDay = routineDay;
        this.routineSemester = routineSemester;
        this.routineTime = routineTime;
    }

    public Routine(String routineSemester, String routineBatch, String routineSection, String courseName, String routineDay, String routineTime, String routineRoom, String courseCode) {
        this.routineSemester = routineSemester;
        this.routineBatch = routineBatch;
        this.routineSection = routineSection;
        this.courseName = courseName;
        this.routineDay = routineDay;
        this.routineTime = routineTime;
        this.routineRoom = routineRoom;
        this.courseCode = courseCode;
    }

    public Routine(int routineID, String teacherCode, String courseName, String routineDay, String routineFaculty, String routineSemester, String routineTime, String routineSection, String routineRoom, String routineBatch, String courseCode, int routineStatus) {
        this.routineID = routineID;
        this.teacherCode = teacherCode;
        this.courseName = courseName;
        this.routineDay = routineDay;
        this.routineFaculty = routineFaculty;
        this.routineSemester = routineSemester;
        this.routineTime = routineTime;
        this.routineSection = routineSection;
        this.routineRoom = routineRoom;
        this.routineBatch = routineBatch;
        this.courseCode = courseCode;
        this.routineStatus = routineStatus;
    }


    public int getRoutineID() {
        return routineID;
    }

    public void setRoutineID(int routineID) {
        this.routineID = routineID;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRoutineDay() {
        return routineDay;
    }

    public void setRoutineDay(String routineDay) {
        this.routineDay = routineDay;
    }

    public String getRoutineFaculty() {
        return routineFaculty;
    }

    public void setRoutineFaculty(String routineFaculty) {
        this.routineFaculty = routineFaculty;
    }

    public String getRoutineSemester() {
        return routineSemester;
    }

    public void setRoutineSemester(String routineSemester) {
        this.routineSemester = routineSemester;
    }

    public String getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(String routineTime) {
        this.routineTime = routineTime;
    }

    public String getRoutineSection() {
        return routineSection;
    }

    public void setRoutineSection(String routineSection) {
        this.routineSection = routineSection;
    }

    public String getRoutineRoom() {
        return routineRoom;
    }

    public void setRoutineRoom(String routineRoom) {
        this.routineRoom = routineRoom;
    }

    public String getRoutineBatch() {
        return routineBatch;
    }

    public void setRoutineBatch(String routineBatch) {
        this.routineBatch = routineBatch;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getRoutineStatus() {
        return routineStatus;
    }

    public void setRoutineStatus(int routineStatus) {
        this.routineStatus = routineStatus;
    }
}
