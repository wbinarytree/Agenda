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
