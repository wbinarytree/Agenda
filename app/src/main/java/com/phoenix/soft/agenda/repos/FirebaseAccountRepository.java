package com.phoenix.soft.agenda.repos;

import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.firebase.AccountFire;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by yaoda on 17/03/17.
 */

public class FirebaseAccountRepository extends Observable<AccountFire> implements AccountRepository {
    private static List<Account> accounts;
    private static List<AccountFire> remoteAccount;
    private static ArrayMap<String, AccountFire> accountArray;
    private Observable<AccountFire> observable;
    private Observer<AccountFire> observer;
    @Inject
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    @Inject
    @Named("Account")
    DatabaseReference accountRef;

    public FirebaseAccountRepository() {
        accountArray = new ArrayMap<>(10);
    }

    @Override
    protected void subscribeActual(Observer<? super AccountFire> observer) {
        MainApplication.getFirebaseComponent().inject(this);
        Listener listener = new Listener(observer);
        accountRef.child("account").addChildEventListener(listener);
        observer.onSubscribe(listener);
    }

    public static Observable<AccountFire> getObservable(){
        return new FirebaseAccountRepository();
    }

    @Override
    public List<Account> getAccountList() {
        return null;
    }

    @Override
    public boolean addAccount(Account account) {
        AccountFire accountFire = account.toAccountFire();
        accountRef.child("account").push().setValue(accountFire);
        return true;
    }

    @Override
    public boolean delAccount(Account account) {
        return false;
    }

    @Override
    public Account getRandomAccount() {
        return null;
    }

    @Override
    public void start() {
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
//                databaseReference.child("Account")
//                                 .setValue(new AccountFire("BankAccount", "www.google.com", 1));
            } else {
                // User is signed out
            }
        };
        auth.addAuthStateListener(authStateListener);

    }
    private class Listener implements Disposable,ChildEventListener {
        Observer<? super AccountFire> observer;

        public Listener(Observer<? super AccountFire> observer) {
            this.observer = observer;
        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            AccountFire accountFire = dataSnapshot.getValue(AccountFire.class);
            observer.onNext(accountFire);
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
    }

    @Override
    public void end() {
        auth.removeAuthStateListener(authStateListener);
    }
}
