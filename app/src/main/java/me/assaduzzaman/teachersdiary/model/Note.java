package me.assaduzzaman.teachersdiary.model;

public class Note {
    private String noteID;
    private String noteDetails;
    private String date;

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
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

    public Note(String noteID, String noteDetails, String date) {

        this.noteID = noteID;
        this.noteDetails = noteDetails;
        this.date = date;
    }
}
