package com.example.planer;

import android.icu.util.Calendar;

import java.util.Date;

import io.realm.RealmObject;

import java.util.UUID;

import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Goal extends RealmObject {

    private String title;
    private String description;
    private long createdAt;
    private int goalsCounter;
    private int goalsCompletedCounter;
    private Date deadline;
    private boolean isOverdue;
    private int year, month, dayOfMonth;
    private boolean completed;
    @PrimaryKey private String goalID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setGoalsCounter(int goalsCounter){
        this.goalsCounter = goalsCounter;
    }

    public int getGoalsCounter() {
        return goalsCounter;
    }

    public int getGoalsCompletedCounter() {
        return goalsCompletedCounter;
    }

    public void setGoalsCompletedCounter(int goalsCompletedCounter) {
        this.goalsCompletedCounter = goalsCompletedCounter;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setDayOfMonth(int day) {
        this.dayOfMonth = day;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public String getGoalID() {
        return goalID;
    }

    public void updateGoalsCompletedCounter(int goalsInput){
        this.goalsCompletedCounter += goalsInput;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}


