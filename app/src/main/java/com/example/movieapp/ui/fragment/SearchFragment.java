package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.R;
import com.example.movieapp.mvp.presenter.SearchPresenter;
import com.example.movieapp.mvp.view.ISearchView;
import com.example.movieapp.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class SearchFragment extends MvpAppCompatFragment implements ISearchView, BackButtonListener {

    private static final String TAG = SearchFragment.class.getSimpleName();

    private View view;
    private SearchView searchView;
    private Button button;

    @InjectPresenter
    SearchPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        Logger.showLog(Logger.VERBOSE, TAG, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        searchView = view.findViewById(R.id.sv_search);
        searchView.setIconifiedByDefault(false);
        button = view.findViewById(R.id.button_search);
        initListeners();
        Logger.showLog(Logger.VERBOSE, TAG, "init");
    }

    private void initListeners() {
        button.setOnClickListener((view) -> {
            startSearch();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void startSearch() {
        String query = searchView.getQuery().toString();
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