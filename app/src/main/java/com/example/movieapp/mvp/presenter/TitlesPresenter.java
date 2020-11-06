package com.example.movieapp.mvp.presenter;

import androidx.annotation.UiThread;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.mvp.model.entity.BasicTitle;
import com.example.movieapp.mvp.model.repo.ISearchRepo;
import com.example.movieapp.mvp.presenter.list.ITitlesListPresenter;
import com.example.movieapp.mvp.view.ITitlesView;
import com.example.movieapp.mvp.view.list.ITitleItemView;
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

public class TitlesPresenter extends MvpPresenter<ITitlesView> {

    private static final String TAG = TitlesPresenter.class.getSimpleName();

    @Inject
    Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    ISearchRepo titlesRepo;

    private final String query;

    private final String noResult = "- No Result -";

    public TitlesPresenter(String query) {
        this.query = query;
        MovieApp.instance.getSearchSubcomponent().inject(this);
    }

    private class TitlesListPresenter implements ITitlesListPresenter {
        private final List<BasicTitle> basicTitles = new ArrayList<>();

        @Override
        public void onItemClick(ITitleItemView view) {
            int index = view.getPos();
            Logger.showLog(Logger.VERBOSE, TAG, "TitlesListPresenter.onItemClick - " + index);
            BasicTitle basicTitle = basicTitles.get(index);
            if (!basicTitle.getNameString().equals(noResult)) {
                Logger.showLog(Logger.VERBOSE, TAG, "TitlesListPresenter.onItemClick - title.getNameString() = " + basicTitle.getNameString());
                Logger.showLog(Logger.VERBOSE, TAG, "TitlesListPresenter.onItemClick - noResult = " + noResult);
                router.navigateTo(new Screens.TitleScreen(basicTitle));
            }
        }

        @Override
        public void bindView(ITitleItemView view) {
            int index = view.getPos();
            BasicTitle basicTitle = basicTitles.get(index);
            setRecyclerData(view, basicTitle);
        }

        @Override
        public int getCount() {
            return basicTitles.size();
        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Logger.showLog(Logger.VERBOSE, TAG, "onFirstViewAttach");
        getViewState().init();
        setData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getViewState().release();
    }

    private void setRecyclerData(ITitleItemView view, BasicTitle basicTitle) {
        Logger.showLog(Logger.VERBOSE, TAG, "setRecyclerData");
        basicTitle.getName().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String name) {
                view.setName(name);
                view.loadImage(basicTitle.getImageUrl());
                view.setType(basicTitle.getType());
                view.setYear(basicTitle.getYear());
                Logger.showLog(Logger.VERBOSE, TAG, "setRecyclerData.onNext - " + name);
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
        Logger.showLog(Logger.VERBOSE, TAG, "setData");
        titlesRepo.getSearch(query).observeOn(scheduler).subscribe(
                (titles) -> {
                    titlesListPresenter.basicTitles.clear();
                    Logger.showLog(Logger.VERBOSE, TAG, "setData.onNext - " + titles.getList());
                    if (titles.getList() == null) {
                        titlesListPresenter.basicTitles.add(new BasicTitle(noResult));
                    } else {
                        Logger.showLog(Logger.VERBOSE, TAG, "setData.onNext - " + titles.getList().size());
                        titlesListPresenter.basicTitles.addAll(titles.getList());
                    }
                    getViewState().updateData();
                },
                (e) -> {
                    Logger.showLog(Logger.VERBOSE, TAG, "setData.onError - " + e.getMessage());
                }
        );
        getViewState().updateData();
    }

    private final TitlesPresenter.TitlesListPresenter titlesListPresenter = new TitlesPresenter.TitlesListPresenter();

    public ITitlesListPresenter getPresenter() {
        return titlesListPresenter;
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}