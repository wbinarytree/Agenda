package com.phoenix.soft.agenda.dagger;

import android.content.Context;

import com.phoenix.soft.agenda.repos.AccountRepository;
import com.phoenix.soft.agenda.repos.FireAccountRepository;
import com.phoenix.soft.agenda.repos.TestAccountRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yaoda on 16/03/17.
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }


    @Provides
    @Singleton
    Context provideAppContext() {
        return context;
    }

    @Provides
    @Singleton
    AccountRepository provideAccoutRepo(Context context) {
        return new TestAccountRepository(context);
    }

    @Provides
    @Singleton
    @Named("Firebase")
    AccountRepository provideFirebaseAccoutRepo() {
        return new FireAccountRepository();
    }
}
