package ru.guar7387.clientserverwithvolleysample.server.callbacks;

import java.util.List;

import ru.guar7387.clientserverwithvolleysample.data.Article;

public interface AllArticlesLoadedCallback {

    public void onAllArticlesLoaded(List<Article> articles);

}
