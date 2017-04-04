package com.phoenix.soft.agenda.repos.source;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.firebase.AccountFire;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by yaoda on 04/04/17.
 */

public class AccountSourceRealTimeFirebase implements AccountSourceRealTime {
    private static final String ACCOUNT = "account";
    private DatabaseReference dbRef;
    private List<Account> actual;
    private Observable<List<Account>> observable;

    public AccountSourceRealTimeFirebase(DatabaseReference dbRef) {
        this.dbRef = dbRef.child(ACCOUNT);
    }

    private static List<Account> parserList(DataSnapshot dataSnapshot) {
        List<Account> vList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            AccountFire fire = snapshot.getValue(AccountFire.class);
            fire.setKey(snapshot.getKey());
            vList.add(fire.toModule());
        }
        return vList;
    }

    @Override
    public Observable<List<Account>> getAccountList() {
        if (observable == null) {
            observable = Observable.create((ObservableEmitter<DataSnapshot> e) -> {
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!e.isDisposed()) {
                            e.onNext(dataSnapshot);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (!e.isDisposed()) {
                            e.onError(new Throwable(databaseError.getMessage()));
                        }
                    }
                };
                dbRef.addValueEventListener(valueEventListener);
                e.setCancellable(() -> dbRef.removeEventListener(valueEventListener));
            }).map(dataSnapshot -> {
                this.actual = parserList(dataSnapshot);
                return this.actual;
            }).replay(1).autoConnect();
        }
        return observable;
    }

    @Override
    public Maybe<Account> getAccount(String id) {
        return null;
    }

    @Override
    public Observable<Boolean> addAccount(Account account) {
        DatabaseReference push = dbRef.push();
        account.setKey(push.getKey());
        return Observable.just(push.setValue(account.toAccountFire()).isSuccessful());
    }

    @Override
    public Observable<Boolean> updateAccount(Account account) {
        int position = -1;
        for (int i = 0; i < actual.size(); i++) {
            if (account.getKey().equals(actual.get(i).getKey())) {
                position = i;
            }
        }
        if (position == -1) {
            return Observable.just(false);
        } else {
            return Observable.just(dbRef.child(actual.toString())
                                        .setValue(account.toAccountFire())
                                        .isSuccessful());
        }
    }

    @Override
    public Observable<Boolean> deleteAccount(Account account) {
        if (!actual.contains(account)) {
            return Observable.just(false);
        } else {
            return Observable.just(dbRef.child(account.getKey()).removeValue().isSuccessful());
        }
    }
}
