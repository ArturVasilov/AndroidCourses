package ru.guar7387.articlesmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple singleton to work with articles.
 * Of course, it is not the best way to do it, but easiest.
 * It contains articles, which are grouped by tag in map and has methods to get articles and etc.
 * This part is not important, just to make app work.
 */
public enum ArticlesStorage {

    /**
     * enum is best singleton!
     */
    INSTANCE,
    ;

    /**
     * key is tag, List is list of articles with this tag
     */
    private final Map<String, List<Article>> articles = new HashMap<>();

    public void addArticle(Article article) {
        List<Article> list = articles.get(article.getTag());
        if (list == null) {
            List<Article> newList = new ArrayList<>();
            newList.add(article);
            articles.put(article.getTag(), newList);
        }
        else {
            list.add(article);
        }
    }

    /**
     * @param tag - given tag
     * @return list of articles with this tag
     */
    public List<Article> getArticlesByTag(String tag) {
        if (tag.equals(Tags.ALL)) {
            List<Article> list = new ArrayList<>();
            for (String key : articles.keySet()) {
                list.addAll(articles.get(key));
            }
            return list;
        }
        List<Article> list = articles.get(tag);
        return list == null ? new ArrayList<Article>() : list;
    }

    /**
     * method just for this demo app, because I'm too lazy to create an automatic parser,
     * which will get articles from some popular blogs and etc.
     * so, here I just put articles, what I find useful myself.
     */
    public void fillArticles() {
        List<Article> material = new ArrayList<>();
        material.add(new Article(Tags.MATERIAL, "Guide", "We challenged ourselves " +
                "to create a visual language for our users that synthesizes the classic " +
                "principles of good design with the innovation and possibility of technology and science. " +
                "This is material design. " +
                "This spec is a living document that will be updated as we continue to develop the tenets and specifics of material design.",
                "http://www.google.ru/design/spec/material-design/introduction.html"));
        material.add(new Article(Tags.MATERIAL, "AppCompat v21 — Material Design for Pre-Lollipop Devices!",
                "The Android 5.0 SDK was released, featuring new UI widgets and material design, our visual language focused on good design. " +
                        "To enable you to bring your latest designs to older Android platforms we have expanded our support libraries," +
                        "including a major update to AppCompat, as well as new RecyclerView, CardView and Palette libraries.",
                "http://android-developers.blogspot.ru/2014/10/appcompat-v21-material-design-for-pre.html"));
        material.add(new Article(Tags.MATERIAL, "Creating Apps with Material Design",
                "Material design is a comprehensive guide for visual, motion, and interaction design across platforms and devices. " +
                        "To use material design in your Android apps, follow the guidelines described in the material design specification " +
                        "and use the new components and functionality available in Android 5.0 (API level 21).",
                "http://developer.android.com/training/material/index.html"));
        material.add(new Article(Tags.MATERIAL, "Material design YouTube channel",
                "This channel suggest you to watch some video about new features in Android 5.0.",
                "https://www.youtube.com/playlist?list=PLWz5rJ2EKKc_rbUGf2brcwvZDwziVdtU6"));
        material.add(new Article(Tags.MATERIAL, "Material palette", "This service provides an easy way to choose material colors for your app!",
                "http://www.materialpalette.com/"));

        articles.put(Tags.MATERIAL, material);

        List<Article> widgets = new ArrayList<>();
        widgets.add(new Article(Tags.WIDGETS, "Creating a Navigation Drawer",
                "The navigation drawer is a panel that displays the app’s main navigation options on the left edge of the screen.\n" +
                        "This lesson describes how to implement a navigation drawer using the DrawerLayout APIs available in the Support Library.",
                "http://developer.android.com/training/implementing-navigation/nav-drawer.html"));
        widgets.add(new Article(Tags.WIDGETS, "Navigation Drawer design",
                "This article contains all needed rules about how Navigation Drawer should look and behave",
                "http://developer.android.com/design/patterns/navigation-drawer.html"));
        widgets.add(new Article(Tags.WIDGETS, "A First Glance at Android’s RecyclerView",
                "This post gives you an introduction to the RecyclerView, " +
                        "it’s many internal classes and interfaces, how they interact and how you can use them.",
                "http://www.grokkingandroid.com/first-glance-androids-recyclerview/"));
        widgets.add(new Article(Tags.WIDGETS, "Creating Lists and Cards",
                "To create complex lists and cards with material design styles in your apps, you can use the RecyclerView and CardView widgets.",
                "https://developer.android.com/training/material/lists-cards.html"));
        widgets.add(new Article(Tags.WIDGETS, "Sliding Tabs",
                "Tabs are great controls for your users—whether it’s for navigation, switching between different views, or filtering a data set.",
                "https://plus.google.com/+AndroidDevelopers/posts/K89RDjC4Gym"));
        widgets.add(new Article(Tags.WIDGETS, "Using ViewPager for Screen Slides",
                "Screen slides are transitions between one entire screen to another and are common with UIs like setup wizards or slideshows. " +
                        "This lesson shows you how to do screen slides with a ViewPager provided by the support library." +
                        "ViewPagers can animate screen slides automatically.",
                "http://developer.android.com/training/animation/screen-slide.html"));

        articles.put(Tags.WIDGETS, widgets);
    }

}
