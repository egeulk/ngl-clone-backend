package com.ngl.clone.Firebase;
public class DirectNotification extends AppNotification {
    public String target;

    public DirectNotification(String target, String title, String message) {
        super(title, message);
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}