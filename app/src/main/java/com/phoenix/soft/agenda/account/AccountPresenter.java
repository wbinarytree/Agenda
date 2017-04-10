package com.phoenix.soft.agenda.account;


import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Transaction;
import com.phoenix.soft.agenda.repos.source.AccountSourceRT;
import com.phoenix.soft.agenda.repos.source.ValueEvent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 22/02/17.
 */
public class AccountPresenter implements AccountContract.Presenter {

    private static final String TAG = "AccountPresenter";
    private final Observable<ValueEvent<Account>> accountUpdate;
    private AccountContract.View view;
    private CompositeDisposable compositeDisposable;
    private AccountSourceRT realTimeRepo;
    private Observable<List<Account>> accountList;

    @Inject
    public AccountPresenter(AccountSourceRT realTime) {
        this.realTimeRepo = realTime;
        compositeDisposable = new CompositeDisposable();
        accountList = realTimeRepo.getAccountList();
        accountUpdate = realTimeRepo.getAccountUpdate();
    }

    @Override
    public void loadAccount() {
        view.showLoading();

        Disposable subscribe = accountList.observeOn(AndroidSchedulers.mainThread())
                                          .doOnEach(disposable -> view.hideLoading())
                                          .doAfterNext(accounts -> compositeDisposable.add(
                                                  accountUpdate.subscribe(value -> view.updateAccount(
                                                          value.getValue(),
                                                          value.getType()),
                                                          throwable -> view.showError())))
                                          .subscribe(accounts -> {
                                              view.initAccountList(accounts);
                                          }, throwable -> view.showError());
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

    @Override
    public void updateTransactionToAccount(Transaction transaction, Account account) {
        if (transaction.getMoney().isNegative()) {
            account.setOutcome(account.getOutcome().plus(transaction.getMoney().abs()));
        } else {
            account.setIncome(account.getIncome().plus(transaction.getMoney()));
        }
        realTimeRepo.updateAccount(account);
    }

}
