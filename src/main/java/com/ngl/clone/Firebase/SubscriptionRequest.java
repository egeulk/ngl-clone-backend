package com.ngl.clone.Firebase;

public class SubscriptionRequest {
    private String subscriber;
    private String topic;

    public SubscriptionRequest(String subscriber, String topic) {
        this.subscriber = subscriber;
        this.topic = topic;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}