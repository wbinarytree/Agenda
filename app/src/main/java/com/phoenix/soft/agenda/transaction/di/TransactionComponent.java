package com.phoenix.soft.agenda.transaction.di;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.dagger.FirebaseComponent;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.transaction.TransactionFragment;

import dagger.Component;

/**
 * Created by yaoda on 24/03/17.
 */
@TranscationScope
@Component(modules = {TransactionModule.class},dependencies = {FirebaseComponent.class})
public interface TransactionComponent {
    Account getAccount();
    void inject(TransactionFragment transactionFragment);
}
