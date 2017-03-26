package com.phoenix.soft.agenda.transaction;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Transaction;
import com.phoenix.soft.agenda.module.firebase.TransactionFire;
import com.phoenix.soft.agenda.rxfirebase.RxDatabase;

import org.joda.money.Money;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by yaoda on 23/02/17.
 */
public class TransactionPresenter implements TransactionContract.Presenter {

    private static final String TAG = "TransactionPresenter";
    private final List<Transaction> transactions;
    @Inject
    @Named("Account")
    DatabaseReference dbRef;
    private TransactionContract.View view;
    private Account account;
    private String key;
    private String end = "";
    private boolean isFirst = true;


    public TransactionPresenter(Account account) {
        this.account = account;
        this.transactions = account.getTransactionList();

    }

    public TransactionPresenter(String key) {
        MainApplication.getFirebaseComponent().inject(this);
        this.key = key;
        transactions = new ArrayList<>();
    }

    @Override
    public void loadDetailList() {
        Query transaction;
        List<Transaction> list = new ArrayList<>();
        if (isFirst) {
            transaction = dbRef.child("transaction").child(key).orderByKey().limitToLast(5);
            RxDatabase.limitToFirst(TransactionFire.class, transaction)
                      .subscribe(new DisposableObserver<TransactionFire>() {
                          @Override
                          public void onNext(TransactionFire transactionFire) {

                              try {
                                  list.add(0, transactionFire.toTransaction());

                              } catch (ParseException e) {
                                  e.printStackTrace();
                              }

                              Log.d(TAG, "onNext: ");
                          }

                          @Override
                          public void onError(Throwable e) {
                              Log.d(TAG, "onError: ");
                              view.showError("Error");
                          }

                          @Override
                          public void onComplete() {
//                if (transactions.isEmpty()) {
//                    view.showNoTransaction();
//                } else {
//                }
                              isFirst = false;
                              end = list.get(0).getKey();
                              transactions.addAll(list);
                              view.showTransactionList(transactions);

                          }
                      });
        } else {
            transaction = dbRef.child("transaction")
                               .child(key)
                               .orderByKey()
                               .endAt(end)
                               .limitToLast(5);
            RxDatabase.limitToFirst(TransactionFire.class, transaction)
                      .skipLast(1)
                      .subscribe(new DisposableObserver<TransactionFire>() {
                          @Override
                          public void onNext(TransactionFire transactionFire) {
                              try {
                                  list.add(0, transactionFire.toTransaction());
                              } catch (ParseException e) {
                                  e.printStackTrace();
                              }

                              Log.d(TAG, "onNext: ");
                          }

                          @Override
                          public void onError(Throwable e) {
                              Log.d(TAG, "onError: ");
                              view.showError("Error");
                          }

                          @Override
                          public void onComplete() {
//                if (transactions.isEmpty()) {
//                    view.showNoTransaction();
//                } else {
//                }
                              end = list.get(0).getKey();
                              transactions.addAll(list);
                              view.showTransactionList(transactions);
                          }
                      });

        }

    }

    @Override
    public void addDetail(String number, boolean add) {
        // TODO: 03/03/17 currency selector
        Money money = add ? Money.parse("USD " + number) : Money.parse("USD " + "-" + number);
        Transaction transaction = new Transaction();
        transaction.setMoney(money);
        transaction.setDate(new Date());
        transaction.setDesc("temp desc");
        transactions.add(0, transaction);
        dbRef.child("transaction")
             .child(key)
             .push()
             .setValue(transaction.toTransactionFire())
             .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: "));
        view.updateList();
    }


    @Override
    public void attachView(TransactionContract.View view) {
        this.view = view;

    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
