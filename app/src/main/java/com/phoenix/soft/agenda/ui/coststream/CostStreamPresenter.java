package com.phoenix.soft.agenda.ui.coststream;

import com.phoenix.soft.agenda.repos.AccountRepository;

/**
 * Created by yaoda on 22/02/17.
 */
public class CostStreamPresenter implements CostStreamContract.Presenter {

    private final AccountRepository repository;
    private final CostStreamContract.View view;

    public CostStreamPresenter(AccountRepository repository, CostStreamContract.View view){
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void loadAccount() {
        view.showAccountList(repository.getAccountList());
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
