package ru.guar7387.servermodule.data;

public class Vote {

    private int id;

    private final int articleId;
    private final int commentId;
    private final int userId;

    private final int rating;

    public Vote(int articleId, int commentId, int userId, int rating) {
        this.articleId = articleId;
        this.commentId = commentId;
        this.userId = userId;
        this.rating = rating;
    }

    public Vote(int id, int articleId, int commentId, int userId, int rating) {
        this(articleId, commentId, userId, rating);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", commentId=" + commentId +
                ", userId=" + userId +
                ", rating=" + rating +
                '}';
    }
}
