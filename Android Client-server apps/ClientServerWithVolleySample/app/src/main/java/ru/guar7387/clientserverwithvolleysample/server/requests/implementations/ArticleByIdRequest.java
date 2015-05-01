package ru.guar7387.clientserverwithvolleysample.server.requests.implementations;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.guar7387.clientserverwithvolleysample.data.Article;
import ru.guar7387.clientserverwithvolleysample.data.Comment;
import ru.guar7387.clientserverwithvolleysample.data.User;
import ru.guar7387.clientserverwithvolleysample.data.Vote;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.ArticleLoadedCallback;
import ru.guar7387.clientserverwithvolleysample.server.requests.AndroidTutorialsJsonRequest;
import ru.guar7387.clientserverwithvolleysample.utils.Logger;

public class ArticleByIdRequest extends AndroidTutorialsJsonRequest {

    public static final String TAG = ArticleByIdRequest.class.getSimpleName();

    private final int articleId;

    private final ArticleLoadedCallback callback;

    public ArticleByIdRequest(int articleId, ArticleLoadedCallback callback) {
        this.articleId = articleId;
        this.callback = callback;
    }

    @Override
    public Map<String, String> getParams() {
        return new HashMap<String, String>() {{
            put(ARTICLE_BY_ID, "{\"token\":\"" + API_KEY + "\",\"id\":\"" + articleId + "\"}");
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
                        return;
                    }
                    JSONArray articleArray = answer.getJSONArray("article");
                    int id = articleArray.getInt(0);
                    String title = articleArray.getString(1);
                    String description = articleArray.getString(2);
                    String url = articleArray.getString(3);
                    String date = articleArray.getString(4);
                    String htmlText = articleArray.getString(5);
                    Article article = new Article(id, title, description, url, date);

                    JSONArray comments = answer.getJSONArray("comments");
                    List<Comment> commentsList = new ArrayList<>();
                    for (int i = 0; i < comments.length(); i++) {
                        JSONArray array = comments.getJSONArray(i);
                        int commentId = array.getInt(0);
                        int userId = array.getInt(1);
                        String text = array.getString(2);
                        Comment comment = new Comment(commentId, id, userId, text);
                        commentsList.add(comment);
                    }

                    JSONArray users = answer.getJSONArray("users");
                    Map<Integer, User> usersMap = new HashMap<>();
                    for (int i = 0; i < users.length(); i++) {
                        JSONArray array = users.getJSONArray(i);
                        int userId = array.getInt(0);
                        String email = array.getString(1);
                        String name = array.getString(2);
                        User user = new User(userId, email, "******", name);
                        usersMap.put(userId, user);
                    }

                    JSONArray votes = answer.getJSONArray("votes");
                    List<Vote> articleVotes = new ArrayList<>();
                    Map<Integer, List<Vote>> commentsVotes = new HashMap<>();
                    for (int i = 0; i < votes.length(); i++) {
                        JSONArray array = votes.getJSONArray(i);
                        int voteId = array.getInt(0);
                        int voteArticleId = array.getInt(1);
                        int voteCommentId = array.getInt(2);
                        int voteUserId = array.getInt(3);
                        int rating = array.getInt(4);
                        Vote vote = new Vote(voteId, voteArticleId, voteCommentId, voteUserId, rating);
                        if (voteArticleId == 0) {
                            List<Vote> votesList = commentsVotes.get(voteCommentId);
                            if (votesList == null) {
                                votesList = new ArrayList<>();
                                commentsVotes.put(voteCommentId, votesList);
                            }
                            votesList.add(vote);
                        }
                        else {
                            articleVotes.add(vote);
                        }
                    }

                    callback.onArticleLoaded(article, htmlText, commentsList, usersMap,
                            articleVotes, commentsVotes);
                } catch (JSONException ignored) {
                    Logger.log(TAG, "Exception was produced during parsing json; " + ignored.getMessage());
                }
            }
        };
    }
}
