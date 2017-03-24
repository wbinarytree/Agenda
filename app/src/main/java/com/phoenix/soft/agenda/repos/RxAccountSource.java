package com.phoenix.soft.agenda.repos;

import com.phoenix.soft.agenda.module.Account;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by yaoda on 21/03/17.
 */

public interface RxAccountSource {
    Maybe<Account> getAccount(String key);

    Maybe<List<Account>> getAccountList();

    boolean addAccount(Account account);

    boolean deleteAccount(Account account);

    boolean updateAccount(Account account);

    void refreshAccount();

}
