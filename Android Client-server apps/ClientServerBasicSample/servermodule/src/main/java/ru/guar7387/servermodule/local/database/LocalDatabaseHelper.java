package ru.guar7387.servermodule.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ru.guar7387.database";

    private static final int DATABASE_VERSION = 1;

    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(articlesTableCreationRequest());
        db.execSQL(usersTableCreationRequest());
        db.execSQL(commentsTableCreationRequest());
        db.execSQL(votesTableCreationRequest());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(articlesTableDropRequest());
        db.execSQL(usersTableDropRequest());
        db.execSQL(commentsTableDropRequest());
        db.execSQL(votesTableDropRequest());

        onCreate(db);
    }

    private String articlesTableCreationRequest() {
        return "CREATE TABLE IF NOT EXISTS " + Constants.Articles.TABLE_NAME + " (" +
                Constants.Articles.ID + " INTEGER PRIMARY KEY, " +
                Constants.Articles.TITLE + " VARCHAR(100), " +
                Constants.Articles.DESCRIPTION + " VARCHAR(500), " +
                Constants.Articles.URL + " VARCHAR(100), " +
                Constants.Articles.DATE + " VARCHAR(50)" + ");";
    }

    private String usersTableCreationRequest() {
        return "CREATE TABLE IF NOT EXISTS " + Constants.Users.TABLE_NAME + " (" +
                Constants.Users.ID + " INTEGER PRIMARY KEY, " +
                Constants.Users.NAME + " VARCHAR(50), " +
                Constants.Users.EMAIL + " VARCHAR(100)" + ");";
    }

    private String commentsTableCreationRequest() {
        return "CREATE TABLE IF NOT EXISTS " + Constants.Comments.TABLE_NAME + " (" +
                Constants.Comments.ID + " INTEGER PRIMARY KEY, " +
                Constants.Comments.ARTICLE_ID + " INTEGER, " +
                Constants.Comments.USER_ID + " INTEGER, " +
                Constants.Comments.TEXT + " VARCHAR(500)" + ");";
    }

    private String votesTableCreationRequest() {
        return "CREATE TABLE IF NOT EXISTS " + Constants.Votes.TABLE_NAME + " (" +
                Constants.Votes.ID + " INTEGER PRIMARY KEY, " +
                Constants.Votes.ARTICLE_ID + " INTEGER, " +
                Constants.Votes.COMMENT_ID + " INTEGER, " +
                Constants.Votes.USER_ID + " INTEGER, " +
                Constants.Votes.RATING + " INTEGER" + ");";
    }

    private String articlesTableDropRequest() {
        return "DROP TABLE IF EXISTS " + Constants.Articles.TABLE_NAME;
    }

    private String usersTableDropRequest() {
        return "DROP TABLE IF EXISTS " + Constants.Users.TABLE_NAME;
    }

    private String commentsTableDropRequest() {
        return "DROP TABLE IF EXISTS " + Constants.Comments.TABLE_NAME;
    }

    private String votesTableDropRequest() {
        return "DROP TABLE IF EXISTS " + Constants.Votes.TABLE_NAME;
    }
}
