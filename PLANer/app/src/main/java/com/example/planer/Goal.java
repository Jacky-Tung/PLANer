package com.example.planer;

import java.util.Date;

import io.realm.RealmObject;

public class Goal extends RealmObject {
    private String title;
    private String description;
    private long createdAt;
    private int goalsCounter;
    private int goalsCompletedCounter;
    private Date deadline;
    private boolean isOverdue;
    private boolean completed;
//    private MyProgressBar progressBar;

    //---//
    //add field for individual goal id for edit if changing functionality.
    //discuss: edit at the moment makes a new note. If editing a note do we want a new createdAt,
    //         or pass value so it's the same as the old
    //---//

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

    public void updateGoalsCompletedCounter(int goalsInput){
        this.goalsCompletedCounter += goalsInput;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    //    public MyProgressBar getProgressBar() {
//        return progressBar;
//    }
//
//    public void setProgressBar(MyProgressBar progressBar) {
//        this.progressBar = progressBar;
//    }
}
