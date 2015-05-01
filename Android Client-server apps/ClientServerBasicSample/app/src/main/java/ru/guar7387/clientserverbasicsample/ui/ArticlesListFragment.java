package ru.guar7387.clientserverbasicsample.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.guar7387.clientserverbasicsample.R;
import ru.guar7387.servermodule.DataModel;
import ru.guar7387.servermodule.data.Article;

public class ArticlesListFragment extends Fragment implements ArticlesAdapter.ItemClickListener {

    private DataModel mModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView root = (RecyclerView) inflater.inflate(R.layout.articles_list_fragment, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        root.setLayoutManager(layoutManager);

        ArticlesAdapter adapter = new ArticlesAdapter(mModel.articles(), this);
        root.setAdapter(adapter);

        return root;
    }

    public void setDataModel(DataModel model) {
        this.mModel = model;
    }

    @Override
    public void onItemClicked(Article article) {
        OnArticleSelected onArticleSelected = (OnArticleSelected) getActivity();
        onArticleSelected.onArticleSelected(article);
    }
}


