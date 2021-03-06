package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.presenter.search.SearchFieldPresenter;
import com.example.movieapp.mvp.view.search.ISearchFieldView;
import com.example.movieapp.ui.BackButtonListener;
import com.example.movieapp.ui.adapter.SearchHistoryAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class SearchFieldFragment extends MvpAppCompatFragment implements ISearchFieldView, ILogger, BackButtonListener {

    private View view;
    private SearchView searchView;
    private Button searchButton;
    private RecyclerView historyRecyclerView;
    private SearchHistoryAdapter historyAdapter;

    @InjectPresenter
    SearchFieldPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_field, container, false);
        historyRecyclerView = view.findViewById(R.id.rv_history_search);
        showVerboseLog(this, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        initViews();
        initListeners();
        showVerboseLog(this, "init");
    }

    private void initViews() {
        searchView = view.findViewById(R.id.sv_search);
        searchButton = view.findViewById(R.id.btn_search);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        historyAdapter = new SearchHistoryAdapter(presenter.getHistoryListPresenter());
        historyRecyclerView.setLayoutManager(layoutManager);
        historyRecyclerView.setAdapter(historyAdapter);
    }

    private void initListeners() {
        searchButton.setOnClickListener((view) -> {
            startSearch(searchView.getQuery().toString());
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                loadHistory(query);
                return false;
            }
        });
    }

    private void startSearch(String query) {
        presenter.getButtonPresenter().onClick(query);
    }

    private void loadHistory(String query) {
        presenter.getEditTextPresenter().onTextChange(query);
    }

    @Override
    public void release() {
        MovieApp.instance.releaseSearchSubcomponent();
    }

    @Override
    public void updateHistoryList() {
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}