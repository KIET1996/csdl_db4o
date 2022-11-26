package com.example.bt_dbo4o.Model;
import java.util.Date;

public class Note {
    public int ID;
    public String TITLE;
    public String CONTENT;
    public Date CREATEDATE;

    public Note() {}

    public Note(int ID, String TITLE, String CONTENT, Date CREATEDATE) {
        this.ID = ID;
        this.TITLE = TITLE;
        this.CONTENT = CONTENT;
        this.CREATEDATE = CREATEDATE;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(Date CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

}
