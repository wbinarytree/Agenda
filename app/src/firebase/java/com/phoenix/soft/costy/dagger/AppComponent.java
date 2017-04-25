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
import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.costy.MainApplication;
import com.phoenix.soft.costy.login.AuthActivity;
import com.phoenix.soft.costy.login.LoginFragment;
import com.phoenix.soft.costy.login.SignUpFragment;
import com.phoenix.soft.costy.transaction.di.TransactionComponent;
import com.phoenix.soft.costy.transaction.di.TransactionModule;
import dagger.Component;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by yaoda on 10/04/17.
 */
@Singleton @Component(modules = { AppModule.class, FirebaseModule.class })
public interface AppComponent {
    TransactionComponent plus(TransactionModule transModule);

    void inject(MainApplication application);

    FirebaseAuth getAuth();

    void inject(AuthActivity activity);

    void inject(LoginFragment loginFragment);

    void inject(SignUpFragment signUpFragment);

    @Named("Account") DatabaseReference getDataRef();
}
