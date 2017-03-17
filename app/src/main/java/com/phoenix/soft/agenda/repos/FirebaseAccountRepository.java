package com.phoenix.soft.agenda.repos;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.firebase.AccountFire;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yaoda on 17/03/17.
 */

public class FirebaseAccountRepository implements AccountRepository {
    private static List<Account> accounts;
    private static List<AccountFire> remoteAccount;
    @Inject
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    @Inject
    @Named("Account")
    DatabaseReference accountRef;

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
        MainApplication.getFirebaseComponent().inject(this);
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void end() {
        auth.removeAuthStateListener(authStateListener);
    }
}
