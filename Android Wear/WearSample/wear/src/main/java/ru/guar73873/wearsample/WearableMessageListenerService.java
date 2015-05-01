package ru.guar73873.wearsample;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WearableMessageListenerService extends WearableListenerService {

    private static final String TAG = WearableMessageListenerService.class.getSimpleName();

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals("/MainActivity")) {
            final String message = new String(messageEvent.getData());
            Log.i(TAG, "Message path received on watch is: " + messageEvent.getPath());
            Log.i(TAG, "Message received on watch is: " + message);

            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }

}


