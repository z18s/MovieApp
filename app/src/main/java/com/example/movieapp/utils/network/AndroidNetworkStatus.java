package com.example.movieapp.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AndroidNetworkStatus implements INetworkStatus, ILogger {

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
                showVerboseLog(this, "onAvailable");
                statusObject.onNext(true);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                showVerboseLog(this, "onUnavailable");
                statusObject.onNext(false);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                showVerboseLog(this, "onLost");
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