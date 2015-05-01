package ru.guar7387.servermodule.local.database;

import java.util.Map;

import ru.guar7387.servermodule.data.Article;
import ru.guar7387.servermodule.data.Comment;
import ru.guar7387.servermodule.data.User;
import ru.guar7387.servermodule.data.Vote;

public interface DatabaseProvider {

    public interface DatabaseOpenCallback {
        public void databaseOpened();
    }

    public void open(DatabaseOpenCallback callback);

    public void close();

    public void readAllArticles(Map<Integer, Article> articles);

    public void updateArticles(Map<Integer, Article> articles);

    public void readAllUsers(Map<Integer, User> users);

    public void updateUsers(Map<Integer, User> users);

    public void readAllComments(Map<Integer, Comment> comments);

    public void updateComments(Map<Integer, Comment> comments);

    public void readAllVotes(Map<Integer, Vote> votes);

    public void updateVotes(Map<Integer, Vote> votes);

    public void addComment(Comment comment);

    public void addVote(Vote vote);

    public void removeComment(int id);

    public void removeVote(int id);

    public void clear();
}
