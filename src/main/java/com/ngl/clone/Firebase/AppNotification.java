package com.ngl.clone.Firebase;
public abstract class AppNotification {
    public String title;
    public String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppNotification(String title, String message) {
        this.title = title;
        this.message = message;
    }
}