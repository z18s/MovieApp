package com.example.movieapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.presenter.search.list.IHistoryListPresenter;
import com.example.movieapp.mvp.view.search.list.ISearchHistoryItemView;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> implements ILogger {

    private final IHistoryListPresenter presenter;

    public SearchHistoryAdapter(IHistoryListPresenter presenter) {
        this.presenter = presenter;
        showVerboseLog(this, "constructor");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View searchHistoryView = inflater.inflate(R.layout.item_search_history, parent, false);
        return new ViewHolder(searchHistoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position = position;
        holder.itemView.setOnClickListener((view) -> {
            presenter.onItemClick(holder);
        });
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements ISearchHistoryItemView {
        TextView textView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_search_history_item_text);
        }

        @Override
        public void setText(String historyName) {
            textView.setText(historyName);
        }

        @Override
        public int getPos() {
            return position;
        }
    }
}