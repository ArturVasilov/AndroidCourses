package ru.guar7387.clientserverwithvolleysample.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.guar7387.clientserverwithvolleysample.R;
import ru.guar7387.clientserverwithvolleysample.data.Article;
import ru.guar7387.clientserverwithvolleysample.data.Comment;
import ru.guar7387.clientserverwithvolleysample.data.User;
import ru.guar7387.clientserverwithvolleysample.data.Vote;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.AddCommentCallback;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.AddVoteCallback;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.Answer;
import ru.guar7387.clientserverwithvolleysample.server.callbacks.ArticleLoadedCallback;
import ru.guar7387.clientserverwithvolleysample.server.requests.CustomRequest;
import ru.guar7387.clientserverwithvolleysample.server.requests.implementations.AddCommentRequest;
import ru.guar7387.clientserverwithvolleysample.server.requests.implementations.AddVoteRequest;
import ru.guar7387.clientserverwithvolleysample.server.requests.implementations.ArticleByIdRequest;
import ru.guar7387.clientserverwithvolleysample.utils.Logger;
import ru.guar7387.clientserverwithvolleysample.utils.MyApplication;

public class ArticleFragment extends Fragment implements View.OnClickListener,
        CommentsAdapter.ItemClickListener, View.OnLongClickListener {

    private static final String TAG = ArticleFragment.class.getSimpleName();

    private Article mArticle;

    private RecyclerView mRecyclerView;

    private View mOpenArticleBrowserView;
    private View mLoadArticleView;

    private TextView mContentText;
    private TextView mTitleText;

    private EditText mNewCommentEdit;

    private CommentsAdapter mCommentsAdapter;

    private MyApplication mApplication;
    private RequestQueue mRequestQueue;

    private RatingBarDialog mRatingBarDialog;

    private List<Vote> mArticleVotes;

    public ArticleFragment() {
        mArticleVotes = new ArrayList<>();
    }

    @SuppressLint("UseSparseArrays")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.article_fragment, container, false);
        mTitleText = (TextView) root.findViewById(R.id.articleTitle);
        mContentText = (TextView) root.findViewById(R.id.articleContent);
        Logger.log(TAG, mArticle.toString());
        mTitleText.setText(mArticle.getTitle());
        mContentText.setText(Html.fromHtml(mArticle.getShortDescription()));

        mApplication = (MyApplication) getActivity().getApplication();
        mRequestQueue = mApplication.getRequestQueue();

        mOpenArticleBrowserView = root.findViewById(R.id.openArticleBrowser);
        mLoadArticleView = root.findViewById(R.id.loadArticle);
        mOpenArticleBrowserView.setOnClickListener(this);
        mLoadArticleView.setOnClickListener(this);

        mNewCommentEdit = (EditText) root.findViewById(R.id.newCommentEdit);
        root.findViewById(R.id.newCommentButton).setOnClickListener(this);

        root.findViewById(R.id.articleContentCard).setOnLongClickListener(this);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.commentsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mCommentsAdapter = new CommentsAdapter(new ArrayList<Comment>(), new HashMap<Integer, User>(),
                new HashMap<Integer, List<Vote>>(), this);
        mRecyclerView.setAdapter(mCommentsAdapter);

        return root;
    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.openArticleBrowser:
                Uri uri = Uri.parse(mArticle.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.loadArticle:
                mRequestQueue.add(new CustomRequest(new ArticleByIdRequest(mArticle.getId(),
                        new ArticleLoadedCallback() {
                            @Override
                            public void onArticleLoaded(Article article, String htmlText,
                                                        List<Comment> comments,
                                                        Map<Integer, User> commentsAuthors,
                                                        List<Vote> articleVotes,
                                                        Map<Integer, List<Vote>> commentsVotes) {
                                Logger.log(TAG, "Article was load");
                                mLoadArticleView.setVisibility(View.GONE);
                                mOpenArticleBrowserView.setVisibility(View.GONE);
                                mContentText.setText(Html.fromHtml(htmlText));
                                mArticleVotes = articleVotes;
                                updateArticleRating();
                                showComments(comments, commentsAuthors, commentsVotes);
                            }
                        })));
                break;

            case R.id.newCommentButton:
                String text = mNewCommentEdit.getText().toString();
                if (text.isEmpty()) {
                    mNewCommentEdit.setError(getString(R.string.comment_is_empty));
                    mNewCommentEdit.requestFocus();
                    return;
                }
                Comment comment = new Comment(mArticle.getId(), mApplication.getCurrentUser().getId(), text);
                addComment(comment);
                break;
        }
    }

    @Override
    public boolean onLongClick(@NonNull View v) {
        if (v.getId() == R.id.articleContentCard) {
            mRatingBarDialog = new RatingBarDialog(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(@NonNull DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        final Vote vote = new Vote(mArticle.getId(), 0, mApplication.getCurrentUser().getId(),
                                mRatingBarDialog.getRating());
                        mRequestQueue.add(new CustomRequest(new AddVoteRequest(vote,
                                mApplication.getCurrentUser(), new AddVoteCallback() {
                            @Override
                            public void call(Answer answer, int id) {
                                if (answer == Answer.OK) {
                                    vote.setId(id);
                                    int index = -1;
                                    for (int i = 0; i < mArticleVotes.size(); i++) {
                                        if (mArticleVotes.get(i).getUserId()
                                                == mApplication.getCurrentUser().getId()) {
                                            index = i;
                                            break;
                                        }
                                    }
                                    if (index >= 0) {
                                        mArticleVotes.remove(index);
                                    }
                                    mArticleVotes.add(vote);
                                    updateArticleRating();
                                    mRatingBarDialog.dismiss();
                                }
                            }
                        })));
                    }
                }
            });
            mRatingBarDialog.show(getFragmentManager(), "content");
            return true;
        }
        return false;
    }

    private void updateArticleRating() {
        double sum = 0.0;
        for (Vote vote : mArticleVotes) {
            sum += vote.getRating();
        }
        float rating = (float) (sum / mArticleVotes.size());
        if (rating >= 1 && rating <= 5) {
            mTitleText.setText(mArticle.getTitle() + " - " + rating);
        }
    }

    @Override
    public void onItemClicked(final Comment comment) {
        mRatingBarDialog = new RatingBarDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    final Vote vote = new Vote(0, comment.getId(), mApplication.getCurrentUser().getId(),
                            mRatingBarDialog.getRating());
                    mRequestQueue.add(new CustomRequest(new AddVoteRequest(vote,
                            mApplication.getCurrentUser(), new AddVoteCallback() {
                        @Override
                        public void call(Answer answer, int id) {
                            if (answer == Answer.OK) {
                                vote.setId(id);
                                mRatingBarDialog.dismiss();
                                mCommentsAdapter.addVote(id, vote);
                            }
                        }
                    })));
                }
            }
        });
        mRatingBarDialog.show(getFragmentManager(), "content");
    }

    private void addComment(final Comment comment) {
        mRequestQueue.add(new CustomRequest(new AddCommentRequest(comment,
                mApplication.getCurrentUser(), new AddCommentCallback() {
            @Override
            public void call(Answer answer, int id) {
                if (answer == Answer.OK) {
                    comment.setId(id);
                    calculateRecyclerHeight(mCommentsAdapter.getItemCount() + 1);
                    mCommentsAdapter.addComment(comment, mApplication.getCurrentUser());
                }
            }
        })));
    }

    private void showComments(List<Comment> comments, Map<Integer, User> commentsAuthors,
                              Map<Integer, List<Vote>> commentsVotes) {
        calculateRecyclerHeight(comments.size());
        mCommentsAdapter.setNewValues(comments, commentsAuthors, commentsVotes);
    }

    private void calculateRecyclerHeight(int count) {
        int itemHeight = getResources().getDimensionPixelSize(R.dimen.comment_item_size);

        mRecyclerView.getLayoutParams().height = count * itemHeight;
    }

    public void setArticle(Article article) {
        this.mArticle = article;
    }

}
