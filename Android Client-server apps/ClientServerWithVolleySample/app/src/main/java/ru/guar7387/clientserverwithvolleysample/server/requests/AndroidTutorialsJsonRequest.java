package ru.guar7387.clientserverwithvolleysample.server.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

import ru.guar7387.clientserverwithvolleysample.utils.Logger;


public abstract class AndroidTutorialsJsonRequest implements CustomJsonRequest {

    private static final String TAG = AndroidTutorialsJsonRequest.class.getSimpleName();

    public static final String API_KEY = "kktb56yjdfg3691ubmpd0qhd";

    public static final String ALL_ARTICLES = "ApiBlog.allArticles";
    public static final String ALL_USERS = "ApiBlog.allUsers";
    public static final String ALL_COMMENTS = "ApiBlog.allComments";
    public static final String ALL_VOTES = "ApiBlog.allVotes";
    public static final String ARTICLE_BY_ID = "ApiBlog.getArticleById";
    public static final String ARTICLE_COMMENTS = "ApiBlog.getArticleComments";
    public static final String USER_BY_ID = "ApiBlog.getUserById";
    public static final String REGISTER_PERSON = "ApiBlog.registerPerson";
    public static final String LOG_IN_PERSON = "ApiBlog.logInPerson";
    public static final String ADD_COMMENT = "ApiBlog.addComment";
    public static final String GET_ARTICLE_VOTES = "ApiBlog.getArticlesVotes";
    public static final String GET_COMMENT_VOTES = "ApiBlog.getCommentVotes";
    public static final String ADD_VOTE = "ApiBlog.vote";

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public String getUrl() {
        return "http://androidtutorials.ru/api/";
    }

    @Override
    public Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //We do not concentrate on error handling now
                Logger.log(TAG, "error");
            }
        };
    }
}
