package ru.guar7387.clientserverbasicsample.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.guar7387.clientserverbasicsample.R;
import ru.guar7387.servermodule.data.Article;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesHolder> {

    static interface ItemClickListener {
        void onItemClicked(Article article);
    }

    private final List<Article> mArticles;

    private final ItemClickListener itemClickListener;

    public ArticlesAdapter(List<Article> articles, ItemClickListener itemClickListener) {
        this.mArticles = articles;
        this.itemClickListener = itemClickListener;
    }

    public static class ArticlesHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView articleTitle;
        private final TextView articleDescription;

        public ArticlesHolder(View itemView) {
            super(itemView);
            view = itemView;
            articleTitle = (TextView) view.findViewById(R.id.articleTitleTextView);
            articleDescription = (TextView) view.findViewById(R.id.articleDescriptionTextView);
        }
    }

    @Override
    public ArticlesHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ArticlesHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.article_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ArticlesHolder holder, int position) {
        final Article article = mArticles.get(position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClicked(article);
            }
        });
        holder.articleTitle.setText(article.getTitle());
        holder.articleDescription.setText(article.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
