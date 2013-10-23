package com.cuuuurzel.bugs.service;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationService extends NotificationListenerService {

    public static final String TAG = "Notification Service";
    public static final String N_POSTED = "com.cuuuurzel.notificationPosted";
    public static final String N_REMOVED = "com.cuuuurzel.notificationRemoved";

    @Override
    public void onCreate() {
    	super.onCreate();
    }
    
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
		sendNotification( sbn, N_POSTED );
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
		sendNotification( sbn, N_REMOVED );
    }
    
    private void sendNotification( StatusBarNotification sbn, String ACTION ) {
    	Intent i = new Intent( ACTION );
    	i.putExtra( "package", sbn.getPackageName() );
    	i.putExtra( "id", sbn.getId() );
    	sendBroadcast( i );    	
    }
}