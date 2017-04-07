package com.phoenix.soft.agenda.transaction.di;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.source.AccountSourceRT;
import com.phoenix.soft.agenda.repos.source.AccountSourceRTFirebase;
import com.phoenix.soft.agenda.repos.source.TransactionSourceRT;
import com.phoenix.soft.agenda.repos.source.TransactionSourceRTFirebase;
import com.phoenix.soft.agenda.transaction.TransactionPresenter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yaoda on 24/03/17.
 */

@Module
public class TransactionModule {

    private Account account;
    public TransactionModule(Account account){
        this.account = account;
    }

    @Provides
    Account providePresenter(){
        return account;
    }
    @Provides
    TransactionSourceRT provideRealTransactionSource(@Named("Account") DatabaseReference dbRef){
        return new TransactionSourceRTFirebase(dbRef,account.getKey());
    }

}
