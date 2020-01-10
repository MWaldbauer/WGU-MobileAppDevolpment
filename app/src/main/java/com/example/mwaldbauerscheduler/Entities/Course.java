package com.example.mwaldbauerscheduler.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "course_table", foreignKeys = {@ForeignKey(entity = Term.class,
            parentColumns = "term",
            childColumns = "term",
            onDelete = RESTRICT)},
            indices = @Index(value = {"course"}, unique = true)
)
public class Course {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "courseID")
    private int courseID;

    @NonNull
    @ColumnInfo(name = "course")
    private String course;

    @NonNull
    @ColumnInfo(name = "startDate")
    private java.sql.Date startDate;

    @NonNull
    @ColumnInfo(name = "startDateAlarm", defaultValue = "false")
    private Boolean startDateAlarm;

    @NonNull
    @ColumnInfo(name = "stopDate")
    private Date stopDate;

    @NonNull
    @ColumnInfo(name = "stopDateAlarm", defaultValue = "false")
    private Boolean stopDateAlarm;

    @NonNull
    @ColumnInfo(name = "completion_status", defaultValue = "Plan to Take")
    private String completionStatus;

    @NonNull
    @ColumnInfo(name = "term")
    private String term;

    @ColumnInfo(name = "mentor")
    private String mentor;

    @ColumnInfo(name = "mentor_phone")
    private String mentorPhone;

    @ColumnInfo(name = "mentor_email")
    private String mentorEmail;

    public Course(@NonNull String course, @NonNull Date startDate, @NonNull Boolean startDateAlarm,
                  @NonNull Date stopDate, @NonNull Boolean stopDateAlarm,
                  @NonNull String completionStatus, @NonNull String term,
                  String mentor, String mentorPhone, String mentorEmail) {
        this.course = course;
        this.startDate = startDate;
        this.startDateAlarm = startDateAlarm;
        this.stopDate = stopDate;
        this.stopDateAlarm = stopDateAlarm;
        this.completionStatus = completionStatus;
        this.term = term;
        this.mentor = mentor;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;

        if (this.completionStatus == null) {this.completionStatus = "Plan to Take";}

    }

    @NonNull
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(@NonNull int courseID) {
        this.courseID = courseID;
    }

    @NonNull
    public String getCourse() {
        return course;
    }

    public void setCourse(@NonNull String course) {
        this.course = course;
    }

    @NonNull
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull Date startDate) {
        this.startDate = startDate;
    }

    @NonNull
    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(@NonNull Date stopDate) {
        this.stopDate = stopDate;
    }

    @NonNull
    public Boolean getStartDateAlarm() {
        return startDateAlarm;
    }

    public void setStartDateAlarm(@NonNull Boolean startDateAlarm) {
        this.startDateAlarm = startDateAlarm;
    }

    @NonNull
    public Boolean getStopDateAlarm() {
        return stopDateAlarm;
    }

    public void setStopDateAlarm(@NonNull Boolean stopDateAlarm) {
        this.stopDateAlarm = stopDateAlarm;
    }

    @NonNull
    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(@NonNull String completionStatus) {
        //Error checking to make sure completionStatus is one of the four valid options?
        this.completionStatus = completionStatus;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }
}
