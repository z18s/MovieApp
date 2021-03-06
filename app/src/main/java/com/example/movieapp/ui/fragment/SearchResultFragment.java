package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.TagConstants;
import com.example.movieapp.mvp.presenter.search.SearchResultPresenter;
import com.example.movieapp.mvp.view.search.ISearchResultView;
import com.example.movieapp.ui.BackButtonListener;
import com.example.movieapp.ui.adapter.SearchResultAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

import static com.example.movieapp.mvp.model.base.SearchConstants.EMPTY_STRING;

public class SearchResultFragment extends MvpAppCompatFragment implements ISearchResultView, ILogger, BackButtonListener {

    private View view;
    private RecyclerView resultRecyclerView;
    private SearchResultAdapter resultAdapter;

    @InjectPresenter
    SearchResultPresenter presenter;

    @ProvidePresenter
    SearchResultPresenter provideTitlesPresenter() {
        return new SearchResultPresenter(getQuery());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_result, container, false);
        resultRecyclerView = view.findViewById(R.id.rv_search_result);
        showVerboseLog(this, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        initViews();
        showVerboseLog(this, "init");
    }

    private void initViews() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        resultAdapter = new SearchResultAdapter(presenter.getSearchListPresenter());
        resultRecyclerView.setLayoutManager(layoutManager);
        resultRecyclerView.setAdapter(resultAdapter);
    }

    private String getQuery() {
        String result = getArguments().getString(TagConstants.QUERY_TAG);
        return (result != null) ? result : EMPTY_STRING;
    }

    @Override
    public void updateResultData() {
        resultAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}