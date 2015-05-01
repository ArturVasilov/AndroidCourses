package ru.guar7387.servermodule.api;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface ServerConnection {

    public void prepareAndConnect(ConnectionRequest request) throws IOException;

    public JSONObject readOutput() throws IOException;

}

