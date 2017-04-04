package com.phoenix.soft.agenda.repos.source;

import com.phoenix.soft.agenda.module.Account;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by yaoda on 04/04/17.
 */

public interface AccountSourceRealTime {
    Observable<List<Account>> getAccountList();

    Maybe<Account> getAccount(String id);

    Observable<Boolean> addAccount(Account account);

    Observable<Boolean> updateAccount(Account account);

    Observable<Boolean> deleteAccount(Account account);
}
