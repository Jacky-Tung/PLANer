package com.example.planer;

import io.realm.RealmObject;

public class Goal extends RealmObject {
    private String title;
    private String description;     // Discuss - necessary?/replaced by subgoal
    private long createdAt;         // Discuss - necessary?
    private int dailyNumber;
    private int totalNumber;

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

    public int getDailyNumber() {
        return dailyNumber;
    }

    public void setDailyNumber(int dailyNumber) {
        this.dailyNumber = dailyNumber;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setTotalNumber(int totalNumber){
        this.totalNumber = totalNumber;
    }
}
