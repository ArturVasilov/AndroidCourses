package ru.guar7387.servermodule.local.database;

public interface Constants {

    public static interface Articles {
        public static final String TABLE_NAME = "articles";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String URL = "url";
        public static final String DATE = "date";
    }

    public static interface Users {
        public static final String TABLE_NAME = "users";
        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String NAME = "name";
    }

    public static interface Comments {
        public static final String TABLE_NAME = "comments";
        public static final String ID = "id";
        public static final String ARTICLE_ID = "article_id";
        public static final String USER_ID = "user_id";
        public static final String TEXT = "text";
    }

    public static interface Votes {
        public static final String TABLE_NAME = "votes";
        public static final String ID = "id";
        public static final String ARTICLE_ID = "article_id";
        public static final String COMMENT_ID = "comment_id";
        public static final String USER_ID = "user_id";
        public static final String RATING = "rating";
    }

}
