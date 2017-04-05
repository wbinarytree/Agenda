package com.phoenix.soft.agenda.account;

import android.util.Log;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.source.AccountSourceRT;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by yaoda on 22/02/17.
 */
public class AccountPresenter implements AccountContract.Presenter {

    private static final String TAG = "AccountPresenter";
    private AccountContract.View view;
    private CompositeDisposable compositeDisposable;
    private AccountSourceRT realTimeRepo;
    private Observable<List<Account>> accountList;

    @Inject
    public AccountPresenter(AccountSourceRT realTime) {
        this.realTimeRepo = realTime;
        compositeDisposable = new CompositeDisposable();
        accountList = realTimeRepo.getAccountList();
    }

    @Override
    public void loadAccount() {
        view.showLoading();
        Disposable subscribe = accountList.observeOn(AndroidSchedulers.mainThread())
                                          .doOnEach(disposable -> view.hideLoading())
                                          .subscribe(accounts -> {
                                                      if (accounts.isEmpty()) {
                                                          view.showNoAccount();
                                                      } else {
                                                          view.showAccountList(accounts);
                                                      }
                                                  },
                                                  throwable -> view.showError());
        compositeDisposable.add(subscribe);
    }

    @Override
    public void addAccount(Account account) {
        realTimeRepo.addAccount(account);
//        view.updateAccount(account);
    }

    @Override
    public void modifyAccount() {
    }

    @Override
    public void deleteAccount(Account account) {
        realTimeRepo.deleteAccount(account);
    }

    @Override
    public void attachView(AccountContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        compositeDisposable.clear();
    }
}
