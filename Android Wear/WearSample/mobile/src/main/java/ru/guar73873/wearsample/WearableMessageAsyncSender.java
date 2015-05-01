package ru.guar73873.wearsample;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class WearableMessageAsyncSender extends
        AsyncTask<String, MessageApi.SendMessageResult, Void> {

    private static final String TAG = WearableMessageAsyncSender.class.getSimpleName();

    private final GoogleApiClient googleClient;

    public WearableMessageAsyncSender(GoogleApiClient googleClient) {
        this.googleClient = googleClient;
    }

    @Override
    protected Void doInBackground(@NonNull String... params) {
        String path = params[0];
        String message = params[1];
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.
                getConnectedNodes(googleClient).await();
        for (Node node : nodes.getNodes()) {
            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                    googleClient, node.getId(), path, message.getBytes()).await();
            publishProgress(result);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(MessageApi.SendMessageResult... values) {
        super.onProgressUpdate(values);
        MessageApi.SendMessageResult result = values[0];
        if (result.getStatus().isSuccess()) {
            Log.i(TAG, "Message sending success");
        }
        else {
            Log.i(TAG, "Message sending failed");
        }
    }

}

