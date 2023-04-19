package com.example.planer;

import io.realm.RealmObject;

public class Goal extends RealmObject {
    String title;
    String description;
    long createdAt;
    Integer dailyNumber;

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

    public Integer getDailyNumber() {
        return dailyNumber;
    }

    public void setDailyNumber(Integer dailyNumber) {
        this.dailyNumber = dailyNumber;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
