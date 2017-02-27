package com.phoenix.soft.agenda.repos;

import com.phoenix.soft.agenda.module.Account;

import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */

public interface AccountRepository {
    List<Account> getAccountList();
    boolean addAccount(Account account);
    boolean delAccount(Account account);

    Account getRandomAccount();
}
