package com.phoenix.soft.agenda.ui.coststream;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.AccountRepository;

import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */
public class CostStreamPresenter implements CostStreamContract.Presenter {

    private final AccountRepository repository;
    private final CostStreamContract.View view;
    private List<Account> accounts;

    public CostStreamPresenter(AccountRepository repository, CostStreamContract.View view) {
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
    public void addAccount() {

    }

    @Override
    public void modifyAccount() {

    }

    @Override
    public void deleteAccount() {

    }
}
