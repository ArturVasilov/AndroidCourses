package ru.guar7387.clientserverwithvolleysample.server.requests.implementations;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ru.guar7387.clientserverwithvolleysample.data.User;
import ru.guar7387.clientserverwithvolleysample.data.Vote;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.AddVoteCallback;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.Answer;
import ru.guar7387.clientserverwithvolleysample.server.requests.AndroidTutorialsJsonRequest;
import ru.guar7387.clientserverwithvolleysample.utils.Logger;

public class AddVoteRequest extends AndroidTutorialsJsonRequest {

    private static final String TAG = AddVoteRequest.class.getSimpleName();

    private final Vote vote;

    private final User user;

    private final AddVoteCallback callback;

    public AddVoteRequest(Vote vote, User user, AddVoteCallback callback) {
        this.vote = vote;
        this.user = user;
        this.callback = callback;
    }

    @Override
    public Map<String, String> getParams() {
        final String voteObject = vote.getArticleId() != 0 ?
                "\"article_id\":\"" + vote.getArticleId() + "\"," :
                "\"comment_id\":\"" + vote.getCommentId() + "\",";
        return new HashMap<String, String>() {{
            put(ADD_VOTE, "{\"token\":\"" + API_KEY + "\"," +
                    "\"user_id\":\"" + user.getId() + "\"," +
                    "\"password\":\"" + user.getPassword() + "\"," +
                    voteObject +
                    "\"rating\":\"" + vote.getRating() + "\"}");
        }};
    }

    @Override
    public Response.Listener<JSONObject> getSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Logger.log(TAG, "Response");
                    JSONObject answer = response.getJSONObject("response");
                    if (!answer.getString("answer").equalsIgnoreCase("ok")) {
                        callback.call(Answer.FAIL, 0);
                        return;
                    }
                    int id = answer.getInt("id");
                    callback.call(Answer.OK, id);
                } catch (JSONException ignored) {
                }
            }
        };
    }
}
