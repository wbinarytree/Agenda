package com.phoenix.soft.costy.repos.source;


import com.phoenix.soft.costy.models.Account;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by yaoda on 04/04/17.
 */

public interface AccountSourceRT {
    Observable<List<Account>> getAccountList();

    Observable<ValueEvent<Account>> getAccountUpdate();

    Maybe<Account> getAccount(String id);

    Observable<Boolean> addAccount(Account account);

    Observable<Boolean> updateAccount(Account account);

    Observable<Boolean> deleteAccount(Account account);

}
