package com.phoenix.soft.agenda.account;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.firebase.AccountFire;
import com.phoenix.soft.agenda.repos.AccountRepository;
import com.phoenix.soft.agenda.repos.FirebaseObservable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by yaoda on 22/02/17.
 */
public class AccountPresenter implements AccountContract.Presenter {

    private static final String TAG = "AccountPresenter";
    private final AccountRepository repository;
    private final AccountContract.View view;
    @Inject
    @Named("Account")
    DatabaseReference databaseReference;
    private List<Account> accounts;

    public AccountPresenter(AccountRepository repository, AccountContract.View view) {
        MainApplication.getFirebaseComponent().inject(this);
        accounts = new ArrayList<>();
        this.repository = repository;
        repository.start();
        this.view = view;
    }

    @Override
    public void loadAccount() {
        FirebaseObservable<AccountFire> observable = new FirebaseObservable<>(AccountFire.class, 10, "account", databaseReference);
        observable.observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new DisposableObserver<AccountFire>() {
                      @Override
                      public void onNext(AccountFire accountFire) {
                          Log.d(TAG, "onNext: ");
                          Account e = accountFire.toAccount();
                          e.setDetailList(new ArrayList<>());
                          accounts.add(e);
                      }

                      @Override
                      public void onError(Throwable e) {
                          Log.d(TAG, "onError: ");
                          view.showError();
                      }

                      @Override
                      public void onComplete() {
                          Log.d(TAG, "onComplete: ");
                          if (accounts.isEmpty()) {
                              view.showNoAccount();
                          } else {
                              view.showAccountList(accounts);
                          }
                      }
                  });

/*
        accounts = repository.getAccountList();
        if (accounts == null) {
            view.showError();
        } else if (accounts.isEmpty()) {
            view.showNoAccount();
        } else {
            view.showAccountList(accounts);
        }*/
    }

    @Override
    public void addAccount(Account account) {
        if (account == null) {
            account = repository.getRandomAccount();
        }
        view.updateAccount();
    }

    @Override
    public void modifyAccount() {

    }

    @Override
    public void deleteAccount() {

    }
}
