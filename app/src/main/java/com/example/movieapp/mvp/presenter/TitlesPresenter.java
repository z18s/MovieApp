package com.example.movieapp.mvp.presenter;

import androidx.annotation.UiThread;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.model.entity.Title;
import com.example.movieapp.mvp.model.repo.ITitlesRepo;
import com.example.movieapp.mvp.presenter.list.ITitlesListPresenter;
import com.example.movieapp.mvp.view.ITitlesView;
import com.example.movieapp.mvp.view.list.ITitleItemView;
import com.example.movieapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class TitlesPresenter extends MvpPresenter<ITitlesView> {

    private static final String TAG = TitlesPresenter.class.getSimpleName();

    private final Scheduler scheduler;
    private final Router router;
    private final ITitlesRepo titlesRepo;
    private final String query;

    private final String noResult = "No Result";

    public TitlesPresenter(Scheduler scheduler, Router router, ITitlesRepo titlesRepo, String query) {
        this.scheduler = scheduler;
        this.router = router;
        this.titlesRepo = titlesRepo;
        this.query = query;
    }

    private class TitlesListPresenter implements ITitlesListPresenter {
        private final List<Title> titles = new ArrayList<>();

        @Override
        public void onItemClick(ITitleItemView view) {
            int index = view.getPos();
            Logger.showLog(Logger.VERBOSE, TAG, "TitlesListPresenter.onItemClick - " + index);
            Title title = titles.get(index);
            router.navigateTo(new Screens.TitleScreen(title));
        }

        @Override
        public void bindView(ITitleItemView view) {
            int index = view.getPos();
            Title title = titles.get(index);
            setRecyclerData(view, title);
        }

        @Override
        public int getCount() {
            return titles.size();
        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Logger.showLog(Logger.VERBOSE, TAG, "onFirstViewAttach");
        getViewState().init();
        setData();
    }

    private void setRecyclerData(ITitleItemView view, Title title) {
        Logger.showLog(Logger.VERBOSE, TAG, "setRecyclerData");
        title.getName().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String name) {
                view.setName(name);
                view.loadImage(title.getImageUrl());
                view.setType(title.getType());
                view.setYear(title.getYear());
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
        titlesRepo.getTitles(query).observeOn(scheduler).subscribe(
                (titles) -> {
                    titlesListPresenter.titles.clear();
                    Logger.showLog(Logger.VERBOSE, TAG, "setData.onNext - " + titles.getList());
                    if (titles.getList() == null) {
                        titlesListPresenter.titles.add(new Title(noResult));
                    } else {
                        Logger.showLog(Logger.VERBOSE, TAG, "setData.onNext - " + titles.getList().size());
                        titlesListPresenter.titles.addAll(titles.getList());
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