/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phoenix.soft.costy.transaction.di;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.costy.models.Account;
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
