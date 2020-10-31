package com.example.movieapp.mvp.presenter;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.model.entity.Title;
import com.example.movieapp.mvp.model.repo.ITitleRepo;
import com.example.movieapp.mvp.view.ITitleView;

import io.reactivex.rxjava3.core.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class TitlePresenter extends MvpPresenter<ITitleView> {

    private static final String TAG = TitlePresenter.class.getSimpleName();

    private final Scheduler scheduler;
    private final Router router;
    private final ITitleRepo titleRepo;
    private final Title title;

    public TitlePresenter(Scheduler scheduler, Router router, ITitleRepo titleRepo, Title title) {
        this.scheduler = scheduler;
        this.router = router;
        this.titleRepo = titleRepo;
        this.title = title;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Logger.showLog(Logger.VERBOSE, TAG, "onFirstViewAttach");
        getViewState().init();
        setData();
    }

    private void setData() {
        Logger.showLog(Logger.VERBOSE, TAG, "setData");
        titleRepo.getTitle(title.getId()).observeOn(scheduler).subscribe(
                (detailedTitle) -> {
                    getViewState().setData(
                            detailedTitle.getName(),
                            detailedTitle.getImageUrl(),
                            detailedTitle.getType(),
                            detailedTitle.getYear(),
                            detailedTitle.getCountry(),
                            detailedTitle.getDirector(),
                            detailedTitle.getRating(),
                            detailedTitle.getPlot()
                    );
                },
                (e) -> {
                    Logger.showLog(Logger.VERBOSE, TAG, "setData.onError " + e.getMessage());
                }
        );
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}