/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phoenix.soft.costy;

import android.app.Application;
import android.util.ArrayMap;
import com.facebook.stetho.Stetho;
import com.google.firebase.auth.FirebaseAuth;
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

    public final static String DATEPATTERN = "EEE MMM dd HH:mm:ss yyyy";
    private static AppComponent appComponent;
    private static ArrayMap<String, TransactionComponent> transactionMap;
    @Inject FirebaseAuth mAuth;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static TransactionComponent addTransaction(Account account) {
        TransactionComponent component = getAppComponent().plus(new TransactionModule(account));
        transactionMap.put(account.getKey(), component);
        return component;
    }

    public static TransactionComponent getTransaction(String key) {
        return transactionMap.get(key);
    }

    public static void removeTransaction(String key) {
        transactionMap.remove(key);
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    @Override public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        appComponent = DaggerAppComponent.create();
        appComponent.inject(this);
        transactionMap = new ArrayMap<>();
    }
}
