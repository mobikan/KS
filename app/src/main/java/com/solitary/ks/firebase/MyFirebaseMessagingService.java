package com.solitary.ks.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
 private static final String TAG = "FCM Service";
 @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
     if (remoteMessage.getData().size() > 0) {


         try {

             JSONObject jsonObject = new JSONObject(remoteMessage.getData());
             Log.e("Tag", remoteMessage.getData().toString());


         } catch (Exception e) {


         }
     }

         Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
    }
}