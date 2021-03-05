package com.example.movieapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.presenter.search.list.ISearchListPresenter;
import com.example.movieapp.mvp.view.search.list.ISearchResultItemView;
import com.example.movieapp.utils.image.GlideImageLoader;
import com.example.movieapp.utils.image.IImageLoader;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> implements ILogger {

    private final ISearchListPresenter presenter;
    private static final IImageLoader<ImageView> imageLoader = new GlideImageLoader();

    public SearchResultAdapter(ISearchListPresenter presenter) {
        this.presenter = presenter;
        showVerboseLog(this, "constructor");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View titleView = inflater.inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(titleView);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements ISearchResultItemView {
        TextView nameView;
        ImageView imageView;
        TextView typeView;
        TextView yearView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.tv_search_result_item_name);
            imageView = itemView.findViewById(R.id.iv_search_result_item_pic);
            typeView = itemView.findViewById(R.id.tv_search_result_item_type);
            yearView = itemView.findViewById(R.id.tv_search_result_item_year);
        }

        @Override
        public void setName(String titleName) {
            nameView.setText(titleName);
        }

        @Override
        public void loadImage(String imageUrl) {
            imageLoader.loadImage(imageUrl, imageView);
        }

        @Override
        public void setType(String titleType) {
            typeView.setText(titleType);
        }

        @Override
        public void setYear(String year) {
            yearView.setText(year);
        }

        @Override
        public int getPos() {
            return position;
        }
    }
}