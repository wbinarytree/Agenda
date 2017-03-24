package com.phoenix.soft.agenda.account;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.RxAccountSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by yaoda on 22/02/17.
 */
public class AccountPresenter implements AccountContract.Presenter {

    private static final String TAG = "AccountPresenter";
    private final RxAccountSource repository;
    private AccountContract.View view;
    private List<Account> accounts;

    @Inject
    public AccountPresenter(RxAccountSource repository) {
        this.repository = repository;
    }

    @Override
    public void loadAccount() {
        view.showLoading();
        repository.getAccountList()
                  .doOnEvent((accountList, throwable) -> view.hideLoading())
                  .subscribe(new DisposableMaybeObserver<List<Account>>() {
                      @Override
                      public void onSuccess(List<Account> accountList) {
                          accounts = accountList;
                          view.showAccountList(accounts);
                      }
                      @Override
                      public void onError(Throwable e) {
                          view.showError();
                      }
                      @Override
                      public void onComplete() {
                          view.showNoAccount();
                      }
                  });
    }

    @Override
    public void addAccount(Account account) {
        repository.addAccount(account);
        view.updateAccount(account);
    }

    @Override
    public void modifyAccount() {
    }

    @Override
    public void deleteAccount(Account account) {
        repository.deleteAccount(account);
    }

    @Override
    public void attachView(AccountContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
