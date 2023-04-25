package com.example.planer;

import io.realm.RealmObject;

public class Goal extends RealmObject {
    private String title;
    private String description;
    private long createdAt;
    private int goalsInput;
    private int goalsCounter;
    private int goalsCompletedCounter;

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

    public int getGoalsInput() {
        return goalsInput;
    }

    public void setGoalsInput(int goalsInput) {
        this.goalsInput = goalsInput;
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
}
