package ru.guar7387.servermodule.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ru.guar7387.servermodule.data.Comment;
import ru.guar7387.servermodule.data.User;
import ru.guar7387.servermodule.data.Vote;

public class SimpleRequestFactory implements RequestFactory {

    public SimpleRequestFactory() {
    }

    @Override
    public ConnectionRequest createRequest(final String apiKey) {
        String params = "";
        if (apiKey.equals(ALL_ARTICLES) || apiKey.equals(ALL_USERS) ||
                apiKey.equals(ALL_COMMENTS) || apiKey.equals(ALL_VOTES)) {
            params = "{\"token\":\"" + API_KEY + "\"}";
        }

        final String paramsCopy = params;

        return new ConnectionRequest() {
            @Override
            public String createRequest() {
                return constructRequest(apiKey, paramsCopy);
            }
        };
    }

    @Override
    public ConnectionRequest createArticleRequest(int articleId) {
        final String apiKey = ARTICLE_BY_ID;
        final String params = "{\"token\":\"" + API_KEY + "\",\"id\":\"" + articleId + "\"}";
        return new ConnectionRequest() {
            @Override
            public String createRequest() {
                return constructRequest(apiKey, params);
            }
        };
    }

    @Override
    public ConnectionRequest createArticleCommentsRequest(int articleId) {
        final String apiKey = ARTICLE_COMMENTS;
        final String params = "{\"token\":\"" + API_KEY + "\",\"id\":\"" + articleId + "\"}";
        return new ConnectionRequest() {
            @Override
            public String createRequest() {
                return constructRequest(apiKey, params);
            }
        };
    }

    @Override
    public ConnectionRequest createUserRequest(int userId) {
        final String apiKey = USER_BY_ID;
        final String params = "{\"token\":\"" + API_KEY + "\",\"id\":\"" + userId + "\"}";
        return new ConnectionRequest() {
            @Override
            public String createRequest() {
                return constructRequest(apiKey, params);
            }
        };
    }

    @Override
    public ConnectionRequest createRegistrationRequest(User user) {
        //TODO
        return null;
    }

    @Override
    public ConnectionRequest createLogInRequest(User user) {
        //TODO
        return null;
    }

    @Override
    public ConnectionRequest createAddCommentRequest(Comment comment, User currentUser) {
        final String apiKey = ADD_COMMENT;
        final String params = "{\"token\":\"" + API_KEY + "\"," +
                "\"user_id\":\"" + currentUser.getId() + "\"," +
                "\"password\":\"" + currentUser.getPassword() + "\"," +
                "\"article_id\":\"" + comment.getArticleId() + "\"," +
                "\"text\":\"" + comment.getText() + "\"}";
        return new ConnectionRequest() {
            @Override
            public String createRequest() {
                return constructRequest(apiKey, params);
            }
        };
    }

    @Override
    public ConnectionRequest createArticleVotesRequest(int articleId) {
        //TODO
        return null;
    }

    @Override
    public ConnectionRequest createCommentVotesRequest(int commentId) {
        //TODO
        return null;
    }

    @Override
    public ConnectionRequest createAddVoteRequest(Vote vote, User currentUser) {
        //TODO
        return null;
    }

    public String constructRequest(String methodName, String methodParams) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(URLEncoder.encode(methodName, "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(methodParams, "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {
        }
        return builder.toString();
    }
}


