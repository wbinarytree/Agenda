package com.phoenix.soft.agenda.dagger;

import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.login.AuthActivity;
import com.phoenix.soft.agenda.login.LoginFragment;
import com.phoenix.soft.agenda.repos.FirebaseAccountRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yaoda on 17/03/17.
 */
@Singleton
@Component(modules = {FirebaseModule.class})
public interface FirebaseComponent {
    void inject(FirebaseAccountRepository repository);
    void inject(MainApplication application);
    void inject(AuthActivity activity);
    void inject(LoginFragment loginFragment);
}
