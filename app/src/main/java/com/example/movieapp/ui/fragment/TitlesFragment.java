package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.R;
import com.example.movieapp.mvp.model.Tags;
import com.example.movieapp.mvp.presenter.TitlesPresenter;
import com.example.movieapp.mvp.view.ITitlesView;
import com.example.movieapp.ui.BackButtonListener;
import com.example.movieapp.ui.adapter.TitleAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class TitlesFragment extends MvpAppCompatFragment implements ITitlesView, BackButtonListener {

    private static final String TAG = TitlesFragment.class.getSimpleName();

    private View view;
    private RecyclerView recyclerView;
    private TitleAdapter adapter;

    @InjectPresenter
    TitlesPresenter presenter;

    @ProvidePresenter
    TitlesPresenter provideTitlesPresenter() {
        return new TitlesPresenter(getQuery());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_titles, container, false);
        recyclerView = view.findViewById(R.id.rv_titles);
        Logger.showLog(Logger.VERBOSE, TAG, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new TitleAdapter(presenter.getPresenter());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Logger.showLog(Logger.VERBOSE, TAG, "init");
    }

    private String getQuery() {
        return getArguments().getString(Tags.QUERY_TAG);
    }

    @Override
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void release() {
        MovieApp.instance.releaseTitlesSubcomponent();
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}