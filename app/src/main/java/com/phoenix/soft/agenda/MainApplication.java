package com.phoenix.soft.agenda;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.phoenix.soft.agenda.dagger.AppComponent;
import com.phoenix.soft.agenda.dagger.AppModule;
import com.phoenix.soft.agenda.dagger.DaggerAppComponent;

/**
 * Created by yaoda on 27/02/17.
 */

public class MainApplication extends Application {

    public AppComponent getBuilder() {
        return builder;
    }

    private AppComponent builder;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        builder = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
