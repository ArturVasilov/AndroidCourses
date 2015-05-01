package ru.guar7387.clientserverbasicsample.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ru.guar7387.clientserverbasicsample.R;
import ru.guar7387.servermodule.callbacks.Callback;
import ru.guar7387.servermodule.DataModel;
import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.data.Article;

public class MainActivity extends ActionBarActivity implements OnArticleSelected {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DataModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mModel = new DataModel(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.log(TAG, "onStart");
        mModel.init();
        mModel.loadAllInfo(new Callback() {
            @Override
            public void call() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                showArticlesList();
            }
        });
    }

    @Override
    protected void onStop() {
        mModel.clearAndSave();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mModel.clearAndSave();
        super.onDestroy();
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
        fragment.setDataModel(mModel);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, fragment);
        transaction.commit();
    }

    @Override
    public void onArticleSelected(Article article) {
        ArticleFragment fragment = new ArticleFragment();
        fragment.setDataModel(mModel);
        fragment.setArticle(article);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, fragment);
        transaction.addToBackStack("article");
        transaction.commit();
    }
}

