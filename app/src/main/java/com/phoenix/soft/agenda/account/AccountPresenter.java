package com.phoenix.soft.agenda.account;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.AccountRepository;

import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */
public class AccountPresenter implements AccountContract.Presenter {

    private final AccountRepository repository;
    private final AccountContract.View view;
    private List<Account> accounts;

    public AccountPresenter(AccountRepository repository, AccountContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void loadAccount() {

        accounts = repository.getAccountList();
        if (accounts == null) {
            view.showError();
        } else if (accounts.isEmpty()) {
            view.showNoAccount();
        } else {
            view.showAccountList(accounts);
        }
    }

    @Override
    public void addAccount(Account account) {
        if (account == null) {
            account = repository.getRandomAccount();
        }
        view.showNewAccount();
    }

    @Override
    public void modifyAccount() {

    }

    @Override
    public void deleteAccount() {

    }
}
