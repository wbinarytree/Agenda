package com.phoenix.soft.agenda.account.di;

import com.phoenix.soft.agenda.account.AccountContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yaoda on 24/03/17.
 */

@Module
public class AccountPresenterModule {
    private final AccountContract.View mView;

    public AccountPresenterModule(AccountContract.View view) {
        mView = view;
    }
    @Provides
    AccountContract.View provideAccountContractView() {
        return mView;
    }
}
