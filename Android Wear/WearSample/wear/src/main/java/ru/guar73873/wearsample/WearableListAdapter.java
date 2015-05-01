package ru.guar73873.wearsample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WearableListAdapter extends WearableListView.Adapter {

    private List<String> mItems;
    private final LayoutInflater mInflater;

    public WearableListAdapter(Context context, List<String> dataset) {
        mInflater = LayoutInflater.from(context);
        mItems = dataset;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(
                R.layout.wearable_list_item, null));
    }

    @Override
    public void onBindViewHolder(
            WearableListView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = itemHolder.textView;
        view.setText(mItems.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

