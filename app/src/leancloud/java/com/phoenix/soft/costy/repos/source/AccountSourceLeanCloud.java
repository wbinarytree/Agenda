package com.phoenix.soft.costy.repos.source;

import android.util.ArrayMap;

import com.phoenix.soft.costy.models.Account;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by yaoda on 04/04/17.
 */

public class AccountSourceLeanCloud implements AccountSourceRT {
    private List<Account> actual;
    {
        actual = new ArrayList<>();
        actual.add(new Account());
    }
    private ArrayMap<String, Account> accountMap;
    private Observable<ValueEvent<Account>> accountUpdate;

    public AccountSourceLeanCloud() {
        accountMap = new ArrayMap<>();
    }


    @Override
    public Observable<List<Account>> getAccountList() {
        return Observable.just(actual);
    }

    @Override
    public Observable<ValueEvent<Account>> getAccountUpdate() {
        return Observable.just(new ValueEvent<>(new Account(),EventType.TYPE_ADD));
    }

    @Override
    public Maybe<Account> getAccount(String id) {
        return null;
    }

    @Override
    public Observable<Boolean> addAccount(Account account) {
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> updateAccount(Account account) {
        return Observable.just(true);

    }

    @Override
    public Observable<Boolean> deleteAccount(Account account) {
        return Observable.just(true);
    }
}
