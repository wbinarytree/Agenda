package com.phoenix.soft.agenda.account;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.source.AccountSourceRealTime;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yaoda on 22/02/17.
 */
public class AccountPresenter implements AccountContract.Presenter {

    private static final String TAG = "AccountPresenter";
    private AccountContract.View view;
    private CompositeDisposable compositeDisposable;
    private AccountSourceRealTime realTimeRepo;

    @Inject
    public AccountPresenter(AccountSourceRealTime realTime) {
        this.realTimeRepo = realTime;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadAccount() {
        view.showLoading();
        compositeDisposable.add(realTimeRepo.getAccountList()
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doOnEach(disposable -> view.hideLoading())
                                            .subscribe(accounts -> {
                                                if (accounts.isEmpty()) {
                                                    view.showNoAccount();
                                                } else {
                                                    view.showAccountList(accounts);
                                                }
                                            }, throwable -> view.showError()));

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
