package com.example.movieapp.mvp.presenter.search;

import androidx.annotation.UiThread;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.search.data.SearchResult;
import com.example.movieapp.mvp.model.search.repo.ISearchRepo;
import com.example.movieapp.mvp.presenter.search.list.ISearchListPresenter;
import com.example.movieapp.mvp.view.search.ISearchResultView;
import com.example.movieapp.mvp.view.search.list.ISearchResultItemView;
import com.example.movieapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class SearchResultPresenter extends MvpPresenter<ISearchResultView> implements ILogger {

    private static final String TAG = SearchResultPresenter.class.getSimpleName();

    @Inject
    Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    ISearchRepo searchRepo;

    private final String query;

    private final String noResult = "- No Result -";

    public SearchResultPresenter(String query) {
        this.query = query;
        MovieApp.instance.getSearchSubcomponent().inject(this);
    }

    private class SearchResultListPresenter implements ISearchListPresenter {
        private final List<SearchResult> searchResults = new ArrayList<>();

        @Override
        public void onItemClick(ISearchResultItemView view) {
            int index = view.getPos();
            showVerboseLog(TAG, "TitlesListPresenter.onItemClick - " + index);
            SearchResult searchResult = searchResults.get(index);
            if (!searchResult.getNameString().equals(noResult)) {
                router.navigateTo(new Screens.TitleScreen(searchResult));
            }
        }

        @Override
        public void bindView(ISearchResultItemView view) {
            int index = view.getPos();
            SearchResult searchResult = searchResults.get(index);
            setRecyclerData(view, searchResult);
        }

        @Override
        public int getCount() {
            return searchResults.size();
        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        setData();
    }

    private void setRecyclerData(ISearchResultItemView view, SearchResult searchResult) {
        showVerboseLog(TAG, "setRecyclerData");
        searchResult.getName().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String name) {
                view.setName(name);
                view.loadImage(searchResult.getImageUrl());
                view.setType(searchResult.getType());
                view.setYear(searchResult.getYear());
                showVerboseLog(TAG, "setRecyclerData.onNext - " + name);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @UiThread
    private void setData() {
        showVerboseLog(TAG, "setData");
        searchRepo.getSearch(query).observeOn(scheduler).subscribe(
                (titles) -> {
                    titlesListPresenter.searchResults.clear();
                    showVerboseLog(TAG, "setData.onNext - " + titles.getList());
                    if (titles.getList() == null) {
                        titlesListPresenter.searchResults.add(new SearchResult(noResult));
                    } else {
                        showVerboseLog(TAG, "setData.onNext - " + titles.getList().size());
                        titlesListPresenter.searchResults.addAll(titles.getList());
                    }
                    getViewState().updateData();
                },
                (e) -> {
                    showVerboseLog(TAG, "setData.onError - " + e.getMessage());
                }
        );
        getViewState().updateData();
    }

    private final SearchResultListPresenter titlesListPresenter = new SearchResultListPresenter();

    public ISearchListPresenter getPresenter() {
        return titlesListPresenter;
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}