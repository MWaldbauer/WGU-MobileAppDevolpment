package com.example.mwaldbauerscheduler.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.NO_ACTION;

@Entity(tableName = "course_table",
            indices = @Index(value = {"course"}, unique = true),
            foreignKeys = {@ForeignKey(entity = Term.class,
            parentColumns = "termID",
            childColumns = "termID",
            onDelete = NO_ACTION)}
)
public class Course {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "courseID")
    private int courseID;

    @NonNull
    @ColumnInfo(name = "course")
    private String course;

    @NonNull
    @ColumnInfo(name = "startDate")
    private Date startDate;

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
    @ColumnInfo(name = "termID")
    private int termID;

    @ColumnInfo(name = "mentor")
    private String mentor;

    @ColumnInfo(name = "mentor_phone")
    private String mentorPhone;

    @ColumnInfo(name = "mentor_email")
    private String mentorEmail;

    public Course(@NonNull String course, @NonNull Date startDate, @NonNull Boolean startDateAlarm,
                  @NonNull Date stopDate, @NonNull Boolean stopDateAlarm,
                  @NonNull String completionStatus, @NonNull int termID,
                  String mentor, String mentorPhone, String mentorEmail) {
        this.course = course;
        this.startDate = startDate;
        this.startDateAlarm = startDateAlarm;
        this.stopDate = stopDate;
        this.stopDateAlarm = stopDateAlarm;
        this.completionStatus = completionStatus;
        this.termID = termID;
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

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
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
