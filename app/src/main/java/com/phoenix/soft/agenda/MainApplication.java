package com.phoenix.soft.agenda;

import android.app.Application;
import android.util.ArrayMap;

import com.facebook.stetho.Stetho;
import com.google.firebase.auth.FirebaseAuth;
import com.phoenix.soft.agenda.dagger.AppComponent;
import com.phoenix.soft.agenda.dagger.DaggerAppComponent;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.transaction.di.TransactionComponent;
import com.phoenix.soft.agenda.transaction.di.TransactionModule;

import javax.inject.Inject;

/**
 * Created by yaoda on 27/02/17.
 */

public class MainApplication extends Application {

    private static AppComponent appComponent;
    private static ArrayMap<String,TransactionComponent> transactionMap;
    public FirebaseAuth  getAuth() {
        return mAuth;
    }

    @Inject
    FirebaseAuth mAuth;


    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static TransactionComponent addTransaction(Account account){
        TransactionComponent component =getAppComponent().plus(new TransactionModule(account));
        transactionMap.put(account.getKey(),component);
        return component;
    }

    public static TransactionComponent getTransaction(String key){
        return transactionMap.get(key);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        appComponent = DaggerAppComponent.create();
        appComponent.inject(this);
        transactionMap = new ArrayMap<>();
    }
}
