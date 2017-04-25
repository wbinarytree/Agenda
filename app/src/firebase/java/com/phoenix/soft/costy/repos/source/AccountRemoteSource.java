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

package com.phoenix.soft.costy.repos.source;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.models.firebase.AccountFire;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import phoenixlib.io.rxfirebase.database.RxDatabase;

/**
 * Created by phoenix on 2017/4/15.
 */

public class AccountRemoteSource implements AccountSourceRT {
    private static final String TAG = "account";
    private final DatabaseReference dbRef;

    public AccountRemoteSource(DatabaseReference dbRef) {
        this.dbRef = dbRef.child(TAG);
    }

    @Override public Observable<List<Account>> getAccountList() {

        RxDatabase.queryOnceList(dbRef, AccountFire.class).map(list -> {
            List<Account> accounts = new ArrayList<>(list.size());
            for (AccountFire accountFire : list) {
                accounts.add(accountFire.toAccount());
            }
            return accounts;
        });
        return null;
    }

    @Override public Observable<ValueEvent<Account>> getAccountUpdate() {
        return null;
    }

    @Override public Maybe<Account> getAccount(String id) {
        return null;
    }

    @Override public Observable<Boolean> addAccount(Account account) {
        return null;
    }

    @Override public Observable<Boolean> updateAccount(Account account) {
        return null;
    }

    @Override public Observable<Boolean> deleteAccount(Account account) {
        return null;
    }
}
