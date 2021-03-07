package com.example.movieapp.mvp.presenter.search;

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

import static com.example.movieapp.mvp.model.base.SearchConstants.EMPTY_STRING;

public class SearchResultPresenter extends MvpPresenter<ISearchResultView> implements ILogger {

    @Inject
    Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    ISearchRepo searchRepo;

    private final String query;

    public SearchResultPresenter(String query) {
        this.query = query;
        MovieApp.instance.getSearchSubcomponent().inject(this);
    }

    private final SearchResultListPresenter searchResultListPresenter = new SearchResultListPresenter();

    private class SearchResultListPresenter implements ISearchListPresenter {
        private final List<SearchResult> searchResults = new ArrayList<>();

        @Override
        public void onItemClick(ISearchResultItemView view) {
            int index = view.getPos();
            showVerboseLog(this, "onItemClick - " + index);
            SearchResult searchResult = searchResults.get(index);
            if (!searchResult.equals(new SearchResult.EmptySearchResult())) {
                router.navigateTo(new Screens.TitleScreen(searchResult.getId()));
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

    public ISearchListPresenter getSearchListPresenter() {
        return searchResultListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        setData();
    }

    private void setRecyclerData(ISearchResultItemView view, SearchResult searchResult) {
        showVerboseLog(this, "setRecyclerData");
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
                showVerboseLog(this, "setRecyclerData.onNext - " + name);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void setData() {
        showVerboseLog(this, "setData");
        searchResultListPresenter.searchResults.clear();
        if (query.equals(EMPTY_STRING)) {
            searchResultListPresenter.searchResults.add(new SearchResult.EmptySearchResult());
        } else {
            searchRepo.getSearch(query).observeOn(scheduler).subscribe(
                    (search) -> {
                        if (search.getList() != null && search.getList().size() != 0) {
                            showVerboseLog(this, "setData.onNext - (" + search.getList().size() + "): " + search.getList());
                            searchResultListPresenter.searchResults.addAll(search.getList());
                        } else {
                            showVerboseLog(this, "setData.onNext - EmptySearchResult");
                            searchResultListPresenter.searchResults.add(new SearchResult.EmptySearchResult());
                        }
                        getViewState().updateResultData();
                    },
                    (e) -> {
                        showVerboseLog(this, "setData.onError - " + e.getMessage());
                    }
            );
        }
        getViewState().updateResultData();
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}