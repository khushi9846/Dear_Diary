package com.example.notepad.Modles;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")   //becoz we need notes in a tabular form
public class Notes implements Serializable {

    @PrimaryKey(autoGenerate = true)                              //First annotation for notes.
    int ID=0;

    @ColumnInfo(name = "Title")
    String title = " ";

    //@column is used to create a column whenever we create a new note these will be thw defaults
   @ColumnInfo(name = "notes")
    String notes = " ";

   @ColumnInfo(name = "Date")
   String date = " "; //empty by default

    @ColumnInfo(name = "Pinned")
    Boolean pinned = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }



 }
