package ru.guar7387.clientserverbasicsample.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.guar7387.clientserverbasicsample.R;
import ru.guar7387.servermodule.DataModel;
import ru.guar7387.servermodule.Logger;
import ru.guar7387.servermodule.data.Comment;
import ru.guar7387.servermodule.data.User;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder> {

    private static final String TAG = CommentsAdapter.class.getSimpleName();

    private final List<Integer> commentsIds;

    private final DataModel model;

    public CommentsAdapter(List<Integer> commentsIds, DataModel model) {
        this.commentsIds = commentsIds;
        this.model = model;
    }

    public void setNewValues(List<Integer> commentsIds) {
        this.commentsIds.clear();
        this.commentsIds.addAll(commentsIds);
        notifyDataSetChanged();
    }

    public void addComment(Comment comment) {
        commentsIds.add(comment.getId());
        notifyDataSetChanged();
    }

    public static class CommentsHolder extends RecyclerView.ViewHolder {

        private final TextView userName;
        private final TextView commentText;

        public CommentsHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.commentsItemUserName);
            commentText = (TextView) itemView.findViewById(R.id.commentsItemText);
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
        Comment comment = model.getComment(commentsIds.get(position));
        User user = model.getUser(comment.getUserId());
        commentsHolder.userName.setText(user.getName());
        commentsHolder.commentText.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        Logger.log(TAG, "getItemCount: " + commentsIds.size());
        return commentsIds.size();
    }
}