/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phoenix.soft.costy.transaction;

import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.models.Transaction;
import com.phoenix.soft.costy.repos.source.TransactionSourceRT;
import com.phoenix.soft.costy.repos.source.ValueEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;

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
    private Observable<List<Transaction>> transactionList;
    private Observable<ValueEvent<Transaction>> transactionUpdate;

    public TransactionPresenter(Account account, TransactionSourceRT transactionSource) {
        this.account = account;
        this.transactionSource = transactionSource;
        disposables = new CompositeDisposable();
        transactionList = transactionSource.getTransactionList();
        transactionUpdate = transactionSource.getTransactionUpdate();
    }

    @Override public void loadDetailList() {
        Disposable subscribe = transactionList.observeOn(AndroidSchedulers.mainThread())
            .doAfterNext(ignore -> subScribeUpdate())
            .subscribe(transactionsList -> {
                transactions = transactionsList;
                view.initTransactionList(transactionsList);
            }, throwable -> view.showError(throwable.getMessage()), () -> {

            });
        disposables.add(subscribe);
    }

    private void subScribeUpdate() {
        disposables.add(transactionUpdate.subscribe(transactionValueEvent -> view.updateList(),
            throwable -> view.showError(throwable.getMessage())));
    }

    @Override public void addDetail(Transaction transaction) {

        transactionSource.addTransaction(transaction);
        //        view.updateList();
    }

    @Override public void attachView(TransactionContract.View view) {
        this.view = view;
    }

    @Override public void detachView() {
        this.view = null;
        disposables.clear();
    }
}
