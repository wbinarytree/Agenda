package com.phoenix.soft.costy.account.di;

import com.phoenix.soft.costy.repos.source.AccountSourceLeanCloud;
import com.phoenix.soft.costy.repos.source.AccountSourceRT;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yaoda on 24/03/17.
 */

@Module
public class AccountPresenterModule {
    @AccountRepoScope
    @Provides
    AccountSourceRT provideRealTimeAccountSource(){
        return new AccountSourceLeanCloud();
    }

}
