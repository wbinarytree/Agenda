package com.phoenix.soft.costy.transaction.di;

import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.repos.source.TransactionSourceRT;
import com.phoenix.soft.costy.repos.source.TransactionSourceRTLeanCloud;
import com.phoenix.soft.costy.transaction.TransactionPresenter;

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
    TransactionSourceRT provideRealTransactionSource(){
        return new TransactionSourceRTLeanCloud();
    }

    @TranscationScope
    @Provides
    TransactionPresenter providePresenter(TransactionSourceRT sourceRT){
        return new TransactionPresenter(account,sourceRT);
    }
}
