package ru.guar7387.servermodule.tasksfactory;

import org.json.JSONObject;

import java.util.Map;

import ru.guar7387.servermodule.callbacks.Callback;
import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.api.ConnectionManager;
import ru.guar7387.servermodule.api.ConnectionRequest;
import ru.guar7387.servermodule.data.Vote;

public class LoadAllVotesTask extends AbstractTask {

    private static final String TAG = LoadAllVotesTask.class.getSimpleName();

    private final Map<Integer, Vote> mVotes;

    public LoadAllVotesTask(int requestCode, ConnectionRequest request,
                               ConnectionManager connectionManager, Callback callback,
                               Map<Integer, Vote> votes) {
        super(requestCode, request, connectionManager, callback);
        this.mVotes = votes;
    }

    @Override
    public void parseJson(JSONObject answer) {
        Logger.log(TAG, "answer - " + answer);
        //TODO
    }
}
