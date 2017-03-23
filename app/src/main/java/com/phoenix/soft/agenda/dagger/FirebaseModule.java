package com.phoenix.soft.agenda.dagger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phoenix.soft.agenda.repos.FirebaseRxAccountSource;
import com.phoenix.soft.agenda.repos.FirebaseRxTransactionRepository;
import com.phoenix.soft.agenda.repos.RxAccountSource;
import com.phoenix.soft.agenda.repos.RxTransactionRepository;

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

    @Provides
    @Singleton
    RxAccountSource provideRxRepo(@Named("Account") DatabaseReference dbref){
        return new FirebaseRxAccountSource(dbref);
    }

    @Provides
    RxTransactionRepository provideRxTransRepo(@Named("Account") DatabaseReference dbRef,String key){
        return new FirebaseRxTransactionRepository(key,dbRef);
    }

}
