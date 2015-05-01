package ru.guar7387.clientserverwithvolleysample.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.guar7387.clientserverwithvolleysample.R;
import ru.guar7387.clientserverwithvolleysample.data.Comment;
import ru.guar7387.clientserverwithvolleysample.data.User;
import ru.guar7387.clientserverwithvolleysample.data.Vote;
import ru.guar7387.clientserverwithvolleysample.utils.Logger;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder> {

    interface ItemClickListener {
        void onItemClicked(Comment comment);
    }

    private static final String TAG = CommentsAdapter.class.getSimpleName();

    private final ItemClickListener mListener;

    private final List<Comment> mComments;
    private final Map<Integer, User> mCommentsAuthors;
    private final Map<Integer, List<Vote>> mCommentsVotes;

    public CommentsAdapter(List<Comment> comments, Map<Integer, User> users,
                           Map<Integer, List<Vote>> commentsVotes,
                           ItemClickListener listener) {
        this.mListener = listener;
        this.mComments = comments;
        this.mCommentsAuthors = users;
        this.mCommentsVotes = commentsVotes;
    }

    public void setNewValues(List<Comment> comments, Map<Integer, User> users, Map<Integer, List<Vote>> commentsVotes) {
        this.mComments.clear();
        this.mComments.addAll(comments);

        this.mCommentsAuthors.clear();
        this.mCommentsAuthors.putAll(users);

        this.mCommentsVotes.clear();
        this.mCommentsVotes.putAll(commentsVotes);

        notifyDataSetChanged();
    }

    public void addComment(Comment comment, User author) {
        mComments.add(comment);
        mCommentsAuthors.put(comment.getUserId(), author);

        notifyDataSetChanged();
    }

    public void addVote(int id, Vote vote) {
        List<Vote> votes = mCommentsVotes.get(id);
        if (votes == null) {
            votes = new ArrayList<>();
            mCommentsVotes.put(id, votes);
        }
        votes.add(vote);
    }

    public static class CommentsHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView userName;
        private final TextView commentText;
        private final TextView ratingTextView;

        public CommentsHolder(View itemView) {
            super(itemView);
            view = itemView;
            userName = (TextView) itemView.findViewById(R.id.commentsItemUserName);
            commentText = (TextView) itemView.findViewById(R.id.commentsItemText);
            ratingTextView = (TextView) itemView.findViewById(R.id.commentsItemRating);
        }
    }

    @Override
    public CommentsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Logger.log(TAG, "onCreateViewHolder");
        return new CommentsHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.comments_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(CommentsHolder commentsHolder, int position) {
        Logger.log(TAG, "onBindViewHolder");
        final Comment comment = mComments.get(position);
        User user = mCommentsAuthors.get(comment.getUserId());
        commentsHolder.userName.setText(user.getName());
        commentsHolder.commentText.setText(comment.getText());

        commentsHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(comment);
            }
        });

        List<Vote> votes = mCommentsVotes.get(comment.getId());
        if (votes != null) {
            double sum = 0.0;
            for (Vote vote : votes) {
                sum += vote.getRating();
            }
            float rating = (float) (sum / votes.size());
            if (rating >= 1 && rating <= 5) {
                commentsHolder.ratingTextView.setText(String.valueOf(rating));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
}