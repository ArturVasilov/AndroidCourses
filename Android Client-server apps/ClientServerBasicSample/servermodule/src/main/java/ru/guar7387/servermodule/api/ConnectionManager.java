package ru.guar7387.servermodule.api;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private final Map<Integer, ServerConnection> mConnections;
    private final Map<Integer, Boolean> mProcessedConnections;

    public ConnectionManager() {
        mConnections = new ConcurrentHashMap<>();
        mProcessedConnections = new ConcurrentHashMap<>();
    }

    public void addConnection(int code, ServerConnection connection) {
        if (!mConnections.containsKey(code)) {
            mConnections.put(code, connection);
            mProcessedConnections.put(code, false);
        }
    }

    public JSONObject process(int code, ConnectionRequest request) {
        ServerConnection connection = mConnections.get(code);
        if (connection != null && !mProcessedConnections.get(code)) {
            mProcessedConnections.put(code, true);
            try {
                connection.prepareAndConnect(request);
                JSONObject answer = connection.readOutput();
                mConnections.remove(code);
                mProcessedConnections.remove(code);
                return answer;
            } catch (IOException ignored) {
            }
        }
        return null;
    }

    public void clear() {
        mConnections.clear();
        mProcessedConnections.clear();
    }
}



