package com.phoenix.soft.costy;

import android.app.Application;
import android.util.ArrayMap;

import com.facebook.stetho.Stetho;

import com.phoenix.soft.costy.dagger.AppComponent;
import com.phoenix.soft.costy.dagger.DaggerAppComponent;
import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.transaction.di.TransactionComponent;
import com.phoenix.soft.costy.transaction.di.TransactionModule;

import javax.inject.Inject;

/**
 * Created by yaoda on 27/02/17.
 */

public class MainApplication extends Application {

    private static AppComponent appComponent;
    private static ArrayMap<String,TransactionComponent> transactionMap;
    public final static String DATEPATTERN = "EEE MMM dd HH:mm:ss yyyy";



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
