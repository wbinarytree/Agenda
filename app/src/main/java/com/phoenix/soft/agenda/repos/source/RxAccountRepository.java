package com.phoenix.soft.agenda.repos.source;

import android.support.annotation.NonNull;
import android.util.ArrayMap;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.RxAccountSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;


/**
 * Created by yaoda on 23/03/17.
 */

public class RxAccountRepository implements RxAccountSource {

    private final RxAccountSource localSource;
    private final RxAccountSource remoteSource;

    private List<Account> mCachedAccounts;
    private boolean mCacheIsDirty = false;

    public RxAccountRepository(@NonNull RxAccountSource localSource, @NonNull RxAccountSource RemoteSource) {

        this.localSource = localSource;
        this.remoteSource = RemoteSource;
    }

    @Override
    public Maybe<Account> getAccount(String key) {
        return null;
    }

    @Override
    public Maybe<List<Account>> getAccountList() {
        if (mCachedAccounts != null && !mCacheIsDirty) {
            return Maybe.just(mCachedAccounts);
        }

        return getAndSaveRemoteAccounts();
    }

    private Maybe<List<Account>> getAndSaveRemoteAccounts() {
        return remoteSource.getAccountList().map(accountList -> {
            mCachedAccounts = accountList;
            return accountList;
        });
    }

    @Override
    public boolean addAccount(@NonNull Account account) {
        //||localSource.addAccount(account)
        return remoteSource.addAccount(account)||mCachedAccounts.add(account);
    }

    @Override
    public boolean deleteAccount(Account account) {
        return false;
    }

    @Override
    public boolean updateAccount(Account account) {
        return false;
    }

    @Override
    public void refreshAccount() {
        mCacheIsDirty = true;
    }


}
