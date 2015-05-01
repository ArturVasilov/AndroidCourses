package ru.guar7387.clientserverwithvolleysample.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;

import java.util.List;

import ru.guar7387.clientserverwithvolleysample.R;
import ru.guar7387.clientserverwithvolleysample.data.Article;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.AllArticlesLoadedCallback;
import ru.guar7387.clientserverwithvolleysample.server.requests.CustomRequest;
import ru.guar7387.clientserverwithvolleysample.server.requests.implementations.AllArticlesRequest;
import ru.guar7387.clientserverwithvolleysample.utils.MyApplication;

public class ArticlesListFragment extends Fragment implements ArticlesAdapter.ItemClickListener {

    private List<Article> mArticles;

    private RecyclerView mArticlesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.articles_list_fragment, container, false);
        final ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        mArticlesList = (RecyclerView) root.findViewById(R.id.articlesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mArticlesList.setLayoutManager(layoutManager);

        RequestQueue requestQueue = ((MyApplication) getActivity().getApplication()).getRequestQueue();
        requestQueue.add(new CustomRequest(new AllArticlesRequest(new AllArticlesLoadedCallback() {
            @Override
            public void onAllArticlesLoaded(List<Article> articles) {
                progressBar.setVisibility(View.GONE);
                mArticles = articles;
                showArticlesList();
            }
        })));

        return root;
    }

    private void showArticlesList() {
        ArticlesAdapter adapter = new ArticlesAdapter(mArticles, this);
        mArticlesList.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Article article) {
        OnArticleSelected onArticleSelected = (OnArticleSelected) getActivity();
        onArticleSelected.onArticleSelected(article);
    }
}


