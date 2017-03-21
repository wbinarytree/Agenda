package com.phoenix.soft.agenda.repos;

import com.phoenix.soft.agenda.module.Account;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by yaoda on 21/03/17.
 */

public interface RxAccountRepository {
    Observable<Account> getAccount();

    Single<List<Account>> getAccountList();

    boolean addAccount(Account account);

    boolean deleteAccount(Account account);

    boolean updateAccount(Account account);
    void start();

    void end();
}