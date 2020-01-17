package com.example.mwaldbauerscheduler.Entities;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "termID")
    private int termID;

    @NonNull
    @ColumnInfo(name = "term")
    private String term;

    @NonNull
    @ColumnInfo(name = "startDate")
    private Date startDate;

    @NonNull
    @ColumnInfo(name = "stopDate")
    private Date stopDate;

    public Term(@NonNull String term, @NonNull Date startDate, @NonNull Date stopDate) {

        this.term = term;
        this.startDate = startDate;
        this.stopDate = stopDate;

        Log.i("Term name in constructor",term); //** Remove later
        Log.i("Term ID", Integer.toString(termID)); //** Remove later. Used to make sure autogenerate works.
    }

    public String toString() {
        String startDateString = startDate.toString();
        String stopDateString = stopDate.toString();
        String newString = term +
                "\nStart Date:" + startDateString +
                "\nStop Date:" + stopDateString;
        return newString;
    }

    public int getTermID() {
        Log.i("Term ID in getTermID", Integer.toString(termID)); //** Remove later
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

}
