package ru.guar7387.servermodule.api;

import ru.guar7387.servermodule.data.Comment;
import ru.guar7387.servermodule.data.User;
import ru.guar7387.servermodule.data.Vote;

public interface RequestFactory {

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

    public ConnectionRequest createRequest(String apiKey);

    public ConnectionRequest createArticleRequest(int articleId);

    public ConnectionRequest createArticleCommentsRequest(int articleId);

    public ConnectionRequest createUserRequest(int userId);

    public ConnectionRequest createRegistrationRequest(User user);

    public ConnectionRequest createLogInRequest(User user);

    public ConnectionRequest createAddCommentRequest(Comment comment, User currentUser);

    public ConnectionRequest createArticleVotesRequest(int articleId);

    public ConnectionRequest createCommentVotesRequest(int commentId);

    public ConnectionRequest createAddVoteRequest(Vote vote, User currentUser);
}



