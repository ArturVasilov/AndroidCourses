package ru.guar7387.servermodule.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.Map;

import ru.guar7387.servermodule.data.Article;
import ru.guar7387.servermodule.data.Comment;
import ru.guar7387.servermodule.data.User;
import ru.guar7387.servermodule.data.Vote;

public class DefaultDatabaseProvider implements DatabaseProvider, Constants {

    private final LocalDatabaseHelper mHelper;

    private SQLiteDatabase mDatabase;

    public DefaultDatabaseProvider(Context context) {
        mHelper = new LocalDatabaseHelper(context);
    }

    @Override
    public void open(final DatabaseOpenCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mDatabase = mHelper.getWritableDatabase();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.databaseOpened();
            }
        }.execute();
    }

    @Override
    public void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    @Override
    public void readAllArticles(Map<Integer, Article> articles) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }

        Cursor cursor = mDatabase.query(Articles.TABLE_NAME, new String[]{Articles.ID,
                        Articles.TITLE, Articles.DESCRIPTION, Articles.URL, Articles.DATE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String url = cursor.getString(3);
            String date = cursor.getString(4);
            //noinspection ObjectAllocationInLoop
            Article article = new Article(id, title, description, url, date);
            articles.put(id, article);
        }
        cursor.close();
    }

    @Override
    public void updateArticles(Map<Integer, Article> articles) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }
        mDatabase.delete(Articles.TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        for (Article article : articles.values()) {
            values.clear();
            values.put(Articles.ID, article.getId());
            values.put(Articles.TITLE, article.getTitle());
            values.put(Articles.DESCRIPTION, article.getShortDescription());
            values.put(Articles.URL, article.getUrl());
            values.put(Articles.DATE, article.getDate());
            mDatabase.insertWithOnConflict(Articles.TABLE_NAME, "null", values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    @Override
    public void readAllUsers(Map<Integer, User> users) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }

        Cursor cursor = mDatabase.query(Users.TABLE_NAME, new String[]{Users.ID, Users.NAME, Users.EMAIL},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            //noinspection ObjectAllocationInLoop
            User user = new User(id, email, "*****", name);
            users.put(id, user);
        }
        cursor.close();
    }

    @Override
    public void updateUsers(Map<Integer, User> users) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }
        mDatabase.delete(Users.TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        for (User user : users.values()) {
            values.clear();
            values.put(Users.ID, user.getId());
            values.put(Users.NAME, user.getName());
            values.put(Users.EMAIL, user.getEmail());
            mDatabase.insertWithOnConflict(Users.TABLE_NAME, "null", values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    @Override
    public void readAllComments(Map<Integer, Comment> comments) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }

        Cursor cursor = mDatabase.query(Comments.TABLE_NAME, new String[]{Comments.ID,
                        Comments.ARTICLE_ID, Comments.USER_ID, Comments.TEXT},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int articleId = cursor.getInt(1);
            int userId = cursor.getInt(2);
            String text = cursor.getString(3);
            //noinspection ObjectAllocationInLoop
            Comment comment = new Comment(id, articleId, userId, text);
            comments.put(id, comment);
        }
        cursor.close();
    }

    @Override
    public void updateComments(Map<Integer, Comment> comments) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }
        mDatabase.delete(Comments.TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        for (Comment comment : comments.values()) {
            values.clear();
            values.put(Comments.ID, comment.getId());
            values.put(Comments.ARTICLE_ID, comment.getArticleId());
            values.put(Comments.USER_ID, comment.getUserId());
            values.put(Comments.TEXT, comment.getText());
            mDatabase.insertWithOnConflict(Comments.TABLE_NAME, "null", values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    @Override
    public void readAllVotes(Map<Integer, Vote> votes) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }

        Cursor cursor = mDatabase.query(Votes.TABLE_NAME, new String[]{Votes.ID,
                        Votes.ARTICLE_ID, Votes.COMMENT_ID, Votes.USER_ID, Votes.RATING},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int articleId = cursor.getInt(1);
            int commentId = cursor.getInt(2);
            int userId = cursor.getInt(3);
            int rating = cursor.getInt(4);
            //noinspection ObjectAllocationInLoop
            Vote vote = new Vote(id, articleId, commentId, userId, rating);
            votes.put(id, vote);
        }
        cursor.close();
    }

    @Override
    public void updateVotes(Map<Integer, Vote> votes) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }
        mDatabase.delete(Votes.TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        for (Vote vote : votes.values()) {
            values.clear();
            values.put(Votes.ID, vote.getId());
            values.put(Votes.ARTICLE_ID, vote.getArticleId());
            values.put(Votes.COMMENT_ID, vote.getCommentId());
            values.put(Votes.USER_ID, vote.getUserId());
            values.put(Votes.RATING, vote.getRating());
            mDatabase.insertWithOnConflict(Votes.TABLE_NAME, "null", values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    @Override
    public void addComment(Comment comment) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Comments.ID, comment.getId());
        values.put(Comments.ARTICLE_ID, comment.getArticleId());
        values.put(Comments.USER_ID, comment.getUserId());
        values.put(Comments.TEXT, comment.getText());
        mDatabase.insertWithOnConflict(Comments.TABLE_NAME, "null", values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void addVote(Vote vote) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Votes.ID, vote.getId());
        values.put(Votes.ARTICLE_ID, vote.getArticleId());
        values.put(Votes.COMMENT_ID, vote.getCommentId());
        values.put(Votes.USER_ID, vote.getUserId());
        values.put(Votes.RATING, vote.getRating());
        mDatabase.insertWithOnConflict(Votes.TABLE_NAME, "null", values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void removeComment(int id) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }

        mDatabase.delete(Comments.TABLE_NAME, Comments.ID + "=" + id, null);
    }

    @Override
    public void removeVote(int id) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mHelper.getWritableDatabase();
        }
        if (mDatabase == null || !mDatabase.isOpen()) {
            return;
        }

        mDatabase.delete(Votes.TABLE_NAME, Votes.ID + "=" + id, null);
    }

    @Override
    public void clear() {
        mDatabase.delete(Articles.TABLE_NAME, null, null);
        mDatabase.delete(Users.TABLE_NAME, null, null);
        mDatabase.delete(Comments.TABLE_NAME, null, null);
        mDatabase.delete(Votes.TABLE_NAME, null, null);
    }
}
