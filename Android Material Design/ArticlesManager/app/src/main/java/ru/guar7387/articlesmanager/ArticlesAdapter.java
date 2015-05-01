package ru.guar7387.articlesmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This class is Adapter for RecyclerView.
 * In RecyclerView we should ViewHolder pattern by default and we have a convinient way to do it
 * First, we create ViewHolder class which extends from RecyclerView.ViewHolder class
 * (if you new to ViewHolder pattern, please read this http://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html)
 * Next, we create a needed constructor (of course one of the parameters is List of items).
 * The second param is not so obvious. RecyclerView extends from ViewGroup, not from any List, such as AbsList,
 * so we cannot assign onItemClickListener to it, and should use trick like callback.
 * And the last thing to do - implement and override needed method.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesHolder> {

    /**
     * callback interface
     */
    static interface ItemClickListener {
        void onItemClicked(Article article);
    }

    /**
     * list of items to show
     */
    private final List<Article> mArticles;

    /**
     * for callback mechanism, called when item in RecyclerView is clicked
     */
    private final ItemClickListener itemClickListener;

    public ArticlesAdapter(List<Article> articles,
                           ItemClickListener itemClickListener) {
        this.mArticles = articles;
        this.itemClickListener = itemClickListener;
    }

    public static class ArticlesHolder extends RecyclerView.ViewHolder {

        /**
         * we need to save a reference to assign an onClickListener
         */
        private final View view;

        /**
         * TextView which shows title of article
         */
        private final TextView articleTitle;

        /**
         * TextView which shows the short description of article
         */
        private final TextView articleDescription;

        public ArticlesHolder(View itemView) {
            super(itemView);
            view = itemView;
            articleTitle = (TextView)
                    view.findViewById(R.id.articleTitleTextView);
            articleDescription = (TextView)
                    view.findViewById(R.id.articleDescriptionTextView);
        }
    }

    /**
     * this method inflates view for list item and return it
     * then onBindViewHolder is called
     * @return inflated view
     */
    @Override
    public ArticlesHolder onCreateViewHolder(
            ViewGroup parent, int index) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.articles_list_item, parent, false);
        return new ArticlesHolder(v);
    }

    /**
     * in this method we shows articles value using TextView
     * @param holder - holder for item
     * @param position - position of clicked item in list
     */
    @Override
    public void onBindViewHolder(ArticlesHolder holder, int position) {
        final Article article = mArticles.get(position);
        //we need to open we dialog, but it's bad idea to do this from adapter,
        //so I just use a callback
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClicked(article);
            }
        });
        //setting other fields
        holder.articleTitle.setText(article.getTitle());
        holder.articleDescription.setText(article.getDescription());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
