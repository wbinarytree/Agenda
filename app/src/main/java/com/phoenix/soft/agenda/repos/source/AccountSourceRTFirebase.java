package com.phoenix.soft.agenda.repos.source;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.firebase.AccountFire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by yaoda on 04/04/17.
 */

public class AccountSourceRTFirebase implements AccountSourceRT {
    private static final String ACCOUNT = "account";
    private DatabaseReference dbRef;
    private List<Account> actual;

    public AccountSourceRTFirebase(DatabaseReference dbRef) {
        this.dbRef = dbRef.child(ACCOUNT);
    }

    private static List<Account> parserList(DataSnapshot dataSnapshot) {
        List<Account> vList = Collections.synchronizedList(new ArrayList<Account>());
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            vList.add(parser(snapshot));
        }
        return vList;
    }

    private static Account parser(DataSnapshot dataSnapshot) {
        AccountFire fire = dataSnapshot.getValue(AccountFire.class);
        fire.setKey(dataSnapshot.getKey());
        return fire.toModule();
    }

    @Override
    public Observable<List<Account>> getAccountList() {
        return Observable.create((ObservableEmitter<DataSnapshot> e) -> {
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
        });
    }

    @Override
    public Observable<Object> getAccountUpdate() {
        return null;
//        return GroupedObservable.create(e -> {
//            ChildEventListener childEventListener = new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    if (!e.isDisposed()) {
//                        Account account = parser(dataSnapshot);
//                        boolean contains = false;
//                        for (Account a : actual) {
//                            if (a.getKey().equals(account.getKey())) {
//                                contains = true;
//                                break;
//                            }
//                        }
//                        if (!contains) {
//                            actual.add(account);
//                            e.onNext(INSTANCE);
//                        }
//                    }
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                    if (!e.isDisposed()) {
//                        Account account = parser(dataSnapshot);
//                        for (int i = 0; i < actual.size(); i++) {
//                            if (actual.get(i).getKey().equals(account.getKey())) {
//                                actual.set(i, account);
//                                e.onNext(INSTANCE);
//                                break;
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//                    if (!e.isDisposed()) {
//                        Account account = parser(dataSnapshot);
//                        if (actual.remove(account)) {
//                            e.onNext(INSTANCE);
//                        }
//                    }
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                    if (!e.isDisposed()) {
//                        // TODO: 05/04/17
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    if (!e.isDisposed()) {
//                        e.onError(new Throwable(databaseError.getMessage()));
//                    }
//                }
//            };
//            dbRef.addChildEventListener(childEventListener);
//            e.setCancellable(() -> dbRef.removeEventListener(childEventListener));
//        }).share();
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
