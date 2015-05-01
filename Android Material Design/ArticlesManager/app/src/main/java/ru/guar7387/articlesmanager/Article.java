package ru.guar7387.articlesmanager;

/**
 * this is a simple class introducing Article
 * it contains 4 final fields, initialized in constructor and used later
 */
public class Article {

    private final String tag;

    private final String title;

    private final String description;

    private final String url;

    public Article(String tag, String title, String description, String url) {
        this.tag = tag;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
