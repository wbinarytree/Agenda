package com.phoenix.soft.agenda.account.di;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.repos.source.AccountSourceRT;
import com.phoenix.soft.agenda.repos.source.AccountSourceRTFirebase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yaoda on 24/03/17.
 */

@Module
public class AccountPresenterModule {
    @AccountRepoScope
    @Provides
    AccountSourceRT provideRealTimeAccountSource(@Named("Account") DatabaseReference dbRef){
        return new AccountSourceRTFirebase(dbRef);
    }

}
