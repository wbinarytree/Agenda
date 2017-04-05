package com.phoenix.soft.agenda.repos.source;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;
import com.phoenix.soft.agenda.module.Account;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by yaoda on 04/04/17.
 */

public class AccountSourceRTImpl implements AccountSourceRT {
    private List<Account> actual;
    private Relay<List<Account>> accountsSubject;


    public AccountSourceRTImpl() {
        actual = new ArrayList<>();
        accountsSubject = BehaviorRelay.createDefault(actual).toSerialized();
    }

    public AccountSourceRTImpl(List<Account> accounts) {
        actual = accounts;
        accountsSubject = BehaviorRelay.createDefault(actual).toSerialized();
    }

    @Override
    public Observable<List<Account>> getAccountList() {
        return accountsSubject;
    }

    @Override
    public Maybe<Account> getAccount(String id) {
        for (Account a : actual) {
            if (a.getKey().equals(id)) return Maybe.just(a);
        }
        return Maybe.empty();
    }

    @Override
    public Observable<Boolean> addAccount(Account account) {
        if (actual.add(account)) {
            accountsSubject.accept(actual);
            return null;
        } else {
            return null;
        }
    }

    @Override
    public Observable<Boolean> updateAccount(Account account) {
        int position = -1;
        for (int i = 0; i < actual.size(); i++) {
            if (account.getKey().equals(actual.get(i).getKey())) {
                position = i;
            }
        }
//        return position != -1 && actual.set(position, account) == null;
        return null;
    }

    @Override
    public Observable<Boolean> deleteAccount(Account account) {
        boolean remove = actual.remove(account);
        if (remove) {
            accountsSubject.accept(actual);
        }
        return null;
    }

    @Override
    public Observable<Object> getAccountUpdate() {
        return null;
    }
}
