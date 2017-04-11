/*
package com.phoenix.soft.costy;

import android.support.test.runner.AndroidJUnit4;

import com.phoenix.soft.costy.account.AccountContract;
import com.phoenix.soft.costy.account.AccountPresenter;
import com.phoenix.soft.costy.repos.source.AccountSourceRT;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

*/
/**
 * Created by yaoda on 11/04/17.
 *//*

@RunWith(AndroidJUnit4.class)
public class AccountPresenterTest {

    private AccountPresenter presenter;
    private AccountSourceRT repo;
    private AccountContract.View view;


    @Before
    public void setUp() throws Exception {
        repo = Mockito.mock(AccountSourceRT.class);
        view = Mockito.mock(AccountContract.View.class);
        presenter = new AccountPresenter(repo);
        presenter.attachView(view);
//        setSchedulerHook();
        Mockito.when(repo.getAccountList()).thenReturn(Observable.just(Collections.emptyList()));
        Mockito.when(repo.getAccountUpdate()).thenReturn(Observable.empty());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
    }

    @Test
    public void loadAccount() throws Exception {
        presenter.loadAccount();

    }

    private void setSchedulerHook() {
        RxJavaPlugins.reset();
        RxJavaPlugins.onIoScheduler(Schedulers.trampoline());
        RxJavaPlugins.onComputationScheduler(Schedulers.trampoline());
        RxJavaPlugins.onNewThreadScheduler(Schedulers.trampoline());
        RxJavaPlugins.onSingleScheduler(Schedulers.trampoline());

        RxAndroidPlugins.reset();
        RxAndroidPlugins.onMainThreadScheduler(Schedulers.trampoline());
    }

}*/
