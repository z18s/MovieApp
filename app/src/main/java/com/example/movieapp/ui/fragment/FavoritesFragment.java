package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.presenter.title.FavoritesPresenter;
import com.example.movieapp.mvp.view.title.IFavoritesView;
import com.example.movieapp.ui.BackButtonListener;
import com.example.movieapp.ui.adapter.FavoritesAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class FavoritesFragment extends MvpAppCompatFragment implements IFavoritesView, SwipeRefreshLayout.OnRefreshListener, ILogger, BackButtonListener {

    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView favoritesRecyclerView;
    private FavoritesAdapter favoritesAdapter;

    @InjectPresenter
    FavoritesPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        favoritesRecyclerView = view.findViewById(R.id.rv_favorites);
        showVerboseLog(this, "onCreateView");
        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        presenter.updateData();
    }

    @Override
    public void init() {
        initViews();
        initListeners();
        showVerboseLog(this, "init");
    }

    private void initViews() {
        swipeRefreshLayout = view.findViewById(R.id.srl_favorites);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        favoritesAdapter = new FavoritesAdapter(presenter.getFavoritesListPresenter());
        favoritesRecyclerView.setLayoutManager(layoutManager);
        favoritesRecyclerView.setAdapter(favoritesAdapter);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void updateFavoritesData() {
        favoritesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}