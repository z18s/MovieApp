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
import com.example.movieapp.mvp.presenter.title.list.IFavoritesListPresenter;
import com.example.movieapp.mvp.view.title.list.IFavoritesItemView;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> implements ILogger {

    private final IFavoritesListPresenter presenter;

    public FavoritesAdapter(IFavoritesListPresenter presenter) {
        this.presenter = presenter;
        showVerboseLog(this, "constructor");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View favoritesView = inflater.inflate(R.layout.item_favorites, parent, false);
        return new FavoritesAdapter.ViewHolder(favoritesView);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements IFavoritesItemView {
        TextView nameView;
        TextView yearView;
        TextView countryView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.tv_favorites_item_name);
            yearView = itemView.findViewById(R.id.tv_favorites_item_year);
            countryView = itemView.findViewById(R.id.tv_favorites_item_country);
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
        public int getPos() {
            return position;
        }
    }
}