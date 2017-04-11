package com.phoenix.soft.costy.account.di;

import com.phoenix.soft.costy.MainActivity;
import com.phoenix.soft.costy.dagger.AppComponent;

import dagger.Component;

/**
 * Created by yaoda on 24/03/17.
 */
@AccountRepoScope
@Component(modules = {AccountPresenterModule.class},dependencies = {AppComponent.class})
public interface AccountPresenterComponent {
    void inject(MainActivity mainActivity);
}
