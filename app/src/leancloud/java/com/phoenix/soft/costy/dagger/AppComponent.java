package com.phoenix.soft.costy.dagger;


import com.phoenix.soft.costy.MainApplication;
import com.phoenix.soft.costy.auth.AuthActivity;
import com.phoenix.soft.costy.auth.LoginFragment;
import com.phoenix.soft.costy.auth.SignUpFragment;
import com.phoenix.soft.costy.transaction.di.TransactionComponent;
import com.phoenix.soft.costy.transaction.di.TransactionModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yaoda on 10/04/17.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    TransactionComponent plus(TransactionModule transModule);

    void inject(MainApplication application);

    void inject(AuthActivity activity);

    void inject(LoginFragment loginFragment);

    void inject(SignUpFragment signUpFragment);

}
