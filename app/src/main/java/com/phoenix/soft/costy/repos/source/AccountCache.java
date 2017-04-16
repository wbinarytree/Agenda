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

import android.util.ArrayMap;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;
import com.jakewharton.rxrelay2.ReplayRelay;
import com.phoenix.soft.costy.models.Account;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

import static com.phoenix.soft.costy.repos.source.EventType.TYPE_ADD;
import static com.phoenix.soft.costy.repos.source.EventType.TYPE_DELETE;
import static com.phoenix.soft.costy.repos.source.EventType.TYPE_UPDATE;

/**
 * Created by phoenix on 2017/4/14.
 */

public class AccountCache implements AccountSourceRT {
    private final ArrayMap<String, Account> cache;
    private final Relay<ValueEvent<Account>> accountUpdate;
    private final Relay<List<Account>> listUpdate;


    public AccountCache() {
        this.accountUpdate = ReplayRelay.createWithSize(1);
        this.listUpdate = BehaviorRelay.create();
        cache = new ArrayMap<>();
    }

    @Override
    public Observable<List<Account>> getAccountList() {
        return listUpdate;
    }

    @Override
    public Observable<ValueEvent<Account>> getAccountUpdate() {
        return accountUpdate;
    }

    @Override
    public Maybe<Account> getAccount(String id) {
        Account item = cache.get(id);
        return item == null ? Maybe.empty() : Maybe.just(item);
    }

    @Override
    public Observable<Boolean> addAccount(Account account) {
        Account put = cache.put(account.getKey(), account);
        accountUpdate.accept(new ValueEvent<>(account, TYPE_ADD));
        listUpdate.accept(new ArrayList<>(cache.values()));
        return put == null ? Observable.just(true) : Observable.just(false);
    }

    @Override
    public Observable<Boolean> updateAccount(Account account) {
        cache.put(account.getKey(), account);
        accountUpdate.accept(new ValueEvent<>(account, TYPE_UPDATE));
        listUpdate.accept(new ArrayList<>(cache.values()));
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> deleteAccount(Account account) {
        Account remove = cache.remove(account.getKey());
        if (remove == null) {
            return Observable.just(false);
        } else {
            accountUpdate.accept(new ValueEvent<>(account,TYPE_DELETE));
            listUpdate.accept(new ArrayList<>(cache.values()));
            return Observable.just(true);
        }
    }
}
