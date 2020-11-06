package com.example.movieapp.ui.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.mvp.model.network.INetworkStatus;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AndroidNetworkStatus implements INetworkStatus {

    private static final String TAG = AndroidNetworkStatus.class.getSimpleName();

    private BehaviorSubject<Boolean> statusObject = BehaviorSubject.create();

    public AndroidNetworkStatus() {
        statusObject.onNext(false);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) MovieApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest networkRequest = new NetworkRequest.Builder().build();

        connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Logger.showLog(Logger.VERBOSE, TAG, "onAvailable");
                statusObject.onNext(true);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                Logger.showLog(Logger.VERBOSE, TAG, "onUnavailable");
                statusObject.onNext(false);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Logger.showLog(Logger.VERBOSE, TAG, "onLost");
                statusObject.onNext(false);
            }
        });
    }

    @Override
    public Observable<Boolean> isOnline() {
        return statusObject;
    }

    @Override
    public Single<Boolean> isOnlineSingle() {
        return statusObject.first(false);
    }
}