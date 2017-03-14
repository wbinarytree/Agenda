package com.phoenix.soft.agenda;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by yaoda on 27/02/17.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
