package ru.guar7387.clientserverwithvolleysample.data;

public class Article {

    private final int id;
    private final String title;
    private final String shortDescription;
    private final String url;
    private final String date;

    public Article(int id, String title, String shortDescription, String url, String date) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.url = url;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
