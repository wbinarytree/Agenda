package com.phoenix.soft.agenda.account.di;

import com.phoenix.soft.agenda.MainActivity;
import com.phoenix.soft.agenda.dagger.FirebaseComponent;
import com.phoenix.soft.agenda.dagger.FragmentScope;

import dagger.Component;

/**
 * Created by yaoda on 24/03/17.
 */
@FragmentScope
@Component(modules = {AccountPresenterModule.class},dependencies = {FirebaseComponent.class})
public interface AccountPresenterComponent {
    void inject(MainActivity mainActivity);
}
