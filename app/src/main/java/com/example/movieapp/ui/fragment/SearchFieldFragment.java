package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.presenter.search.SearchFieldPresenter;
import com.example.movieapp.mvp.view.search.ISearchFieldView;
import com.example.movieapp.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class SearchFieldFragment extends MvpAppCompatFragment implements ISearchFieldView, ILogger, BackButtonListener {

    private View view;
    private SearchView searchView;
    private Button button;

    @InjectPresenter
    SearchFieldPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_field, container, false);
        showVerboseLog(this, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        searchView = view.findViewById(R.id.sv_search);
        searchView.setIconifiedByDefault(false);
        button = view.findViewById(R.id.button_search);
        initListeners();
        showVerboseLog(this, "init");
    }

    private void initListeners() {
        button.setOnClickListener((view) -> {
            startSearch(searchView.getQuery().toString());
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void startSearch(String query) {
        presenter.getPresenter().onClick(query);
    }

    @Override
    public void release() {
        MovieApp.instance.releaseSearchSubcomponent();
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}