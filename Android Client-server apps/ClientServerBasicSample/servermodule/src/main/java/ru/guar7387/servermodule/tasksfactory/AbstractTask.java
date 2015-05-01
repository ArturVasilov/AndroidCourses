package ru.guar7387.servermodule.tasksfactory;

import android.os.AsyncTask;

import org.json.JSONObject;

import ru.guar7387.servermodule.callbacks.Callback;
import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.ConnectionRequest;
import ru.guar7387.servermodule.api.ServerConnection;
import ru.guar7387.servermodule.api.StandardServerConnection;

public abstract class AbstractTask extends AsyncTask<Void, Void, Void> {

    private static final String API_URL = "http://androidtutorials.ru/api/";

    private final int requestCode;

    private final ConnectionRequest request;
    private final ConnectionManager connectionManager;
    private final Callback callback;

    protected AbstractTask(int requestCode, ConnectionRequest request,
                           ConnectionManager connectionManager, Callback callback) {
        this.requestCode = requestCode;
        this.request = request;
        this.connectionManager = connectionManager;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ServerConnection connection = new StandardServerConnection(API_URL);
        connectionManager.addConnection(requestCode, connection);
        JSONObject jsonObject = connectionManager.process(requestCode, request);
        parseJson(jsonObject);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            callback.call();
        }
    }

    public abstract void parseJson(JSONObject answer);
}


