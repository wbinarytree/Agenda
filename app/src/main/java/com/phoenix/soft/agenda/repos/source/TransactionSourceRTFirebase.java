package com.phoenix.soft.agenda.repos.source;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.module.Transaction;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by yaoda on 05/04/17.
 */

public class TransactionSourceRTFirebase implements TransactionSourceRT {
    private DatabaseReference dbRef;

    public TransactionSourceRTFirebase(DatabaseReference dbRef) {

        this.dbRef = dbRef;
    }

    @Override
    public Observable<List<Transaction>> getTransactionList() {
        return Observable.create(new ObservableOnSubscribe<List<Transaction>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Transaction>> e) throws Exception {
                ChildEventListener childEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                dbRef.addChildEventListener(childEventListener);
                e.setCancellable(() -> dbRef.removeEventListener(childEventListener));
            }
        });
    }

    @Override
    public Maybe<Transaction> getTransaction(String id) {
        return null;
    }

    @Override
    public Observable<Boolean> addTransaction(Transaction transaction) {
        return null;
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
