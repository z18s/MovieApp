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
import com.example.movieapp.mvp.presenter.title.list.IUserRatingsListPresenter;
import com.example.movieapp.mvp.view.title.list.IUserRatingsItemView;
import com.example.movieapp.utils.image.GlideImageLoader;
import com.example.movieapp.utils.image.IImageLoader;

public class UserRatingsAdapter extends RecyclerView.Adapter<UserRatingsAdapter.ViewHolder> implements ILogger {

    private static final IImageLoader<ImageView> imageLoader = new GlideImageLoader();
    private final IUserRatingsListPresenter presenter;

    public UserRatingsAdapter(IUserRatingsListPresenter presenter) {
        this.presenter = presenter;
        showVerboseLog(this, "constructor");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userRatingsView = inflater.inflate(R.layout.item_ratings, parent, false);
        return new UserRatingsAdapter.ViewHolder(userRatingsView);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements IUserRatingsItemView {
        ImageView posterImage;
        TextView nameView;
        TextView yearView;
        TextView countryView;
        TextView userRatingView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.iv_user_ratings_item_pic);
            nameView = itemView.findViewById(R.id.tv_user_ratings_item_name);
            yearView = itemView.findViewById(R.id.tv_user_ratings_item_year);
            countryView = itemView.findViewById(R.id.tv_user_ratings_item_country);
            userRatingView = itemView.findViewById(R.id.tv_user_ratings_item_rating);
        }

        @Override
        public void setPoster(String imageUrl) {
            imageLoader.loadImage(imageUrl, posterImage);
        }

        @Override
        public void setName(String titleName) {
            nameView.setText(titleName);
        }

        @Override
        public void setYear(String year) {
            yearView.setText(year);
        }

        @Override
        public void setCountry(String country) {
            countryView.setText(country);
        }

        @Override
        public void setUserRating(String userRating) {
            userRatingView.setText(userRating);
        }

        @Override
        public int getPos() {
            return position;
        }
    }
}