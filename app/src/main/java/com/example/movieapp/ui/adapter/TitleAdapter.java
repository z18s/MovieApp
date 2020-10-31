package com.example.movieapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Logger;
import com.example.movieapp.R;
import com.example.movieapp.mvp.presenter.list.ITitlesListPresenter;
import com.example.movieapp.mvp.view.image.GlideImageLoader;
import com.example.movieapp.mvp.view.image.IImageLoader;
import com.example.movieapp.mvp.view.list.ITitleItemView;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder> {

    private static final String TAG = TitleAdapter.class.getSimpleName();

    private final ITitlesListPresenter presenter;
    private static final IImageLoader<ImageView> imageLoader = new GlideImageLoader();

    public TitleAdapter(ITitlesListPresenter presenter) {
        this.presenter = presenter;
        Logger.showLog(Logger.VERBOSE, TAG, "constructor");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View titleView = inflater.inflate(R.layout.item_title, parent, false);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements ITitleItemView {
        TextView nameView;
        ImageView imageView;
        TextView typeView;
        TextView yearView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.titles_item_name);
            imageView = itemView.findViewById(R.id.titles_item_image);
            typeView = itemView.findViewById(R.id.titles_item_type);
            yearView = itemView.findViewById(R.id.titles_item_year);
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