package ru.guar7387.clientserverwithvolleysample.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.RequestQueue;

import java.util.List;

import ru.guar7387.clientserverwithvolleysample.R;
import ru.guar7387.clientserverwithvolleysample.data.Article;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.AllArticlesLoadedCallback;
import ru.guar7387.clientserverwithvolleysample.server.requests.implementations.AllArticlesRequest;
import ru.guar7387.clientserverwithvolleysample.server.requests.CustomRequest;
import ru.guar7387.clientserverwithvolleysample.utils.MyApplication;

public class MainActivity extends ActionBarActivity implements OnArticleSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showArticlesList();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() >= 1) {
            fragmentManager.popBackStackImmediate();
            fragmentManager.beginTransaction().commit();
        }
        else {
            super.onBackPressed();
        }
    }

    private void showArticlesList() {
        ArticlesListFragment fragment = new ArticlesListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, fragment);
        transaction.commit();
    }

    @Override
    public void onArticleSelected(Article article) {
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArticle(article);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, fragment);
        transaction.addToBackStack("article");
        transaction.commit();
    }

}
