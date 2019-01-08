package me.assaduzzaman.teachersdiary.model;

public class Note {
    private int noteID;
    private String noteDetails;
    private String date;

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteDetails() {
        return noteDetails;
    }

    public void setNoteDetails(String noteDetails) {
        this.noteDetails = noteDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Note(String noteDetails, String date) {

        this.noteDetails = noteDetails;
        this.date = date;
    }

    public Note(int noteID, String noteDetails, String date) {

        this.noteID = noteID;
        this.noteDetails = noteDetails;
        this.date = date;
    }
}
