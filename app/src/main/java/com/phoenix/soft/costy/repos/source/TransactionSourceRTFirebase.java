package com.phoenix.soft.costy.repos.source;

import android.util.ArrayMap;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.phoenix.soft.costy.module.Transaction;
import com.phoenix.soft.costy.module.firebase.TransactionFire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by yaoda on 05/04/17.
 */

public class TransactionSourceRTFirebase implements TransactionSourceRT {
    private static final String TRANS = "transaction";
    private DatabaseReference dbRef;
    private List<Transaction> actual;
    private ArrayMap<String, Transaction> transactionMap;
    private Observable<ValueEvent<Transaction>> transactionUpdate;
    private Observable<List<Transaction>> transListObservable;


    public TransactionSourceRTFirebase(DatabaseReference dbRef, String key) {
        this.dbRef = dbRef.child(TRANS).child(key);
        transactionMap = new ArrayMap<>();
    }

    private static List<Transaction> parserList(DataSnapshot dataSnapshot) {
        List<Transaction> vList = Collections.synchronizedList(new ArrayList<Transaction>());
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Transaction transaction = parser(snapshot);
            if (transaction != null) {
                vList.add(0, transaction);
            }
        }
        return vList;
    }

    private static Transaction parser(DataSnapshot dataSnapshot) {
        TransactionFire fire = dataSnapshot.getValue(TransactionFire.class);
        if (fire != null) {
            fire.setKey(dataSnapshot.getKey());
            return fire.toModule();
        } else {
            return null;
        }


    }

    @Override
    public Observable<List<Transaction>> getTransactionList() {
        if (transListObservable == null) {
            transListObservable = Observable.create((ObservableEmitter<DataSnapshot> e) -> {
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!e.isDisposed()) {
                            e.onNext(dataSnapshot);
//                        e.onComplete();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (!e.isDisposed()) {
                            e.onError(new Throwable(databaseError.getMessage()));
                        }
                    }
                };
                dbRef.addListenerForSingleValueEvent(valueEventListener);
//            dbRef.addValueEventListener(valueEventListener);
                e.setCancellable(() -> dbRef.removeEventListener(valueEventListener));
            }).map(dataSnapshot -> {
                this.actual = parserList(dataSnapshot);
                for (Transaction account : actual) {
                    transactionMap.put(account.getKey(), account);
                }
                return this.actual;
            }).replay(1).autoConnect();
        }
        return transListObservable;
    }

    @Override
    public Observable<ValueEvent<Transaction>> getTransactionUpdate() {
        if (transactionUpdate == null) {
            transactionUpdate = Observable.create((ObservableEmitter<ValueEvent<Transaction>> e) -> {
                ChildEventListener childEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (!e.isDisposed()) {
                            Transaction transaction = parser(dataSnapshot);
                            String key = dataSnapshot.getKey();
                            if (!transactionMap.containsKey(key)) {
                                actual.add(0, transaction);
                                transactionMap.put(key, transaction);
                                e.onNext(new ValueEvent<>(transaction, EventType.TYPE_ADD));
                            }
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        if (!e.isDisposed()) {
                            Transaction transaction = parser(dataSnapshot);
                            String key = dataSnapshot.getKey();
                            Transaction accountInMap = transactionMap.get(key);
                            if (accountInMap != null) {
                                actual.set(actual.indexOf(accountInMap), transaction);
                                transactionMap.put(key, transaction);
                                e.onNext(new ValueEvent<>(transaction, EventType.TYPE_UPDATE));
                            }
                        }
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        if (!e.isDisposed()) {
                            Transaction transaction = parser(dataSnapshot);
                            String key = dataSnapshot.getKey();
                            if (transactionMap.containsKey(key)) {
                                actual.remove(transactionMap.get(key));
                                transactionMap.remove(key);
                                e.onNext(new ValueEvent<>(transaction, EventType.TYPE_DELETE));
                            }
                        }
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        if (!e.isDisposed()) {
                            // TODO: 05/04/17
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (!e.isDisposed()) {
                            e.onError(new Throwable(databaseError.getMessage()));
                        }
                    }
                };
                dbRef.addChildEventListener(childEventListener);
                e.setCancellable(() -> dbRef.removeEventListener(childEventListener));
            }).share();
        }
        return transactionUpdate;
    }

    @Override
    public Maybe<Transaction> getTransaction(String id) {
        return null;
    }

    @Override
    public Observable<Boolean> addTransaction(Transaction transaction) {
        DatabaseReference push = dbRef.push();
        transaction.setKey(push.getKey());
        return Observable.just(push.setValue(transaction.toFire()).isSuccessful());
    }

    @Override
    public Observable<Boolean> updateTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Observable<Boolean> deleteTransaction(Transaction transaction) {
        return null;
    }
}
