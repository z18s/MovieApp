package com.example.movieapp;

import android.util.Log;

public enum Logger {

    VERBOSE,
    DEBUG,
    INFO,
    WARN,
    ERROR;

    private static final boolean VERBOSE_RUNNING = true;
    private static final boolean DEBUG_RUNNING = false;
    private static final boolean INFO_RUNNING = false;
    private static final boolean WARN_RUNNING = false;
    private static final boolean ERROR_RUNNING = false;

    public static void showLog(Logger type, String tag, String message) {
        switch (type) {
            case VERBOSE:
                if (VERBOSE_RUNNING) {
                    Log.v(tag, message);
                }
                break;
            case DEBUG:
                if (DEBUG_RUNNING) {
                    Log.d(tag, message);
                }
                break;
            case INFO:
                if (INFO_RUNNING) {
                    Log.i(tag, message);
                }
                break;
            case WARN:
                if (WARN_RUNNING) {
                    Log.w(tag, message);
                }
                break;
            case ERROR:
                if (ERROR_RUNNING) {
                    Log.e(tag, message);
                }
                break;
        }
    }
}