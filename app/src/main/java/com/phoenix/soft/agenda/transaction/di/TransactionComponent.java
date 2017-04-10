package com.phoenix.soft.agenda.transaction.di;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.transaction.TransactionFragment;

import dagger.Subcomponent;

/**
 * Created by yaoda on 24/03/17.
 */
@TranscationScope
@Subcomponent(modules = {TransactionModule.class})
public interface TransactionComponent {
    Account getAccount();
    void inject(TransactionFragment transactionFragment);
}
