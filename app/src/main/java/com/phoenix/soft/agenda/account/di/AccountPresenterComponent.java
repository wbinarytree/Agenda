package com.phoenix.soft.agenda.account.di;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.dagger.AppComponent;

import dagger.Component;

/**
 * Created by yaoda on 24/03/17.
 */
@AccountRepoScope
@Component(modules = {AccountPresenterModule.class},dependencies = {AppComponent.class})
public interface AccountPresenterComponent {
    void inject(MainActivity mainActivity);
}
