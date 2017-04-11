package com.phoenix.soft.costy.transaction.di;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.costy.module.Account;
import com.phoenix.soft.costy.repos.source.TransactionSourceRT;
import com.phoenix.soft.costy.repos.source.TransactionSourceRTFirebase;
import com.phoenix.soft.costy.transaction.TransactionPresenter;

import javax.inject.Named;

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
    Account provideAccount(){
        return account;
    }

    @TranscationScope
    @Provides
    TransactionSourceRT provideRealTransactionSource(@Named("Account") DatabaseReference dbRef){
        return new TransactionSourceRTFirebase(dbRef,account.getKey());
    }

    @TranscationScope
    @Provides
    TransactionPresenter providePresenter(TransactionSourceRT sourceRT){
        return new TransactionPresenter(account,sourceRT);
    }
}
