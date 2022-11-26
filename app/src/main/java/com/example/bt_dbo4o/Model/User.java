package com.example.bt_dbo4o.Model;

import com.example.bt_dbo4o.Model.Note;

import java.util.ArrayList;

public class User {
    private String FULLNAME;
    private String USERNAME;
    private String PASSWORD;
    private ArrayList<Note> notes;

    public User() {}

    public User(String USERNAME) {
        this.USERNAME = USERNAME;
        this.notes = new ArrayList<>();
    }

    public User( String FULLNAME, String USERNAME, String PASSWORD) {
        this.FULLNAME = FULLNAME;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.notes = new ArrayList<>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void  setNotes(ArrayList<Note> notes){
        this.notes = notes;
    }

    public boolean addNote(Note note) {
        return this.notes.add(note);
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

}
