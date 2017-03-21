package com.phoenix.soft.agenda.account;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.RxAccountRepository;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by yaoda on 22/02/17.
 */
public class AccountPresenter implements AccountContract.Presenter {

    private static final String TAG = "AccountPresenter";
    private final RxAccountRepository repository;
    private final AccountContract.View view;
    private List<Account> accounts;

    public AccountPresenter(RxAccountRepository repository, AccountContract.View view) {
        this.repository = repository;
        repository.start();
        this.view = view;

    }

    @Override
    public void loadAccount() {
        repository.getAccountList()
                  .subscribe(new DisposableSingleObserver<List<Account>>() {
                      @Override
                      public void onSuccess(List<Account> accountList) {
                          accounts = accountList;
                          if (accounts.isEmpty()) {
                              view.showNoAccount();
                          } else {
                              view.showAccountList(accounts);
                          }

                      }

                      @Override
                      public void onError(Throwable e) {
                          view.showError();
                      }
                  });
    }

    @Override
    public void addAccount(Account account) {
        repository.addAccount(account);
    }

    @Override
    public void modifyAccount() {
    }

    @Override
    public void deleteAccount(Account account) {
        repository.deleteAccount(account);
    }
}
