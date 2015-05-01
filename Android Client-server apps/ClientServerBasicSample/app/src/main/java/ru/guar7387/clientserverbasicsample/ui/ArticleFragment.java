package ru.guar7387.clientserverbasicsample.ui;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.guar7387.clientserverbasicsample.R;
import ru.guar7387.servermodule.DataModel;
import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.callbacks.Answer;
import ru.guar7387.servermodule.callbacks.AnswerCallback;
import ru.guar7387.servermodule.callbacks.ArticleCommentsCallback;
import ru.guar7387.servermodule.callbacks.FullArticleTextCallback;
import ru.guar7387.servermodule.data.Article;
import ru.guar7387.servermodule.data.Comment;

public class ArticleFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ArticleFragment.class.getSimpleName();

    private DataModel mDataModel;

    private Article mArticle;

    private TextView mContentText;

    private CommentsAdapter mCommentsAdapter;

    private View openArticleBrowserView;
    private View loadArticleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.article_fragment, container, false);
        TextView title = (TextView) root.findViewById(R.id.articleTitle);
        mContentText = (TextView) root.findViewById(R.id.articleContent);
        Logger.log(TAG, mArticle.toString());
        title.setText(mArticle.getTitle());
        mContentText.setText(mArticle.getShortDescription());

        openArticleBrowserView = root.findViewById(R.id.openArticleBrowser);
        loadArticleView = root.findViewById(R.id.loadArticle);
        openArticleBrowserView.setOnClickListener(this);
        loadArticleView.setOnClickListener(this);

        RecyclerView comments = (RecyclerView) root.findViewById(R.id.commentsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        comments.setLayoutManager(layoutManager);

        mCommentsAdapter = new CommentsAdapter(new ArrayList<Integer>(), mDataModel);
        comments.setAdapter(mCommentsAdapter);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openArticleBrowser:
                Uri uri = Uri.parse(mArticle.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.loadArticle:
                mDataModel.loadArticle(mArticle.getId(), new FullArticleTextCallback() {
                    @Override
                    public void onArticleLoaded(String htmlText) {
                        loadArticleView.setVisibility(View.GONE);
                        openArticleBrowserView.setVisibility(View.GONE);
                        mContentText.setText(Html.fromHtml(htmlText));
                    }
                }, new ArticleCommentsCallback() {
                    @Override
                    public void onArticleCommentLoaded(List<Integer> commentsIds) {
                        showComments(commentsIds);
                    }
                });
                break;
        }
    }

    private void addComment(final Comment comment) {
        mDataModel.addComment(comment, new AnswerCallback() {
            @Override
            public void call(Answer answer) {
                if (answer == Answer.OK) {
                    mCommentsAdapter.addComment(comment);
                }
            }
        });
    }

    private void showComments(List<Integer> commentsId) {
        Logger.log(TAG, "setting new values: " + commentsId);
        mCommentsAdapter.setNewValues(commentsId);
    }

    public void setArticle(Article article) {
        this.mArticle = article;
    }

    public void setDataModel(DataModel dataModel) {
        this.mDataModel = dataModel;
    }
}
