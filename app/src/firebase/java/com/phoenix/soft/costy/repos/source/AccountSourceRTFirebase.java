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

package com.phoenix.soft.costy.repos.source;

import android.util.ArrayMap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.models.firebase.AccountFire;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yaoda on 04/04/17.
 */

public class AccountSourceRTFirebase implements AccountSourceRT {
    private static final String ACCOUNT = "account";
    private DatabaseReference dbRef;
    private List<Account> actual;
    private ArrayMap<String, Account> accountMap;
    private Observable<ValueEvent<Account>> accountUpdate;

    public AccountSourceRTFirebase(DatabaseReference dbRef) {
        this.dbRef = dbRef.child(ACCOUNT);
        accountMap = new ArrayMap<>();
    }

    private static List<Account> parserList(DataSnapshot dataSnapshot) {
        List<Account> vList = Collections.synchronizedList(new ArrayList<Account>());
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Account account = parser(snapshot);
            if (account != null) {
                vList.add(account);
            }
        }
        return vList;
    }

    private static Account parser(DataSnapshot dataSnapshot) {
        AccountFire fire = dataSnapshot.getValue(AccountFire.class);
        if (fire != null) {
            fire.setKey(dataSnapshot.getKey());
            return fire.toModule();
        } else {
            return null;
        }
    }

    @Override public Observable<List<Account>> getAccountList() {
        return Observable.create((ObservableEmitter<DataSnapshot> e) -> {
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!e.isDisposed()) {
                        e.onNext(dataSnapshot);
                        //                        e.onComplete();
                    }
                }

                @Override public void onCancelled(DatabaseError databaseError) {
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
            for (Account account : actual) {
                accountMap.put(account.getKey(), account);
            }
            return this.actual;
        }).replay(1).autoConnect();
    }

    @Override public Observable<ValueEvent<Account>> getAccountUpdate() {
        if (accountUpdate == null) {
            accountUpdate = Observable.create((ObservableEmitter<ValueEvent<Account>> e) -> {
                ChildEventListener childEventListener = new ChildEventListener() {
                    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (!e.isDisposed()) {
                            Account account = parser(dataSnapshot);
                            String key = dataSnapshot.getKey();
                            if (!accountMap.containsKey(key)) {
                                actual.add(account);
                                accountMap.put(key, account);
                                e.onNext(new ValueEvent<>(account, EventType.TYPE_ADD));
                            }
                        }
                    }

                    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        if (!e.isDisposed()) {
                            Account account = parser(dataSnapshot);
                            String key = dataSnapshot.getKey();
                            Account accountInMap = accountMap.get(key);
                            if (accountInMap != null) {
                                actual.set(actual.indexOf(accountInMap), account);
                                accountMap.put(key, account);
                                e.onNext(new ValueEvent<>(account, EventType.TYPE_UPDATE));
                            }
                        }
                    }

                    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
                        if (!e.isDisposed()) {
                            Account account = parser(dataSnapshot);
                            String key = dataSnapshot.getKey();
                            if (accountMap.containsKey(key)) {
                                actual.remove(accountMap.get(key));
                                accountMap.remove(key);
                                e.onNext(new ValueEvent<>(account, EventType.TYPE_DELETE));
                            }
                        }
                    }

                    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        if (!e.isDisposed()) {
                            // TODO: 05/04/17
                        }
                    }

                    @Override public void onCancelled(DatabaseError databaseError) {
                        if (!e.isDisposed()) {
                            e.onError(new Throwable(databaseError.getMessage()));
                        }
                    }
                };
                dbRef.addChildEventListener(childEventListener);
                e.setCancellable(() -> dbRef.removeEventListener(childEventListener));
            }).share();
        }
        return accountUpdate;
    }

    @Override public Maybe<Account> getAccount(String id) {
        return null;
    }

    @Override public Observable<Boolean> addAccount(Account account) {
        DatabaseReference push = dbRef.push();
        account.setKey(push.getKey());
        return Observable.just(push.setValue(account.toAccountFire()).isSuccessful());
    }

    @Override public Observable<Boolean> updateAccount(Account account) {
        int position = -1;
        for (int i = 0; i < actual.size(); i++) {
            if (account.getKey().equals(actual.get(i).getKey())) {
                position = i;
            }
        }
        if (position == -1) {
            return Observable.error(
                new Throwable("Cant find Account match :" + account.getKey() + "in repository"));
        } else {
            return Observable.just(
                dbRef.child(account.getKey()).setValue(account.toAccountFire()).isSuccessful());
        }
    }

    @Override public Observable<Boolean> deleteAccount(Account account) {
        if (!actual.contains(account)) {
            return Observable.just(false);
        } else {
            return Observable.just(dbRef.child(account.getKey()).removeValue().isSuccessful());
        }
    }
}
