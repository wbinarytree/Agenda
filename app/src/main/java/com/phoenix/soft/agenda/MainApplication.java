package com.phoenix.soft.agenda;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.firebase.auth.FirebaseAuth;
import com.phoenix.soft.agenda.dagger.DaggerFirebaseComponent;
import com.phoenix.soft.agenda.dagger.FirebaseComponent;


import javax.inject.Inject;

/**
 * Created by yaoda on 27/02/17.
 */

public class MainApplication extends Application {

    private static FirebaseComponent firebaseComponent;

    public FirebaseAuth  getAuth() {
        return mAuth;
    }

    @Inject
    FirebaseAuth mAuth;


    public static FirebaseComponent getFirebaseComponent() {
        return firebaseComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        firebaseComponent = DaggerFirebaseComponent.create();
        firebaseComponent.inject(this);
    }
}
