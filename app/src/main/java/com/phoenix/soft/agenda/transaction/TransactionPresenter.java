package com.phoenix.soft.agenda.transaction;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Transaction;
import com.phoenix.soft.agenda.repos.source.TransactionSourceRT;

import org.joda.money.Money;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 23/02/17.
 */
public class TransactionPresenter implements TransactionContract.Presenter {

    private static final String TAG = "TransactionPresenter";
    private List<Transaction> transactions;
    private TransactionSourceRT transactionSource;
    private CompositeDisposable disposables;
    private TransactionContract.View view;
    private Account account;
    private String end = "";
    private boolean isFirst = true;

    @Inject
    public TransactionPresenter(Account account, TransactionSourceRT transactionSource) {
        this.account = account;
        this.transactionSource = transactionSource;
        disposables = new CompositeDisposable();
    }

    @Override
    public void loadDetailList() {
        Disposable subscribe = transactionSource.getTransactionList()
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(transactionsList -> {
                                                            transactions = transactionsList;
                                                            if (transactions.isEmpty()) {
                                                                view.showNoTransaction();
                                                            } else {
                                                                view.showTransactionList(transactionsList);
                                                                subScribeUpdate();
                                                            }
                                                        },
                                                        throwable -> view.showError(throwable.getMessage()),
                                                        () -> {

                                                        });
        disposables.add(subscribe);

    }

    private void subScribeUpdate() {
        disposables.add(transactionSource.getTransactionUpdate()
                                         .subscribe(transactionValueEvent -> view.updateList(),
                                                 throwable -> view.showError(throwable.getMessage())));
    }

    @Override
    public void addDetail(Transaction transaction) {

        transactionSource.addTransaction(transaction);
//        view.updateList();
    }


    @Override
    public void attachView(TransactionContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        disposables.clear();
    }
}
