package com.example.movieapp.mvp.presenter.search;

import static com.example.movieapp.mvp.model.base.SearchConstants.EMPTY_STRING;
import static com.example.movieapp.mvp.model.base.SearchConstants.NO_RESULT;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.search.cache.IHistoryCache;
import com.example.movieapp.mvp.model.search.cache.ISearchCache;
import com.example.movieapp.mvp.presenter.search.button.ISearchButtonPresenter;
import com.example.movieapp.mvp.presenter.search.list.IHistoryListPresenter;
import com.example.movieapp.mvp.presenter.search.text.ISearchEditTextPresenter;
import com.example.movieapp.mvp.view.search.ISearchFieldView;
import com.example.movieapp.mvp.view.search.list.ISearchHistoryItemView;
import com.example.movieapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class SearchFieldPresenter extends MvpPresenter<ISearchFieldView> implements ILogger {

    @Inject
    Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    IHistoryCache historyCache;
    @Inject
    ISearchCache searchCache;

    public SearchFieldPresenter() {
        MovieApp.instance.getSearchSubcomponent().inject(this);
    }

    private final SearchButtonPresenter searchButtonPresenter = new SearchButtonPresenter();
    private final SearchEditTextPresenter searchEditTextPresenter = new SearchEditTextPresenter();
    private final SearchHistoryListPresenter searchHistoryListPresenter = new SearchHistoryListPresenter();

    private class SearchButtonPresenter implements ISearchButtonPresenter {

        @Override
        public void onClick(String query) {
            router.navigateTo(new Screens.SearchResultScreen(query));
        }
    }

    private class SearchEditTextPresenter implements ISearchEditTextPresenter {

        @Override
        public void onTextChange(String chars) {
            setHistoryData(chars);
        }
    }

    private class SearchHistoryListPresenter implements IHistoryListPresenter {
        private final List<String> historyList = new ArrayList<>();

        @Override
        public void onItemClick(ISearchHistoryItemView view) {
            int index = view.getPos();
            showVerboseLog(this, "onItemClick - " + index);
            String historyItem = historyList.get(index);
            if (!historyItem.equals(NO_RESULT)) {
                router.navigateTo(new Screens.SearchResultScreen(historyItem));
            }
        }

        @Override
        public void bindView(ISearchHistoryItemView view) {
            int index = view.getPos();
            String historyItem = historyList.get(index);
            view.setText(historyItem);
        }

        @Override
        public int getCount() {
            return historyList.size();
        }
    }

    public ISearchButtonPresenter getSearchButtonPresenter() {
        return searchButtonPresenter;
    }

    public ISearchEditTextPresenter getEditTextPresenter() {
        return searchEditTextPresenter;
    }

    public IHistoryListPresenter getHistoryListPresenter() {
        return searchHistoryListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        setHistoryData(EMPTY_STRING);
    }

    private void setHistoryData(String chars) {
        showVerboseLog(this, "setHistoryData");
        searchHistoryListPresenter.historyList.clear();
        if (chars.equals(EMPTY_STRING)) {
            searchHistoryListPresenter.historyList.add(EMPTY_STRING);
        } else {
            historyCache.getSearch(chars).observeOn(scheduler).subscribe(
                    (searchQueryList) -> {
                        if (searchQueryList != null && searchQueryList.size() != 0) {
                            showVerboseLog(this, "setHistoryData.onNext - (" + searchQueryList.size() + "):" + searchQueryList);
                            searchHistoryListPresenter.historyList.addAll(searchQueryList);
                        } else {
                            showVerboseLog(this, "setHistoryData.onNext - EmptyResult");
                            searchHistoryListPresenter.historyList.add(EMPTY_STRING);
                        }
                        getViewState().updateHistoryList();
                    },
                    (e) -> {
                        showVerboseLog(this, "setHistoryData.onError - " + e.getMessage());
                    }
            );
        }
        getViewState().updateHistoryList();
    }

    public void onClearSearchDataButtonClick() {
        historyCache.clear();
        searchCache.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getViewState().release();
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}