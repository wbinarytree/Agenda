package com.phoenix.soft.agenda.repos.source;

import android.support.annotation.NonNull;
import android.util.ArrayMap;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.RxAccountSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by yaoda on 23/03/17.
 */

public class RxAccountRepository implements RxAccountSource {

    private final RxAccountSource localSource;
    private final RxAccountSource remoteSource;

    private ArrayMap<String, Account> mCachedAccounts;
    private boolean mCacheIsDirty = false;

    public RxAccountRepository(@NonNull RxAccountSource localSource, @NonNull RxAccountSource RemoteSource) {

        this.localSource = localSource;
        this.remoteSource = RemoteSource;
        mCachedAccounts = new ArrayMap<>();
    }

    @Override
    public Single<Account> getAccount(String key) {
        return null;
    }

    @Override
    public Single<List<Account>> getAccountList() {
        if (mCachedAccounts != null && !mCacheIsDirty) {
            return Observable.fromIterable(mCachedAccounts.values()).toList(mCachedAccounts.size());
        } else if (mCachedAccounts == null) {
            mCachedAccounts = new ArrayMap<>();
        }

        return getAndSaveRemoteAccounts();
    }

    private Single<List<Account>> getAndSaveRemoteAccounts() {
        return remoteSource.getAccountList()
                           .flatMap(accountList -> Observable.fromIterable(accountList)
                                                             .doOnNext(account -> {
                                                                 localSource.addAccount(account);
                                                                 mCachedAccounts.put(account.getKey(), account);
                                                             })
                                                             .toList());
    }

    @Override
    public boolean addAccount(@NonNull Account account) {
        localSource.addAccount(account);
        remoteSource.addAccount(account);
        mCachedAccounts.put(account.getKey(),account);
        return true;
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
