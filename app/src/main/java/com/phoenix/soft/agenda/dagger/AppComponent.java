package com.phoenix.soft.agenda.dagger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.login.AuthActivity;
import com.phoenix.soft.agenda.login.LoginFragment;
import com.phoenix.soft.agenda.login.SignUpFragment;
import com.phoenix.soft.agenda.transaction.TransactionPresenter;
import com.phoenix.soft.agenda.transaction.di.TransactionComponent;
import com.phoenix.soft.agenda.transaction.di.TransactionModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yaoda on 10/04/17.
 */
@Singleton
@Component(modules = {AppModule.class,FirebaseModule.class})
public interface AppComponent {
    TransactionComponent plus(TransactionModule transModule);

    void inject(MainApplication application);

    FirebaseAuth getAuth();

    void inject(AuthActivity activity);

    void inject(LoginFragment loginFragment);

    void inject(SignUpFragment signUpFragment);

    @Named("Account")
    DatabaseReference getDataRef();
}
