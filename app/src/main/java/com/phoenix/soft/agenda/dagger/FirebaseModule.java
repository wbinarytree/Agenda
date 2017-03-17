package com.phoenix.soft.agenda.dagger;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FilterReader;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yaoda on 17/03/17.
 */

@Module
public class FirebaseModule {
    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }
    @Provides
    @Singleton
    @Named("Account")
    DatabaseReference provideAccoutDatabaseRef(FirebaseUser user){
        return FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
    }

    @Provides
    @Singleton
    FirebaseUser provideUser(FirebaseAuth auth){
        return auth.getCurrentUser();
    }

}
