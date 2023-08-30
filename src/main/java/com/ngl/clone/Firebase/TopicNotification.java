package com.ngl.clone.Firebase;
public class TopicNotification extends AppNotification {
    public String topic;

    public TopicNotification(String topic, String title, String message) {
        super(title, message);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}