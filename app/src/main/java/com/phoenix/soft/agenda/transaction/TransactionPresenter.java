package com.phoenix.soft.agenda.transaction;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
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
    private final TransactionContract.View view;
    @Inject
    @Named("Account")
    DatabaseReference dbRef;
    private Account account;
    private String key;

    public TransactionPresenter(Account account, TransactionContract.View view) {
        this.account = account;
        this.transactions = account.getTransactionList();
        this.view = view;
    }

    public TransactionPresenter(String key, TransactionContract.View view) {
        MainApplication.getFirebaseComponent().inject(this);
        this.key = key;
        transactions = new ArrayList<>();
        this.view = view;
    }

    @Override
    public void loadDetailList() {

        RxDatabase.limitToFirst(TransactionFire.class, dbRef.child("transaction")
                                                            .child(key)
                                                            .limitToFirst(10)
                                                            .orderByChild("date"))
                  .subscribe(new DisposableObserver<TransactionFire>() {
                      @Override
                      public void onNext(TransactionFire transactionFire) {
                          try {
                              transactions.add(transactionFire.toTransaction());
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
                          view.showTransactionList(transactions);
                      }
                  });
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
}
