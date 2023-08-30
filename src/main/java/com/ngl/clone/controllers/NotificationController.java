package com.ngl.clone.controllers;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ngl.clone.Firebase.DirectNotification;
import com.ngl.clone.Firebase.FCMService;
import com.ngl.clone.Firebase.SubscriptionRequest;
import com.ngl.clone.Firebase.TopicNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.ExecutionException;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = {"*","http://localhost:4200", "https://nursimayasor.com:4200"}, methods = {RequestMethod.GET, RequestMethod.POST}) // Apply CORS at class level
public class NotificationController {
    private final FCMService fcm;

    @Autowired
    public NotificationController(FCMService fcm) {
        this.fcm = fcm;
    }

    @PostMapping("/notification")
    public void sendTargetedNotification(@RequestBody DirectNotification notification) throws ExecutionException, InterruptedException {
        fcm.sendNotificationToTarget(notification);
    }

    @PostMapping("/topic/notification")
    public void sendNotificationToTopic(@RequestBody TopicNotification notification) {
        fcm.sendNotificationToTopic(notification);
    }

    @PostMapping("/topic/subscription")
    public void subscribeToTopic(@RequestBody SubscriptionRequest subscription) throws FirebaseMessagingException {
        fcm.subscribeToTopic(subscription);
    }
}
