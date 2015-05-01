package ru.guar7387.clientserverwithvolleysample.data;

public class Comment {

    private int id;

    private final int articleId;
    private final int userId;
    private final String text;

    public Comment(int articleId, int userId, String text) {
        this.articleId = articleId;
        this.userId = userId;
        this.text = text;
    }

    public Comment(int id, int articleId, int userId, String text) {
        this(articleId, userId, text);
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

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                '}';
    }
}
