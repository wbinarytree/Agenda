package com.phoenix.soft.costy;

import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phoenix.soft.costy.repos.source.AccountSourceRTFirebase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;

/**
 * Created by yaoda on 05/04/17.
 */
@RunWith(AndroidJUnit4.class)
public class AccountSourceRTFirebaseTest {

    public AccountSourceRTFirebase testBase;
    public Observable<String> observable;

    @Before
    public void setUp() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword("awangyaoda@hotmail.com", "930621a123");
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                                                  .getReference()
                                                  .child(FirebaseAuth.getInstance()
                                                                     .getCurrentUser()
                                                                     .getUid());
        testBase = new AccountSourceRTFirebase(dbRef);
//        observable = Observable.just("Test");
    }

    @Test
    public void RepoTest() {
        System.out.println("sdsad");
        testBase.getAccountList().subscribe(System.out::println);
    }

}