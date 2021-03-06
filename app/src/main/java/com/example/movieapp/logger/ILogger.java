package com.example.movieapp.logger;

import android.util.Log;

public interface ILogger {
    boolean VERBOSE_RUNNING = true;
    boolean DEBUG_RUNNING = false;
    boolean INFO_RUNNING = false;

    default void showVerboseLog(Object object, String message) {
        if (VERBOSE_RUNNING) {
            Log.v(object.getClass().getSimpleName(), message);
        }
    }

    default void showDebugLog(Object object, String message) {
        if (DEBUG_RUNNING) {
            Log.d(object.getClass().getSimpleName(), message);
        }
    }

    default void showInfoLog(Object object, String message) {
        if (INFO_RUNNING) {
            Log.i(object.getClass().getSimpleName(), message);
        }
    }
}