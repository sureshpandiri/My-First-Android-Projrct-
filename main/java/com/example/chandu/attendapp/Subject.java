package com.example.chandu.attendapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "subject_table")
public class Subject {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;



    private int total;

    private int attended;

    public Subject(String title, int total, int attended) {
        this.title = title;
        this.total = total;
        this.attended = attended;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTotal() {
        return total;
    }

    public int getAttended() {
        return attended;
    }
}
