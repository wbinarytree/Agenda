package com.phoenix.soft.agenda;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.phoenix.soft.agenda.dagger.AppComponent;
import com.phoenix.soft.agenda.dagger.AppModule;
import com.phoenix.soft.agenda.dagger.DaggerAppComponent;
import com.phoenix.soft.agenda.dagger.DaggerFirebaseComponent;
import com.phoenix.soft.agenda.dagger.FirebaseComponent;
import com.phoenix.soft.agenda.dagger.FirebaseModule;

import javax.inject.Inject;

/**
 * Created by yaoda on 27/02/17.
 */

public class MainApplication extends Application {

    private static FirebaseComponent firebaseComponent;

    public AppComponent getBuilder() {
        return builder;
    }

    public FirebaseAuth  getAuth() {
        return mAuth;
    }

    @Inject
    FirebaseAuth mAuth;
    private AppComponent builder;

    public static FirebaseComponent getFirebaseComponent() {
        return firebaseComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        builder = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        firebaseComponent = DaggerFirebaseComponent.create();
        firebaseComponent.inject(this);

    }
}
