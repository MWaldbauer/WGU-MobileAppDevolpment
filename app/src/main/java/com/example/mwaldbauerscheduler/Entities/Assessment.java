package com.example.mwaldbauerscheduler.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.NO_ACTION;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "assessment_table", foreignKeys = {@ForeignKey(entity = Course.class,
        parentColumns = "course",
        childColumns = "course",
        onDelete = NO_ACTION)}
)

public class Assessment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessmentID")
    private int assessmentID;

    @NonNull
    @ColumnInfo(name = "assessment")
    private String assessment;

    @NonNull
    @ColumnInfo(name = "goalDate")
    private Date goalDate;

    @NonNull
    @ColumnInfo(name = "goalDateAlarm", defaultValue = "false")
    private Boolean goalDateAlarm;

    //The constructor sets our default value as it's determined at runtime.
    @NonNull
    @ColumnInfo(name = "stopDate")
    private Date stopDate;

    @ColumnInfo(name = "notes")
    private String notes;

    @NonNull
    @ColumnInfo(name = "course")
    private String course;


    public Assessment(@NonNull String assessment, @NonNull Date goalDate, @NonNull Date stopDate,
                      @NonNull Boolean goalDateAlarm, String notes, @NonNull String course) {
        this.assessment = assessment;
        this.goalDate = goalDate;
        this.goalDateAlarm = goalDateAlarm;
        this.stopDate = stopDate;
        this.notes = notes;
        this.course = course;
    }

    @NonNull
    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(@NonNull int assessmentID) {
        this.assessmentID = assessmentID;
    }

    @NonNull
    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(@NonNull String assessment) {
        this.assessment = assessment;
    }

    @NonNull
    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(@NonNull Date goalDate) {
        this.goalDate = goalDate;
    }

    @NonNull
    public Boolean getGoalDateAlarm() {
        return goalDateAlarm;
    }

    public void setGoalDateAlarm(@NonNull Boolean goalDateAlarm) {
        this.goalDateAlarm = goalDateAlarm;
    }

    @NonNull
    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(@NonNull Date stopDate) {
        this.stopDate = stopDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes; }

    @NonNull
    public String getCourse() {
        return course;
    }

    public void setCourse(@NonNull String course) {
        this.course = course;
    }


}
