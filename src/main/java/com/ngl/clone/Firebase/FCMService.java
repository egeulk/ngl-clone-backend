package com.ngl.clone.Firebase;


import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {
    public void sendNotificationToTarget(DirectNotification notification) throws InterruptedException, ExecutionException {
        WebpushNotification webpushNotification = WebpushNotification.builder()
                .setTitle(notification.getTitle())
                .setBody(notification.getMessage())
                .setIcon("https://assets.mapquestapi.com/icon/v2/circle@2x.png")
                .build();

        WebpushConfig webpushConfig = WebpushConfig.builder()
                .setNotification(webpushNotification)
                .build();

        Message message = Message.builder()
                .setWebpushConfig(webpushConfig)
                .setToken(notification.getTarget())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message).get();
    }
    public void sendNotificationToTopic(TopicNotification notification) {
        Message message = Message.builder()
                .setWebpushConfig(
                        WebpushConfig.builder()
                                .setNotification(
                                        WebpushNotification.builder()
                                                .setTitle(notification.getTitle())
                                                .setBody(notification.getMessage())
                                                .setIcon("https://assets.mapquestapi.com/icon/v2/circle@2x.png")
                                                .build()
                                )
                                .build()
                )
                .setTopic(notification.topic)
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);

    }

    public void subscribeToTopic(SubscriptionRequest subscription) throws FirebaseMessagingException {
        List<String> subscribers = new ArrayList<String>();
        subscribers.add(subscription.getSubscriber());
        FirebaseMessaging.getInstance().subscribeToTopic(subscribers, subscription.getTopic());
    }
}