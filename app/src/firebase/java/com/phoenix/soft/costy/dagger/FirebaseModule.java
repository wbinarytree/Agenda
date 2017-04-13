package com.phoenix.soft.costy.dagger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    @Named("Account")
    DatabaseReference provideAccountDatabaseRef(FirebaseUser user) {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        return FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
    }

    @Provides
    @Singleton
    FirebaseUser provideUser(FirebaseAuth auth) {
        return auth.getCurrentUser();
    }

}
