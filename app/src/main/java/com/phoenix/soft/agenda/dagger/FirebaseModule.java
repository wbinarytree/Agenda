package com.phoenix.soft.agenda.dagger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phoenix.soft.agenda.repos.RxSource;
import com.phoenix.soft.agenda.repos.RxTransactionSource;
import com.phoenix.soft.agenda.repos.TransactionRxSource;
import com.phoenix.soft.agenda.repos.source.FirebaseRxAccountSource;
import com.phoenix.soft.agenda.repos.FirebaseRxTransactionRepository;
import com.phoenix.soft.agenda.repos.RxAccountSource;
import com.phoenix.soft.agenda.repos.RxTransactionRepository;
import com.phoenix.soft.agenda.repos.source.RxAccountRepository;

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
    DatabaseReference provideAccountDatabaseRef(FirebaseUser user){
        return FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
    }

    @Provides
    @Singleton
    FirebaseUser provideUser(FirebaseAuth auth){
        return auth.getCurrentUser();
    }

    @Provides
    @Singleton
    FirebaseRxAccountSource provideRxRepo(@Named("Account") DatabaseReference dbRef){
        return new FirebaseRxAccountSource(dbRef);
    }

    @Provides
    @Singleton
    RxAccountSource provideRxSource(FirebaseRxAccountSource repo){
        return  new RxAccountRepository(null,repo);
    }


    @Provides
    RxTransactionRepository provideRxTransRepo(@Named("Account") DatabaseReference dbRef,String key){
        return new FirebaseRxTransactionRepository(key,dbRef);
    }

    @Provides
    TransactionRxSource provideRxTransSoure(@Named("Account") DatabaseReference dbRef,String key){
        return new TransactionRxSource(key,dbRef);
    }

}
