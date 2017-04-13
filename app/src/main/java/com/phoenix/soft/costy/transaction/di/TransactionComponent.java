package com.phoenix.soft.costy.transaction.di;

import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.transaction.TransactionFragment;

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
