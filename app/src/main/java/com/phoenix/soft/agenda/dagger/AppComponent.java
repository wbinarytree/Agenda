package com.phoenix.soft.agenda.dagger;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yaoda on 16/03/17.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    //void inject(MainActivity activity);
}
