package com.shreya.model;

import java.io.Serializable;

public class Note implements Serializable {

    private String title;
    private String content;
private String dateTime;
private String category;
private boolean pinned;
private boolean favorite;
private boolean deleted;
private String imagePath;
private String reminderDate;
private String reminderTime;
private boolean reminded;


    public Note(String title, String content, String dateTime, String category,boolean favorite, boolean pinned, String imagePath) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.category = category;
        this.pinned = pinned;
        this.favorite = favorite;
        this.deleted = false;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    public void setTitle(String title) {
    this.title = title;
}

public void setContent(String content) {
    this.content = content;
}
public String getDateTime() {
    return dateTime;
}
public String getCategory() {
    return category;
}
public void setCategory(String category) {
    this.category = category;
}
public boolean isPinned() {
    return pinned;
}
public void setPinned(boolean pinned) {
    this.pinned = pinned;
}
public boolean isFavorite() {
    return favorite;
}

public void setFavorite(boolean favorite) {
    this.favorite = favorite;
}
public boolean isDeleted() {
    return deleted;
}

public void setDeleted(boolean deleted) {
    this.deleted = deleted;
}
public String getImagePath() {
    return imagePath;
}

public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
}
public String getReminderDate() {
    return reminderDate;
}

public void setReminderDate(String reminderDate) {
    this.reminderDate = reminderDate;
}

public String getReminderTime() {
    return reminderTime;
}

public void setReminderTime(String reminderTime) {
    this.reminderTime = reminderTime;
}

public boolean isReminded() {
    return reminded;
}

public void setReminded(boolean reminded) {
    this.reminded = reminded;
}
}