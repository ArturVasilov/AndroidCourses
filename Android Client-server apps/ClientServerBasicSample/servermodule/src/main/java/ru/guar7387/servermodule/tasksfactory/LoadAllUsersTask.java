package ru.guar7387.servermodule.tasksfactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import ru.guar7387.servermodule.callbacks.Callback;
import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.ConnectionRequest;
import ru.guar7387.servermodule.data.User;

public class LoadAllUsersTask extends AbstractTask {

    private static final String TAG = LoadAllUsersTask.class.getSimpleName();

    private final Map<Integer, User> mUsers;

    public LoadAllUsersTask(int requestCode, ConnectionRequest request,
                               ConnectionManager connectionManager, Callback callback,
                               Map<Integer, User> users) {
        super(requestCode, request, connectionManager, callback);
        this.mUsers = users;
    }

    @Override
    public void parseJson(JSONObject answer) {
        Logger.log(TAG, "answer - " + answer);
        try {
            JSONArray articles = answer.getJSONObject("response").getJSONArray("answer");
            for (int i = 0; i < articles.length(); i++) {
                try {
                    JSONArray array = articles.getJSONArray(i);
                    int id = array.getInt(0);
                    String email = array.getString(1);
                    String name = array.getString(2);
                    //noinspection ObjectAllocationInLoop
                    User user = new User(id, email, "*****", name);
                    mUsers.put(id, user);
                } catch (JSONException ignored) {
                }
            }
        } catch (JSONException ignored) {
        }
    }
}
