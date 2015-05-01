package ru.guar7387.clientserverwithvolleysample.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RatingBar;

import ru.guar7387.clientserverwithvolleysample.R;

@SuppressLint("ValidFragment")
public class RatingBarDialog extends DialogFragment {

    private final DialogInterface.OnClickListener mVoteButtonClickListener;

    private RatingBar mRatingBar;

    public RatingBarDialog(DialogInterface.OnClickListener voteButtonClickListener) {
        this.mVoteButtonClickListener = voteButtonClickListener;
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mRatingBar = (RatingBar) inflater.inflate(R.layout.ratingbar_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(getResources().getString(R.string.vote), mVoteButtonClickListener);
        builder.setCancelable(true).setView(mRatingBar);
        return builder.create();
    }

    public int getRating() {
        return Math.round(mRatingBar.getRating());
    }
}
